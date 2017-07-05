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
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIGastoDetDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

public class FragmentoDetGastoFteIngDep extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoDetGastoFteIngDep.class.getSimpleName();

    EditText txtMontoAlimentacion;
    EditText txtMontoEducacion;
    EditText txtMontoObligaciones;
    EditText txtMontoOtros;
    EditText txtMontoServicios;
    EditText txtMontoTransporte;
    EditText txtMontoTotal;
    double Gastottoal =0;
    String IdPersFteIngreso;

    Button btnAceptar;
    Button btnCancelar;

    View view;

    public FragmentoDetGastoFteIngDep() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? AND " +
                ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " = ?"
                ,
                new String[]{IdPersFteIngreso,"1"},
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

    public interface  OnMontoGasto{
        void  OnGastoAceptar(double monto);
    }

    OnMontoGasto listener;

    public void IniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragmento_det_gasto_fte_ing_dep, container, false);

        txtMontoAlimentacion = (EditText) view.findViewById(R.id.txtMontoAlimentacion);
        txtMontoEducacion = (EditText) view.findViewById(R.id.txtMontoEducacion);
        txtMontoOtros = (EditText) view.findViewById(R.id.txtMontoOtros);
        txtMontoObligaciones = (EditText) view.findViewById(R.id.txtMontoObligaciones);
        txtMontoServicios = (EditText) view.findViewById(R.id.txtMontoServicios);
        txtMontoTransporte = (EditText) view.findViewById(R.id.txtMontoTransporte);

        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);
        btnAceptar = (Button) view.findViewById(R.id.btnAceptarDetGastoFteIgrDep);
        btnCancelar = (Button) view.findViewById(R.id.btnCancelarDetGastoFteIgrDep);

        //region eventos textchanged

        txtMontoAlimentacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });
        txtMontoEducacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });
        txtMontoOtros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });
        txtMontoObligaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });
        txtMontoServicios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });
        txtMontoTransporte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OnSumaTotal();
            }
        });


        btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*double MontoTotalGasto;
                        if (TextUtils.isEmpty(txtMontoTotal.getText().toString())){
                            MontoTotalGasto=0;
                        }else {
                            MontoTotalGasto =Double.parseDouble(txtMontoTotal.getText().toString());
                        }
                        */
                        OnGurdarLocal();
                        listener.OnGastoAceptar(Gastottoal);
                        dismiss();
                    }
                }
        );

        btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //listener.OnGastoAceptar(0);
                        dismiss();

                    }
                }
        );

        //endregion
        //IniciarGastos();
        getActivity().getSupportLoaderManager().restartLoader(1, null, this);
        return view;
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
                        txtMontoTransporte.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 4:
                        txtMontoServicios.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 5:
                        txtMontoObligaciones.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 6:
                        txtMontoOtros.setText(String.valueOf(gastoSel.nMonto));
                        break;
                }


            }
            OnSumaTotal();

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnMontoGasto) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó Detalle gasto");

        }
    }
    private void OnSumaTotal(){
        try {
            double MontoAlimentacion;
            double MontoEducacion;
            double MontoOtros;
            double MontoObligaciones;
            double MontoServicios;
            double MontoTransporte;
           // double Total;

            if(TextUtils.isEmpty(txtMontoAlimentacion.getText().toString())){
                MontoAlimentacion = 0;
            }else{
                MontoAlimentacion = UGeneral.RoundDouble( Double.parseDouble(txtMontoAlimentacion.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoEducacion.getText().toString())){
                MontoEducacion = 0;
            }else{
                MontoEducacion = UGeneral.RoundDouble( Double.parseDouble(txtMontoEducacion.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoOtros.getText().toString())){
                MontoOtros = 0;
            }else{
                MontoOtros = UGeneral.RoundDouble( Double.parseDouble(txtMontoOtros.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoObligaciones.getText().toString())){
                MontoObligaciones = 0;
            }else{
                MontoObligaciones =UGeneral.RoundDouble( Double.parseDouble(txtMontoObligaciones.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoServicios.getText().toString())){
                MontoServicios = 0;
            }else{
                MontoServicios = UGeneral.RoundDouble( Double.parseDouble(txtMontoServicios.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoTransporte.getText().toString())){
                MontoTransporte = 0;
            }else{
                MontoTransporte = UGeneral.RoundDouble( Double.parseDouble(txtMontoTransporte.getText().toString()));
            }
            Gastottoal =UGeneral.RoundDouble( MontoAlimentacion+MontoEducacion+MontoOtros+MontoObligaciones+MontoServicios+MontoTransporte);
            //Constantes.MontoGasto = Total;
            txtMontoTotal.setText( Double.toString(Gastottoal));
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void OnGurdarLocal(){

        List<PersFIGastoDetDto> result = new ArrayList<>();

        //Inicializando los gastos
        PersFIGastoDetDto gasto = new PersFIGastoDetDto();

        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 1;
        if(txtMontoAlimentacion.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else{
            gasto.nMonto = Double.parseDouble(txtMontoAlimentacion.getText().toString());
        }
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);

        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 2;
        if(txtMontoEducacion.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else{
            gasto.nMonto = Double.parseDouble(txtMontoEducacion.getText().toString());
        }
        gasto.cDescripcionGasto = "EDUCACION";
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);

        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 3;
        if(txtMontoTransporte.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else {
            gasto.nMonto = Double.parseDouble(txtMontoTransporte.getText().toString());
        }
        gasto.cDescripcionGasto = "TRANSPORTE";
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 4;
        if(txtMontoServicios.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else{
            gasto.nMonto = Double.parseDouble(txtMontoServicios.getText().toString());
        }
        gasto.cDescripcionGasto = "SERVICIOS";
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 5;
        if (txtMontoObligaciones.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else{
            gasto.nMonto = Double.parseDouble(txtMontoObligaciones.getText().toString());
        }
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 6;
        if(txtMontoOtros.getText().toString().equals("")){
            gasto.nMonto = 0.0;
        }else{
            gasto.nMonto = Double.parseDouble(txtMontoOtros.getText().toString());
        }
        gasto.cDescripcionGasto = "OTROS";
        gasto.IdPersFteIngreso = IdPersFteIngreso;
        result.add(gasto);

        new UConsultas.TareaUpdateGasto(getActivity().getContentResolver(),result).execute();

    }



}
