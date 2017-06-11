package pe.com.cmacica.flujocredito.AgenteServicio;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import pe.com.cmacica.flujocredito.Utilitarios.Constantes;

/**
 * Created by jhcc on 17/06/2016.
 */
public final class VolleySingleton {
    // Atributos
    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new VolleySingleton(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Obtiene la instancia de la cola de peticiones
     *
     * @return cola de peticiones
     */

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Añade la petición a la cola
     *
     * @param req petición
     * @param <T> Resultado final de tipo T
     */

    public <T> void addToRequestQueue(Request<T> req) {

        RetryPolicy policy = new DefaultRetryPolicy(Constantes.TimeOutService
                ,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

}

