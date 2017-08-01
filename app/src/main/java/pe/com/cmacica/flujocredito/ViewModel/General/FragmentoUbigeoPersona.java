package pe.com.cmacica.flujocredito.ViewModel.General;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.Arrays;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.Model.General.UbigeoModel;
import pe.com.cmacica.flujocredito.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoUbigeoPersona extends DialogFragment {
    private static final String TAG = FragmentoUbigeoPersona.class.getSimpleName();
    private View Vista;
    private Spinner spn_Departamento, spn_Provincia, spn_Distrito;
    private Button btn_Aceptar;
    private Gson gson = new Gson();
    private UbigeoModel DepartamentoSel;
    private UbigeoModel ProvinciaSel;
    private UbigeoModel DistritoSel;
    public String CodUbigeo;
    fragmento_consultar_datos frag=new fragmento_consultar_datos();

    public FragmentoUbigeoPersona() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Vista = inflater.inflate(R.layout.fragmento_ubigeo_persona, container, false);
        spn_Departamento = (Spinner) Vista.findViewById(R.id.spn_Departamento);
        spn_Provincia = (Spinner) Vista.findViewById(R.id.spn_Provincia);
        spn_Distrito = (Spinner) Vista.findViewById(R.id.spn_Distrito);
        btn_Aceptar = (Button) Vista.findViewById(R.id.btn_Aceptar);
        OnCargarDepartamentos();

        spn_Departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DepartamentoSel  = (UbigeoModel) parent.getItemAtPosition(position);

               OnCargarProvincias();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_Provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProvinciaSel  = (UbigeoModel) parent.getItemAtPosition(position);

                OnCargarDistritos();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_Distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistritoSel  = (UbigeoModel) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frag.Calculado=DistritoSel.getcUbigeoCodReniec().toString();
                Snackbar.make(Vista, "Datos asignados correctamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                onDestroyView();
            }
        });
        return Vista;
    }

    private void OnCargarDepartamentos() {
        try {

            String Url = String.format(SrvCmacIca.GET_UBIGEO, "", "1");
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
                                            ProcesarDepartamento(response);
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

    private void ProcesarDepartamento(JSONObject response) {
        try {

            JSONArray ListaDepartamentos = response.getJSONArray("Data");
            UbigeoModel[] ArrayDepartamentos = gson.fromJson(ListaDepartamentos.toString(), UbigeoModel[].class);

            ArrayAdapter<UbigeoModel> AdpSpinnerDepartamentos = new ArrayAdapter<UbigeoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayDepartamentos)
            );

            AdpSpinnerDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_Departamento.setAdapter(AdpSpinnerDepartamentos);


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

    private void OnCargarProvincias() {
        try {

            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
            if (DepartamentoSel == null) {

                return;
            }
            String Url = String.format(SrvCmacIca.GET_UBIGEO, DepartamentoSel.getcUbigeoCod().substring(1, 3),2);

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
                                            ProcesarProvincias(response);
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

    private void ProcesarProvincias(JSONObject response) {
        try {

            JSONArray ListaProvincias = response.getJSONArray("Data");
            UbigeoModel[] ArrayProvincias = gson.fromJson(ListaProvincias.toString(), UbigeoModel[].class);

            ArrayAdapter<UbigeoModel> adpSpinnerProvincias= new ArrayAdapter<UbigeoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayProvincias)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerProvincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spn_Provincia.setAdapter(adpSpinnerProvincias);

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

    private void OnCargarDistritos() {
        try {

            //progressDialog = ProgressDialog.show(getContext(),"Espere por favor","Generando calendario.");
            if (ProvinciaSel == null) {

                return;
            }
            String Url = String.format(SrvCmacIca.GET_UBIGEO, ProvinciaSel.getcUbigeoCod().substring(1, 5),3);

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
                                            ProcesarDistritos(response);
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

    private void ProcesarDistritos(JSONObject response) {
        try {

            JSONArray ListaDistritos = response.getJSONArray("Data");
            UbigeoModel[] ArrayDistritos = gson.fromJson(ListaDistritos.toString(), UbigeoModel[].class);

            ArrayAdapter<UbigeoModel> adpSpinnerDistritos= new ArrayAdapter<UbigeoModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    Arrays.asList(ArrayDistritos)
            );
            //adpSpinnerTipoCredito.
            adpSpinnerDistritos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spn_Distrito.setAdapter(adpSpinnerDistritos);

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
