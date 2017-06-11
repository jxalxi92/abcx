package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

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

import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Calificacion.CredClienteModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.DetCalifSbsModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.InfoDeuVencModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.InfoEstandarModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.InfoLinCredModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.InfoSBSModel;
import pe.com.cmacica.flujocredito.Model.Calificacion.ReglasNegocioModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorCalifSbs;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorCredCliente;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorDeuVenc;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorInfoEstandar;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorInfoSBS;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorLinCred;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorReglasNegocio;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.ViewModel.Seguridad.ActividadLogin;


public class fragmentoInicio extends Fragment {

    /*
    Etiqueta de depuracion
     */
    private static final String TAG = fragmentoInicio.class.getSimpleName();
    private ProgressDialog progressDialog ;
    private RecyclerView recyclerView;

    private RecyclerView rvInfoEstandar;
    private AdaptadorInfoEstandar adpInfoEstandar;

    private RecyclerView rvInfoSBS;
    private AdaptadorInfoSBS adpInfoSBS;

    private RecyclerView rvInfoLinCred;
    private AdaptadorLinCred adpInfoLinCred;

    private RecyclerView rvInfoDeuVenc;
    private AdaptadorDeuVenc adpInfoDeuVenc;

    private RecyclerView rvCredVigente;
    private AdaptadorCredCliente adpCredCliente;

    private RecyclerView rvreglasnegocio;
    private AdaptadorReglasNegocio adpReglasNeg;

    private LinearLayoutManager layoutManager;
    private AdaptadorCalifSbs adaptador;
    private Gson gson = new Gson();
    private TextView lblNombre;
    private TextView lblCodSbs;
    private TextView lblNunEnt;
    private TextView lblCalifCmac;
    private TextView lblDeudadSF;
    private TextView lblCalifSBS;
    private EditText txtDoi;

    private TextView lblTituloFteIgr;
    private View dividerInfoEstandar;
    private TextView lblTituloSBS;
    private View dividerSBS;
    private TextView lblTituloLinCred;
    private View dividerLineasCred;
    private TextView lblTituloDeuVencidos;
    private View dividerVencido;
    private TextView titulo_Det_Calif;
    private View dividerCalif6meses;
    private TextView lblDatInterno;
    private View dividerDatInterno;

    private TextView titulo_Cred_Cliente;
    private View dividerCredCliente;

    private TextView titulo_reglas_negocio;
    private View dividerreglasnegocio;

    private LinearLayout llLinCred;
    private LinearLayout llInfoSBS;
    private LinearLayout llDeudaVenc;
    private LinearLayout llDatInterno;


    private Button btnBuscar;
    private Button btnNuevo;

    RadioButton rbDNI;
    RadioButton rbRUC;
    RadioButton rbtCarnetExt;

    private View view;

