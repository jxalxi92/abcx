package pe.com.cmacica.flujocredito.ViewModel.General;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaModel;
import pe.com.cmacica.flujocredito.R;

import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

public class fragmento_consultar_datos extends Fragment {

    private static final String TAG = fragmento_consultar_datos.class.getSimpleName();
    private EditText TxtDni;
    private View view;
    private Button btnBuscar;
    private Button btnNuevo;
    private EditText txtDniR,txtPersona,txtDirecion,txtReferencia,txtTelefono,txtEmail,txtOcupacion,txtnrohijos,
            txtEstadoCivil,TxtGradoInstruccion;

    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog ;
    PersonaModel per=new PersonaModel();
    private ConstanteModel EstadoSel;
    private ConstanteModel GradoSel;
    private FloatingActionButton fabGuardar;

    public fragmento_consultar_datos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_consultar_datos,container,false);

//ASIGNACION DE CONTROLES--------------------------------------------------------------------------
        AsignarControles();


//VALIDACIONES-------------------------------------------------------------------------------------
        txtDniR.setInputType(InputType.TYPE_NULL);
        txtPersona.setInputType(InputType.TYPE_NULL);
        txtDniR.setFocusable(false);
        txtPersona.setFocusable(false);
        txtEstadoCivil.setFocusable(false);
        TxtGradoInstruccion.setFocusable(false);
        fabGuardar.setEnabled(false);



//ACCIONES DE CONTROLES---------------------------------------------------------------------------


        btnBuscar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CargarDatos();
                    }
                }
        );
        btnNuevo.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Inicializar();
                    }
                });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String Direccion=txtDirecion.getText().toString();
               String Referencia=txtReferencia.getText().toString();
               String Telefono=txtTelefono.getText().toString();
               String Email=txtEmail.getText().toString();
               String Ocupacion=txtOcupacion.getText().toString();
               String NroHijos=txtnrohijos.getText().toString();

                if (EstadoSel==null || GradoSel==null)
                {
                    Snackbar.make(view, "Sincronize Datos en Mennu->Ajustes->Constantes->Sincronizar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (Direccion.equals("") || Referencia.equals("") || Telefono.equals("") || Email.equals("") ||
                        Ocupacion.equals("") || NroHijos.equals("") )
                {
                    Snackbar.make(view, "No deje Campos Vacíos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else {
                            OnGuardar(per);
                            Inicializar();
                }
            }
        });



        return view;
    }

