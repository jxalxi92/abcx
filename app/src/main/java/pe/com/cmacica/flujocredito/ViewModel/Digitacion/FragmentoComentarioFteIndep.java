package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;

import static pe.com.cmacica.flujocredito.R.id.txtComentario1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoComentarioFteIndep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoComentarioFteIndep extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = FragmentoComentarioFteIndep.class.getSimpleName();
    String IdDigitacion;
    String IdPersFteIngreso;
    EditText TxtComentario1;
    EditText TxtComentario2;
    EditText TxtComentario3;
    View view;

    public FragmentoComentarioFteIndep() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentoComentarioFteIndep newInstance(String Codigocliente){
        FragmentoComentarioFteIndep fragment = new FragmentoComentarioFteIndep();
        Bundle args = new Bundle();
        args.putString(Constantes.KeyIdPersona, Codigocliente);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_comentario_fte_indep, container, false);
        IdDigitacion =getArguments().getString(Constantes.KeyIdPersona);

        TxtComentario1 = (EditText) view.findViewById(txtComentario1);
        TxtComentario2 = (EditText) view.findViewById(R.id.txtComentario2);
        TxtComentario3 = (EditText) view.findViewById(R.id.txtComentario3);

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);

        return view;
    }

    public void OnCargarDatosLocales(Cursor query){

        try{
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());
            if (!query.moveToNext()){
                return;
            }

            query.moveToPosition(0);
            TxtComentario1.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.cComentario1));
            TxtComentario2.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.cComentario2));
            TxtComentario3.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.cComentario3));

            IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso);

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d("Cursor ingreso egreso:",IdDigitacion);
        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion + " = ?",
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

        if (TxtComentario1.getText().toString().equals("") || TxtComentario2.getText().toString().equals("")
                || TxtComentario3.getText().toString().equals("")){
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Ingrese los tres comentarios correspondiente a la fuente Independiente.")
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
            Log.d(TAG,"Updade:" +IdDigitacion);
            ContentValues valores = new ContentValues();
            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.cComentario1,TxtComentario1.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.cComentario2,TxtComentario2.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.cComentario3,TxtComentario3.getText().toString());

            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe,"1");

            new UConsultas.TareaUpdateFteInDep(getActivity().getContentResolver(),valores,IdPersFteIngreso)
                    .execute(ContratoDbCmacIca.PersFIInDependienteTable.crearUriPersFIInDependiente(IdPersFteIngreso));


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
