package pe.com.cmacica.flujocredito.ViewModel.Recuperaciones;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Cobranza.ClienteCobranzaModel;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.TipoCreditoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones.AdaptadorClienteRecuperaciones;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentoListaRecuperaciones extends Fragment {
    private static final String TAG = fragmentoListaRecuperaciones.class.getSimpleName();

    private ProgressDialog progressDialog;
    private Gson gson = new Gson();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CheckBox chck_TipoCredito;
    private AdaptadorClienteRecuperaciones adaptador;
    private Spinner spnTipoCredito;
    private TipoCreditoModel TipoCreditoSel;
   public static List<ClienteRecuperacionModel>  ListClientes=new ArrayList<ClienteRecuperacionModel>();
    public fragmentoListaRecuperaciones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragmento_lista_recuperaciones, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.rv_clienteRecuperaciones);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        spnTipoCredito=(Spinner)vista.findViewById(R.id.spnTipoCredito);
        chck_TipoCredito=(CheckBox)vista.findViewById(R.id.chck_TipoCredito);
        spnTipoCredito.setEnabled(false);
        OnCargarClientes();



        spnTipoCredito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoCreditoSel = (TipoCreditoModel) parent.getItemAtPosition(position);

                if (TipoCreditoSel.getnTipoCreditos() !=0) {
                    List<ClienteRecuperacionModel> ListaNueva = new ArrayList<ClienteRecuperacionModel>();

                 for (ClienteRecuperacionModel CliE : ListClientes)
                 {
                     if (CliE.getNtipocredito()==TipoCreditoSel.getnTipoCreditos()) {
                         ListaNueva.add(CliE);

                     }
                 }
                    adaptador = new AdaptadorClienteRecuperaciones(getActivity(),ListaNueva);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chck_TipoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chck_TipoCredito.isChecked())
                {
                    spnTipoCredito.setEnabled(true);
                    OnCargarLitaTipoCredito();
                }
                else
                {
                    spnTipoCredito.setEnabled(false);
                    spnTipoCredito.setAdapter(null);
                    TipoCreditoSel=null;
                    adaptador = new AdaptadorClienteRecuperaciones(getActivity(), ListClientes);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));

                }
            }
        });
        return vista;

    }

    private void OnCargarClientes() {

        String url = String.format(SrvCmacIca.GET_CLIENTES_RECUPERACIONES, UPreferencias.ObtenerCodigoPersonaLogeo(getActivity()));
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
                                        ProcesarClientes(response);                                    }
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
    private void ProcesarClientes(JSONObject response){

        try {
            if (response.getBoolean("IsCorrect")){


                JSONArray ListaClientesRecuperaciones = response.getJSONArray("Data");
                ClienteRecuperacionModel[] ArrayClientesRecuperaciones= gson.fromJson(ListaClientesRecuperaciones.toString(), ClienteRecuperacionModel[].class);
                ListClientes= Arrays.asList(ArrayClientesRecuperaciones);

                adaptador = new AdaptadorClienteRecuperaciones(getActivity(), Arrays.asList(ArrayClientesRecuperaciones));
                recyclerView.setAdapter(adaptador);
                recyclerView.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));

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
   private void OnCargarLitaTipoCredito() {
        try {

            String Url = String.format( SrvCmacIca.GET_ALL_TIPOCREDITO);
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
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void ProcesarListaTipoCredito(JSONObject response) {
        try {
            // Obtener atributo "estado"
            JSONArray ListaTipoCredito = response.getJSONArray("Data");

            TipoCreditoModel[] ArrayTipoCredito = gson.fromJson(ListaTipoCredito.toString(), TipoCreditoModel[].class);

            List<TipoCreditoModel> TipoCreditoList=new ArrayList<TipoCreditoModel>(Arrays.asList(ArrayTipoCredito));

            TipoCreditoList.add(0,new TipoCreditoModel(0,"SELECCIONAR"));


            ArrayAdapter<TipoCreditoModel> adpSpinnerTipoCredito = new ArrayAdapter<TipoCreditoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    TipoCreditoList
            );
            //adpSpinnerTipoCredito.
            adpSpinnerTipoCredito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnTipoCredito.setAdapter(adpSpinnerTipoCredito);


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
}