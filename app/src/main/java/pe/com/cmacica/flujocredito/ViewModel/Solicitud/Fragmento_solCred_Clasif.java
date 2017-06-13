package pe.com.cmacica.flujocredito.ViewModel.Solicitud;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.Arrays;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.DatoPersonaSolicitudModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.FrecuenciaPagoModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.ProductoModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.SolCredClasifModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento_solCred_Clasif extends AppCompatDialogFragment {
    private static final String TAG = Fragmento_solCred_Clasif.class.getSimpleName();
    private Gson gson = new Gson();
    private View Vista;
    private Spinner spn_Periodo;
    private EditText txt_Monto;
    private TextView lbl_Ventas;
    private TextView lbl_Condicion;
    private Button btnGuardar;

    private ProductoModel ProductoSel;
    private DatoPersonaSolicitudModel Cliente;
    private FrecuenciaPagoModel FrecuenciaPagoSel;

    private Double VentasAnuales;
    public Fragmento_solCred_Clasif() {
        // Required empty public constructor
    }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState)  {

        Vista=inflater.inflate(R.layout.fragmento_sol_cred_clasif, container, false);
        spn_Periodo = (Spinner) Vista.findViewById(R.id.spn_Periodo);
        txt_Monto = (EditText) Vista.findViewById(R.id.txt_Monto);
        lbl_Ventas = (TextView) Vista.findViewById(R.id.lbl_Ventas);
        lbl_Condicion = (TextView) Vista.findViewById(R.id.lbl_Condicion);
        btnGuardar=(Button) Vista.findViewById(R.id.btnGuardar);
        OnCargarFrecPago();

        spn_Periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FrecuenciaPagoSel = (FrecuenciaPagoModel) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            });
        return  Vista;
    }

    private void OnCargarFrecPago() {

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
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
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
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayFrecPago)
            );

            AdpSpinnerFrecPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_Periodo.setAdapter(AdpSpinnerFrecPago);


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

    private void OnGuardar(){
        Gson gsonpojo = new GsonBuilder().create();
        SolCredClasifModel SolCredClasif=new SolCredClasifModel();

        SolCredClasif.Fecha= UGeneral.obtenerTiempoCorto();
        SolCredClasif.Doi=Cliente.getDatoPersonal().getCodigoPersona();

        SolCredClasif.Monto=Double.parseDouble(txt_Monto.getText().toString());
        SolCredClasif.Ventas1=VentasAnuales;
    }
}



