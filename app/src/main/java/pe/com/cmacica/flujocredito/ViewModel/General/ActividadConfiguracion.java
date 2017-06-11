package pe.com.cmacica.flujocredito.ViewModel.General;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Sync.Configuracion.ConstanteProgressIntentService;
import pe.com.cmacica.flujocredito.Sync.Configuracion.PersonaProgressIntentService;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.Utilitarios.UService;

public class ActividadConfiguracion extends AppCompatActivity {

    Button btnSyncPersona;
    TextView lblPorSyncPer;
    TextView lblPorSyncCons;
    Button btnSyncConstante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_configuracion);
        btnSyncPersona = (Button) findViewById(R.id.btnSyncPersona);
        btnSyncConstante=(Button)findViewById(R.id.btnSyncConstante);
        lblPorSyncPer = (TextView) findViewById(R.id.lblPorSyncPer);
        lblPorSyncCons=(TextView) findViewById(R.id.lblPorSynCons);
        lblPorSyncPer.setVisibility(View.GONE);
        lblPorSyncCons.setVisibility(View.GONE);
        // Filtro de acciones que serán alertadas
        IntentFilter filter = new IntentFilter(Constantes.ACTION_RUN_ISERVICE);
        filter.addAction(Constantes.ACTION_PROGRESS_EXIT);

        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver = new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

        btnSyncPersona.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnValidaAntesDeSinc(1);
                    }
                });

        btnSyncConstante.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnValidaAntesDeSinc(2);
                    }
                });
    }

    public void OnValidaAntesDeSinc(int NrodeSincronizacion){

        if(UPreferencias.ObtenerIndDesconectado(this).equals("1")){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Ha iniciado sesión en modo desconectado, no puede realizar está operación.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                        }
                    })
                    .show();
        }else {
            if (UService.hayConexion(this)) {

              switch (NrodeSincronizacion) {
                  case 1:
                      OnSyncPersona();
                      break;
                  case 2:
                      OnSyncConstante();
                      break;
              }
            } else {
                Snackbar.make(findViewById(R.id.actividad_configuracion),
                        "No hay conexion disponible. Inténtelo mas tarde",
                        Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private void OnSyncPersona(){
        Intent intent = new Intent(this, PersonaProgressIntentService.class);
        intent.setAction(Constantes.ACTION_RUN_ISERVICE);
        startService(intent);
    }

    private void OnSyncConstante(){
        Intent intent = new Intent(this, ConstanteProgressIntentService.class);
        intent.setAction(Constantes.ACTION_RUN_ISERVICE);
        startService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Broadcast receiver que recibe las emisiones desde los servicios
    private class ResponseReceiver extends BroadcastReceiver {

        // Sin instancias
        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {

                case Constantes.ACTION_RUN_ISERVICE:
                    //Region Constantes-EstadoCivil
                    lblPorSyncCons.setVisibility(View.VISIBLE);
                    lblPorSyncCons.setText("100");
                    btnSyncConstante.setEnabled(false);
                    lblPorSyncCons.setText(intent.getIntExtra(Constantes.EXTRA_PROGRESS, -1) + "");
                  /*Log.d("sync","ACTION_RUN_ISERVICE");lblPorSyncPer.setVisibility(View.VISIBLE);
                  lblPorSyncPer.setText("100");
                  btnSyncPersona.setEnabled(false);
                   lblPorSyncPer.setText(intent.getIntExtra(Constantes.EXTRA_PROGRESS, -1) + "");
*/


                    //btnSyncPersona.setBackground(R.color.colorAccent);
                    break;

                case Constantes.ACTION_PROGRESS_EXIT:
                    Log.d("sync","ACTION_PROGRESS_EXIT");
                  btnSyncPersona.setEnabled(true);
                  lblPorSyncPer.setVisibility(View.GONE);

                    //Constantes-EstadoCivil
                    btnSyncConstante.setEnabled(true);
                    lblPorSyncCons.setVisibility(View.GONE);
                    break;
            }
        }
    }


}
