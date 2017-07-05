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

public class FragmentoGastoOpeAdmDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoDetGastoFteIngDep.class.getSimpleName();
    double nMontoTotal =0;

    EditText txtMontoAguaLuz;
    EditText txtMontoAlquiler;
    EditText txtMontoDeudas;
    EditText txtMontoOtros;
    EditText txtMontoPersonal;
    EditText txtMontoTransporte;
    EditText txtMontoTributos;
    EditText txtMontoTotal;

    Button btnAceptar;
    Button btnCancelar;
    String IdPersFteIngreso;
    View view;
    ActualizaMontoFteIgrIndp listener;

    public void IniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }
    public FragmentoGastoOpeAdmDet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragmento_gasto_ope_adm_det, container, false);

        txtMontoAguaLuz = (EditText) view.findViewById(R.id.txtMontoAguaLuz);
        txtMontoAlquiler = (EditText) view.findViewById(R.id.txtMontoAlquiler);
        txtMontoDeudas = (EditText) view.findViewById(R.id.txtMontoDeudas);
        txtMontoOtros = (EditText) view.findViewById(R.id.txtMontoOtros);
        txtMontoPersonal = (EditText) view.findViewById(R.id.txtMontoPersonal);
        txtMontoTransporte = (EditText) view.findViewById(R.id.txtMontoTransporte);
        txtMontoTributos = (EditText) view.findViewById(R.id.txtMontoTributos);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar) ;
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar) ;

        //region Eventos Textchanged y Click
        txtMontoAguaLuz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoGastoOpeAdm();
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
                OnCalcularMontoGastoOpeAdm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoDeudas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoGastoOpeAdm();
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
                OnCalcularMontoGastoOpeAdm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoPersonal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoGastoOpeAdm();
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
                OnCalcularMontoGastoOpeAdm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoTributos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoGastoOpeAdm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnGuardarLocal();
                        listener.OnActualizaMonto(nMontoTotal,6);
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
                        txtMontoPersonal.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoTributos.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 3:
                        txtMontoTransporte.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 4:
                        txtMontoAlquiler.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 5:
                        txtMontoAguaLuz.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 6:
                        txtMontoDeudas.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 7:
                        txtMontoOtros.setText(String.valueOf(gastoSel.nMonto));
                        break;
                }


            }

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void OnCalcularMontoGastoOpeAdm(){

        try{

            double nMontoAguaLuz,nMontoAlquiler,nMontoDeudas,nMontoOtros,nMontoPersonal,nMontoTransporte,nMontoTributos = 0;

            if(TextUtils.isEmpty(txtMontoAguaLuz.getText().toString())){
                nMontoAguaLuz = 0;
            }else{
                nMontoAguaLuz =UGeneral.RoundDouble( Double.parseDouble(txtMontoAguaLuz.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoAlquiler.getText().toString())){
                nMontoAlquiler = 0;
            }else{
                nMontoAlquiler = UGeneral.RoundDouble( Double.parseDouble(txtMontoAlquiler.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoDeudas.getText().toString())){
                nMontoDeudas = 0;
            }else{
                nMontoDeudas = UGeneral.RoundDouble( Double.parseDouble(txtMontoDeudas.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoOtros.getText().toString())){
                nMontoOtros = 0;
            }else{
                nMontoOtros =UGeneral.RoundDouble( Double.parseDouble(txtMontoOtros.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoPersonal.getText().toString())){
                nMontoPersonal = 0;
            }else{
                nMontoPersonal = UGeneral.RoundDouble( Double.parseDouble(txtMontoPersonal.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoTransporte.getText().toString())){
                nMontoTransporte = 0;
            }else{
                nMontoTransporte = UGeneral.RoundDouble( Double.parseDouble(txtMontoTransporte.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoTributos.getText().toString())){
                nMontoTributos = 0;
            }else{
                nMontoTributos =UGeneral.RoundDouble( Double.parseDouble(txtMontoTributos.getText().toString()));
            }

            nMontoTotal=UGeneral.RoundDouble( nMontoAguaLuz+nMontoAlquiler+nMontoDeudas+nMontoOtros+nMontoPersonal+nMontoTransporte+nMontoTributos);
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
                new String[]{IdPersFteIngreso,"5"},
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
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoPersonal.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoPersonal.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoTributos.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoTributos.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);

            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 3;
            if(txtMontoTransporte.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoTransporte.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 4;
            if(txtMontoAlquiler.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoAlquiler.getText().toString());
            }

            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 5;
            if(txtMontoAguaLuz.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoAguaLuz.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 6;
            if(txtMontoDeudas.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoDeudas.getText().toString());
            }

            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 5;
            gasto.nPrdConceptoCod = 7;
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

    public interface  OnMontoGasto{
        void  OnGastoAceptar(double monto);
    }
}
