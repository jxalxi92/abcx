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
import android.widget.RadioButton;
import android.widget.Toast;

import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoIgrEgrSolesDep extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoIgrEgrSolesDep.class.getSimpleName();

    EditText txtIngresosTotal;
    EditText txtIngresosConyu;
    EditText txtIngresoCliente;
    EditText txtEgresoFamiliar;
    EditText txtOtrosIngresos;
    EditText txtOtrasEnt;
    EditText txtSaldo;

    RadioButton rbtSoles;
    RadioButton rbtDolares;

    String IdDigitacion;

    String IdPersFteIngreso;
    View view;

    public static FragmentoIgrEgrSolesDep newInstance(String Codigocliente){

        FragmentoIgrEgrSolesDep fragment = new FragmentoIgrEgrSolesDep();
        Bundle args = new Bundle();
        args.putString(Constantes.KeyIdPersona, Codigocliente);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentoIgrEgrSolesDep() {

    }

    //region Cargar última fuente de ingreso


    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_igr_egr_soles_dep,container, false);
        IdDigitacion =getArguments().getString(Constantes.KeyIdPersona);

        txtIngresoCliente =(EditText) view.findViewById(R.id.txtMontoIngresoCliente);
        txtIngresosConyu = (EditText) view.findViewById(R.id.txtMontoIngresoConyugue);
        txtOtrosIngresos = (EditText) view.findViewById(R.id.txtMontoOtrosIngresos);
        txtIngresosTotal = (EditText) view.findViewById(R.id.txtMontoTotalIngresos);
        txtEgresoFamiliar = (EditText) view.findViewById(R.id.txtMontoEgresos);
        txtOtrasEnt = (EditText) view.findViewById(R.id.txtMontoCuotasOtraEnt);
        txtSaldo = (EditText) view.findViewById(R.id.txtMontoSaldo);
        rbtSoles = (RadioButton) view.findViewById(R.id.rbtSoles);
        rbtDolares = (RadioButton) view.findViewById(R.id.rbtDolares);

        txtEgresoFamiliar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentoDetGastoFteIngDep obj = new  FragmentoDetGastoFteIngDep();
                        obj.IniciarDialogo(IdPersFteIngreso);
                        obj.show( getFragmentManager(),  "TAG_DET_GASTO");
                        //txtEgresoFamiliar.setText("1200");
                    }
                }
        );

        txtIngresoCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculoIngreso();
            }
        });
        txtIngresosConyu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculoIngreso();

            }
        });
        txtOtrosIngresos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 CalculoIngreso();

            }
        });
        txtIngresosTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
                CalcularSaldo();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        txtEgresoFamiliar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // txtEgresoFamiliar.setText(Double.toString( Constantes.MontoGasto));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //txtEgresoFamiliar.setText(Double.toString( Constantes.MontoGasto));

            }
        });

        txtOtrasEnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalcularSaldo();
            }
        });

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        //getActivity().getSupportLoaderManager().restartLoader(1, null, this);

        return view;
    }

    //region Calculos de cuotas

    public void OnActualizarMontoEgreso(double montoegreso){
        txtEgresoFamiliar.setText(Double.toString(montoegreso));
        ContentValues valores = new ContentValues();
        valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam,String.valueOf(montoegreso));
        valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe,"1");

        new UConsultas.TareaUpdateFteDep(getActivity().getContentResolver(),valores,IdDigitacion)
                .execute(ContratoDbCmacIca.PersFIDependienteTable.crearUriPersFIDependiente(IdDigitacion));

        CalcularSaldo();
    }

    private void CalculoIngreso(){
        try {
            double IngresoCliente;
            double IngresoConyugue;
            double OtrosIngresos;
            double TotalIngreso;

            if(TextUtils.isEmpty( txtIngresoCliente.getText().toString())) {
                IngresoCliente=0;
            }else{
                IngresoCliente =UGeneral.RoundDouble( Double.parseDouble(txtIngresoCliente.getText().toString()) );
            }

            if (TextUtils.isEmpty( txtIngresosConyu.getText().toString())){
                IngresoConyugue=0;
            }else{
                IngresoConyugue = UGeneral.RoundDouble( Double.parseDouble(txtIngresosConyu.getText().toString()));
            }

            if (TextUtils.isEmpty( txtOtrosIngresos.getText().toString())){
                OtrosIngresos=0;
            }else{

                OtrosIngresos =UGeneral.RoundDouble( Double.parseDouble(txtOtrosIngresos.getText().toString() ));
            }

            TotalIngreso = UGeneral.RoundDouble(  IngresoCliente+IngresoConyugue+ OtrosIngresos);
            txtIngresosTotal.setText( Double.toString( TotalIngreso ) );
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    private void CalcularSaldo(){

        try {
            double EgresoFamiliar;
            double CuotasOtrasEnt;
            double saldo=0;
            double TotalIngreso;

            if (TextUtils.isEmpty(txtEgresoFamiliar.getText().toString())){
                EgresoFamiliar=0;
            }else{
                EgresoFamiliar = UGeneral.RoundDouble( Double.parseDouble(txtEgresoFamiliar.getText().toString()) );
            }

            if (TextUtils.isEmpty(txtOtrasEnt.getText().toString())){
                CuotasOtrasEnt=0;
            }else{
                CuotasOtrasEnt = UGeneral.RoundDouble( Double.parseDouble(txtOtrasEnt.getText().toString()) );
            }

            if (TextUtils.isEmpty(txtIngresosTotal.getText().toString())){
                TotalIngreso=0;
            }else{
                TotalIngreso =UGeneral.RoundDouble( Double.parseDouble(txtIngresosTotal.getText().toString()) );
            }

            saldo =UGeneral.RoundDouble(  TotalIngreso-(CuotasOtrasEnt+EgresoFamiliar));

            if (saldo <0) {

                txtSaldo.setTextColor(getResources().getColor( R.color.colorPrimary));
            }
            else{
                txtSaldo.setTextColor(Color.BLUE);
            }

            txtSaldo.setText(String.valueOf( saldo));
        }
        catch (Exception e) {
            Log.d(TAG, e.getMessage());

            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
    //endregion

    public void OnCargarDatosLocales(Cursor query) {

        try {
            Log.d("Tamaño xxx", " " + query.getCount());
            if (!query.moveToNext()) {
                return;
            }
            Log.d("Tamaño curso ds", " " + query.getCount());
            query.moveToPosition(0);
            txtIngresosConyu.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon));
            txtIngresoCliente.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli));
            txtOtrosIngresos.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng));
            txtOtrasEnt.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas));
            txtEgresoFamiliar.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam));
            IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso);
            int TipoMoneda = Integer.parseInt(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda));
            if(TipoMoneda == 2){
                rbtDolares.setChecked(true);
                rbtSoles.setChecked(false);
            }
            else{
                rbtDolares.setChecked(false);
                rbtSoles.setChecked(true);
            }


            CalculoIngreso();
            CalcularSaldo();
        } catch (Exception e) {

            Log.e(TAG, e.getMessage(), e);

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

    public void OnGuardar() {

        Log.d(TAG, "Updade:" + IdDigitacion);

        Double nSaldo =0.0;
        if (!txtSaldo.getText().toString().equals("")) {
            nSaldo = Double.parseDouble(txtSaldo.getText().toString());
        }
        if(nSaldo <=0){
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Monto excedente no puede ser menor igual que 0.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            return;
                        }
                    })
                    .show();
        } else {
            ContentValues valores = new ContentValues();
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli, txtIngresoCliente.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon, txtIngresosConyu.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng, txtOtrosIngresos.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam, txtEgresoFamiliar.getText().toString());
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas, txtOtrasEnt.getText().toString());

            if (rbtSoles.isChecked()) {
                valores.put(ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda, "1");
            } else {
                valores.put(ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda, "2");
            }
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe, "1");

            new UConsultas.TareaUpdateFteDep(getActivity().getContentResolver(), valores, IdPersFteIngreso)
                    .execute(ContratoDbCmacIca.PersFIDependienteTable.crearUriPersFIDependiente(IdPersFteIngreso));

            ContentValues valores2 = new ContentValues();

            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe2,"1");
            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe,"1");
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
