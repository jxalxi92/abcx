package pe.com.cmacica.flujocredito.ViewModel.Solicitud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.DatoPersonaSolicitudModel;
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
import pe.com.cmacica.flujocredito.ViewModel.ActividadPrincipal;
import pe.com.cmacica.flujocredito.ViewModel.Seguridad.ActividadLogin;


public  class ActividadMantSolCred extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = ActividadMantSolCred.class.getSimpleName();
    private Gson gson = new Gson();
    private int Perstipo;
    private double MontoSolicitado;

    DatoPersonaSolicitudModel Cliente;

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
    private TextView lblProyectoImnmobiliario;
    private FloatingActionButton Fab_Buscar,Fab_nuevo;

    //region PROPIEDADES---------------------------------------------------------------------------------------
    private TipoCreditoModel TipoCreditoSel;
    private ProductoModel ProductoSel;
    private ConstanteModel SectorSel;
    private ConstanteModel MonedaSel;
    private ConstanteModel Condicion;
    private CampañasModel CampañaSel;
    //CARDVIEW--------------------------------------------------------------------------------------
    private CardView CarViewInstitucion;

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


    /*********************************************************************************************************************
     **************************************************<  M E T O D O S  >*************************************************
     **********************************************************************************************************************/
    public void showToolbar(String tittle, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; go home

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Desea Regresar a la Ventana Anterior?")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                // ActividadLogin.this.finish();
                            }})
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir

                                onBackPressed();
                            }
                        })
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
        CarViewInstitucion=(CardView)findViewById(R.id.CarViewInstitucion);
        // txtNombres.setInputType(InputType.TYPE_NULL);
        //txtTipoPersona.setInputType(InputType.TYPE_NULL);
        lblProyectoImnmobiliario=(TextView)findViewById(R.id.lblProyectoImnmobiliario);

        chckAgropecuario.setVisibility(View.INVISIBLE);
        spnAgropecuario.setVisibility(View.INVISIBLE);
        chckCampañas.setEnabled(false);
        CarViewInstitucion.setVisibility(View.GONE);
        spnProyInmobilirio.setEnabled(false);
        txtTea.setVisibility(View.GONE);
        lblProyectoImnmobiliario.setVisibility(View.GONE);
        spnProyInmobilirio.setVisibility(View.GONE);
    }

    private void InicializarControles() {
        //OnCargarLitaTipoCredito();
        ProcesarListaMoneda();
        ProcesarTipoPeriodo();
        OnCagarProceso();
        OnCargarAgenciasBnAge();

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
                    OnCargarProducto();
                }

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
                switch (ProductoSel.getcCredProductos()) {
                    case "301": //CrediSueldo
                        CarViewInstitucion.setVisibility(View.VISIBLE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setVisibility(View.INVISIBLE);
                        spnAgropecuario.setVisibility(View.INVISIBLE);
                        spnProyecto.setVisibility(View.INVISIBLE);
                        ProcesarSector();
                        break;
                    case "404"://MI VIVIENDA - TECHO PROPIO
                    case "405"://HIPOTECARIO CAJA CASA
                        CarViewInstitucion.setVisibility(View.GONE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setVisibility(View.INVISIBLE);
                        spnAgropecuario.setVisibility(View.INVISIBLE);
                        spnProyInmobilirio.setEnabled(true);
                        OnCargarProyectoInmobiliario();
                        break;
                    case "102"://COMERCIAL AGRICOLA
                    case "202"://COMERCIAL AGRICOLA
                        CarViewInstitucion.setVisibility(View.GONE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setChecked(true);
                        OnCargarAgropecuario();
                        spnProyecto.setVisibility(View.INVISIBLE);
                        break;
                    case "208"://ASOCIACIONES Y/O GRUPOS ORG
                    case "309"://ASOCIACIONES Y/O GRUPOS ORG
                        CarViewInstitucion.setVisibility(View.GONE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setVisibility(View.INVISIBLE);
                        spnAgropecuario.setVisibility(View.INVISIBLE);
                        spnProyecto.setVisibility(View.VISIBLE);
                        spnProyInmobilirio.setEnabled(true);
                        OnCargarProyectos();
                        break;
                    case "205":
                    case "204":
                    case "201":
                    case "101":
                        CarViewInstitucion.setVisibility(View.GONE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setVisibility(View.INVISIBLE);
                        spnAgropecuario.setVisibility(View.VISIBLE);
                        spnProyecto.setVisibility(View.INVISIBLE);
                        chckAgropecuario.setChecked(false);
                        chckAgropecuario.setEnabled(true);
                        OnCargarAgropecuario();
                    default:
                        CarViewInstitucion.setVisibility(View.GONE);
                        spnProyInmobilirio.setVisibility(View.GONE);
                        chckAgropecuario.setVisibility(View.INVISIBLE);
                        spnAgropecuario.setVisibility(View.INVISIBLE);
                        spnProyecto.setVisibility(View.INVISIBLE);
                }
                chckCampañas.setChecked(false);
                chckCampañas.setEnabled(true);
                OnCargarFrecPago();
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
        spnCampañas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CampañaSel=(CampañasModel) parent.getItemAtPosition(position);

                if (CampañaSel.getIdCampana()==129)
                {
                        txtTea.setVisibility(View.VISIBLE);
                }
                else
                {
                    txtTea.setVisibility(View.GONE);
                }
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
                    OnCargarCampañas();
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
                    Snackbar.make(findViewById(R.id.llOperacionSim),
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

            }
        });

    }
    private void onLimpiar(){
        txt_Dni.setText("");
        txtNombres.setText("");
        txtTipoPersona.setText("");

    }
    private void ProcesarListaMoneda() {
        try {


            List<ConstanteModel> ListaMoneda = new ArrayList<ConstanteModel>();
            ListaMoneda.add(new ConstanteModel(1011, 1, "SOLES",0));
            ListaMoneda.add(new ConstanteModel(1011, 2, "DÓLARES",0));

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
            ListaTipoPeriodo.add(new ConstanteModel(1011, 0, "FECHA FIJA",0));
            ListaTipoPeriodo.add(new ConstanteModel(1011, 1, "PERIODO FIJO",0));

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

    //region Procesos
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
    //endregion

    //region Tipo Creditos
    private void OnCargarLitaTipoCredito() {
        try {

            String Url = String.format( SrvCmacIca.GET_PERSONA_TIPOCREDITO,Cliente.getDatoPersonal().getnPersPersoneria());
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
    //endregion

    //region CargarCondicion
    private void OnCargarCondicion() {
        try {
            String Url = String.format(SrvCmacIca.GET_SEL_CONDICION_SOL, Cliente.getDatoPersonal().getCodigoPersona());
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
                                            OnProcesarCondicion(response);
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
    private void OnProcesarCondicion(JSONObject response){
        try {
            if (response.getBoolean("IsCorrect")) {

                String CondicionJson = response.getJSONObject("Data").toString();
                Condicion = gson.fromJson(CondicionJson,ConstanteModel.class);
                txtCondicion.setText(Condicion.toString());


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

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){

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
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    //endregion

    //region Camapañas
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
    //endregion

    //region Agencia BN
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
    //endregion

    //region Agropecuario
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
    //endregion

    //region Destino
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
    //endregion

    //region ProcesarProyectoInmobiliario
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
    //endregion

    //region IntsConvenio
    private void ProcesarSector(){
        try {

            // Obtener atributo "estado"

            List<ConstanteModel> ListaTipoPeriodo = new ArrayList<ConstanteModel>();
            ListaTipoPeriodo.add(new ConstanteModel(7011, 2, "PUBLICO",0));
            ListaTipoPeriodo.add(new ConstanteModel(7011, 3, "PRIVADO",0));

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
    //endregion

    //region Frecuencia de Pago
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
    //endregion

    //region Proyectos
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
    //endregion

    //region Producto
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
    //endregion

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
                                            OnProcesarDatoClienteSolCred(response);
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

    private void OnProcesarDatoClienteSolCred(JSONObject response) {
        try {
            if (response.getBoolean("IsCorrect")) {

                Cliente = gson.fromJson(response.getJSONObject("Data").toString(), DatoPersonaSolicitudModel.class);

                txtNombres.setText(Cliente.getDatoPersonal().getNombrePersona());
                txtTipoPersona.setText(Cliente.getDatoPersonal().getTipoPersona());
                Perstipo= Cliente.getDatoPersonal().getnPersPersoneria();
                txt_Dni.setFocusable(false);
                txtNombres.setFocusable(false);
                txtTipoPersona.setFocusable(false);
                //cargar los combos
                OnCargarLitaTipoCredito();
                OnCargarCondicion();


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

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){

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

    //endregion


    private void OnVerificarEvaMensual(){

        try {

            String Url =String.format(SrvCmacIca.GET_VERIF_EVA_MEN,
                    TipoCreditoSel.getnTipoCreditos(),
                    Cliente.getDatoPersonal().getNumeroDocumento(),
                    Cliente.getDatoPersonal().getCodigoTipoDocumento(),
                    String.valueOf( MontoSolicitado),
                    "false","false");

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

                JSONObject ClasifJson = response.getJSONObject("Data");

                int tipocredito = ClasifJson.getInt("nTipoCredito") ;

                //hacer que el combo cargue el tipocredito el que tenga el codigo nTipoCredito
                //((ArrayAdapter)spnTipoCredito.getAdapter()).getPosition()

                for (int i = 0; i < spnTipoCredito.getAdapter().getCount(); i++) {
                    if (((TipoCreditoModel) spnTipoCredito.getItemAtPosition(i)).getnTipoCreditos() == tipocredito) {
                        spnTipoCredito.setSelection(i);

                        break;
                    }
                }
            }else{
                if (response.getBoolean("IsContinue")){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage(response.getString("Message"))
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    //ActividadLogin.this.finish();
                                    spnTipoCredito.setSelection(0);

                                }})

                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                }else {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage(response.getString("Message"))
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    //ActividadLogin.this.finish();
                                    spnTipoCredito.setSelection(0);
                                }})

                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                }
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

