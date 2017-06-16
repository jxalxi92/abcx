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


/**
 * Created by jccu on 26/05/2017.
 */

public class ConstanteProgressIntentService extends IntentService
{
    private static final String TAG = ConstanteProgressIntentService.class.getSimpleName();

    NotificationCompat.Builder builder;

    int tiempo;
    public ConstanteProgressIntentService() {
        super("ConstanteProgressIntentService");
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
                    .setContentTitle("Sincronizando Constante")
                    .setContentText("Espere por favor...");

            builder.setProgress(100, 0, false);
            startForeground(1, builder.build());

            OnListarConstantes();
            tiempo = 900000;

            Thread.sleep(tiempo);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void OnListarConstantes(){
        HashMap<String, String> cabeceras = new HashMap<>();

        // Petición GET
        new RESTService(this).get( String.format(SrvCmacIca.URL_SYNC_BATCHConstante),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar GET
                        Log.d(TAG, "retorno del servicio.");
                        ProcesarConstante(response);
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
    private void ProcesarConstante(JSONObject response){
        try {

            if(response.getBoolean("IsCorrect")){

                ContentResolver resolver =getContentResolver();
                JSONArray data = response.getJSONArray("Data");
                resolver.delete(ContratoDbCmacIca.ConstanteTable.URI_CONTENIDO,null,null);
                int longitud=data.length();
                Log.d(TAG, "Longitud de Constantes: "+ longitud);
                int Progreso,Progreso2=0;
                for (int i=0;i< longitud; i++ ){
                    Progreso = Math.round ((100*i)/longitud);
                    ContentValues valores = new ContentValues();
                    JSONObject ConsSel;
                    ConsSel= (JSONObject) data.get(i);

                    valores.put(ContratoDbCmacIca.ConstanteTable.nConsCod ,ConsSel.getString("CodigoConstante"));
                    valores.put(ContratoDbCmacIca.ConstanteTable.nConsValor ,ConsSel.getString("CodigoValor"));
                    valores.put(ContratoDbCmacIca.ConstanteTable.cConsDescripcion ,ConsSel.getString("Descripcion"));
                    valores.put(ContratoDbCmacIca.ConstanteTable.nConsEquivalente ,ConsSel.getString("Equivalente"));

                    resolver.insert(ContratoDbCmacIca.ConstanteTable.URI_CONTENIDO, valores);
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

        Toast.makeText(this, "Sincronización Completa de Cosntantes", Toast.LENGTH_SHORT).show();
        // Emisión para avisar que se terminó el servicio
        Intent localIntent = new Intent(Constantes.ACTION_PROGRESS_EXIT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        Log.d(TAG, "Sincronización Completa.");
    }
}
