package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Digitacion.AdaptadorPersonaFteIgr;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.Utilitarios.UService;



public class FragmentoPersonaFteIngreso extends Fragment //{
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = FragmentoPersonaFteIngreso.class.getSimpleName();
    private View view;
    private RecyclerView recyclerView;
    private AdaptadorPersonaFteIgr adaptador;

    public interface ListenerFteIgreso{
        public void OnSyncronizar();
    }

    ListenerFteIgreso listenerFteIgreso;

    //private BroadcastReceiver receptorSync;

    public FragmentoPersonaFteIngreso() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragmento_persona_fte_ingreso, container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.reciclador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adaptador = new AdaptadorPersonaFteIgr(getFragmentManager());
        recyclerView.setAdapter(adaptador);

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.accion_sync) {
            if(UPreferencias.ObtenerIndDesconectado(getActivity()).equals("1")){
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Ha iniciado sesión en modo desconectado, no puede realizar está operación.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){

                            }
                        })
                        .show();
            }else {
                if (UService.hayConexion(getActivity())) {

                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Sincronizar")
                            .setMessage("Si sincroniza las fuentes de ingreso del cliente, no podrá volver a sincronizar hasta el día siguiente. Asegúrece de registrar todos los datos necesario. \n¿Está seguro de sincronizar?")
                            .setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mostrarProgreso(true);
                                    sincroizar();
                                }
                            })
                            .show();

                } else {
                    Snackbar.make(view.findViewById(R.id.fragmentoPersonaFteIgr),
                            "No hay conexion disponible. La sincronización queda pendiente",
                            Snackbar.LENGTH_LONG).show();
                    mostrarProgreso(false);
                }
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_persona_fte_igr, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listenerFteIgreso = (ListenerFteIgreso) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó Detalle gasto");

        }
    }
    private void sincroizar(){

        listenerFteIgreso.OnSyncronizar();
    }
    private void mostrarProgreso(boolean mostrar) {
        view.findViewById(R.id.barra).setVisibility(mostrar ? View.VISIBLE : View.GONE);
    }

    public void ResultadoSincronizar(String mensaje){

        mostrarProgreso(false);
        Snackbar.make(view.findViewById(R.id.fragmentoPersonaFteIgr),
                mensaje, Snackbar.LENGTH_LONG).show();
    }

    //region cargar cursor DBCmacIca

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContratoDbCmacIca.DigitacionTable.URI_CONTENIDO,
                null,null,null,null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
    }

    //endregion cargar cursor DBCmacIca

}
