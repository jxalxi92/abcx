package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.cmacica.flujocredito.Base.ActualizaMontoFteIgrIndp;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIGastoDetDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoPasivoNoCorrienteDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoPasivoNoCorrienteDet.class.getSimpleName();
    EditText txtMontoOtros;
    EditText txtMontoPrestamo;
    EditText txtMontoProveedores;
    EditText txtMontoTotal;

    View view;

    String IdPersFteIngreso;
    double nMontoTotal =0;
    ActualizaMontoFteIgrIndp listener;
    Button btnAceptar;
    Button btnCancelar;


    public void OnIniciarDialogo(String idPersFteIngreso){
        IdPersFteIngreso=idPersFteIngreso;
    }

    public FragmentoPasivoNoCorrienteDet() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragmento_pasivo_no_corriente_det, container, false);

        txtMontoOtros = (EditText) view.findViewById(R.id.txtMontoOtros);
        txtMontoPrestamo = (EditText) view.findViewById(R.id.txtMontoPrestamo);
        txtMontoProveedores = (EditText) view.findViewById(R.id.txtMontoProveedores);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        txtMontoOtros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularPasivoNoCorriente();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoPrestamo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularPasivoNoCorriente();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoProveedores.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularPasivoNoCorriente();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar) ;
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar) ;

        btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnGuardarLocal();
                        listener.OnActualizaMonto(nMontoTotal,4);
                        dismiss();
                    }
                }
        );
        btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        //IniciarPasivoNoCorriente();

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);
        return  view;
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
    private void OnIniciarGastoLocal(Cursor query){

        try{
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());
            /*if (!query.moveToNext()){
                return;
            }*/
            List<PersFIGastoDetDto> ListaGasto = new ArrayList<>();

            while (query.moveToNext()){
                ListaGasto.add( UConsultas.ConverCursorToPersFIGastoDetDto(query));
            }

            for(PersFIGastoDetDto gastoSel :ListaGasto) {
                switch (gastoSel.nPrdConceptoCod) {
                    case 1:
                        txtMontoProveedores.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoPrestamo.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 3:
                        txtMontoOtros.setText(String.valueOf(gastoSel.nMonto));
                        break;

                }


            }

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }

    private void OnCalcularPasivoNoCorriente(){
        try{

            double nMontoOtros,nMontoPrestamo,MontoProveedores=0;

            if(TextUtils.isEmpty(txtMontoOtros.getText().toString())){
                nMontoOtros = 0;
            }else{
                nMontoOtros = UGeneral.RoundDouble( Double.parseDouble(txtMontoOtros.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoPrestamo.getText().toString())){
                nMontoPrestamo = 0;
            }else{
                nMontoPrestamo = UGeneral.RoundDouble( Double.parseDouble(txtMontoPrestamo.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoProveedores.getText().toString())){
                MontoProveedores = 0;
            }else{
                MontoProveedores = UGeneral.RoundDouble( Double.parseDouble(txtMontoProveedores.getText().toString()));
            }

            nMontoTotal = UGeneral.RoundDouble( nMontoOtros + nMontoPrestamo + MontoProveedores);
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
                new String[]{IdPersFteIngreso,"8"},
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

    public void OnGuardarLocal(){
        try{

            List<PersFIGastoDetDto> result = new ArrayList<>();
            //Inicializando los gastos
            PersFIGastoDetDto gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 8;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoProveedores.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoProveedores.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 8;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoPrestamo.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoPrestamo.getText().toString());
            }

            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 8;
            gasto.nPrdConceptoCod = 3;
            if(txtMontoOtros.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoOtros.getText().toString());
            }
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
}
