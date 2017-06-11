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
public class FragmentoIgrFueraNegDet extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoIgrFueraNegDet.class.getSimpleName();

    double nMontoTotal =0;
    String IdPersFteIngreso;
    EditText txtMontoPermanente;
    EditText txtMontoEventual;
    EditText txtMontoTotal;

    Button btnAceptar;
    Button btnCancelar;

    View view;
    ActualizaMontoFteIgrIndp listener;

    public void IniciarDialogo(String   idPersFteIngreso){
        IdPersFteIngreso = idPersFteIngreso;
    }

    public FragmentoIgrFueraNegDet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_igr_fuera_neg_det, container, false);

        txtMontoPermanente = (EditText) view.findViewById(R.id.txtMontoPermanente);
        txtMontoEventual = (EditText) view.findViewById(R.id.txtMontoEventual);
        txtMontoTotal = (EditText) view.findViewById(R.id.txtMontoTotal);

        txtMontoPermanente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoFueraNeg();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoEventual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularMontoFueraNeg();
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
                        listener.OnActualizaMonto(nMontoTotal,5);
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
                        txtMontoPermanente.setText(String.valueOf(gastoSel.nMonto));
                        break;
                    case 2:
                        txtMontoEventual.setText(String.valueOf(gastoSel.nMonto));
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

    public void OnCalcularMontoFueraNeg(){
        try{
            double nMontoEventual,nMontoPermanente= 0;

            if(TextUtils.isEmpty(txtMontoPermanente.getText().toString())){
                nMontoPermanente = 0;
            }else{
                nMontoPermanente = UGeneral.RoundDouble( Double.parseDouble(txtMontoPermanente.getText().toString()));
            }

            if(TextUtils.isEmpty(txtMontoEventual.getText().toString())){
                nMontoEventual = 0;
            }else{
                nMontoEventual = UGeneral.RoundDouble( Double.parseDouble(txtMontoEventual.getText().toString()));
            }

            nMontoTotal = UGeneral.RoundDouble( nMontoEventual + nMontoPermanente);
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
                new String[]{IdPersFteIngreso,"6"},
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
            gasto.nTpoGasto = 6;
            gasto.nPrdConceptoCod = 1;
            if(txtMontoPermanente.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoPermanente.getText().toString());
            }

            gasto.IdPersFteIngreso = IdPersFteIngreso;
            result.add(gasto);
            gasto = new PersFIGastoDetDto();
            gasto.nTpoGasto = 6;
            gasto.nPrdConceptoCod = 2;
            if(txtMontoEventual.getText().toString().equals("")){
                gasto.nMonto =0.0;
            }else{
                gasto.nMonto = Double.parseDouble(txtMontoEventual.getText().toString());
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
