package pe.com.cmacica.flujocredito.ViewModel.Solicitud;



import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.DatoPersonaSolicitudModel;
import pe.com.cmacica.flujocredito.Model.Solicitud.SolCredClasifModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

public class Fragmento_solCred_Clasif extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = Fragmento_solCred_Clasif.class.getSimpleName();
    private View Vista;
    private Spinner spn_Periodo;
    private EditText txt_Monto;
    private TextView lbl_Ventas;

    private Button btnAceptar,btnProcesar;


    private DatoPersonaSolicitudModel Cliente_L;
    private ConstanteModel ConstanteSel;

    private Double Monto,VentasAnuales;
    private Double MontoSolicitado;
    private ProgressDialog progressDialog ;

    public Fragmento_solCred_Clasif() {
        // Required empty public constructor
    }

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState)  {

        Vista=inflater.inflate(R.layout.fragmento_sol_cred_clasif,null);
        spn_Periodo = (Spinner) Vista.findViewById(R.id.spn_Periodo);
        txt_Monto = (EditText) Vista.findViewById(R.id.txt_Monto);
        lbl_Ventas = (TextView) Vista.findViewById(R.id.lbl_Ventas);
       // lbl_Condicion = (TextView) Vista.findViewById(R.id.lbl_Condicion);
        btnAceptar=(Button) Vista.findViewById(R.id.btnAceptar);
        btnProcesar=(Button) Vista.findViewById(R.id.btnProcesar);
        getActivity().getSupportLoaderManager().restartLoader(1, null, this);
        txt_Monto.setText(String.valueOf(MontoSolicitado));

        spn_Periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (txt_Monto.getText().length()==0)
               {
                   Monto=0.0;
               }
               else
               {
                   ConstanteSel = (ConstanteModel) parent.getItemAtPosition(position);
                   Monto=Double.parseDouble(txt_Monto.getText().toString());
                   VentasAnuales=ConstanteSel.getEquivalente()*Monto;
                   lbl_Ventas.setText(String.valueOf(VentasAnuales));
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Desea Regresar a la Ventana Anterior?")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                // ActividadLogin.this.finish();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })//sin listener
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir

                                OnGuardar();
                                btnProcesar.setEnabled(false);
                            }
                        })
                        .show();



            }
            });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return  Vista;
    }
    public void Datos(double Monto,DatoPersonaSolicitudModel Cliente)
    {
        MontoSolicitado=Monto;
        Cliente_L=Cliente;
    }


    private void OnCargarConstantes(Cursor query) {


        List<ConstanteModel> ListaConstantePeriodo = new ArrayList<ConstanteModel>();
        ListaConstantePeriodo.add(new ConstanteModel(9045,1,"TRIMESTRAL",4));
        ListaConstantePeriodo.add(new ConstanteModel(9045,2,"SEMESTRAL",2));
        ListaConstantePeriodo.add(new ConstanteModel(9045,3,"ANUAL",1));
        ListaConstantePeriodo.add(new ConstanteModel(9045,4,"MENSUAL",12));
        ListaConstantePeriodo.add(new ConstanteModel(9045,5,"QUINCENAL",25));
        ListaConstantePeriodo.add(new ConstanteModel(9045,6,"SEMANAL",52));
        ListaConstantePeriodo.add(new ConstanteModel(9045,7,"DIARIO",365));

        ArrayAdapter<ConstanteModel> adpSpinnerPeriodo = new ArrayAdapter<ConstanteModel>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                ListaConstantePeriodo
        );
        adpSpinnerPeriodo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Periodo.setAdapter(adpSpinnerPeriodo);
    }

    private void OnGuardar(){
        Gson gsonpojo = new GsonBuilder().create();
        SolCredClasifModel SolCredClasif=new SolCredClasifModel();

        SolCredClasif.Fecha= UGeneral.obtenerTiempoCorto();
        SolCredClasif.Doi=Cliente_L.getDatoPersonal().getNumeroDocumento();
        SolCredClasif.Monto=Monto;
        SolCredClasif.Ventas1=VentasAnuales;

        String json = gsonpojo.toJson(SolCredClasif);
        HashMap<String, String> cabeceras = new HashMap<>();

        new RESTService(getActivity()).post(SrvCmacIca.POST_INGRESO_VENTAS, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProcesarGuardar(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Volley: " + error.toString());
                        progressDialog.cancel();
                        // errorservice(error);
                    }
                }
                , cabeceras);
    }

    private void ProcesarGuardar(JSONObject response) {

        try {
            if (response.getBoolean("IsCorrect")) {

                Snackbar.make(Vista, "Se Guardo Correctamente los Datos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.ConstanteTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.ConstanteTable.nConsCod + " IN (?)"
                ,
                new String[]{"9045"} ,
                null);

        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnCargarConstantes(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}



