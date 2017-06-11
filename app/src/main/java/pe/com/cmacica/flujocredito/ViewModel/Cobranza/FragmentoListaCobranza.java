package pe.com.cmacica.flujocredito.ViewModel.Cobranza;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Cobranza.ClienteCobranzaModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Cobranza.AdaptadorClienteCobranza;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoListaCobranza extends Fragment {

    private static final String TAG = FragmentoListaCobranza.class.getSimpleName();
    private View vista;
    private ProgressDialog progressDialog ;
    private Gson gson = new Gson();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorClienteCobranza adaptador;
    public FragmentoListaCobranza() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragmento_lista_cobranza, container, false);

        recyclerView = (RecyclerView)vista.findViewById(R.id.rv_clienteCobranza);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        OnCargarClientes();
        return vista;
    }

    private void OnCargarClientes()
    {
        String url = String.format(SrvCmacIca.GET_CLIENTES_COBRANZA, UPreferencias.ObtenerCodigoPersonaLogeo(getActivity()));
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
    private void ProcesarClientes(JSONObject response)
    {
        try {
            if (response.getBoolean("IsCorrect")){

                JSONArray ListaClientesCobranza = response.getJSONArray("Data");
                ClienteCobranzaModel[] ArrayClientesCobranza= gson.fromJson(ListaClientesCobranza.toString(), ClienteCobranzaModel[].class);

                adaptador = new AdaptadorClienteCobranza(getActivity(), Arrays.asList(ArrayClientesCobranza));
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

}
