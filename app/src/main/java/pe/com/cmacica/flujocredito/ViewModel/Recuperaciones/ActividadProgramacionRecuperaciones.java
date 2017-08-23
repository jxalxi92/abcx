package pe.com.cmacica.flujocredito.ViewModel.Recuperaciones;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ProgramacionDetModel;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ProgramacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones.AdaptadorProgramacion;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;


public class ActividadProgramacionRecuperaciones extends AppCompatActivity {
    private static final String TAG = ActividadProgramacionRecuperaciones.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorProgramacion adaptador;
    private FloatingActionButton fab_Actualizar;
    private Button btn_Registrar;
    private ProgressDialog progressDialog;

public static List<ClienteRecuperacionModel>ListaProgramados;
    public static void createInstance(Activity activity, List<ClienteRecuperacionModel> pListaProgramados) {

        ListaProgramados = pListaProgramados;
        Intent intent = getLaunchIntent(activity);
        activity.startActivity(intent);
    }
    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, ActividadProgramacionRecuperaciones.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_programacion_recuperaciones);
        recyclerView = (RecyclerView) findViewById(R.id.rv_clienteProgramacion);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fab_Actualizar=(FloatingActionButton)findViewById(R.id.fab_Actualizar);
        btn_Registrar=(Button)findViewById(R.id.btn_Registrar) ;
        showToolbar(getResources().getString(R.string.Programación), true);

        for (int i=0;i<ListaProgramados.size();i++)
        {
            ListaProgramados.get(i).setSeleccionado(false);
        }
        adaptador = new AdaptadorProgramacion(this, ListaProgramados);
        recyclerView.setAdapter(adaptador);
        recyclerView.addItemDecoration(new DecoracionLineaDivisoria(this));

        fab_Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Actualizar();
            }
        });
        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Guardar();

            }
        });
    }
    private void showToolbar(String tittle, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void Actualizar() {

        List<ClienteRecuperacionModel>ListaAuxiliar=new ArrayList<ClienteRecuperacionModel> ();
        ClienteRecuperacionModel Clie;
        for (int i=0;i<ListaProgramados.size();i++)
        {
            if (ListaProgramados.get(i).isSeleccionado()==false)
            {
                Clie=ListaProgramados.get(i);
                ListaAuxiliar.add(Clie);
                ListaProgramados.remove(i);
            }
        }
        Collections.sort(ListaProgramados, (o1, o2) -> o1.getPosicion().compareTo(o2.getPosicion()));

        for (int i=0;i<ListaProgramados.size();i++)
        {
            ListaProgramados.get(i).setPosicion(String.valueOf(i+1));
        }
        if (ListaAuxiliar.size()!=0)
        {
            for (int i=0;i<ListaAuxiliar.size();i++)
            {
                Clie=ListaAuxiliar.get(i);
                ListaProgramados.add(Clie);
            }
        }

        adaptador = new AdaptadorProgramacion(this, ListaProgramados);
        recyclerView.setAdapter(adaptador);
        recyclerView.addItemDecoration(new DecoracionLineaDivisoria(this));

    }

    private void Guardar()
    {
        progressDialog = ProgressDialog.show(this,"Espere por favor","Guardando Datos");
        Gson gsonpojo = new GsonBuilder().create();
        ProgramacionModel Prog=new ProgramacionModel();
        Prog.cPersCodAna= UPreferencias.ObtenerCodigoPersonaLogeo(this);
        Prog.cUser=UPreferencias.ObtenerUserLogeo(this);
        Prog.cImei=UPreferencias.ObtenerImei(this);
        Prog.cAgeCod= UPreferencias.ObtenerCodAgencia(this);
        Prog.nEstado=1;
        ProgramacionDetModel ProgDet;
        List<ProgramacionDetModel> ListProgDet=new ArrayList<ProgramacionDetModel>();

        for (int i=0;i<ListaProgramados.size();i++)
        {
            ProgDet = new ProgramacionDetModel();
            ProgDet.cPersCodClie=ListaProgramados.get(i).getCodigo();
            ProgDet.dFechaVisita=ListaProgramados.get(i).getFechaRec();
            ProgDet.nOrdenVisita=ListaProgramados.get(i).getPosicion();
            ProgDet.cCtaCodProg=ListaProgramados.get(i).getcCodcred();
            ListProgDet.add(i,ProgDet);
        }
        Prog.ProgramacionDetalle=ListProgDet;

        String json = gsonpojo.toJson(Prog);
        HashMap<String, String> cabeceras = new HashMap<>();

        new RESTService(this).post(SrvCmacIca.POST_REGISTRO_PROGRAMACION, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.cancel();
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
    private void ProcesarGuardar(JSONObject response)
    {
        try {
            if (response.getBoolean("IsCorrect")) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage(response.getString("Message"))
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                //ActividadLogin.this.finish();
                            }})

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                finish();
                            }
                        })
                        .show();
            }
        }
        catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed (){
        fragmentoListaRecuperaciones frag=new fragmentoListaRecuperaciones();

     for (int i=0;i<ListaProgramados.size();i++)
     {
         for (int j=0;j<frag.ListClientes.size();j++)
         {
             if (frag.ListClientes.get(j).getDocumento()==ListaProgramados.get(i).getDocumento())
             {
                 frag.ListClientes.get(j).setSeleccionado(true);
             }
         }
     }
        super.onBackPressed();

    }
}
