package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ActualizaMontoFteIgrIndp;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIGastoDetDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;


public class FragmentoActivoFijoDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoActivoFijoDet.class.getSimpleName();

    EditText txtMontoInmuebles;
    EditText txtMontoMaqEq;
    EditText txtMontoTotal;

    double nMontoTotal =0;

    Button btnAceptar;
    Button btnCancelar;

    View view;
    ActualizaMontoFteIgrIndp listener;
    String IdPersFteIngreso;

    public void IniciarDialogo(String idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }

    public FragmentoActivoFijoDet() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ActualizaMontoFteIgrIndp) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó Detalle gasto");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_activo_fijo_det, container, false);

        txtMontoInmuebles = (EditText) view.findViewById(R.id.txtMontoInmuebles);
        txtMontoMaqEq = (EditText) view.findViewById(R.id.txtMontoMaqEq);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        txtMontoInmuebles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoActivoFijo();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        txtMontoMaqEq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoActivoFijo();
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* double nMontoMaqEq = 0;


                if(TextUtils.isEmpty(txtMontoMaqEq.getText().toString())){
                    nMontoMaqEq = 0;
                }else{
                    nMontoMaqEq = Double.parseDouble(txtMontoMaqEq.getText().toString());
                }
                txtMontoMaqEq.setText(String.valueOf(UGeneral.round((float) nMontoMaqEq,2 )));*/
            }
        });

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar) ;
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar) ;

        btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnGurdarLocal();
                        listener.OnActualizaMonto( nMontoTotal,3);
                        dismiss();
                    }
                }
        );

        btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //OnGurdarLocal();
                        //listener.OnActualizaMonto(nMontoTotal,3);
                        dismiss();
                    }
                }
        );

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);

        return view;
    }

    private void OnIniciarGastoLocal(Cursor query){

        try{
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());

            List<PersFIGastoDetDto> ListaGasto = new ArrayList<>();

            while (query.moveToNext()){
                ListaGasto.add( UConsultas.ConverCursorToPersFIGastoDetDto(query));
            }

            for(PersFIGastoDetDto gastoSel :ListaGasto) {
                switch (gastoSel.nPrdConceptoCod) {
                    case 1:
                        txtMontoInmuebles.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoMaqEq.setText(String.valueOf(gastoSel.nMonto));
                        break;

                }


            }

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }

    public void OnCalcularMontoActivoFijo(){

        try{

            double nMontoInmuebles,nMontoMaqEq = 0;

            if(TextUtils.isEmpty(txtMontoInmuebles.getText().toString())){
                nMontoInmuebles = 0;
            }else{
                nMontoInmuebles =UGeneral.RoundDouble( Double.parseDouble(txtMontoInmuebles.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoMaqEq.getText().toString())){
                nMontoMaqEq = 0;
            }else{
                nMontoMaqEq = UGeneral.RoundDouble( Double.parseDouble(txtMontoMaqEq.getText().toString()));
            }

            nMontoTotal = UGeneral.RoundDouble(  nMontoInmuebles + nMontoMaqEq);
            txtMontoTotal.setText(String.valueOf( nMontoTotal));

        }catch (Exception e){

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? AND " +
                        ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " = ?"
                ,
                new String[]{IdPersFteIngreso,"4"},
                null);

        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnIniciarGastoLocal(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void OnGurdarLocal(){

        try{
            List<PersFIGastoDetDto> result = new ArrayList<>();

            //Inicializando los gastos
            PersFIGastoDetDto gasto = new PersFIGastoDetDto();

            gasto.nTpoGasto = 4;
            gasto.nPrdConceptoCod = 1;
            if (txtMontoInmuebles.getText().toString().equals(""))
            {
                gasto.nMonto=0.0;
            }else{
                gasto.nMonto = (double) UGeneral.round( Float.parseFloat(txtMontoInmuebles.getText().toString()),2 );
                gasto.nMonto = UGeneral.RoundDouble( Double.parseDouble(txtMontoInmuebles.getText().toString()));

            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 4;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoMaqEq.getText().toString().equals("")){
                gasto.nMonto=0.0;
            }else{
                gasto.nMonto = UGeneral.RoundDouble( Double.parseDouble(txtMontoMaqEq.getText().toString()));
            }
            gasto.cDescripcionGasto = "EDUCACIÓN";
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);


            new UConsultas.TareaUpdateGasto(getActivity().getContentResolver(),result).execute();
        }catch (Exception ex){
            Toast.makeText(
                    getActivity(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