    public fragmentoInicio() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragmento_inicio,container,  false);
        txtDoi = (EditText) view.findViewById(R.id.TxtDoi);
        lblTituloFteIgr = (TextView) view.findViewById(R.id.lblTituloFteIgr);
        dividerInfoEstandar = (View) view.findViewById(R.id.dividerInfoEstandar);
        lblTituloSBS = (TextView) view.findViewById(R.id.lblTituloSBS);
        dividerSBS = (View) view.findViewById(R.id.dividerSBS);
        lblTituloLinCred = (TextView) view.findViewById(R.id.lblTituloLinCred);
        dividerLineasCred = (View) view.findViewById(R.id.dividerLineasCred);
        lblTituloDeuVencidos = (TextView) view.findViewById(R.id.lblTituloDeuVencidos);
        dividerVencido = (View) view.findViewById(R.id.dividerVencido);
        titulo_Det_Calif = (TextView) view.findViewById(R.id.titulo_Det_Calif);
        dividerCalif6meses = (View) view.findViewById(R.id.dividerCalif6meses);

        rbDNI = (RadioButton) view.findViewById(R.id.rbDNI);
        rbRUC = (RadioButton) view.findViewById(R.id.rbRUC);
        rbtCarnetExt= (RadioButton) view.findViewById(R.id.rbtCarnetExt);


        titulo_Cred_Cliente = (TextView) view.findViewById(R.id.titulo_Cred_Cliente);
        dividerCredCliente =  view.findViewById(R.id.dividerCredCliente);


        titulo_reglas_negocio = (TextView) view.findViewById(R.id.titulo_reglas_negocio);
        dividerreglasnegocio = (View) view.findViewById(R.id.dividerreglasnegocio);

        llLinCred = (LinearLayout) view.findViewById(R.id.llLinCred);
        llInfoSBS = (LinearLayout) view.findViewById(R.id.llInfoSBS);
        llDeudaVenc= (LinearLayout) view.findViewById(R.id.llDeudaVenc);
        llDatInterno = (LinearLayout) view.findViewById(R.id.llDatInterno);

        btnBuscar = (Button) view.findViewById(R.id.btnBuscarCalif);
        btnNuevo = (Button) view.findViewById(R.id.btnNuevoCalif);
        recyclerView = (RecyclerView)  view.findViewById(R.id.rvCalif);
        rvInfoEstandar = (RecyclerView) view.findViewById(R.id.rvInfoEstandar) ;
        rvInfoSBS = (RecyclerView) view.findViewById(R.id.rvInfoSBS) ;
        rvInfoLinCred = (RecyclerView) view.findViewById(R.id.rvInfoLinCred);
        rvInfoDeuVenc = (RecyclerView) view.findViewById(R.id.rvInfoDeuVenc);
        rvCredVigente = (RecyclerView) view.findViewById(R.id.rvCredVigente);
        rvreglasnegocio= (RecyclerView) view.findViewById(R.id.rvreglasnegocio);

        layoutManager = new LinearLayoutManager(getActivity());
        rvInfoEstandar.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getActivity());
        rvInfoSBS .setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getActivity());
        rvInfoDeuVenc.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getActivity());
        rvInfoLinCred.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(getActivity());
        rvCredVigente.setLayoutManager(layoutManager);
        rvCredVigente.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));
        layoutManager = new LinearLayoutManager(getActivity());
        rvreglasnegocio.setLayoutManager(layoutManager);

        lblCodSbs = (TextView) view.findViewById(R.id.lblCodigoSbs);
        lblCalifSBS = (TextView)view.findViewById(R.id.lblCalifSBS);
        lblCalifCmac = (TextView) view.findViewById(R.id.lblCalifCmac);
        lblDeudadSF = (TextView) view.findViewById(R.id.lblDeudaSf);
        lblNunEnt = (TextView) view.findViewById(R.id.lblNumEnt);
        lblNombre = (TextView) view.findViewById(R.id.lblnombre);
        lblDatInterno = (TextView) view.findViewById(R.id.lblDatInterno);
        dividerDatInterno = (View) view.findViewById(R.id.dividerDatInterno);

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
                    public void onClick(View v) {

                        Inicializar();
                    }
        });
        Inicializar();
        return view;
    }

    private void Inicializar(){

        txtDoi.setText("");
        lblTituloFteIgr.setVisibility(View.GONE);
        dividerInfoEstandar.setVisibility(View.GONE);
        lblTituloSBS.setVisibility(View.GONE);
        dividerSBS.setVisibility(View.GONE);
        lblTituloLinCred.setVisibility(View.GONE);
        dividerLineasCred.setVisibility(View.GONE);
        lblTituloDeuVencidos.setVisibility(View.GONE);
        dividerVencido.setVisibility(View.GONE);
        titulo_Det_Calif.setVisibility(View.GONE);
        dividerCalif6meses.setVisibility(View.GONE);
        llLinCred.setVisibility(View.GONE);
        llInfoSBS.setVisibility(View.GONE);
        llDeudaVenc.setVisibility(View.GONE);

        lblDatInterno.setVisibility(View.GONE);
        dividerDatInterno.setVisibility(View.GONE);
        llDatInterno.setVisibility(View.GONE);

        titulo_Cred_Cliente.setVisibility(View.GONE);
        dividerCredCliente.setVisibility(View.GONE);

        titulo_reglas_negocio.setVisibility(View.GONE);
        dividerreglasnegocio.setVisibility(View.GONE);

        //region Info Estandar

        adpInfoEstandar = new AdaptadorInfoEstandar(new ArrayList<InfoEstandarModel>());
        rvInfoEstandar.setAdapter(adpInfoEstandar);
        //endregion
        //region InfoSBS
        adpInfoSBS = new AdaptadorInfoSBS(new ArrayList<InfoSBSModel>());
        rvInfoSBS.setAdapter(adpInfoSBS);

        //endregion
        //region Linea Cred
        adpInfoLinCred = new AdaptadorLinCred(new ArrayList<InfoLinCredModel>());
        rvInfoLinCred.setAdapter(adpInfoLinCred);
        //endregion
        //region Deuda Vencia
        adpInfoDeuVenc = new AdaptadorDeuVenc(new ArrayList<InfoDeuVencModel>());
        rvInfoDeuVenc.setAdapter(adpInfoDeuVenc);
        //endregion
        adaptador = new AdaptadorCalifSbs(getActivity(),new ArrayList<DetCalifSbsModel>(),getFragmentManager(),"");
        recyclerView.setAdapter(adaptador);

        //region Creditos Cliente
        adpCredCliente = new AdaptadorCredCliente(new ArrayList<CredClienteModel>(),getActivity(),getFragmentManager());
        rvCredVigente.setAdapter(adpCredCliente);
        //endregion

        //region Reglas de Negocio
        adpReglasNeg = new AdaptadorReglasNegocio(new ArrayList<ReglasNegocioModel>());
        rvreglasnegocio.setAdapter(adpReglasNeg);
        //endregion



    }
    private void CargarDatos(){

        if(UPreferencias.ObtenerIndDesconectado(getActivity()).equals("1")){
            new AlertDialog.Builder(getActivity())
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

            String doi = txtDoi.getText().toString();



            String cTipoDoc="D" ;
            if(rbDNI.isChecked()){
                cTipoDoc = "D";
                if(doi.length()!=8){
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
            }
            if (rbRUC.isChecked()){
                cTipoDoc = "R";
                if(doi.length()!=11){
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Ingrese un número de RUC correcto.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
            }
            if (rbtCarnetExt.isChecked()){
                cTipoDoc="4";
                if(doi.length()<8 && doi.length()>12 ){
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Ingrese un Carnet de extrangería correcto.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                }
                            })
                            .show();
                    return;
                }
            }



            progressDialog = ProgressDialog.show(getContext(), "Espere por favor", "Obteniendo calificación.");
            String IMEI  = UPreferencias.ObtenerImei(getActivity());
            String Url =String.format( SrvCmacIca.GET_INFOCLIENTE , doi,cTipoDoc,IMEI);
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
                                            procesarRespuesta(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                            progressDialog.cancel();
                                            new AlertDialog.Builder(getActivity())
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setTitle("Aviso")
                                                    .setMessage("Error al conectarse al servidor.")
                                                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //Salir
                                                            return;
                                                            //txtPassword.setText("");
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                            )
                    );
        }
    }
    private void procesarRespuesta(JSONObject response) {
        try {
            Inicializar();
            progressDialog.cancel();
            if (response.getBoolean("IsCorrect")){

                JSONObject Data = response.getJSONObject("Data");


                if( Data.getString("InfoCmacIca") != "null"){
                    JSONObject InfoCmacIca = Data.getJSONObject("InfoCmacIca");
                    String codsbs = InfoCmacIca.getString("CodigoSbs");
                    lblCalifCmac.setText(Data.getJSONObject("InfoCmacIca").getString("CalifCmac"));
                    lblNombre.setText(Data.getJSONObject("InfoCmacIca").getString("NombreDeudor"));
                    String CaifSbs = UConsultas.CalifSBS(Data.getJSONObject("InfoCmacIca").getInt("CalifSBS"));
                    lblCalifSBS.setText(CaifSbs);
                    lblCodSbs.setText(codsbs);
                    lblDeudadSF.setText(Data.getJSONObject("InfoCmacIca").getString("SaldoCapital"));
                    lblNunEnt.setText(Data.getJSONObject("InfoCmacIca").getString("NumIfis"));

                    JSONArray DetCalif = Data.getJSONObject("InfoCmacIca").getJSONArray("Calif6Meses");

                    DetCalifSbsModel [] ArrayDetCalif = gson.fromJson(DetCalif.toString(),DetCalifSbsModel[].class);
                    lblDatInterno.setVisibility(View.VISIBLE);
                    dividerDatInterno.setVisibility(View.VISIBLE);
                    llDatInterno.setVisibility(View.VISIBLE);

                    if(ArrayDetCalif.length>0){
                        titulo_Det_Calif.setVisibility(View.VISIBLE);
                        dividerCalif6meses.setVisibility(View.VISIBLE);
                    }else{
                        titulo_Det_Calif.setVisibility(View.GONE);
                        dividerCalif6meses.setVisibility(View.GONE);
                    }
                    adaptador = new AdaptadorCalifSbs(getActivity(),Arrays.asList(ArrayDetCalif),getFragmentManager(),codsbs);
                    recyclerView.setAdapter(adaptador);
                }
                else{
                    lblDatInterno.setVisibility(View.GONE);
                    dividerDatInterno.setVisibility(View.GONE);
                    llDatInterno.setVisibility(View.GONE);
                }
                //reglas de negocio
                if(Data.getString("ReglasInternas") != "null"){
                    JSONArray ArrReglasJson = Data.getJSONArray("ReglasInternas");
                    ReglasNegocioModel[] ArrReglas = gson.fromJson(ArrReglasJson.toString(),ReglasNegocioModel[].class);

                    if (ArrReglas.length>0){
                        titulo_reglas_negocio.setVisibility(View.VISIBLE);
                        dividerreglasnegocio.setVisibility(View.VISIBLE);
                        adpReglasNeg = new AdaptadorReglasNegocio(Arrays.asList(ArrReglas));
                        rvreglasnegocio.setAdapter(adpReglasNeg);

                    }else{
                        titulo_reglas_negocio.setVisibility(View.GONE);
                        dividerreglasnegocio.setVisibility(View.GONE);
                    }

                }
                //endregion
                //region Creditos cliente
                if(Data.getString("CreditosVigentes") != "null"){
                    JSONArray ArrReglasJson = Data.getJSONArray("CreditosVigentes");
                    CredClienteModel[] ArrReglas = gson.fromJson(ArrReglasJson.toString(),CredClienteModel[].class);

                    if (ArrReglas.length>0){
                        titulo_Cred_Cliente.setVisibility(View.VISIBLE);
                        dividerCredCliente.setVisibility(View.VISIBLE);
                        adpCredCliente = new AdaptadorCredCliente(Arrays.asList(ArrReglas),getActivity(),getFragmentManager());
                        rvCredVigente.setAdapter(adpCredCliente);

                    }else{
                        titulo_Cred_Cliente.setVisibility(View.GONE);
                        dividerCredCliente.setVisibility(View.GONE);
                    }

                }

                //endregion
                //region Info Estandar
                JSONArray jsonIfoestandar = Data.getJSONArray("InfoEstandar");

                if(jsonIfoestandar == null){
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("No se encontraró información, vuelva a intentar.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    return;
                                    //txtPassword.setText("");
                                }
                            })
                            .show();
                    return;
                }

                InfoEstandarModel[] ArrayInfoEstandar= gson.fromJson(jsonIfoestandar.toString(),InfoEstandarModel[].class);

                if(ArrayInfoEstandar.length>0){

                    lblTituloFteIgr.setVisibility(View.VISIBLE);
                    dividerInfoEstandar.setVisibility(View.VISIBLE);
                }else{
                    lblTituloFteIgr.setVisibility(View.GONE);
                    dividerInfoEstandar.setVisibility(View.GONE);
                }
                adpInfoEstandar = new AdaptadorInfoEstandar(Arrays.asList(ArrayInfoEstandar));
                rvInfoEstandar.setAdapter(adpInfoEstandar);
                rvInfoEstandar.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));
                //endregion
                //region InfoSBS
                JSONArray jsonInfoSBS = Data.getJSONArray("InfoSBS");
                InfoSBSModel[] ArrayInfoSBS = gson.fromJson(jsonInfoSBS.toString(),InfoSBSModel[].class);
                if(ArrayInfoSBS.length>0){
                    lblTituloSBS.setVisibility(View.VISIBLE);
                    dividerSBS.setVisibility(View.VISIBLE);
                    llInfoSBS.setVisibility(View.VISIBLE);
                }else{
                    lblTituloSBS.setVisibility(View.GONE);
                    dividerSBS.setVisibility(View.GONE);
                    llInfoSBS.setVisibility(View.GONE);
                }
                adpInfoSBS = new AdaptadorInfoSBS(Arrays.asList(ArrayInfoSBS));
                rvInfoSBS.setAdapter(adpInfoSBS);

                //endregion
                //region Linea Cred
                JSONArray jsonLinCred = Data.getJSONArray("InfoLinCred");
                InfoLinCredModel[] ArrarInfoLinCred = gson.fromJson(jsonLinCred.toString(),InfoLinCredModel[].class);
                if(ArrarInfoLinCred.length>0){
                    lblTituloLinCred.setVisibility(View.VISIBLE);
                    dividerLineasCred.setVisibility(View.VISIBLE);
                    llLinCred.setVisibility(View.VISIBLE);
                }else{
                    lblTituloLinCred.setVisibility(View.GONE);
                    dividerLineasCred.setVisibility(View.GONE);
                    llLinCred.setVisibility(View.GONE);
                }
                adpInfoLinCred = new AdaptadorLinCred(Arrays.asList(ArrarInfoLinCred));
                rvInfoLinCred.setAdapter(adpInfoLinCred);
                //endregion
                //region Deuda Vencia
                JSONArray jsonDeuVenc = Data.getJSONArray("InfoDeuVenc");
                InfoDeuVencModel[] ArrayInfoDeuVenc = gson.fromJson(jsonDeuVenc.toString(),InfoDeuVencModel[].class);
                if(ArrayInfoDeuVenc.length>0){
                    lblTituloDeuVencidos.setVisibility(View.VISIBLE);
                    dividerVencido.setVisibility(View.VISIBLE);
                    llDeudaVenc.setVisibility(View.VISIBLE);
                }else{
                    lblTituloDeuVencidos.setVisibility(View.GONE);
                    dividerVencido.setVisibility(View.GONE);
                    llDeudaVenc.setVisibility(View.GONE);
                }
                adpInfoDeuVenc = new AdaptadorDeuVenc(Arrays.asList(ArrayInfoDeuVenc));
                rvInfoDeuVenc.setAdapter(adpInfoDeuVenc);
                //endregion
            }
            else{

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

                Inicializar();

            }



        } catch (JSONException e) {
            progressDialog.cancel();
            Log.d(TAG, e.getMessage());
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("No se encontraron datos.")

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

    }



}
