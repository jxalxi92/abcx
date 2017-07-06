package pe.com.cmacica.flujocredito.ViewModel;

import android.Manifest;
import android.accounts.Account;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.Utilitarios.USeguridad;
import pe.com.cmacica.flujocredito.ViewModel.Cobranza.FragmentoListaCobranza;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.ActividadFteIgrDep;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.ActividadFteIgrIndep;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.FragmentoPersonaFteIgrDet;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.FragmentoPersonaFteIngreso;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.fragmentoInicio;
import pe.com.cmacica.flujocredito.ViewModel.General.ActividadConfiguracion;
import pe.com.cmacica.flujocredito.ViewModel.General.fragmento_consultar_datos;
import pe.com.cmacica.flujocredito.ViewModel.PlanPago.fragmentoSimuladorCredito;
import pe.com.cmacica.flujocredito.ViewModel.Recuperaciones.fragmentoListaRecuperaciones;
import pe.com.cmacica.flujocredito.ViewModel.Solicitud.FragmentoListaSolCred;


public class ActividadPrincipal extends AppCompatActivity implements
        FragmentoPersonaFteIgrDet.IOperacionTipoFuente,
        DatePickerDialog.OnDateSetListener,
        FragmentoPersonaFteIngreso.ListenerFteIgreso {

    private static final String TAG = ActividadPrincipal.class.getSimpleName();
    private TextView lblUserName;
    private TextView lblemail;

    private DrawerLayout drawerLayout;

    private BroadcastReceiver receptorSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        agregarToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        receptorSync = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.i(TAG, "Comenzando a sincronizar: 2" );
                String mensaje = intent.getStringExtra("extra.mensaje");
                OnEnviarBroadcast(mensaje);
            }
        };

        View header = navigationView.getHeaderView(0);
        lblUserName = (TextView) header.findViewById(R.id.username);
        lblemail = (TextView) header.findViewById(R.id.email);
        //String user = UPreferencias.ObtenerUserLogeo(this);
        lblUserName.setText( UPreferencias.ObtenerNombreCompleto(this));
        lblemail.setText( UPreferencias.ObtenerAgenciaLogeo(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar receptor
        IntentFilter filtroSync = new IntentFilter(Intent.ACTION_SYNC);
        LocalBroadcastManager.getInstance(this).registerReceiver(receptorSync, filtroSync);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptorSync);
    }
    private void agregarToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        String TagGrament = "";

        switch (itemDrawer.getItemId()) {
            case R.id.nav_home:
                fragmentoGenerico = new fragmentoInicio();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.nav_calendario:
                fragmentoGenerico = new fragmentoSimuladorCredito();
                TagGrament = fragmentoSimuladorCredito.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.navFuenteIngreso:
                fragmentoGenerico = new FragmentoPersonaFteIngreso(); // FragmentoFteIngreso();
                TagGrament = FragmentoPersonaFteIngreso.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.nav_SolCred:
                fragmentoGenerico = new FragmentoListaSolCred(); // Fragmento Solicitud;
                TagGrament = FragmentoListaSolCred.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.nav_GestCob:
                fragmentoGenerico = new FragmentoListaCobranza(); // Fragmento Cobranza;
                TagGrament = FragmentoListaCobranza.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;

            case R.id.nav_RegistroPersona:
                fragmentoGenerico = new fragmento_consultar_datos(); // FragmentoRegistroPersona();
                TagGrament = fragmento_consultar_datos.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;
        /*    case R.id.nav_Recuperaciones:
                fragmentoGenerico = new fragmentoListaRecuperaciones(); //
                TagGrament = fragmentoListaRecuperaciones.class.getSimpleName();
                // Setear título actual
                setTitle(itemDrawer.getTitle());
                break;*/

            case R.id.item_Ajustes:
                this.startActivityForResult(
                        new Intent(this, ActividadConfiguracion.class),3);
                break;
            case R.id.nav_log_out:

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir")
                        .setMessage("¿Está seguro?")
                        .setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Salir
                                ActividadPrincipal.this.finish();
                            }
                        })
                        .show();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico, TagGrament)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        fragmentoSimuladorCredito simulador = (fragmentoSimuladorCredito) getSupportFragmentManager().findFragmentByTag(fragmentoSimuladorCredito.class.getSimpleName());
        if (simulador != null) {

            simulador.actualizarFecha(year, monthOfYear, dayOfMonth);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setMessage("¿Está seguro?")
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            ActividadPrincipal.this.finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

    private void sincroizar(){

        Account cuentaActiva = USeguridad.obtenerCuentaActiva(this);

        if (ContentResolver.isSyncActive(cuentaActiva,ContratoDbCmacIca.AUTORIDAD)){
            Log.d(TAG, "Ignorando sincronización ya que existe una en proceso.");
            return;
        }
        //eliminamos los registros
        Log.d(TAG, "Solicitando sincronización manual");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(cuentaActiva, ContratoDbCmacIca.AUTORIDAD, bundle);
    }


    @Override
    public void OnTipoFuenteOperacion(int operacion,  DigitacionDto DatosCliente) {
        Intent intent;
        switch (operacion) {
            case 1:

                String Telefono="";
                if ( DatosCliente.cPersTelefono.equals("")){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("No cuenta con número teléfono.")
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    return;
                                }
                            })
                            .show();

                    return;
                }
                Telefono = DatosCliente.cPersTelefono;
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+Telefono));
                //Verificando si existe  tiene permiso para realizar llamadas
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                this.startActivity(intent);
                break;
            case 2:

                String Telefono2="";
                if (DatosCliente.cPersTelefono2.equals("")){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("No cuenta con número celular.")
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    return;
                                }
                            })
                            .show();

                    return;
                }
                Telefono2 = DatosCliente.cPersTelefono2;
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+Telefono2));
                //Verificando si existe  tiene permiso para realizar llamadas
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                this.startActivity(intent);
                break;

            case 3:
                ActividadFteIgrDep.launch(this,DatosCliente);
                break;
            case 4:
                ActividadFteIgrIndep.launch(this,DatosCliente);
                break;
        }
    }

    @Override
    public void OnSyncronizar() {
        sincroizar();
    }

    private void OnEnviarBroadcast(String mensaje){
        try{
            FragmentoPersonaFteIngreso obj = null;
            obj = (FragmentoPersonaFteIngreso) getSupportFragmentManager().findFragmentByTag(FragmentoPersonaFteIngreso.class.getSimpleName());
            if (obj != null){
                obj.ResultadoSincronizar(mensaje);
            }

        }catch (Exception e){
            Log.d("Enviar Brod 1",e.getMessage());
        }

    }


}
