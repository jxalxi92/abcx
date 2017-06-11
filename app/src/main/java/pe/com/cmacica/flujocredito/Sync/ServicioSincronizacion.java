package pe.com.cmacica.flujocredito.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Created by jhcc on 03/08/2016.
 */
public class ServicioSincronizacion extends Service {
    // Instancia del sincronizar adapter
    private static SyncAdapter syncAdapter = null;
    // Objeto para prevenir errores entre hilos
    private static final Object lock = new Object();


    @Override
    public void onCreate() {
        synchronized (lock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Retorna interfaz de comunicación para uso interno del framework
     */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}

