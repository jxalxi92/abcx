package pe.com.cmacica.flujocredito.ViewModel.General;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Base.ActualizaMontoFteIgrIndp;
import pe.com.cmacica.flujocredito.Base.IActualizaPersonaFteIngreso;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.General.AdaptadorPersona;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.DbCmacIcaHelper;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.ActividadFteIgrDep;

public class ActividadBusquedaPersona extends AppCompatActivity{

    private final static String TAG= ActividadBusquedaPersona.class.getName().toString();
    private DbCmacIcaHelper manejadorDB;

    Cursor cursor;
    String consulta;
    AdaptadorPersona adp;

   // IActualizaPersonaFteIngreso listener;
    private RecyclerView recyclerView;

    static String IdPersFteIngreso;

    public static void launch(Activity actividad, String pIdPersFteIngreso){

        Intent intent = createInstance(actividad,pIdPersFteIngreso );
        actividad.startActivityForResult(intent, 100 );

    }
    public static Intent createInstance(Context contexto, String pIdPersFteIngreso){
        Intent intent = new Intent(contexto, ActividadBusquedaPersona.class);

        IdPersFteIngreso = pIdPersFteIngreso;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manejadorDB = new DbCmacIcaHelper(this);
        setContentView(R.layout.actividad_busqueda_persona);
        recyclerView = (RecyclerView) findViewById(R.id.reciclador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DecoracionLineaDivisoria(this));

        //listener = new ActividadFteIgrDep();
        adp= new AdaptadorPersona(this,IdPersFteIngreso);
        recyclerView.setAdapter(adp);


    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    //Log.d(TAG, "onQueryTextSubmit ");
                    consulta = s;
                    if (!consulta.equals("")){
                        if (consulta.length()>4){

                            cursor=filterpersona(consulta);

                            adp.swapCursor(cursor);
                        }

                    }else{
                        adp.swapCursor(null);
                    }


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //Log.d(TAG, "onQueryTextChange ");
                    consulta = s;
                    if (!consulta.equals("")){
                        if (consulta.length()>4){

                            cursor=filterpersona(consulta);

                            adp.swapCursor(cursor);
                        }else{
                            adp.swapCursor(null);
                        }
                    }

                    return false;
                }

            });

        }
        return true;

    }
    public Cursor filterpersona (String search){
        //Obtener Base de Datos
        SQLiteDatabase db  = manejadorDB.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+
                DbCmacIcaHelper.Tablas.Personas+
                " WHERE "+ ContratoDbCmacIca.PersonaTable.cPersNombre+
                 "  LIKE  '%" +search + "%' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }
}
