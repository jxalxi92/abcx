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
public class FragmentoDisponibleDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoDetGastoFteIngDep.class.getSimpleName();
    EditText txtMontoAhorros;
    EditText txtMontoCtaEfec;
    EditText txtMontoEfectivo;
    EditText txtMontoTotal;

    double nMontodisponible  =0;
    String IdPersFteIngreso;

    Button btnAceptar;
    Button btnCancelar;
    ActualizaMontoFteIgrIndp listener;
    View view;

    public void IniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }
    public FragmentoDisponibleDet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_disponible_det, container, false);

        txtMontoAhorros = (EditText) view.findViewById(R.id.txtMontoAhorros);
        txtMontoCtaEfec = (EditText) view.findViewById(R.id.txtMontoCtaEfec);
        txtMontoEfectivo = (EditText) view.findViewById(R.id.txtMontoEfectivo);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar) ;
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar) ;

        txtMontoAhorros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontodisponible();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoCtaEfec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontodisponible();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoEfectivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontodisponible();
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
                        listener.OnActualizaMonto(nMontodisponible,1);
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
    private void OnCalcularMontodisponible(){

        try {

            double nMontoAhorros,nMontoCtaEfec,nMontoEfectivo= 0;
            if(TextUtils.isEmpty(txtMontoAhorros.getText().toString())){
                nMontoAhorros = 0;
            }else{
                nMontoAhorros = UGeneral.RoundDouble( Double.parseDouble( txtMontoAhorros.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoCtaEfec.getText().toString())){
                nMontoCtaEfec = 0;
            }else{
                nMontoCtaEfec = UGeneral.RoundDouble( Double.parseDouble(txtMontoCtaEfec.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoEfectivo.getText().toString())){
                nMontoEfectivo = 0;
            }else{
                nMontoEfectivo = UGeneral.RoundDouble( Double.parseDouble(txtMontoEfectivo.getText().toString()));
            }

            nMontodisponible = UGeneral.RoundDouble(  nMontoAhorros + nMontoCtaEfec + nMontoEfectivo);
            txtMontoTotal.setText(String.valueOf( nMontodisponible));


        }catch (Exception e){
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
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
                        txtMontoEfectivo.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoAhorros.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 3:
                        txtMontoCtaEfec.setText(String.valueOf(gastoSel.nMonto));
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
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? AND " +
                        ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " = ?"
                ,
                new String[]{IdPersFteIngreso,"2"},
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
            gasto.nTpoGasto = 2;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoEfectivo.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoEfectivo.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 2;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoAhorros.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{

                gasto.nMonto = Double.parseDouble(txtMontoAhorros.getText().toString());
            }
            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 2;
            gasto.nPrdConceptoCod = 3;
            if(txtMontoCtaEfec.getText().toString().equals("")){
                gasto.nMonto = 0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoCtaEfec.getText().toString());
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
