package pe.com.cmacica.flujocredito.ViewModel.Solicitud;

import android.content.DialogInterface;
import android.os.Bundle;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.FrecuenciaPagoModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaBusqModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.Model.Solicitud.ActividadesAgropecuariasModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.ColocAgenciaBNModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.DestinosModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.ProductoModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.CampañasModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.CredProcesosModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.SolCredClasifModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.TipoCreditoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;


public  class ActividadMantSolCred extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = ActividadMantSolCred.class.getSimpleName();
    private Gson gson = new Gson();
    private int Perstipo;
    private double MontoSolicitado;
    private String NumeroDocumento;

 //VARIABLES CONTROLES------------------------------------------------------------------------------
    SearchView Search;
   //EDITEXT---------------------------------------------------------------------------------------
    private EditText txt_Dni,txtNombres, txtCondicion, txtMonto, txtNroCuotas, txtDias,
            txtCodModular, txtTea,txtTipoPersona;
    //SPINNER---------------------------------------------------------------------------------------
    private Spinner spnProceso, spnTipoCredito, spnProducto, spnMoneda, spnFrecPago, spnAgropecuario,
            spnSector, spnIntSector, spnCampañas, spnDestino, spnBancoNacion, spnProyInmobilirio,
            spnProyecto, spnSolicitud;
    //CHECKBOX--------------------------------------------------------------------------------------
    private CheckBox chckAgropecuario, chckCampañas, chckBancoNacion, chckMicroSeguro, chckAutoAsignado;
    private FloatingActionButton Fab_Buscar,Fab_nuevo;

    //region PROPIEDADES---------------------------------------------------------------------------------------
    private TipoCreditoModel TipoCreditoSel;
    private ProductoModel ProductoSel;
    private ConstanteModel SectorSel;
    private ConstanteModel MonedaSel;

    public ActividadMantSolCred() {
    }

    //endregion --------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mant_sol_cred);
        showToolbar(getResources().getString(R.string.RegistroSolicitud), true);
        getSupportLoaderManager().initLoader(0,null,this);

        /*mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
//ASIGNACION DE CONTROLES---------------------------------------------------------------------------
        AsignarControles();
//--------------------------------------------------------------------------------------------------
        InicializarControles();
//VALIDACIONES--------------------------------------------------------------------------------------
        txtNombres.setInputType(InputType.TYPE_NULL);
        txtTipoPersona.setInputType(InputType.TYPE_NULL);
        spnBancoNacion.setEnabled(false);
        spnCampañas.setEnabled(false);
        spnAgropecuario.setEnabled(false);
//EVENTOS DE CONTROLES------------------------------------------------------------------------------
        EventosControles();
    }
 //LOADER CALLBACKS---------------------------------------------------------------------------------
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> cursor = new CursorLoader(this,
                ContratoDbCmacIca.ConstanteTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.ConstanteTable.nConsCod + " IN (?)"
                ,
                new String[]{"9068"} ,
                null);

        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnCargarConstantes(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}


//METODOS------------------------------------------------------------------------------------------
    public void showToolbar(String tittle, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void AsignarControles() {

        txtNombres = (EditText) findViewById(R.id.txtNombres);
        txtTipoPersona = (EditText) findViewById(R.id.txtTipoPersona);
        txtCondicion = (EditText) findViewById(R.id.txtCondicion);
        txtMonto = (EditText) findViewById(R.id.TxtMontoSol);
        txtNroCuotas = (EditText) findViewById(R.id.txtNroCuotas);
        txtDias = (EditText) findViewById(R.id.txtDiasFrecuencia);
        txtCodModular = (EditText) findViewById(R.id.txtCodModular);
        txtTea = (EditText) findViewById(R.id.txtTea);
        spnProceso = (Spinner) findViewById(R.id.spinnerProceso);
        spnTipoCredito = (Spinner) findViewById(R.id.spinnerTipoCredito);
        spnProducto = (Spinner) findViewById(R.id.spinnerProducto);
        spnMoneda = (Spinner) findViewById(R.id.spinnerMoneda);
        spnFrecPago = (Spinner) findViewById(R.id.spinnerFrecPago);
        spnAgropecuario = (Spinner) findViewById(R.id.spinnerAgropecuario);
        spnSector = (Spinner) findViewById(R.id.spinnerSector);
        spnIntSector = (Spinner) findViewById(R.id.spinnerIntSector);
        spnCampañas = (Spinner) findViewById(R.id.spinnerCampaña);
        spnDestino = (Spinner) findViewById(R.id.spinnerDestino);
        spnBancoNacion = (Spinner) findViewById(R.id.spinnerBanco);
        spnProyInmobilirio = (Spinner) findViewById(R.id.spinnerProyImmobiliario);
        spnProyecto = (Spinner) findViewById(R.id.spinnerProyecto);
        spnSolicitud = (Spinner) findViewById(R.id.spinnerSolicitud);
        chckAgropecuario = (CheckBox) findViewById(R.id.chckAgropecuario);
        chckCampañas = (CheckBox) findViewById(R.id.chckCampaña);
        chckBancoNacion = (CheckBox) findViewById(R.id.chckBancoNacion);
        chckMicroSeguro = (CheckBox) findViewById(R.id.chckMicroSeguro);
        Fab_Buscar=(FloatingActionButton)findViewById(R.id.Fab_Buscar);
        Fab_nuevo=(FloatingActionButton)findViewById(R.id.Fab_nuevo);
        chckAutoAsignado = (CheckBox) findViewById(R.id.chckAsignado);
        txt_Dni=(EditText) findViewById(R.id.txt_Dni);
        // txtNombres.setInputType(InputType.TYPE_NULL);
        //txtTipoPersona.setInputType(InputType.TYPE_NULL);


    }

    private void InicializarControles() {
        OnCargarLitaTipoCredito();
        ProcesarListaMoneda();
        ProcesarTipoPeriodo();
        OnCagarProceso();
        OnCargarCampañas();
        OnCargarProyectos();
        OnCargarAgenciasBnAge();
        ProcesarSector();

    }

    private void EventosControles(){

        spnTipoCredito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoCreditoSel = (TipoCreditoModel) parent.getItemAtPosition(position);

                double MontoSoles;
                if (txtMonto.getText().toString().equals("")){
                    MontoSolicitado=0;

                }else{
                    MontoSolicitado=Double.parseDouble(txtMonto.getText().toString());
                }

                if (TipoCreditoSel.getnTipoCreditos()!=0)
                {
                    OnVerificarEvaMensual();
                }

                OnCargarProducto();
                OnCargarDestino();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MonedaSel = (ConstanteModel) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ProductoSel = (ProductoModel) parent.getItemAtPosition(position);
                OnCargarFrecPago();
                OnCargarAgropecuario();
                OnCargarDestino();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spnSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SectorSel = (ConstanteModel) parent.getItemAtPosition(position);
                OnCargarIntsConvenio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        chckBancoNacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chckBancoNacion.isChecked())
                {
                    spnBancoNacion.setEnabled(true);
                }
                else
                {
                    spnBancoNacion.setEnabled(false);
                }
            }
        });
        chckCampañas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chckCampañas.isChecked())
                {
                    spnCampañas.setEnabled(true);
                }
                else
                {
                    spnCampañas.setEnabled(false);
                }
            }
        });
        chckAgropecuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chckAgropecuario.isChecked())
                {
                    spnAgropecuario.setEnabled(true);
                }
                else
                {
                    spnAgropecuario.setEnabled(false);
                }
            }
        });
        Fab_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Dni;
                Dni=txt_Dni.getText().toString();
                if (txt_Dni.length()==0 || txt_Dni.length()<8)
                {
                    Snackbar.make(findViewById(R.id.actividadGestionCobranza),
                            "Numero de Documento Incorrecto",
                            Snackbar.LENGTH_LONG).show();
                    onLimpiar();
                }
                else
                {
                    //OnBuscarPersona(Dni);
                    OnSelDatoClienteSolCred(Dni);
                    //txt_Dni.setEnabled(false);

                }

            }
        });
        Fab_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLimpiar();
                txt_Dni.setFocusableInTouchMode(true);
                txt_Dni.setFocusable(true);
                txtNombres.setFocusable(true);
                txtTipoPersona.setFocusable(true);
            }
        });

    }
    private void onLimpiar(){
        txt_Dni.setText("");
        txtNombres.setText("");
        txtTipoPersona.setText("");

    }
    private void OnCagarProceso(){
        try {

            String Url =String.format(SrvCmacIca.GET_PROCESO);
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarProceso(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarProceso(JSONObject response){
        try {
            JSONArray ListaProcesos = response.getJSONArray("Data");
            CredProcesosModel[] ArrayProceso = gson.fromJson(ListaProcesos.toString(), CredProcesosModel[].class);

            ArrayAdapter<CredProcesosModel> adpSpinnerProcesos = new ArrayAdapter<CredProcesosModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayProceso)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerProcesos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnProceso.setAdapter(adpSpinnerProcesos);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarCampañas(){
        try{
            String Url =String.format(SrvCmacIca.GET_CAMPAÑAS,UPreferencias.ObtenerCodAgencia(this));
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarCampañas(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarCampañas(JSONObject response){
        try {
            JSONArray ListaCampañas = response.getJSONArray("Data");
            CampañasModel[] ArrayCampañas = gson.fromJson(ListaCampañas.toString(), CampañasModel[].class);

            ArrayAdapter<CampañasModel> adpSpinnerCampañas = new ArrayAdapter<CampañasModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayCampañas)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerCampañas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnCampañas.setAdapter(adpSpinnerCampañas);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarAgenciasBnAge(){

        try{
            String Url =String.format(SrvCmacIca.GET_AGENCIASBN_AGE,UPreferencias.ObtenerCodAgencia(this));
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarAgenciasBnAge(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarAgenciasBnAge(JSONObject response){
        try {
            JSONArray ListaAgenciasBnAge = response.getJSONArray("Data");
            ColocAgenciaBNModel[] ArrayAgenciasBnAge= gson.fromJson(ListaAgenciasBnAge.toString(), ColocAgenciaBNModel[].class);

            ArrayAdapter<ColocAgenciaBNModel> adpSpinnerAgenciasBnAge = new ArrayAdapter<ColocAgenciaBNModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayAgenciasBnAge)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerAgenciasBnAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnBancoNacion.setAdapter(adpSpinnerAgenciasBnAge);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
}

    private void OnCargarProducto() {
        try {

            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
             if (TipoCreditoSel == null || TipoCreditoSel.getnTipoCreditos()==0  || UPreferencias.ObtenerCodAgencia(this)==null) {

                return;
            }
            String Url =String.format(SrvCmacIca.GET_FILTER_PRODUCTO,UPreferencias.ObtenerCodAgencia(this)  , TipoCreditoSel.getnTipoCreditos());

            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarProducto(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarProducto(JSONObject response) {
        try {
            // Obtener atributo "estado"
            JSONArray ListaProducto = response.getJSONArray("Data");
            ProductoModel[] ArrayTipoCredito = gson.fromJson(ListaProducto.toString(), ProductoModel[].class);

            ArrayAdapter<ProductoModel> adpSpinnerProducto = new ArrayAdapter<ProductoModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayTipoCredito)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnProducto.setAdapter(adpSpinnerProducto);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarProyectos(){
        try {

            String Url =String.format(SrvCmacIca.GET_PROYECTOS);
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarProyectos(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarProyectos(JSONObject response){
        try {
            JSONArray ListaProyectos = response.getJSONArray("Data");
            PersonaDto[] ArrayProyectos = gson.fromJson(ListaProyectos.toString(), PersonaDto[].class);

            ArrayAdapter<PersonaDto> adpSpinnerProyectos = new ArrayAdapter<PersonaDto>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayProyectos)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerProyectos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnProyecto.setAdapter(adpSpinnerProyectos);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarLitaTipoCredito() {
        try {

            String Url = SrvCmacIca.GET_ALL_TIPOCREDITO;
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarListaTipoCredito(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }


                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarListaTipoCredito(JSONObject response) {
        try {
            // Obtener atributo "estado"
            JSONArray ListaTipoCredito = response.getJSONArray("Data");

            TipoCreditoModel[] ArrayTipoCredito = gson.fromJson(ListaTipoCredito.toString(), TipoCreditoModel[].class);

          List<TipoCreditoModel>TipoCreditoList=new ArrayList<TipoCreditoModel>(Arrays.asList(ArrayTipoCredito));

            TipoCreditoList.add(0,new TipoCreditoModel(0,"SELECCIONAR"));

            ArrayAdapter<TipoCreditoModel> adpSpinnerTipoCredito = new ArrayAdapter<TipoCreditoModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    TipoCreditoList
            );
            //adpSpinnerTipoCredito.
            adpSpinnerTipoCredito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTipoCredito.setAdapter(adpSpinnerTipoCredito);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarListaMoneda() {
        try {


            List<ConstanteModel> ListaMoneda = new ArrayList<ConstanteModel>();
            ListaMoneda.add(new ConstanteModel(1011, 1, "SOLES"));
            ListaMoneda.add(new ConstanteModel(1011, 2, "DÓLARES"));

            ArrayAdapter<ConstanteModel> adpSpinnerMoneda = new ArrayAdapter<ConstanteModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    ListaMoneda
            );
            adpSpinnerMoneda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnMoneda.setAdapter(adpSpinnerMoneda);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarTipoPeriodo() {
        try {

            // Obtener atributo "estado"

            List<ConstanteModel> ListaTipoPeriodo = new ArrayList<ConstanteModel>();
            ListaTipoPeriodo.add(new ConstanteModel(1011, 0, "FECHA FIJA"));
            ListaTipoPeriodo.add(new ConstanteModel(1011, 1, "PERIODO FIJO"));

            ArrayAdapter<ConstanteModel> adpSpinnerTipoFrec = new ArrayAdapter<ConstanteModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    ListaTipoPeriodo
            );
            adpSpinnerTipoFrec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFrecPago.setAdapter(adpSpinnerTipoFrec);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarFrecPago() {

        try {
            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
            if (ProductoSel == null) {
                return;
            }

            String Url = SrvCmacIca.GET_FRECUENCIA_PAGO + ProductoSel.getcCredProductos();
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarFrecPago(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarFrecPago(JSONObject response) {
        try {
            // Obtener atributo "estado"
            JSONArray ListaFrecPago = response.getJSONArray("Data");
            FrecuenciaPagoModel[] ArrayFrecPago = gson.fromJson(ListaFrecPago.toString(), FrecuenciaPagoModel[].class);

            ArrayAdapter<FrecuenciaPagoModel> AdpSpinnerFrecPago = new ArrayAdapter<FrecuenciaPagoModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayFrecPago)
            );

            AdpSpinnerFrecPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFrecPago.setAdapter(AdpSpinnerFrecPago);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarConstantes(Cursor query) {


        List<ConstanteModel> ListaConstanteEstadosSolictud = new ArrayList<>();

        while (query.moveToNext()) {
            ConstanteModel obj = UConsultas.ConverCursorToConstanteModel(query);
            if (obj.getCodigoValor() == 6 || obj.getCodigoValor()==7 || obj.getCodigoValor()==8) {
                ListaConstanteEstadosSolictud.add(obj);
            } else {

            }
        }

        ArrayAdapter<ConstanteModel> adpSpinnerEstadoSolicitud = new ArrayAdapter<ConstanteModel>(
                this,
                android.R.layout.simple_spinner_item,
                ListaConstanteEstadosSolictud
        );
        adpSpinnerEstadoSolicitud.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSolicitud.setAdapter(adpSpinnerEstadoSolicitud);


    }

    private void OnCargarAgropecuario(){
        try {

            if (ProductoSel == null) {
                return;
            }
            String Url =String.format(SrvCmacIca.GET_AGROPECUARIOS,ProductoSel.getcCredProductos());
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarAgropecuarios(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarAgropecuarios(JSONObject response){
        try {

            JSONArray ListaAgropecuarios = response.getJSONArray("Data");
            ActividadesAgropecuariasModel[] ArrayAgropecuarios = gson.fromJson(ListaAgropecuarios.toString(), ActividadesAgropecuariasModel[].class);

            ArrayAdapter<ActividadesAgropecuariasModel> AdpSpinnerAgropecuarios= new ArrayAdapter<ActividadesAgropecuariasModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayAgropecuarios)
            );

            AdpSpinnerAgropecuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAgropecuario.setAdapter(AdpSpinnerAgropecuarios);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarDestino(){
        try {
            if (ProductoSel == null || TipoCreditoSel==null) {
                return;}
            String Url =String.format(SrvCmacIca.GET_DESTINOS,TipoCreditoSel.getnTipoCreditos(),ProductoSel.getcCredProductos());
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarDestinos(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarDestinos(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaDestinos = response.getJSONArray("Data");
            DestinosModel[] ArrayDestinos = gson.fromJson(ListaDestinos.toString(), DestinosModel[].class);

            ArrayAdapter<DestinosModel> AdpSpinnerDestinos = new ArrayAdapter<DestinosModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayDestinos)
            );

            AdpSpinnerDestinos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDestino.setAdapter(AdpSpinnerDestinos);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarProyectoInmobiliario(){
        try {
            if (ProductoSel == null ) {
                return;}
            String Url =String.format(SrvCmacIca.GET_PROYECTOS_INMOBILIARIOS,UPreferencias.ObtenerCodAgencia(this),ProductoSel.getcCredProductos());
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarProyectoInmobiliario(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarProyectoInmobiliario(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaDestinos = response.getJSONArray("Data");
            DestinosModel[] ArrayDestinos = gson.fromJson(ListaDestinos.toString(), DestinosModel[].class);

            ArrayAdapter<DestinosModel> AdpSpinnerDestinos = new ArrayAdapter<DestinosModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayDestinos)
            );

            AdpSpinnerDestinos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDestino.setAdapter(AdpSpinnerDestinos);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarSector(){
        try {

            // Obtener atributo "estado"

            List<ConstanteModel> ListaTipoPeriodo = new ArrayList<ConstanteModel>();
            ListaTipoPeriodo.add(new ConstanteModel(7011, 2, "PUBLICO"));
            ListaTipoPeriodo.add(new ConstanteModel(7011, 3, "PRIVADO"));

            ArrayAdapter<ConstanteModel> adpSpinnerTipoFrec = new ArrayAdapter<ConstanteModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    ListaTipoPeriodo
            );
            adpSpinnerTipoFrec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSector.setAdapter(adpSpinnerTipoFrec);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnCargarIntsConvenio(){
        try {

            if (Perstipo==0 || SectorSel==null)
            {
                return;
            }
            String Url =String.format(SrvCmacIca.GET_INTS_CONVENIO,Perstipo,SectorSel.getCodigoValor());
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarIntsConvenio(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarIntsConvenio(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaIntsConvenio = response.getJSONArray("Data");
            PersonaBusqModel[] ArrayIntsConvenio = gson.fromJson(ListaIntsConvenio.toString(), PersonaBusqModel[].class);

            ArrayAdapter<PersonaBusqModel> AdpSpinnerIntsConvenio = new ArrayAdapter<PersonaBusqModel>(
                    this,
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayIntsConvenio)
            );

            AdpSpinnerIntsConvenio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnIntSector.setAdapter(AdpSpinnerIntsConvenio);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnBuscarPersona(String Dni){
        try {
            String Url = String.format(SrvCmacIca.GET_BUSQUEDA_PERSONA, Dni);
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarBuscarPersona(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                    )
            );
}
   catch (Exception ex) {
        Log.d(TAG, ex.getMessage());
        Toast.makeText(
                this,
                ex.getMessage(),
                Toast.LENGTH_LONG).show();
    }
    }

    /*********************************************************************************************************************
     **************************************************<  M E T O D O S  >*************************************************
     **********************************************************************************************************************/
    //region SelDatoClienteSolCred

    private void OnSelDatoClienteSolCred(String Dni){
        try {

            String Url = String.format(
                    SrvCmacIca.GET_DATO_CLIENTE_SOL,
                    Dni,
                    UPreferencias.ObtenerUserLogeo(this),
                    UPreferencias.ObtenerCodAgencia(this));
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            //ProcesarBuscarPersona(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    //endregion

    private void  ProcesarBuscarPersona(JSONObject response){
        try {
            if (response.getBoolean("IsCorrect")){

                JSONArray ListaPersona = response.getJSONArray("Data");
                PersonaBusqModel[] ArrayPersona = gson.fromJson(ListaPersona.toString(), PersonaBusqModel[].class);

                txtNombres.setText(ArrayPersona[0].getNombrePersona());
                txtTipoPersona.setText(ArrayPersona[0].getTipoPersona());
                Perstipo=ArrayPersona[0].getnPersPersoneria();
                NumeroDocumento=ArrayPersona[0].getNumeroDocumento();
                OnCargarIntsConvenio();
                OnVerificarEvaMensual();

            }else{
                new AlertDialog.Builder(this)
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
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnVerificarEvaMensual(){

        try {

            if(NumeroDocumento==null )
            {
                return;
            }
            String Url =String.format(SrvCmacIca.GET_VERIF_EVA_MEN,NumeroDocumento);
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarVerificarEvaMensual(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            // progressDialog.cancel();
                                        }
                                    }
                            )
                    );
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarVerificarEvaMensual(JSONObject response){
        try {

            if (response.getBoolean("IsCorrect")){

                JSONArray ListaVentas = response.getJSONArray("Data");
                SolCredClasifModel[] ArrayVentas = gson.fromJson(ListaVentas.toString(), SolCredClasifModel[].class);

             if (ArrayVentas.length==0 && MontoSolicitado==0)
             {
                 new AlertDialog.Builder(this)
                         .setIcon(android.R.drawable.ic_dialog_alert)
                         .setTitle("Aviso")
                         .setMessage(response.getString("El Cliente no posee información en los ultimos 6 meses,debe indicar el monto solicitado"))
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
             else
             {
                 new AlertDialog.Builder(this)
                         .setIcon(android.R.drawable.ic_dialog_alert)
                         .setTitle("Aviso")
                         .setMessage(response.getString("Cargar el Fragmento Con los Datos A mostrar"))
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


            }else{
                new AlertDialog.Builder(this)
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
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }



    }

