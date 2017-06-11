package pe.com.cmacica.flujocredito.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by jhcc on 03/08/2016.
 */
public class ServicioAutenticacion extends Service {
    // Instancia del autenticador
    private Autenticador autenticador;

    @Override
    public void onCreate() {
        // Nueva instancia del autenticador
        autenticador = new Autenticador(this);
    }

    /*
     * Ligando el servicio al framework de Android
     */
    @Override
    public IBinder onBind(Intent intent) {
        return autenticador.getIBinder();
    }
}

