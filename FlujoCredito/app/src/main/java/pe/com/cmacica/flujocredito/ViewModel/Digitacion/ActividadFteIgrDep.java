package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Base.IActualizaPersonaFteIngreso;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.ViewModel.PlanPago.fragmentoSimuladorCredito;

public class ActividadFteIgrDep extends AppCompatActivity implements
        FragmentoDetGastoFteIngDep.OnMontoGasto,
        DatePickerDialog.OnDateSetListener,IActualizaPersonaFteIngreso {


    static DigitacionDto DatosBaseCliente;


    public ActividadFteIgrDep (){

    }

    public static void launch(Activity actividad, DigitacionDto DatosCliente){

        Intent intent = createInstance(actividad,DatosCliente );
        actividad.startActivityForResult(intent, 100 );
    }
    public static Intent createInstance(Context contexto, DigitacionDto DatosCliente){
        Intent intent = new Intent(contexto, ActividadFteIgrDep.class);
        intent.putExtra(Constantes.KeyIdPersona,DatosCliente.CodigoPersona);
        DatosBaseCliente = DatosCliente;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_fte_igr_dependiente);
        setTitle("Dependiente");
        agregarToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager
                .beginTransaction()
                .replace(R.id.contenedor_principal, FragmentoFteIngreso.createInstance(Constantes.CodigoFteDependiente,DatosBaseCliente),"FragmentoFteIngreso" )
                .commit();

    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.accion_confirmar:
                //OnAddStuden();
                OnGuardarLocal();
                /*Snackbar.make(findViewById(R.id.actividadFteIgrDep),
                        "Agregado correctamente",
                        Snackbar.LENGTH_LONG).show();*/
                break;
            case R.id.accion_eliminar:
                /*Snackbar.make(findViewById(R.id.actividadFteIgrDep),
                        "Registro eliminado",
                        Snackbar.LENGTH_LONG).show();*/
                //eliminar();
                break;
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_fte_ingreso, menu);

        // Verificación de visibilidad acción eliminar
        // if (uriContacto != null) {
        menu.findItem(R.id.accion_eliminar).setVisible(true);
        //}

        return true;
    }
    @Override
    public void OnGastoAceptar(double monto) {


        //FragmentoIgrEgrSolesDep detGasto = (FragmentoIgrEgrSolesDep) getSupportFragmentManager().findFragmentByTag(FragmentoIgrEgrSolesDep.class.getSimpleName());
        FragmentoIgrEgrSolesDep detGasto = null;
        for (int i=0;i<4;i++){

            if (getSupportFragmentManager().getFragments().get(i).toString().substring(0,23).equals("FragmentoIgrEgrSolesDep")){
                detGasto = (FragmentoIgrEgrSolesDep) getSupportFragmentManager().getFragments().get(i);
            }
        }
        //detGasto  = (FragmentoIgrEgrSolesDep) getSupportFragmentManager().getFragments().equals()
        if (detGasto != null){
            detGasto.OnActualizarMontoEgreso(monto);
        }else{
            detGasto.OnActualizarMontoEgreso(1200);
        }
    }

    private void OnGuardarLocal(){

        Fragment fragmento = null;
        for( Fragment fragsel: getSupportFragmentManager().getFragments()){
            if(fragsel!= null && fragsel.getUserVisibleHint() && fragsel.getTag() == null ){
                fragmento =fragsel;
                break;
            }
        }
        if(fragmento!= null){
            String nombreFra= fragmento.toString().substring(0,20);
            switch (nombreFra){
                case "FragmentoIgrEgrSoles":
                    FragmentoIgrEgrSolesDep frafigreso = (FragmentoIgrEgrSolesDep) fragmento;
                    frafigreso.OnGuardar();
                    break;
                case "FragmentoComentarioF":
                    FragmentoComentarioFteIngreso frafcomentario = (FragmentoComentarioFteIngreso) fragmento;
                    frafcomentario.OnGuardar();
                    break;
                case "FragmentoDatosClient":
                    FragmentoDatosClienteFteIgrDep fragDatPer = (FragmentoDatosClienteFteIgrDep) fragmento;
                    fragDatPer.OnGuardar();
                    break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Fragment fragmento = null;
        for( Fragment fragsel: getSupportFragmentManager().getFragments()){
            if(fragsel!= null && fragsel.getUserVisibleHint() && fragsel.getTag() == null ){
                fragmento =fragsel;
                break;
            }
        }

        if(fragmento!= null){
            String nombreFra= fragmento.toString().substring(0,20);
            switch (nombreFra){

                case "FragmentoDatosClient":
                    FragmentoDatosClienteFteIgrDep fragDatPer = (FragmentoDatosClienteFteIgrDep) fragmento;
                    fragDatPer.actualizarFecha(year, monthOfYear, dayOfMonth);
                    break;
            }
        }
    }

    @Override
    public void OnActualizaPersona(PersonaDto PersonaSel, int TipoFte) {
        if(TipoFte == 1) {
            Fragment fragmento = null;
            for (Fragment fragsel : getSupportFragmentManager().getFragments()) {
                if (fragsel != null && fragsel.getUserVisibleHint() && fragsel.getTag() == null) {
                    fragmento = fragsel;
                    break;
                }
            }

            if (fragmento != null) {
                String nombreFra = fragmento.toString().substring(0, 20);
                switch (nombreFra) {

                    case "FragmentoDatosClient":
                        FragmentoDatosClienteFteIgrDep fragDatPer = (FragmentoDatosClienteFteIgrDep) fragmento;
                        fragDatPer.ActualizaRazonsocial(PersonaSel);
                        break;
                }
            }
        }
    }
}
