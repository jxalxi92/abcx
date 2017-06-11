package pe.com.cmacica.flujocredito.ViewModel.PlanPago;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Simulador.CondicionesPlanPagoModel;
import pe.com.cmacica.flujocredito.Utilitarios.Dialogos.DateDialog;
import pe.com.cmacica.flujocredito.Model.General.AgenciaModel;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.FrecuenciaPagoModel;
import pe.com.cmacica.flujocredito.Model.PlanPagoModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.ProductoModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.TipoCreditoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentoSimuladorCredito extends Fragment  {

    private static final String TAG = fragmentoSimuladorCredito.class.getSimpleName();

    //region Propiedades

    private AgenciaModel AgenciaSel;
    private TipoCreditoModel TipoCreditoSel;
    private ConstanteModel MonedaSel;
    private ConstanteModel TipoPeriodoSel;
    private ProductoModel ProductoSel;
    private FrecuenciaPagoModel FrecPagoSel;


    //endregion

    //region Controles

    private ProgressDialog progressDialog ;
    private Gson gson = new Gson();
    private View view;

    private Spinner spinnerTipoCredito;
    private Spinner spinnerAgencia;
    private Spinner spinnerProducto;
    private Spinner spinnerMoneda;
    private Spinner spinnerFrecPago;
    private Spinner spinnerTipoPeriodo;

    private EditText txtNumCuotas;
    private EditText txtMontoSol;
    private EditText txtTEM;
    private EditText txtGracia;
    private EditText txtDiaPago;

    private TextView lblFecha;

    private CheckBox checkboxSegDes;
    //private CheckBox checkboxMultMicro;
    private CheckBox checkboxMicroSeg;

    private Button BtnSimular;
    private Button BtnInicializar;


    //endregion

    public fragmentoSimuladorCredito() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragmento_simulador_credito, container, false);
        checkboxSegDes = (CheckBox) view.findViewById(R.id.checkboxSegDes);
        //checkboxMultMicro = (CheckBox) view.findViewById(R.id.checkboxMultMicro);
        checkboxMicroSeg = (CheckBox) view.findViewById(R.id.checkboxMicroSeg);

        txtGracia = (EditText) view.findViewById(R.id.TxtGracia);
        txtMontoSol = (EditText) view.findViewById(R.id.TxtMontoSol);
        txtNumCuotas = (EditText) view.findViewById(R.id.TxtNumCuotas);
        txtTEM = (EditText) view.findViewById(R.id.TxtTasa);
        txtDiaPago = (EditText) view.findViewById(R.id.TxtDiaPago) ;
        lblFecha = (TextView) view.findViewById(R.id.lblFechaDesem);
        lblFecha.setText(UGeneral.obtenerTiempoCorto());
        BtnSimular = (Button) view.findViewById(R.id.btnSimCredito);
        spinnerAgencia = (Spinner) view.findViewById(R.id.spinnerAgencia);
        spinnerAgencia.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AgenciaSel = (AgenciaModel) parent.getItemAtPosition(position);
                CargarProducto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTipoCredito = (Spinner) view.findViewById(R.id.spinnerTipoCredito);
        spinnerTipoCredito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoCreditoSel = (TipoCreditoModel) parent.getItemAtPosition(position);
                CargarProducto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProducto= (Spinner) view.findViewById(R.id.spinnerProducto);
        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProductoSel = (ProductoModel) parent.getItemAtPosition(position);
                CargarFrecPago();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerMoneda = (Spinner) view.findViewById(R.id.spinnerMoneda);
        spinnerMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MonedaSel = (ConstanteModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTipoPeriodo = (Spinner) view.findViewById(R.id.spinnerTipoPeriodo);

        spinnerFrecPago = (Spinner) view.findViewById(R.id.spinnerFrecPago);
        spinnerFrecPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FrecPagoSel = (FrecuenciaPagoModel) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // lblFecha.setText("2016-04-30");

        lblFecha.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DateDialog();
                        picker.show(getFragmentManager(),"DatePicker");
                    }
                }
        );


        BtnSimular.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción
                        //progressDialog.onStart();
                        OnSimularCalendario();

                        //OnDetallePlanPago();
                    }
                }
        );

        BtnInicializar = (Button) view.findViewById(R.id.btnInicializador);
        BtnInicializar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnInicilaizar();
                    }
                });


        CargarListaAgencia();
        CargarLitaTipoCredito();
        ProcesarListaMoneda();
        ProcesarTipoPeriodo();
        spinnerTipoPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoPeriodoSel = (ConstanteModel) parent.getItemAtPosition(position);

                if(TipoPeriodoSel != null){
                    if(TipoPeriodoSel.getCodigoValor()==1){

                        spinnerFrecPago.setVisibility(View.VISIBLE);
                        txtDiaPago.setVisibility(View.GONE);

                    }else {
                        spinnerFrecPago.setVisibility(View.GONE);
                        txtDiaPago.setVisibility(View.VISIBLE);
                        txtDiaPago.setText("1");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    //region Metodos

    private void CargarListaAgencia(){
        try {
            String Url = SrvCmacIca.GET_ALL_AGENCIAS;
            VolleySingleton.
                    getInstance(getActivity()).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    Url,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            ProcesarListaAgencia(response);
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
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarListaAgencia(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaAgencia = response.getJSONArray("Data");
            AgenciaModel[] ArrayPlanPago = gson.fromJson(ListaAgencia.toString(),AgenciaModel[].class);

            ArrayAdapter adpAgencias = new ArrayAdapter<AgenciaModel>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayPlanPago)
            );

            adpAgencias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAgencia.setAdapter(adpAgencias);

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    private void CargarLitaTipoCredito(){
        try {

            String Url = SrvCmacIca.GET_ALL_TIPOCREDITO;
            VolleySingleton.
                    getInstance(getActivity()).
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
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarListaTipoCredito(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaTipoCredito = response.getJSONArray("Data");
            TipoCreditoModel[] ArrayTipoCredito = gson.fromJson(ListaTipoCredito.toString(),TipoCreditoModel[].class);

            ArrayAdapter<TipoCreditoModel> adpSpinnerTipoCredito = new ArrayAdapter<TipoCreditoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                     Arrays.asList(ArrayTipoCredito)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerTipoCredito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerTipoCredito.setAdapter(adpSpinnerTipoCredito);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void CargarProducto(){
        try {

            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
            if (TipoCreditoSel == null || AgenciaSel == null) {
                return;
            }

            String Url =String.format(SrvCmacIca.GET_FILTER_PRODUCTO,  AgenciaSel.getCodigoAgencia(), TipoCreditoSel.getnTipoCreditos());
            VolleySingleton.
                    getInstance(getActivity()).
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
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarProducto(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaProducto = response.getJSONArray("Data");
            ProductoModel[] ArrayTipoCredito = gson.fromJson(ListaProducto.toString(),ProductoModel[].class);

            ArrayAdapter<ProductoModel> adpSpinnerProducto = new ArrayAdapter<ProductoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayTipoCredito)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerProducto.setAdapter(adpSpinnerProducto);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarListaMoneda(){
        try {
            // Obtener atributo "estado"

            List<ConstanteModel> ListaMoneda = new ArrayList<ConstanteModel>();
            ListaMoneda.add(new ConstanteModel(1011,1,"SOLES"));
            ListaMoneda.add(new ConstanteModel(1011,2,"DÓLARES"));

            ArrayAdapter<ConstanteModel> adpSpinnerMoneda = new ArrayAdapter<ConstanteModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    ListaMoneda
            );
            adpSpinnerMoneda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMoneda.setAdapter(adpSpinnerMoneda);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void ProcesarTipoPeriodo(){
        try {

            // Obtener atributo "estado"

            List<ConstanteModel> ListaTipoPeriodo = new ArrayList<ConstanteModel>();
            ListaTipoPeriodo.add(new ConstanteModel(1011,0,"FECHA FIJA"));
            ListaTipoPeriodo.add(new ConstanteModel(1011,1,"PERIODO FIJO"));

            ArrayAdapter<ConstanteModel> adpSpinnerTipoFrec = new ArrayAdapter<ConstanteModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    ListaTipoPeriodo
            );
            adpSpinnerTipoFrec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipoPeriodo.setAdapter(adpSpinnerTipoFrec);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void CargarFrecPago(){

        try {
            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
            if (ProductoSel == null) {
                return;
            }

            String Url = SrvCmacIca.GET_FRECUENCIA_PAGO + ProductoSel.getcCredProductos();
            VolleySingleton.
                    getInstance(getActivity()).
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
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarFrecPago(JSONObject response){
        try {
            // Obtener atributo "estado"
            JSONArray ListaFrecPago = response.getJSONArray("Data");
            FrecuenciaPagoModel[] ArrayFrecPago = gson.fromJson(ListaFrecPago.toString(),FrecuenciaPagoModel[].class);



            ArrayAdapter<FrecuenciaPagoModel> AdpSpinnerFrecPago = new ArrayAdapter<FrecuenciaPagoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayFrecPago)
            );

            AdpSpinnerFrecPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFrecPago.setAdapter(AdpSpinnerFrecPago);


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void OnInicilaizar(){

        txtGracia.setText("");
        txtTEM.setText("");
        txtNumCuotas.setText("");
        txtMontoSol.setText("");
        txtDiaPago.setText("");
    }

    private boolean OnValidaPrevioSimular(){

        return  true;
    }

    private void OnSimularCalendario(){

        try {

            if (TipoCreditoSel == null ||TipoPeriodoSel == null || MonedaSel == null ){
                return;
            }
            //validando monto
            if( txtMontoSol.getText().toString().equals("")){
                txtMontoSol.setText("0");

            }
            if(Double.parseDouble(txtMontoSol.getText().toString())<500 || Double.parseDouble(txtMontoSol.getText().toString())>10000000){
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Ingrese un monto mayor a 500 y menos a 10000000 soles.")
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .show();

                return;
            }else{
                txtMontoSol.setText(String.valueOf(Integer.parseInt(txtMontoSol.getText().toString())));
            }
            //validando cuotas
            if( txtNumCuotas.getText().toString().equals("")){
                txtNumCuotas.setText("0");
            }
            if(Integer.parseInt(txtNumCuotas.getText().toString())<1 || Integer.parseInt(txtNumCuotas.getText().toString())>240){
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Ingrese número de cuotas correcto 1-240.")
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){

                            }
                        })
                        .show();

                return;
            }
            //validando TEM
            if( txtTEM.getText().toString().equals( "")){
                txtTEM.setText("0");
            }
            if(Double.parseDouble(txtTEM.getText().toString())<=0 || Double.parseDouble(txtTEM.getText().toString())>15){
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Ingrese una TEM correcto 0.01 - 15.")
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){

                            }
                        })
                        .show();

                return;
            }
            //validando día de pago
            int diaPago=0;

            if(TipoPeriodoSel.getCodigoValor()==0) {
                if(txtDiaPago.getText().toString().equals("")){
                    diaPago=0;
                }else{
                    diaPago =Integer.parseInt ( txtDiaPago.getText().toString());
                }

                if (diaPago <= 0 || diaPago > 31) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Ingrese un día de pago correcto.")
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Salir
                                    //ActividadLogin.this.finish();
                                    txtDiaPago.setText("");
                                    //return;
                                }
                            })
                            .show();

                    return;
                }
            }
            int cPeriodo = 0;
            if(TipoPeriodoSel.getCodigoValor()==1){
                cPeriodo =FrecPagoSel.getnDias();
            }
            int PeriocidadEndDias = 30;
            if (TipoPeriodoSel.getCodigoValor() == 1){
               // PeriocidadEndDias = Integer.parseInt(txtNumCuotas.getText().toString()) * FrecPagoSel.getnDias();
                PeriocidadEndDias=FrecPagoSel.getnDias();
            }


            progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");

            CondicionesPlanPagoModel parametro = new CondicionesPlanPagoModel();
            parametro.CapitalColocado = Double.parseDouble(txtMontoSol.getText().toString());
            parametro.CapitalCuotaDesembolso = Double.parseDouble(txtMontoSol.getText().toString());;
            parametro.nTipoCredito = TipoCreditoSel.getnTipoCreditos();
            parametro.TipoGeneracion = 0;
            parametro.TipoPlanPago=0;// verificar credi emprende
            parametro.TipoDesembolso =0;
            parametro.TipoGracia = 1;
            parametro.TipoPeriodicidad = TipoPeriodoSel.getCodigoValor();// verificar los pagadiarios
            parametro.Destino = 0;
            parametro.NumeroCalendario = 1;
            parametro.TEM = Double.parseDouble(txtTEM.getText().toString());
            parametro.NumeroCuotas = Integer.parseInt(txtNumCuotas.getText().toString());
            parametro.Moneda = MonedaSel.getCodigoValor();
            parametro.DiaFijo = diaPago;
            parametro.PeriodoFijo = cPeriodo;
            parametro.PeriodicidadEnDias = PeriocidadEndDias;
            parametro.FechaDesembolsoOriginal =lblFecha.getText().toString();
            if(txtGracia.getText().toString().equals("")){
                txtGracia.setText("0");
                parametro.DiasGracia = 0;
            }else{
                parametro.DiasGracia =Integer.parseInt( txtGracia.getText().toString());
            }

            parametro.ProximoMes = false;
            parametro.ProximaCuotaDesembolso = 1;
            parametro.ProrrateoIntMiViv = false;
            parametro.ReembolsoMiViv = false;
            parametro.GeneraDesgravamen = checkboxSegDes.isChecked();
            parametro.GeneraMicroseguro = checkboxMicroSeg.isChecked();
            parametro.GeneraMultiriesgo = false;// checkboxMultMicro.isChecked();
            parametro.cImei = UPreferencias.ObtenerImei(getActivity());

            if ( parametro.nTipoCredito == 11 || parametro.nTipoCredito == 10
                    || parametro.nTipoCredito == 9)
            {
                parametro.GeneraDesgravamen = false;
            }
            if(ProductoSel.getcCredProductos().equals("210")){
                parametro.TipoPeriodicidad =3;
            }
            if(ProductoSel.getcCredProductos().equals("320")){
                parametro.TipoPeriodicidad =2;
            }

            Gson gs  = new GsonBuilder().create();
            String datos =gs.toJson(parametro);
            HashMap<String, String> cabeceras = new HashMap<>();

            new RESTService(getContext()).post(SrvCmacIca.POST_PLAN_PAGO, datos,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ProcesarRespuestasimulador(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    , cabeceras);


            }catch (Exception ex){
                Log.d(TAG, ex.getMessage());
                Toast.makeText(
                        getActivity(),
                        ex.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

    }

    private void ProcesarRespuestasimulador(JSONObject response) {
        try {
            // Obtener atributo "estado"
            progressDialog.cancel();
            if (response.getBoolean("IsCorrect")) {

                JSONArray PlanPago = response.getJSONArray("Data");
                PlanPagoModel[] ArrayPlanPago = gson.fromJson(PlanPago.toString(), PlanPagoModel[].class);
                List<PlanPagoModel> ListaPlanPago = Arrays.asList(ArrayPlanPago);
                double MontoTotal = 0;

                for(PlanPagoModel cuotasel:ListaPlanPago){
                    MontoTotal += Double.parseDouble(cuotasel.getCuota());
                }

                 float Montoound = UGeneral.round( (float) MontoTotal,2);

                ActividadDetallePlanPago.createInstance(
                        getActivity(), ListaPlanPago, TipoCreditoSel.getcDescripcion(), ProductoSel.getcDescripcion(), txtMontoSol.getText().toString(), MonedaSel.getDescripcion(),
                        txtNumCuotas.getText().toString(), TipoPeriodoSel.getDescripcion(), lblFecha.getText().toString(), txtGracia.getText().toString(), String.valueOf(Montoound),
                        Float.parseFloat(txtTEM.getText().toString())
                );


            }else{
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage(response.getString("Message"))
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir
                                return;
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
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        mes +=1;
        String cdia,cmes;
        if(dia<10){
            cdia ="0" + String.valueOf(dia);
        }else{
            cdia =String.valueOf(dia);
        }
        if(mes<10){
            cmes ="0" + String.valueOf(mes);
        }else{
            cmes =String.valueOf(mes);
        }

        lblFecha.setText(ano + "-" + cmes + "-" + cdia);
    }
    //endregion

}
