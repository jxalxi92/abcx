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


public class FragmentoInventarioDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoInventarioDet.class.getSimpleName();
    EditText txtMontoMercInsumo;
    EditText txtMontoProdProceso;
    EditText txtMontoProdTerminado;
    EditText txtMontoTotal;
    View view;


    String IdPersFteIngreso;
    double nMontoTotal =0;
    ActualizaMontoFteIgrIndp listener;
    Button btnAceptar;
    Button btnCancelar;


    public void OnIniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }

    public FragmentoInventarioDet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_inventario_det, container, false);

        txtMontoMercInsumo = (EditText) view.findViewById(R.id.txtMontoMercInsumo);
        txtMontoProdProceso = (EditText) view.findViewById(R.id.txtMontoProdProceso);
        txtMontoProdTerminado = (EditText) view.findViewById(R.id.txtMontoProdTerminado);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        //region Eventos textchanged

        txtMontoMercInsumo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularInventario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoProdProceso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularInventario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoProdTerminado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularInventario();
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
                        listener.OnActualizaMonto(nMontoTotal,2);
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

        //endregion

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
                        txtMontoMercInsumo.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoProdProceso.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 3:
                        txtMontoProdTerminado.setText(String.valueOf(gastoSel.nMonto));
                        break;
                }
            }

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
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
    private void OnCalcularInventario(){
        try{
            double nMontoMercInsumo,nMontoProdProceso,nMontoProdTerminado= 0;

            if(TextUtils.isEmpty(txtMontoMercInsumo.getText().toString())){
                nMontoMercInsumo = 0;
               // txtMontoMercInsumo.setText("0");
            }else{
                nMontoMercInsumo = UGeneral.RoundDouble( Double.parseDouble(txtMontoMercInsumo.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoProdProceso.getText().toString())){
                nMontoProdProceso = 0;
               // txtMontoProdProceso.setText("0");
            }else{
                nMontoProdProceso = UGeneral.RoundDouble( Double.parseDouble(txtMontoProdProceso.getText().toString()));
                //txtMontoProdProceso.setText("0");
            }

            if(TextUtils.isEmpty(txtMontoProdTerminado.getText().toString())){
                nMontoProdTerminado = 0;
                //txtMontoProdTerminado.setText("0");
            }else{
                nMontoProdTerminado = UGeneral.RoundDouble( Double.parseDouble(txtMontoProdTerminado.getText().toString()));
            }

            nMontoTotal = UGeneral.RoundDouble( nMontoMercInsumo + nMontoProdProceso + nMontoProdTerminado);
            txtMontoTotal.setText(String.valueOf(nMontoTotal));


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
                new String[]{IdPersFteIngreso,"3"},
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void OnGuardarLocal(){
        try{
            List<PersFIGastoDetDto> result = new ArrayList<>();

            //Inicializando los gastos
            PersFIGastoDetDto gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 3;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoMercInsumo.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoMercInsumo.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 3;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoProdProceso.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoProdProceso.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 3;
            gasto.nPrdConceptoCod = 3;
            if(txtMontoProdTerminado.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoProdTerminado.getText().toString());
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
