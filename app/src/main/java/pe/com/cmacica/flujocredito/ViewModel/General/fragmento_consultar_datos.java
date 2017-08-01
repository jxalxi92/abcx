package pe.com.cmacica.flujocredito.ViewModel.General;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.AreaTelefonoModel;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.General.OcupacionModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.TipoCreditoModel;
import pe.com.cmacica.flujocredito.R;

import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.ViewModel.Solicitud.Fragmento_solCred_Clasif;

public class fragmento_consultar_datos extends Fragment {

    private static final String TAG = fragmento_consultar_datos.class.getSimpleName();
    private EditText TxtDni;
    private View view;
    private Button btnBuscar;
    private Button btnNuevo;
    private EditText txtDniR,txtPersona,txtDirecion,txtReferencia,txtTelefono,txtEmail,
            txtEstadoCivil,TxtGradoInstruccion,txt_Codigo,txt_celular;
    private Spinner spnOcupacion,spn_Hijos,spnTipoDomicilio,spnCondicion;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog ;
    PersonaModel per=new PersonaModel();
    private OcupacionModel OcupacionSel;
    private ConstanteModel NroHijos;
    private ConstanteModel CondicionSel;
    private ConstanteModel TipoDomicilioSel;
    public static  String Calculado = null;

    private FloatingActionButton fabGuardar;
    private Gson gson = new Gson();

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

        spnOcupacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OcupacionSel = (OcupacionModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCondicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CondicionSel = (ConstanteModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnTipoDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoDomicilioSel = (ConstanteModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_Hijos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NroHijos =(ConstanteModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


                if (Direccion.equals("") || Referencia.equals("")  || Email.equals("")  )

                {
                    Snackbar.make(view, "No deje Campos Vacíos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if( txtTelefono.getText().length()==0 && txt_celular.getText().length()==0)
                {
                    Snackbar.make(view, "Ingrese Teléfono o Celular", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (txtTelefono.getText().length()>0 && txt_Codigo.getText().length()==0){

                    Snackbar.make(view, "Ingrese Código de Teléfono", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;


                }
                if (Calculado !=null)
                {
                    per.domicDptoCod=Calculado.substring(0,2);
                    per.domicProvCod=Calculado.substring(2,4);
                    per.domicDistCod=Calculado.substring(4,6);
                }
                if (per.domicProvCod.equals("00"))
                {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("El domicilio de la Persona No es de Perú,Por favor Ingrese una Dirección dentro del país")
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {


                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    FragmentManager manager=getFragmentManager();
                                    FragmentoUbigeoPersona frag=new FragmentoUbigeoPersona();
                                    frag.show(manager,"Domicilio");

                                }
                            })
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();

                 return;
                }
                if (txtEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && txtEmail.length() > 0)
                {
                    OnGuardar(per);

                }
                else
                {
                    Snackbar.make(view, "Email No Válido", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return view;
    }
//METODOS-------------------------------------------------------------------------------------------
    private void Inicializar(){
        TxtDni.requestFocus();
        fabGuardar.setEnabled(false);
        TxtDni.setText("");
        txtDniR.setText("");
        txtPersona.setText("");
        txtDirecion.setText("");
        TxtGradoInstruccion.setText("");
        txtEstadoCivil.setText("");
        txtReferencia.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        spnOcupacion.setAdapter(null);
        spn_Hijos.setAdapter(null);
        spnCondicion.setAdapter(null);
        spnTipoDomicilio.setAdapter(null);
        txt_Codigo.setText("");
        txt_celular.setText("");
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
    txt_Codigo=(EditText) view.findViewById(R.id.txt_Codigo);
    txt_celular=(EditText) view.findViewById(R.id.txt_celular);
    spn_Hijos=(Spinner)view.findViewById(R.id.spn_Hijos);
    spnTipoDomicilio=(Spinner)view.findViewById(R.id.spnTipoDomicilio);
    spnCondicion=(Spinner)view.findViewById(R.id.spnCondicion);
    txtEstadoCivil=(EditText) view.findViewById(R.id.txtEstadoCivil);
    TxtGradoInstruccion=(EditText) view.findViewById(R.id.TxtGradoInstruccion);
    spnOcupacion=(Spinner) view.findViewById(R.id.spnOcupacion);
    fabGuardar=(FloatingActionButton)view.findViewById(R.id.fab_guardar);
}

    private void OnCargarOcupacion(){

        String url = String.format(SrvCmacIca.GET_OCUPACION);
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
                                        ProcesarOcupacion(response);                                    }
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
    private void ProcesarOcupacion(JSONObject response)
    {
        try {
            JSONArray ListaOcupacion = response.getJSONArray("Data");
            OcupacionModel[] ArrayOcupacion = gson.fromJson(ListaOcupacion.toString(), OcupacionModel[].class);

            ArrayAdapter<OcupacionModel> adpSpinnerOcupacion = new ArrayAdapter<OcupacionModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayOcupacion)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerOcupacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnOcupacion.setAdapter(adpSpinnerOcupacion);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarNroHijos() {

        List<ConstanteModel> ListaNroHijos = new ArrayList<ConstanteModel>();
        ListaNroHijos.add(new ConstanteModel(1,0,"0",0));
        ListaNroHijos.add(new ConstanteModel(1,1,"1",0));
        ListaNroHijos.add(new ConstanteModel(1,2,"2",0));
        ListaNroHijos.add(new ConstanteModel(1,3,"3",0));
        ListaNroHijos.add(new ConstanteModel(1,4,"4",0));
        ListaNroHijos.add(new ConstanteModel(1,5,"5",0));
        ListaNroHijos.add(new ConstanteModel(1,6,"6",0));
        ListaNroHijos.add(new ConstanteModel(1,7,"7",0));
        ListaNroHijos.add(new ConstanteModel(1,8,"8",0));
        ListaNroHijos.add(new ConstanteModel(1,9,"9",0));
        ListaNroHijos.add(new ConstanteModel(1,10,"10",0));
        ListaNroHijos.add(new ConstanteModel(1,11,"11",0));
        ListaNroHijos.add(new ConstanteModel(1,12,"12",0));
        ListaNroHijos.add(new ConstanteModel(1,13,"13",0));
        ListaNroHijos.add(new ConstanteModel(1,14,"14",0));
        ListaNroHijos.add(new ConstanteModel(1,15,"15",0));

        ArrayAdapter<ConstanteModel> adpSpinnerNroHijos = new ArrayAdapter<ConstanteModel>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                ListaNroHijos
        );
        adpSpinnerNroHijos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Hijos.setAdapter(adpSpinnerNroHijos);
    }

    private void OnCargarCondicion() {

        List<ConstanteModel> ListaCondicion = new ArrayList<ConstanteModel>();
        ListaCondicion.add(new ConstanteModel(1029,1,"PROPIA",0));
        ListaCondicion.add(new ConstanteModel(1029,2,"ALQUILADA",0));
        ListaCondicion.add(new ConstanteModel(1029,3,"DE FAMILIA",0));
        ListaCondicion.add(new ConstanteModel(1029,4,"ALQUILER VENTA",0));


        ArrayAdapter<ConstanteModel> adpSpinnerCondicion = new ArrayAdapter<ConstanteModel>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                ListaCondicion
        );
        adpSpinnerCondicion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCondicion.setAdapter(adpSpinnerCondicion);
    }
    private void OnCargarTipoDomicilio() {

        List<ConstanteModel> ListaTipoDomicilio = new ArrayList<ConstanteModel>();
        ListaTipoDomicilio.add(new ConstanteModel(1018,1,"DOMICILIO",0));
        ListaTipoDomicilio.add(new ConstanteModel(1018,2,"NEGOCIO",0));

        ArrayAdapter<ConstanteModel> adpSpinnerTipoDomicilio = new ArrayAdapter<ConstanteModel>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                ListaTipoDomicilio
        );
        adpSpinnerTipoDomicilio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDomicilio.setAdapter(adpSpinnerTipoDomicilio);
    }
    private void OnBuscarPersona(String Dni){
        progressDialog = ProgressDialog.show(getActivity(),"Espere por favor","Cargando Datos");
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
                                        progressDialog.cancel();
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
                 per.domicDptoCod=js.getString("domicDptoCod");

                 per.domicProvCod=js.getString("domicProvCod");
                 per.domicDistCod=js.getString("domicDistCod");
                 per.nacDptoCod=js.getString("nacDptoCod");
                 per.nacProvCod=js.getString("nacProvCod");
                 per.nacDistCod=js.getString("nacDistCod");

                 per.gradoInstruccionCod=js.getInt("gradoInstruccionCod");
                 per.estadoCivilCod=js.getInt("estadoCivilCod");
                 per.domicDistDes=js.getString("domicDistDes");
                 per.domicProvDes=js.getString("domicProvDes");
                 per.domicDptoDes=js.getString("domicDptoDes");
                 txt_Codigo.setText(CodigoCiudadTelefono(per.domicDptoDes.trim()));
                 per.estaturaDes=js.getString("estaturaDes");
                 per.fechaNacimiento=js.getString("fechaNacimiento");
                 per.fechaInscripcion=js.getString("fechaInscripcion");
                 per.fechaExpedicion=js.getString("fechaExpedicion");
                 per.sexoCod=js.getString("sexoCod");

                 OnCargarCondicion();
                 OnCargarTipoDomicilio();
                 OnCargarOcupacion();
                 OnCargarNroHijos();
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
        if (txtTelefono.getText().length()>0)
        {
            Per.telefono=txt_Codigo.getText().toString()+txtTelefono.getText().toString();
        }
        if (txt_celular.getText().length()>0)
        {
            Per.Celular=txt_celular.getText().toString();
        }
        Per.Nacionalidad="04028";
        Per.email=txtEmail.getText().toString();
        Per.ocupacion=OcupacionSel.getcDescripcion();
        Per.nPersNatHijos= NroHijos.getCodigoValor();
        Per.cCondicion=String.valueOf(CondicionSel.getCodigoValor());
        Per.nTpoDomicilio=TipoDomicilioSel.getCodigoValor();

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

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Se Guardó Correctamente los Datos")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                //ActividadLogin.this.finish();
                            }})

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                Inicializar();
                            }
                        })
                        .show();
                Inicializar();
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

    public String CodigoCiudadTelefono(String Ciudad)

    {
        String Resultado="";
        List<AreaTelefonoModel> ListaAreaTelefono = new ArrayList<AreaTelefonoModel>();
        ListaAreaTelefono.add(new AreaTelefonoModel("01","AMAZONAS", "041"));
        ListaAreaTelefono.add(new AreaTelefonoModel("02","ANCASH", "043"));
        ListaAreaTelefono.add(new AreaTelefonoModel("03","APURIMAC", "083"));
        ListaAreaTelefono.add(new AreaTelefonoModel("04","AREQUIPA", "054"));
        ListaAreaTelefono.add(new AreaTelefonoModel("05","AYACUCHO", "066"));
        ListaAreaTelefono.add(new AreaTelefonoModel("06","CAJAMARCA","076"));
        ListaAreaTelefono.add(new AreaTelefonoModel("07","CALLAO", "01"));
        ListaAreaTelefono.add(new AreaTelefonoModel("08","CUSCO", "084"));
        ListaAreaTelefono.add(new AreaTelefonoModel("09","HUANCAVELICA", "067"));
        ListaAreaTelefono.add(new AreaTelefonoModel("10","HUANUCO", "062"));
        ListaAreaTelefono.add(new AreaTelefonoModel("11","ICA", "056"));
        ListaAreaTelefono.add(new AreaTelefonoModel("12","JUNIN", "064"));
        ListaAreaTelefono.add(new AreaTelefonoModel("13","LA LIBERTAD", "044"));
        ListaAreaTelefono.add(new AreaTelefonoModel("14","LAMBAYEQUE", "074"));
        ListaAreaTelefono.add(new AreaTelefonoModel("15","LIMA", "01"));
        ListaAreaTelefono.add(new AreaTelefonoModel("16","LORETO", "065"));
        ListaAreaTelefono.add(new AreaTelefonoModel("17","MADRE DE DIOS", "082"));
        ListaAreaTelefono.add(new AreaTelefonoModel("18","MOQUEGUA", "053"));
        ListaAreaTelefono.add(new AreaTelefonoModel("19","PASCO", "063"));
        ListaAreaTelefono.add(new AreaTelefonoModel("20","PIURA", "073"));
        ListaAreaTelefono.add(new AreaTelefonoModel("21","PUNO", "051"));
        ListaAreaTelefono.add(new AreaTelefonoModel("22","SAN MARTIN", "042"));
        ListaAreaTelefono.add(new AreaTelefonoModel("23","TACNA", "052"));
        ListaAreaTelefono.add(new AreaTelefonoModel("24","TUMBES", "072"));
        ListaAreaTelefono.add(new AreaTelefonoModel("25","UCAYALI", "061"));

        for (int i=0;i<ListaAreaTelefono.size()-1;i++)
        {
            if ((ListaAreaTelefono.get(i).getDepartamento()).equals(Ciudad.trim()))
            {
                Resultado=ListaAreaTelefono.get(i).getCodigoTelefonico();
            }
        }
        return Resultado;
    }
public void Ubigeo(String Ubigeo)
{
    Calculado=Ubigeo;

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

