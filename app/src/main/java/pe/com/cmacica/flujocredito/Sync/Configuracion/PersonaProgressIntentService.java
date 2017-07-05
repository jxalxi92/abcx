package pe.com.cmacica.flujocredito.Sync.Configuracion;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;


/**
 * Created by Jhcc on 12/10/2016.
 */

public class PersonaProgressIntentService extends IntentService {

    private static final String TAG = PersonaProgressIntentService.class.getSimpleName();

    NotificationCompat.Builder builder;

    int tiempo;
    public PersonaProgressIntentService() {
        super("PersonaProgressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constantes.ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }

    protected void handleActionRun() {
        try {
            // Se construye la notificación
             builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentTitle("Sincronizando Persona")
                    .setContentText("Espere por favor...");

            builder.setProgress(100, 0, false);
            startForeground(1, builder.build());

            OnListarPersonas();
            tiempo = 900000;

            Thread.sleep(tiempo);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //region guardar Persona
    private void OnListarPersonas(){
        HashMap<String, String> cabeceras = new HashMap<>();

        // Petición GET
        new RESTService(this).get( String.format(SrvCmacIca.URL_SYNC_BATCHPersona, "2", UPreferencias.ObtenerImei(this)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar GET
                        Log.d(TAG, "retorno del servicio.");
                        ProcesarPersonas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Procesar Error
                        //tratarErrores(error);
                    }
                }, cabeceras);
    }

    private void ProcesarPersonas(JSONObject response){
        try {

            if(response.getBoolean("IsCorrect")){

                ContentResolver resolver =getContentResolver();
                JSONArray data = response.getJSONArray("Data");
                resolver.delete(ContratoDbCmacIca.PersonaTable.URI_CONTENIDO,null,null);
                int longitud=data.length();
                Log.d(TAG, "Longitud de personas: "+ longitud);
                int Progreso,Progreso2=0;
                for (int i=0;i< longitud; i++ ){
                    Progreso = Math.round ((100*i)/longitud);
                    ContentValues valores = new ContentValues();
                    JSONObject PerSel;
                    PerSel= (JSONObject) data.get(i);
                    valores.put(ContratoDbCmacIca.PersonaTable.IdPersona, ContratoDbCmacIca.PersonaTable.generarIdPersona());
                    valores.put(ContratoDbCmacIca.PersonaTable.cPersCod ,PerSel.getString("cPersCod"));
                    valores.put(ContratoDbCmacIca.PersonaTable.cPersNombre ,PerSel.getString("cPersNombre"));
                    valores.put(ContratoDbCmacIca.PersonaTable.cDoi ,PerSel.getString("cDOI"));

                    resolver.insert(ContratoDbCmacIca.PersonaTable.URI_CONTENIDO, valores);
                    // Poner en primer plano
                    if(Progreso != Progreso2){

                        Progreso2 = Progreso;
                        builder.setProgress(100, Progreso2, false);
                        builder.setContentText("Procesando...  " +Progreso2 + "%" );
                        startForeground(1, builder.build());


                        Intent localIntent = new Intent(Constantes.ACTION_RUN_ISERVICE)
                                .putExtra(Constantes.EXTRA_PROGRESS, i);

                        // Emisión de {@code localIntent}
                        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                    }

                }
                // Quitar de primer plano
                stopForeground(true);
                tiempo = 0;
                Intent localIntent = new Intent(Constantes.ACTION_PROGRESS_EXIT);
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                this.onDestroy();

            }else{
                // Quitar de primer plano
                Log.d(TAG, "retorno del servicio2.");

                Toast.makeText(this, response.getString("Message"), Toast.LENGTH_SHORT).show();
                stopForeground(true);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "Sincronización Completo.", Toast.LENGTH_SHORT).show();
        // Emisión para avisar que se terminó el servicio
        Intent localIntent = new Intent(Constantes.ACTION_PROGRESS_EXIT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        Log.d(TAG, "Sincronización Completo.");
    }
}
