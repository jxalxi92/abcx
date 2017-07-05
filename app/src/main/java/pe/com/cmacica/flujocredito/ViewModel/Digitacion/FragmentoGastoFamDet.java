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

public class FragmentoGastoFamDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoGastoFamDet.class.getSimpleName();

    EditText txtMontoAlimentacion;
    EditText txtMontoEducacion;
    EditText txtMontoObligaciones;
    EditText txtMontoOtros;
    EditText txtMontoServicios;
    EditText txtMontoServDom;
    EditText txtMontoTransporte;
    EditText txtMontoAlquiler;
    EditText txtMontoTotal;

    double nMontoTotal =0;
    String IdPersFteIngreso;
    Button btnAceptar;
    Button btnCancelar;

    View view;

    ActualizaMontoFteIgrIndp listener;

    public void IniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }

    public FragmentoGastoFamDet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_gasto_fam_det, container, false);

        txtMontoAlimentacion = (EditText) view.findViewById(R.id.txtMontoAlimentacion);
        txtMontoEducacion = (EditText) view.findViewById(R.id.txtMontoEducacion);
        txtMontoObligaciones = (EditText) view.findViewById(R.id.txtMontoObligaciones);
        txtMontoOtros = (EditText) view.findViewById(R.id.txtMontoOtros);
        txtMontoServicios = (EditText) view.findViewById(R.id.txtMontoServicios);
        txtMontoServDom = (EditText) view.findViewById(R.id.txtMontoServDom);
        txtMontoTransporte = (EditText) view.findViewById(R.id.txtMontoTransporte);
        txtMontoAlquiler = (EditText) view.findViewById(R.id.txtMontoAlquiler);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        //region Textchanged
        txtMontoAlimentacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoEducacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoObligaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoOtros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoServicios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoServDom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoTransporte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoAlquiler.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularGastoFamiliar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion
        getActivity().getSupportLoaderManager().restartLoader(1, null, this);

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar) ;
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar) ;

        btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnGuardarLocal();
                        listener.OnActualizaMonto(nMontoTotal,7);
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


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? AND " +
                        ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " = ?"
                ,
                new String[]{IdPersFteIngreso,"7"},
                null);

        return cursor;
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
                        txtMontoAlimentacion.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoEducacion.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 3:
                        txtMontoAlquiler.setText(String.valueOf(gastoSel.nMonto));
                    case 4:
                        txtMontoTransporte.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 5:
                        txtMontoServicios.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 6:
                        txtMontoServDom.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 7:
                        txtMontoObligaciones.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 8:
                        txtMontoOtros.setText(String.valueOf(gastoSel.nMonto));
                        break;
                }


            }

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnIniciarGastoLocal(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void OnCalcularGastoFamiliar(){
        try{

            double nMontoAlimentacion,nMontoEducacion,MontoObligaciones,nMontoOtros,nMontoServicios,
            nMontoServDom,nMontoTransporte,nMontoAlquiler = 0;

            if(TextUtils.isEmpty(txtMontoAlimentacion.getText().toString())){
                nMontoAlimentacion = 0;
            }else{
                nMontoAlimentacion = UGeneral.RoundDouble( Double.parseDouble(txtMontoAlimentacion.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoEducacion.getText().toString())){
                nMontoEducacion = 0;
            }else{
                nMontoEducacion = UGeneral.RoundDouble( Double.parseDouble(txtMontoEducacion.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoObligaciones.getText().toString())){
                MontoObligaciones = 0;
            }else{
                MontoObligaciones = UGeneral.RoundDouble( Double.parseDouble(txtMontoObligaciones.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoOtros.getText().toString())){
                nMontoOtros = 0;
            }else{
                nMontoOtros = UGeneral.RoundDouble( Double.parseDouble(txtMontoOtros.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoServicios.getText().toString())){
                nMontoServicios = 0;
            }else{
                nMontoServicios = UGeneral.RoundDouble( Double.parseDouble(txtMontoServicios.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoServDom.getText().toString())){
                nMontoServDom = 0;
            }else{
                nMontoServDom = UGeneral.RoundDouble( Double.parseDouble(txtMontoServDom.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoTransporte.getText().toString())){
                nMontoTransporte = 0;
            }else{
                nMontoTransporte = UGeneral.RoundDouble( Double.parseDouble(txtMontoTransporte.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoAlquiler.getText().toString())){
                nMontoAlquiler = 0;
            }else{
                nMontoAlquiler = UGeneral.RoundDouble( Double.parseDouble(txtMontoAlquiler.getText().toString()));
            }

            nMontoTotal =UGeneral.RoundDouble(nMontoAlimentacion+nMontoEducacion+MontoObligaciones+nMontoOtros+nMontoServicios+nMontoServDom+nMontoTransporte+nMontoAlquiler);
            txtMontoTotal.setText(String.valueOf( nMontoTotal));

        }catch (Exception e){

        }
    }

    public void OnGuardarLocal(){
        try{
            List<PersFIGastoDetDto> result = new ArrayList<>();

            //Inicializando los gastos
            PersFIGastoDetDto gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoAlimentacion.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoAlimentacion.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoEducacion.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoEducacion.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 3;
            if(txtMontoAlquiler.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoAlquiler.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 4;
            if(txtMontoTransporte.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoTransporte.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 5;
            if(txtMontoServicios.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoServicios.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 6;
            if(txtMontoServDom.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoServDom.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 7;
            if(txtMontoObligaciones.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoObligaciones.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 7;
            gasto.nPrdConceptoCod = 8;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
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
}
