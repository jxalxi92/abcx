package pe.com.cmacica.flujocredito.ViewModel.Recuperaciones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones.AdaptadorProgramacion;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;

public class ActividadProgramacionRecuperaciones extends AppCompatActivity {
    private static final String TAG = ActividadProgramacionRecuperaciones.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorProgramacion adaptador;

private static List<ClienteRecuperacionModel>ListaProgramados;
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
        showToolbar(getResources().getString(R.string.Programación), true);

      adaptador = new AdaptadorProgramacion(this, ListaProgramados);
        recyclerView.setAdapter(adaptador);
        /*recyclerView.addItemDecoration(new DecoracionLineaDivisoria(this));*/

    }
    private void showToolbar(String tittle, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