//METODOS-------------------------------------------------------------------------------------------

    private void Inicializar(){

        TxtDni.setText("");
        txtDniR.setText("");
        txtPersona.setText("");
        txtDirecion.setText("");
        txtReferencia.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtOcupacion.setText("");
        txtnrohijos.setText("");
    }

    private void CargarDatos(){

        String dni = TxtDni.getText().toString();
        if(dni.length()!=8){
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Ingrese un DNI correcto.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                        }
                    })
                    .show();
            return;
        }
        else
        {
            OnBuscarPersona(dni);
        }
    }

    private void AsignarControles(){

    TxtDni = (EditText) view.findViewById(R.id.TxtDni);
    btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
    btnNuevo = (Button) view.findViewById(R.id.btnNuevo);
    txtDniR= (EditText) view.findViewById(R.id.txt_dni_r);
    txtPersona=(EditText) view.findViewById(R.id.txt_persona);
    txtDirecion=(EditText) view.findViewById(R.id.txt_direccion);
    txtReferencia=(EditText) view.findViewById(R.id.txt_referencia);
    txtTelefono=(EditText) view.findViewById(R.id.txt_telefono);
    txtEmail=(EditText) view.findViewById(R.id.txt_email);
    txtOcupacion=(EditText) view.findViewById(R.id.txt_ocupacion);
    txtnrohijos=(EditText) view.findViewById(R.id.txt_nrohijos);
    txtEstadoCivil=(EditText) view.findViewById(R.id.txtEstadoCivil);
    TxtGradoInstruccion=(EditText) view.findViewById(R.id.TxtGradoInstruccion);


    fabGuardar=(FloatingActionButton)view.findViewById(R.id.fab_guardar);
}

    private void OnBuscarPersona(String Dni){

        String url = String.format(SrvCmacIca.GET_OBTENERPERSONA,Dni);
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //Procesar la respuesta Json
                                        ProcesarPersona(response);                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.cancel();
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }
                        )
                );
    }

    private void ProcesarPersona(JSONObject response) {
        try {
             if (response.getBoolean("IsCorrect")){

                 JSONObject js;
                 new JSONObject();
                 js= response.getJSONObject("Data");
                 txtDniR.setText(js.getString("numDoc"));
                 txtPersona.setText(js.getString("apellidoPaterno")+" "+js.getString("apellidoMaterno")+" "+js.getString("nombres"));
                 txtDirecion.setText(js.getString("direccion"));
                 txtEstadoCivil.setText(js.getString("estadoCivilDes"));
                 TxtGradoInstruccion.setText(js.getString("gradoInstruccionDes"));

                 per.usuario= UPreferencias.ObtenerUserLogeo(getActivity());
                 per.numDoc=js.getString("numDoc");
                 per.apellidoPaterno=js.getString("apellidoPaterno");
                 per.apellidoMaterno=js.getString("apellidoMaterno");
                 per.nombres=js.getString("nombres");
                 per.docSustentTipDes=js.getString("docSustentTipDes");
                 per.nacDptoCod=js.getString("nacDptoCod");
                 per.nacProvCod=js.getString("nacProvCod");
                 per.nacDistCod=js.getString("nacDistCod");
                 per.domicDistDes=js.getString("domicDistDes");
                 per.domicProvDes=js.getString("domicProvDes");
                 per.domicDptoDes=js.getString("domicDptoDes");
                 per.estaturaDes=js.getString("estaturaDes");
                 per.fechaNacimiento=js.getString("fechaNacimiento");
                 per.fechaInscripcion=js.getString("fechaInscripcion");
                 per.fechaExpedicion=js.getString("fechaExpedicion");
                 per.sexoCod=js.getString("sexoCod");

                 fabGuardar.setEnabled(true);
             }else{
                 new AlertDialog.Builder(getActivity())
                         .setIcon(android.R.drawable.ic_dialog_alert)
                         .setTitle("Aviso")
                         .setMessage(response.getString("Message"))
                         .setOnDismissListener(new DialogInterface.OnDismissListener() {
                             @Override
                             public void onDismiss(DialogInterface arg0) {
                                 //ActividadLogin.this.finish();
                             }})
                         //.setNegativeButton(android.R.string.cancel, null)//sin listener
                         .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                             @Override
                             public void onClick(DialogInterface dialog, int which){
                                 //Salir
                                 //ActividadLogin.this.finish();
                                 //txtPassword.setText("");
                             }
                         })
                         .show();
                 }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnGuardar(PersonaModel Per){

       Gson gsonpojo = new GsonBuilder().create();
        Per.direccion=txtDirecion.getText().toString().toUpperCase();
        Per.referencia=txtReferencia.getText().toString().toUpperCase();
        Per.telefono=txtTelefono.getText().toString();
        Per.email=txtEmail.getText().toString();
        Per.ocupacion=txtOcupacion.getText().toString().toUpperCase();
        Per.nPersNatHijos= Integer.parseInt(txtnrohijos.getText().toString());
        Per.estadoCivilCod=EstadoSel.getCodigoValor();
        Per.gradoInstruccionCod=GradoSel.getCodigoValor();

        String json = gsonpojo.toJson(per);
        HashMap<String, String> cabeceras = new HashMap<>();

        new RESTService(getActivity()).post(SrvCmacIca.POST_PERSONA, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProcesarGuardar(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Volley: " + error.toString());
                        progressDialog.cancel();
                       // errorservice(error);
                    }
                }
                , cabeceras);
    }

    private void ProcesarGuardar(JSONObject response){

        try {
            if (response.getBoolean("IsCorrect")) {

                Snackbar.make(view, "Se Guardó Correctamente los Datos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

//--------------------------------------------------------------------------------------------------
// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    if (mListener != null) {
        mListener.onFragmentInteraction(uri);
    }
}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

