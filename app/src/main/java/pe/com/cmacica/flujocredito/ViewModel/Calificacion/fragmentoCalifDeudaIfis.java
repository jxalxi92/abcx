package pe.com.cmacica.flujocredito.ViewModel.Calificacion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Calificacion.DetCalifIfiModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdaptadorDeudaIfis;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;

public class fragmentoCalifDeudaIfis extends DialogFragment {

    private static final String TAG = fragmentoCalifDeudaIfis.class.getSimpleName();
    private View rootView;
    private RecyclerView recyclerView;
    private Gson gson = new Gson();
    private AdaptadorDeudaIfis adaptador;

    private Context context;
    public fragmentoCalifDeudaIfis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        rootView = inflater.inflate(R.layout.fragmento_calif_deuda_ifis, container);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDeudaIfis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //setadapter

       /* ArrayList<DetCalifIfiModel> lista = new ArrayList<DetCalifIfiModel>() ;
        lista.add(new DetCalifIfiModel("cmac ica","2012"));
        lista.add(new DetCalifIfiModel("cmac ica","2013"));
        lista.add(new DetCalifIfiModel("cmac ica","2014"));
        adaptador = new AdaptadorDeudaIfis(this.getActivity(), lista);
        recyclerView.setAdapter(adaptador);*/

        this.getDialog().setTitle("Lista Ifis Deudoras");
        //get your recycler view and populate it.
        return rootView;
    }

    public void  CrearInstancia(String codsbs, String fecha, Context context){
       // progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Obteniendo calificación.");

        this.context = context;

        String url = String.format(SrvCmacIca.GET_DEUDA_IFIS,codsbs,
                fecha.substring(6,10) +"-" +
                fecha.substring(3,5) +"-" +
                fecha.substring(0,2) );

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
                                        CargarAdpatador(response);                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }
                        )
                );

    }

    public void CargarAdpatador(JSONObject response){
        try {

            if (response.getBoolean("IsCorrect")){
                JSONArray DetCalif = response.getJSONArray("Data");
                DetCalifIfiModel[] ArrayDetCalif = gson.fromJson(DetCalif.toString(),DetCalifIfiModel[].class);

                adaptador = new AdaptadorDeudaIfis(context,Arrays.asList(ArrayDetCalif) );
                recyclerView.setAdapter(adaptador);
            }


        } catch (JSONException e) {

            Log.d(TAG, e.getMessage());
            //Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }



}
