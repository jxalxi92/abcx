package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pe.com.cmacica.flujocredito.Base.ActualizaMontoFteIgrIndp;
import pe.com.cmacica.flujocredito.Base.IActualizaPersonaFteIngreso;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;

public class ActividadFteIgrIndep extends AppCompatActivity implements ActualizaMontoFteIgrIndp
        ,IActualizaPersonaFteIngreso {

    static DigitacionDto DatosBaseCliente;

    public static void launch(Activity actividad, DigitacionDto DatosCliente){
        Intent intent = createInstance(actividad,DatosCliente );

        actividad.startActivityForResult(intent, 100 );
    }
    public static Intent createInstance(Context contexto, DigitacionDto DatosCliente){
        Intent intent = new Intent(contexto, ActividadFteIgrIndep.class);
        intent.putExtra(Constantes.KeyIdPersona,DatosCliente.CodigoPersona);
        DatosBaseCliente = DatosCliente;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_fte_igr_indep);

        setTitle("Independiente");
        agregarToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager
                .beginTransaction()
                .replace(R.id.contenedor_principal, FragmentoFteIngreso.createInstance(Constantes.CodigoFteIndependiente,
                        DatosBaseCliente),"FragmentoFteIngreso" )
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.accion_confirmar:
                OnGuardarLocal();
                //insertar();
                /*Snackbar.make(findViewById(R.id.actividadFteIgrIndep),
                        "Agregado correctamente",
                        Snackbar.LENGTH_LONG).show();*/
                break;
            case R.id.accion_eliminar:
                /*Snackbar.make(findViewById(R.id.actividadFteIgrIndep),
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


    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void OnActualizaMonto(double monto, int Tipo) {

        try{

            FragmentoBalanceFteIgr obj = null;

            for (int i=0;i<getSupportFragmentManager().getFragments().size();i++){

                if (getSupportFragmentManager().getFragments().get(i).toString().substring(0,22).equals("FragmentoBalanceFteIgr")){
                    obj = (FragmentoBalanceFteIgr) getSupportFragmentManager().getFragments().get(i);
                }
            }

            if (obj != null){
                obj.ActualizaMonto(monto,Tipo);
            }


        }catch (Exception e){

        }

    }

    private void OnGuardarLocal2(){
        FragmentoBalanceFteIgr detGasto = null;
        for (int i=0;i<4;i++){
            //  for (int i=0;i<getSupportFragmentManager().getFragments().size() -1;i++){
            if (getSupportFragmentManager().getFragments().get(i).toString().substring(0,22).equals("FragmentoBalanceFteIgr")){
                detGasto = (FragmentoBalanceFteIgr) getSupportFragmentManager().getFragments().get(i);
            }
        }
        //detGasto  = (FragmentoIgrEgrSolesDep) getSupportFragmentManager().getFragments().equals()
        if (detGasto != null){
            detGasto.OnGuardar();
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
                case "FragmentoBalanceFteI":
                    FragmentoBalanceFteIgr frafigreso = (FragmentoBalanceFteIgr) fragmento;
                    frafigreso.OnGuardar();
                    break;
                case "FragmentoComentarioF":
                    FragmentoComentarioFteIndep frafcomentario = (FragmentoComentarioFteIndep) fragmento;
                    frafcomentario.OnGuardar();
                    break;
                case "FragmentoDatosClient":
                    FragmentoDatosClienteFteIgrIndp fragDatPer = (FragmentoDatosClienteFteIgrIndp) fragmento;
                    fragDatPer.OnGuardar();
                    break;
            }
        }


    }

    @Override
    public void OnActualizaPersona(PersonaDto PersonaSel, int TipoFte) {
        if(TipoFte==2) {
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
                        FragmentoDatosClienteFteIgrIndp fragDatPer = (FragmentoDatosClienteFteIgrIndp) fragmento;
                        fragDatPer.ActualizaRazonsocial(PersonaSel);
                        break;
                }
            }
        }
    }
}


