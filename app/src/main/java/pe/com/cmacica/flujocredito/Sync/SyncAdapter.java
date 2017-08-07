package pe.com.cmacica.flujocredito.Sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Model.ResultOpe;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Sync.Procesador.ProcLocal;
import pe.com.cmacica.flujocredito.Sync.Procesador.ProcRemoto;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

/**
 * Created by jhcc on 03/08/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = SyncAdapter.class.getSimpleName();

    // Extras para intent local
    public static final String EXTRA_RESULTADO = "extra.resultado";
    private static final String EXTRA_MENSAJE = "extra.mensaje";

    private static final int ESTADO_PETICION_FALLIDA = 107;
    private static final int ESTADO_TIEMPO_ESPERA = 108;
    private static final int ESTADO_ERROR_PARSING = 109;
    private static final int ESTADO_ERROR_SERVIDOR = 110;


    private ContentResolver cr;
    private Gson gson = new Gson();
    private ProcRemoto procRemoto = new ProcRemoto();
    public Context contexto;

    public SyncAdapter(Context context, boolean autoInitialize) {

        super(context, autoInitialize);
        Log.d(TAG, "constructor sync");
        cr = context.getContentResolver();
        contexto = context;
    }

    private void enviarBroadcast(boolean estado, String mensaje) {

        Log.d(TAG, "enviarBroadcast sync");
        Intent intentLocal = new Intent(Intent.ACTION_SYNC);
        intentLocal.putExtra(EXTRA_RESULTADO, estado);
        intentLocal.putExtra(EXTRA_MENSAJE, mensaje);
        LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentLocal);
    }
    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        cr = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "Comenzando a sincronizar:" + account);

        syncRemota();
    }

    private void syncLocal() {
        // Construcción de cabeceras
        Log.d(TAG, "SyncLocal");
        HashMap<String, String> cabeceras = new HashMap<>();
        //cabeceras.put("Authorization", UPreferencias.obtenerClaveApi(getContext()));

        // Petición GET
        new RESTService(getContext()).get(
                String.format( SrvCmacIca.URL_SYNC_BATCH_FTEIGR_REQUEST ,
                        UPreferencias.ObtenerCodigoPersonaLogeo(contexto)
                        ,UPreferencias.ObtenerImei(contexto), UGeneral.obtenerTiempoCorto()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar GET
                        tratarGet(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Procesar Error
                        tratarErrores(error);
                    }
                }, cabeceras);
    }

    private void tratarGet(JSONObject respuesta) {

        try {

            if(respuesta.getBoolean("IsCorrect")){
                //eliminando los registros
                cr.delete(ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,null,null);
                cr.delete(ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO,null,null);
                cr.delete(ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,null,null);
                cr.delete(ContratoDbCmacIca.PersFteIngresoTable.URI_CONTENIDO,null,null);
                cr.delete(ContratoDbCmacIca.DigitacionTable.URI_CONTENIDO,null,null);

                // Crear referencia de lista de operaciones
                ArrayList<ContentProviderOperation> ops = new ArrayList<>();
                // Convertir array JSON de descuentos a modelo
                ProcLocal manejadorDigitacion = new ProcLocal();
                if(respuesta.getJSONArray("Data").length()!=0) {
                    manejadorDigitacion.procesar(respuesta.getJSONArray("Data"));
                    manejadorDigitacion.procesarOperaciones(ops, cr);

                    // ¿ Hay operaciones por realizar ?
                    if (ops.size() > 0) {
                        Log.d(TAG, "# Cambios en \'contacto\': " + ops.size() + " ops.");
                        // Aplicar batch de operaciones
                        cr.applyBatch(ContratoDbCmacIca.AUTORIDAD, ops);
                        // Notificar cambio al content provider
                        cr.notifyChange(ContratoDbCmacIca.DigitacionTable.URI_CONTENIDO, null, false);
                    } else {
                        Log.d(TAG, "Sin cambios remotos");
                    }
                }
                /*else{
                    enviarBroadcast(true, "Sincronización completa");
                }*/
                Log.d(TAG, "enviarBroadcast");
                enviarBroadcast(true, "Sincronización completa");
            }else{
                enviarBroadcast(false,respuesta.getString("Message"));
            }
            // Sincronización remota
            //syncRemota();

        } catch (RemoteException | OperationApplicationException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void syncRemota() {

        Log.d(TAG, "syncRemota");
        procRemoto = new ProcRemoto();
        // Construir payload con todas las operaciones remotas pendientes

        String datos = procRemoto.crearPayload(cr,
                UPreferencias.ObtenerImei(contexto)
        );

        if (datos != null) {
            Log.d(TAG, "Payload de contactos:" + datos);

            HashMap<String, String> cabeceras = new HashMap<>();

            new RESTService(getContext()).post(SrvCmacIca.URL_SYNC_BATCH_FTEIGR_RESPONSE, datos,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                tratarPost(response);
                            } catch (JSONException e) {
                                enviarBroadcast(false, "Error al sincronizar");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tratarErrores(error);
                        }
                    }
                    , cabeceras);
        } else {
            Log.d(TAG, "Sin cambios locales");
            enviarBroadcast(true, "Sincronización completa");
        }
    }

    private void tratarPost(JSONObject response) throws JSONException {
        // Desmarcar inserciones locales
        //ProcRemoto.desmarcarContactos(cr);
        if (response.getBoolean("IsCorrect")== true){
            syncLocal();
        }else{
            enviarBroadcast(false, response.getString("Message"));
        }
        //enviarBroadcast(true, "Sincronización completa post");

    }
    private void tratarErrores(VolleyError error) {
        // Crear respuesta de error por defecto
        ResultOpe respuesta = new ResultOpe(ESTADO_PETICION_FALLIDA,
                "Petición incorrecta");

        // Verificación: ¿La respuesta tiene contenido interpretable?
        if (error.networkResponse != null) {

            String s = new String(error.networkResponse.data);
            try {
                respuesta = gson.fromJson(s, ResultOpe.class);
            } catch (JsonSyntaxException e) {
                Log.d(TAG, "Error de parsing: " + s);
            }

        }

        if (error instanceof NetworkError) {
            respuesta = new ResultOpe(ESTADO_TIEMPO_ESPERA
                    , "Error en la conexión. Intentalo de nuevo");
        }

        // Error de espera al servidor
        if (error instanceof TimeoutError) {
            respuesta = new ResultOpe(ESTADO_TIEMPO_ESPERA, "Error de espera del servidor");
        }

        // Error de parsing
        if (error instanceof ParseError) {
            respuesta = new ResultOpe(ESTADO_ERROR_PARSING, "La respuesta no es formato JSON");
        }

        // Error conexión servidor
        if (error instanceof ServerError) {
            respuesta = new ResultOpe(ESTADO_ERROR_SERVIDOR, "Error en el servidor");
        }

        if (error instanceof NoConnectionError) {
            respuesta = new ResultOpe(ESTADO_ERROR_SERVIDOR
                    , "Servidor no disponible, prueba mas tarde");
        }

        Log.d(TAG, "Error Respuesta:" + (respuesta != null ? respuesta.toString() : "()")
                + "\nDetalles:" + error.getMessage());

        enviarBroadcast(false, respuesta.getMensaje());

    }
}
