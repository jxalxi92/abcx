package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoComentarioFteIngreso extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoComentarioFteIngreso.class.getSimpleName();
    String IdDigitacion;

    EditText txtComentario1;
    EditText TxtComentario2;
    String IdPersFteIngreso;
    View view;

    public static FragmentoComentarioFteIngreso newInstance(String Codigocliente){

        FragmentoComentarioFteIngreso fragment = new FragmentoComentarioFteIngreso();
        Bundle args = new Bundle();
        args.putString(Constantes.KeyIdPersona, Codigocliente);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentoComentarioFteIngreso() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_comentario_fte_ingreso, container, false);
        IdDigitacion =getArguments().getString(Constantes.KeyIdPersona);

        txtComentario1 = (EditText) view.findViewById(R.id.txtComentarioEvalSocioEcoUnidadFamiliar);
        TxtComentario2 = (EditText) view.findViewById(R.id.txtComentarioEvolEcoUnidadFamiliar);

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);
        //getActivity().getSupportLoaderManager().initLoader(0, null, this);
        return view;
    }

    public void OnCargarDatosLocales(Cursor query){

        try{
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());
            if (!query.moveToNext()){
                return;
            }

            query.moveToPosition(0);
            txtComentario1.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.cComentario1));
            TxtComentario2.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.cComentario2));
            IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso);
        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d("Cursor ingreso egreso:",IdDigitacion);
        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion + " = ?",
                new String[]{IdDigitacion}, null);

        return cursor;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnCargarDatosLocales(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void OnGuardar(){

        Log.d(TAG,"Updade:" +IdDigitacion);
        if (txtComentario1.getText().toString().equals("") || TxtComentario2.getText().toString().equals("") ){
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Ingrese los dos comentarios correspondiente a la fuente Dependiente.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir
                            return;
                        }
                    })
                    .show();
        }else{
            ContentValues valores = new ContentValues();
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.cComentario1,txtComentario1.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.cComentario2,TxtComentario2.getText().toString());

            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe,"1");

            new UConsultas.TareaUpdateFteDep(getActivity().getContentResolver(),valores,IdPersFteIngreso)
                    .execute(ContratoDbCmacIca.PersFIDependienteTable.crearUriPersFIDependiente(IdPersFteIngreso));

            ContentValues valores2 = new ContentValues();

            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe3,"1");
            new UConsultas.TareaUpdateFteIngreso(getActivity().getContentResolver(),valores2,IdPersFteIngreso)
                    .execute();

            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Agregado correctamente.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir
                            return;
                        }
                    })
                    .show();
        }

    }


}
