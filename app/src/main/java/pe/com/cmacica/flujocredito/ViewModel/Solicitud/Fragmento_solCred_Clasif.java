package pe.com.cmacica.flujocredito.ViewModel.Solicitud;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.R;

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


    public Fragmento_solCred_Clasif() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Vista = LayoutInflater.from(getActivity()).inflate(R.layout.fragmento_sol_cred__clasif, null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("TAG", "diste click en boton Dialog");
            }


        };
        return new AlertDialog.Builder(getActivity())
                .setTitle("Changing Mesage")
                .setView(Vista)
                .setPositiveButton(android.R.string.ok,listener)
                .create();
    }

}

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        spn_Periodo = (Spinner) Vista.findViewById(R.id.spn_Periodo);
        txt_Monto = (EditText) Vista.findViewById(R.id.txt_Monto);
        lbl_Ventas = (TextView) Vista.findViewById(R.id.lbl_Ventas);
        lbl_Condicion = (TextView) Vista.findViewById(R.id.lbl_Condicion);
        return Vista;
    }*/

  /*   private void OnCargarPeriodo() {
      try {
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
       }  catch(Exception ex){
            Log.d(TAG, ex.getMessage());
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }*/

  /*  private void ProcesarFrecPago(JSONObject response){

    }*/


