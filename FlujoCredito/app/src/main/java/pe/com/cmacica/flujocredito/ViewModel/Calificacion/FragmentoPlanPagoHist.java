package pe.com.cmacica.flujocredito.ViewModel.Calificacion;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.List;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.Calificacion.HistPlanPagoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion.AdptadorPlanPagoHist;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.ViewModel.Seguridad.ActividadLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoPlanPagoHist extends DialogFragment {

    private static final String TAG = ActividadHistPlanPago.class.getSimpleName();

    private Context context;
    String cCtaCod;
    private Gson gson = new Gson();
    private RecyclerView recyclerView;
    private AdptadorPlanPagoHist adaptador;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progressDialog ;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Obtener instancia de la action bar
        ActionBar actionBar = ((AppCompatActivity) getActivity())
                .getSupportActionBar();

        if (actionBar != null) {
            // Habilitar el Up Button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_plan_pago_hist, container, false);
        layoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvHistPlanPago);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addItemDecoration(new DecoracionLineaDivisoria(context));
        //iniciarHora(view);// Setear hora inicial
        // iniciarFecha(view);// Setear fecha inicial

        OnListarPlanPago();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_planpago_hist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                // procesarDescartar()

                ((Activity) context).finish();
                break;
            //case R.id.action_save:
                // procesarGuardar()
              //  break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void  CrearInstancia(String cCtaCod , Context contexto){
        // progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Obteniendo calificación.");

        context = contexto;
        this.cCtaCod = cCtaCod;


    }
    private void OnListarPlanPago(){

        progressDialog = ProgressDialog.show(getActivity(),"Espere por favor","Aviso.");
        String url = String.format(SrvCmacIca.GET_PLANPAGO_HIST,cCtaCod, UPreferencias.ObtenerImei(context) );

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
                                        progressDialog.cancel();
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }
                        )
                );
    }
    public void CargarAdpatador(JSONObject response){
        try {
            progressDialog.cancel();
            if (response.getBoolean("IsCorrect")){
                JSONArray DetCalif = response.getJSONArray("Data");
                HistPlanPagoModel[] ArrayPlanPago = gson.fromJson(DetCalif.toString(),HistPlanPagoModel[].class);

                List<HistPlanPagoModel> lista = Arrays.asList(ArrayPlanPago);

                for(HistPlanPagoModel item :lista){
                    switch (item.getEstadoCuota()){
                        case 1:
                            item.setHex( getResources().getString(R.color.red));
                            break;
                        case 2:
                            item.setHex( getResources().getString(R.color.yellow));
                            break;
                        case 3:
                            item.setHex( getResources().getString(R.color.green));
                            break;
                        default:
                            item.setHex(getResources().getString(R.color.color_light));
                            break;
                    };
                }


                adaptador = new AdptadorPlanPagoHist( lista,context);
                recyclerView.setAdapter(adaptador);

                if(lista.size()<=0){

                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Este crédito no posee un plan de pago.")
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    dismiss();
                                    getActivity().finish();
                                }})
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    ///
                                    //txtPassword.setText("");
                                }
                            })
                            .show();
                }


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
