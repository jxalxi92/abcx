package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.AgenteServicio.VolleySingleton;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoBalanceFteIgr extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FragmentoBalanceFteIgr.class.getSimpleName();
    //region Controles
    EditText txtMontoInventario;
    EditText txtMontoActivoCorriente;
    EditText txtMontoDisponible;
    EditText txtMontoCuentasxCobrar;
    EditText txtMontoActivoNoCorriente;
    EditText txtMontoActivoFijo;
    EditText txtMontoTotalActivo;
    EditText txtMontoPasivoCorriente;
    EditText txtMontoProveedores;
    EditText txtMontoOtrosPrestamos;
    EditText txtMontoPrestamoCmac;
    EditText txtMontoPasivoNoCorriente;
    EditText txtMontoTotalPasivo;
    EditText txtMontoPatrimonio;

    EditText txtMontoVentasNetas;
    EditText txtMontoRecCtasCobrar;
    EditText txtMontoIgrFueraNegocio;
    EditText txtMontoCostoMerc;
    EditText txtMontoGastoOpeAdm;
    EditText txtMontoGastoFam;
    EditText txtMontoSaldo;

    RadioButton rbtSoles;
    RadioButton rbtDolares;
    View view;
    //endregion
    //region Variables
    String IdPersona;
    String IdPersFteIngreso;


    //endregion

    public static FragmentoBalanceFteIgr newInstance(String Codigocliente){

        FragmentoBalanceFteIgr fragment = new FragmentoBalanceFteIgr();
        Bundle args = new Bundle();
        args.putString(Constantes.KeyIdPersona, Codigocliente);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentoBalanceFteIgr() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragmento_balance_fte_igr, container, false);

        IdPersona =getArguments().getString(Constantes.KeyIdPersona);

        txtMontoInventario =(EditText)  view.findViewById(R.id.txtMontoInventario);
        txtMontoActivoCorriente = (EditText) view.findViewById(R.id.txtMontoActivoCorriente);
        txtMontoDisponible = (EditText) view.findViewById(R.id.txtMontoDisponible);
        txtMontoCuentasxCobrar = (EditText) view.findViewById(R.id.txtMontoCuentasxCobrar);
        txtMontoActivoNoCorriente = (EditText) view.findViewById(R.id.txtMontoActivoNoCorriente);
        txtMontoActivoFijo = (EditText) view.findViewById(R.id.txtMontoActivoFijo);
        txtMontoTotalActivo = (EditText) view.findViewById(R.id.txtMontoTotalActivo);
        txtMontoPasivoCorriente = (EditText) view.findViewById(R.id.txtMontoPasivoCorriente);
        txtMontoProveedores = (EditText) view.findViewById(R.id.txtMontoProveedores);
        txtMontoOtrosPrestamos = (EditText) view.findViewById(R.id.txtMontoOtrosPrestamos);
        txtMontoPrestamoCmac = (EditText) view.findViewById(R.id.txtMontoPrestamoCmac);
        txtMontoPasivoNoCorriente = (EditText) view.findViewById(R.id.txtMontoPasivoNoCorriente);
        txtMontoTotalPasivo = (EditText) view.findViewById(R.id.txtMontoTotalPasivo) ;
        txtMontoPatrimonio = (EditText) view.findViewById(R.id.txtMontoPatrimonio) ;
        txtMontoVentasNetas = (EditText) view.findViewById(R.id.txtMontoVentasNetas);
        txtMontoRecCtasCobrar = (EditText) view.findViewById(R.id.txtMontoRecCtasCobrar);
        txtMontoIgrFueraNegocio = (EditText) view.findViewById(R.id.txtMontoIgrFueraNegocio);
        txtMontoCostoMerc = (EditText) view.findViewById(R.id.txtMontoCostoMerc);
        txtMontoGastoOpeAdm = (EditText) view.findViewById(R.id.txtMontoGastoOpeAdm);
        txtMontoGastoFam = (EditText) view.findViewById(R.id.txtMontoGastoFam);
        txtMontoSaldo = (EditText) view.findViewById(R.id.txtMontoSaldo);

        rbtSoles = (RadioButton) view.findViewById(R.id.rbtSoles);
        rbtDolares = (RadioButton) view.findViewById(R.id.rbtDolares);

        //region Eventos Click y TextChanged

        txtMontoDisponible.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentoDisponibleDet obj = new FragmentoDisponibleDet();
                        obj.IniciarDialogo(IdPersFteIngreso);
                        obj.show(getFragmentManager(), "TAG_Disponible");
                    }
                }
        );
        txtMontoInventario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentoInventarioDet obj = new FragmentoInventarioDet();
                        obj.OnIniciarDialogo(IdPersFteIngreso);
                        obj.show(getFragmentManager(), "TAG_inventario");
                    }
                }
        );
        txtMontoActivoFijo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentoActivoFijoDet obj = new FragmentoActivoFijoDet();
                        obj.IniciarDialogo(IdPersFteIngreso);
                        obj.show(getFragmentManager(), "TAG_ActivoFijo");
                    }
                }
        );
        txtMontoPasivoNoCorriente.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentoPasivoNoCorrienteDet obj = new FragmentoPasivoNoCorrienteDet();
                        obj.OnIniciarDialogo(IdPersFteIngreso);
                        obj.show(getFragmentManager(), "TAG_PasivoNoCorriente");
                    }
                }
        );

        txtMontoIgrFueraNegocio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentoIgrFueraNegDet obj = new FragmentoIgrFueraNegDet();
                        obj.IniciarDialogo(IdPersFteIngreso);
                        obj.show(getFragmentManager(),"TAG_IgrFueraNeg");
                    }
                }
        );

        txtMontoGastoOpeAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoGastoOpeAdmDet obj = new FragmentoGastoOpeAdmDet();
                obj.IniciarDialogo(IdPersFteIngreso);
                obj.show(getFragmentManager(),"TAG_GastoOpeAdm");
            }
        });
        txtMontoGastoFam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoGastoFamDet obj = new FragmentoGastoFamDet();
                obj.IniciarDialogo(IdPersFteIngreso);
                obj.show(getFragmentManager(),"TAG_GastoFam");
            }
        });

        //region eventos text Changed
        txtMontoDisponible.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalActivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoCuentasxCobrar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalActivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoInventario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalActivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoActivoFijo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalActivo();
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
                OnCalcularTotalPasivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoOtrosPrestamos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalPasivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoPrestamoCmac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalPasivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoPasivoNoCorriente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularTotalPasivo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoTotalActivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularPatrimonio();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoTotalPasivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularPatrimonio();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtMontoVentasNetas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoRecCtasCobrar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoIgrFueraNegocio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoCostoMerc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoGastoOpeAdm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtMontoGastoFam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnCalcularIgrEgr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion
        //endregion
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        //getActivity().getSupportLoaderManager().restartLoader(1, null, this);

        //OnObtenerFuenteIngreso();

        return view;
    }

    //region OnCalcularMontos

    private void OnCalcularTotalActivo(){
        try {

            double nMontoActivoCorriente,nMontoTotalActivo,nMontoDisponible,nMontoCuentasxCobrar,nMontoInventario,
                    nMontoActivoNoCorriente,nMontoActivoFijo=0;

            if (TextUtils.isEmpty(txtMontoDisponible.getText())){
                nMontoDisponible =0;
            }else{
                nMontoDisponible =UGeneral.RoundDouble( Double.parseDouble(txtMontoDisponible.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoCuentasxCobrar.getText())){
                nMontoCuentasxCobrar =0;
            }else{
                nMontoCuentasxCobrar =UGeneral.RoundDouble( Double.parseDouble(txtMontoCuentasxCobrar.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoInventario.getText())){
                nMontoInventario =0;
            }else{
                nMontoInventario = UGeneral.RoundDouble( Double.parseDouble(txtMontoInventario.getText().toString()));
            }
            nMontoActivoCorriente = UGeneral.RoundDouble(  nMontoDisponible+ nMontoCuentasxCobrar+nMontoInventario);
            txtMontoActivoCorriente.setText(String.valueOf( nMontoActivoCorriente));

            if (TextUtils.isEmpty(txtMontoActivoFijo.getText())){
                nMontoActivoFijo =0;
            }else{
                nMontoActivoFijo =UGeneral.RoundDouble( Double.parseDouble(txtMontoActivoFijo.getText().toString()));
            }

            nMontoActivoNoCorriente = nMontoActivoFijo;
            txtMontoActivoNoCorriente.setText(String.valueOf(nMontoActivoNoCorriente));

            nMontoTotalActivo = UGeneral.RoundDouble(  nMontoActivoCorriente + nMontoActivoNoCorriente);
            txtMontoTotalActivo.setText(String.valueOf(nMontoTotalActivo));

        }catch (Exception e ){
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void OnCalcularTotalPasivo(){
        try {

            double  nMontoTotalPasivo,nMontoPasivoCorriente,nMontoProveedores,
                    nMontoOtrosPrestamos,nMontoPrestamosCmac,nMontoPasivoNoCorriente =0;

            if (TextUtils.isEmpty(txtMontoProveedores.getText())){
                nMontoProveedores =0;
            }else{
                nMontoProveedores = UGeneral.RoundDouble( Double.parseDouble(txtMontoProveedores.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoOtrosPrestamos.getText())){
                nMontoOtrosPrestamos =0;
            }else{
                nMontoOtrosPrestamos = UGeneral.RoundDouble( Double.parseDouble(txtMontoOtrosPrestamos.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoPrestamoCmac.getText())){
                nMontoPrestamosCmac =0;
            }else{
                nMontoPrestamosCmac = UGeneral.RoundDouble( Double.parseDouble(txtMontoPrestamoCmac.getText().toString()));
            }

            nMontoPasivoCorriente = UGeneral.RoundDouble(  nMontoProveedores+ nMontoOtrosPrestamos+nMontoPrestamosCmac);
            txtMontoPasivoCorriente.setText(String.valueOf( nMontoPasivoCorriente));

            if (TextUtils.isEmpty(txtMontoPasivoNoCorriente.getText())){
                nMontoPasivoNoCorriente =0;
            }else{
                nMontoPasivoNoCorriente = UGeneral.RoundDouble( Double.parseDouble(txtMontoPasivoNoCorriente.getText().toString()));
            }

            nMontoTotalPasivo = UGeneral.RoundDouble( nMontoPasivoNoCorriente + nMontoPasivoCorriente);
            txtMontoTotalPasivo.setText(String.valueOf(nMontoTotalPasivo));

        }catch (Exception e ){
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void OnCalcularPatrimonio(){
        try {

            double nMontoTotalActivo,nMontoTotalPasivo,nMontoPatrimonio =0;

            if (TextUtils.isEmpty(txtMontoTotalActivo.getText())){
                nMontoTotalActivo =0;
            }else{
                nMontoTotalActivo = UGeneral.RoundDouble( Double.parseDouble(txtMontoTotalActivo.getText().toString()));
            }
            if (TextUtils.isEmpty(txtMontoTotalPasivo.getText())){
                nMontoTotalPasivo =0;
            }else{
                nMontoTotalPasivo = UGeneral.RoundDouble( Double.parseDouble(txtMontoTotalPasivo.getText().toString()));
            }

            nMontoPatrimonio =UGeneral.RoundDouble( nMontoTotalActivo - nMontoTotalPasivo);
            txtMontoPatrimonio.setText(String.valueOf( nMontoPatrimonio));




        }catch (Exception e ){
            Toast toast = Toast.makeText(getActivity(), "Monto incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void OnCalcularIgrEgr(){

        try {

            double nMontoVentasNetas,nMontoRecCtasxCobrar,nMontoIgrNeg,nMontoCostoMercado,
                    nMontoGastoOpeAdm,nMontoGastoFam,nMontoSaldo= 0;

            if (TextUtils.isEmpty(txtMontoVentasNetas.getText())){
                nMontoVentasNetas =0;
            }else{
                nMontoVentasNetas = UGeneral.RoundDouble( Double.parseDouble(txtMontoVentasNetas.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoRecCtasCobrar.getText())){
                nMontoRecCtasxCobrar =0;
            }else{
                nMontoRecCtasxCobrar = UGeneral.RoundDouble( Double.parseDouble(txtMontoRecCtasCobrar.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoIgrFueraNegocio.getText())){
                nMontoIgrNeg =0;
            }else{
                nMontoIgrNeg = UGeneral.RoundDouble( Double.parseDouble(txtMontoIgrFueraNegocio.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoCostoMerc.getText())){
                nMontoCostoMercado =0;
            }else{
                nMontoCostoMercado = UGeneral.RoundDouble( Double.parseDouble(txtMontoCostoMerc.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoGastoOpeAdm.getText())){
                nMontoGastoOpeAdm =0;
            }else{
                nMontoGastoOpeAdm = UGeneral.RoundDouble( Double.parseDouble(txtMontoGastoOpeAdm.getText().toString()));
            }

            if (TextUtils.isEmpty(txtMontoGastoFam.getText())){
                nMontoGastoFam =0;
            }else{
                nMontoGastoFam = UGeneral.RoundDouble( Double.parseDouble(txtMontoGastoFam.getText().toString()));
            }

            nMontoSaldo = UGeneral.RoundDouble( nMontoVentasNetas+nMontoRecCtasxCobrar+nMontoIgrNeg - (nMontoCostoMercado +nMontoGastoOpeAdm +nMontoGastoFam));

            txtMontoSaldo.setText(String.valueOf( nMontoSaldo));


        }catch (Exception ex){

        }

    }

    //endregion

    //region ActualizarBd Local

    public void ActualizaMonto(double Monto,int Tipo){

        try{
            ContentValues valores = new ContentValues();


            switch (Tipo){
                case 1:
                    txtMontoDisponible.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp,String.valueOf(Monto));
                    break;
                case 2:
                    txtMontoInventario.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios,String.valueOf(Monto));
                    break;
                case 3:
                    txtMontoActivoFijo.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos,String.valueOf(Monto));
                    break;
                case 4:
                    txtMontoPasivoNoCorriente.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente,String.valueOf(Monto));
                    break;
                case 5:
                    txtMontoIgrFueraNegocio.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam,String.valueOf(Monto));
                    break;
                case 6:
                    txtMontoGastoOpeAdm.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros,String.valueOf(Monto));
                    break;
                case 7:
                    txtMontoGastoFam.setText(String.valueOf(Monto));
                    valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam,String.valueOf(Monto));
                    break;

            }
            valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe,"1");

            new UConsultas.TareaUpdateFteInDep(getActivity().getContentResolver(),valores,IdPersFteIngreso)
                    .execute();

        }catch (Exception e){

        }

    }

    //endregion

    //region Cargar última fuente de ingreso

    private void OnCargarDatosLocales(Cursor query){

        Log.d("Tamaño cursor", " "+ query.getCount());
        if (!query.moveToNext()){
            return;
        }

        txtMontoDisponible.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp));
        txtMontoCuentasxCobrar.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar));
        txtMontoInventario.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios));
        txtMontoActivoFijo.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos));

        txtMontoProveedores.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores));
        txtMontoOtrosPrestamos.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros));
        txtMontoPrestamoCmac.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT));
        txtMontoPasivoNoCorriente.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente));

        txtMontoVentasNetas.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas));
        txtMontoIgrFueraNegocio.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam));
        txtMontoRecCtasCobrar.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar));
        txtMontoCostoMerc.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas));
        txtMontoGastoOpeAdm.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros));
        txtMontoGastoFam.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam));

        IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso);

        int TipoMoneda =1;
        if(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda) == null){
            TipoMoneda =1;
        }else{
            TipoMoneda = Integer.parseInt(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda));
        }

        if(TipoMoneda == 2){
            rbtDolares.setChecked(true);
            rbtSoles.setChecked(false);
        }
        else{
            rbtDolares.setChecked(false);
            rbtSoles.setChecked(true);
        }

        OnCalcularPatrimonio();
        OnCalcularIgrEgr();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("Cursor ingreso egreso:",IdPersona);
        Loader<Cursor> cursor = new CursorLoader(getActivity(),
                ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion + " = ?",
                new String[]{IdPersona}, null);

        return cursor;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        OnCargarDatosLocales(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //endregion
    public void OnGuardar() {
        Log.d(TAG, "Updade:" + IdPersona);

        Double nPatrimonio, nSaldo = 0.0;
        if (txtMontoPatrimonio.getText().equals("")) {
            nPatrimonio = 0.0;
        } else {
            nPatrimonio = Double.parseDouble(txtMontoPatrimonio.getText().toString());
        }
        if (txtMontoSaldo.getText().equals("")) {
            nSaldo = 0.0;
        } else {
            nSaldo = Double.parseDouble(txtMontoSaldo.getText().toString());
        }

        if (nPatrimonio <= 0.0) {

            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Patrimonio no puede ser menor igual que 0.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            return;
                        }
                    })
                    .show();
            return;
        }
        if (nSaldo <= 0) {

            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("El saldo no puede ser menor igual que 0.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            return;
                        }
                    })
                    .show();

            return;
        }

        ContentValues valores = new ContentValues();
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp, UGeneral.RoundDouble( Double.parseDouble( txtMontoDisponible.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar,UGeneral.RoundDouble( Double.parseDouble( txtMontoCuentasxCobrar.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios,UGeneral.RoundDouble( Double.parseDouble( txtMontoInventario.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos,UGeneral.RoundDouble( Double.parseDouble( txtMontoActivoFijo.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores,UGeneral.RoundDouble( Double.parseDouble( txtMontoProveedores.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT,UGeneral.RoundDouble( Double.parseDouble( txtMontoPrestamoCmac.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros, UGeneral.RoundDouble( Double.parseDouble(txtMontoOtrosPrestamos.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente,UGeneral.RoundDouble( Double.parseDouble( txtMontoPasivoNoCorriente.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIPatrimonio,UGeneral.RoundDouble( Double.parseDouble( txtMontoPatrimonio.getText().toString())));

        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas,UGeneral.RoundDouble( Double.parseDouble( txtMontoVentasNetas.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar,UGeneral.RoundDouble( Double.parseDouble( txtMontoRecCtasCobrar.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam,UGeneral.RoundDouble( Double.parseDouble( txtMontoIgrFueraNegocio.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas,UGeneral.RoundDouble( Double.parseDouble( txtMontoCostoMerc.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros, UGeneral.RoundDouble( Double.parseDouble(txtMontoGastoOpeAdm.getText().toString())));
        valores.put(ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam,UGeneral.RoundDouble( Double.parseDouble( txtMontoGastoFam.getText().toString())));


        if (rbtSoles.isChecked()) {
            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda, "1");
        } else {
            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda, "2");
        }
        valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe, "1");

        new UConsultas.TareaUpdateFteInDep(getActivity().getContentResolver(), valores, IdPersFteIngreso)
                .execute(ContratoDbCmacIca.PersFIInDependienteTable.crearUriPersFIInDependiente(IdPersFteIngreso));

        ContentValues valores2 = new ContentValues();

        valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe2, "1");
        new UConsultas.TareaUpdateFteIngreso(getActivity().getContentResolver(), valores2, IdPersFteIngreso)
                .execute();

        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aviso")
                .setMessage("Agregado correctamente.")
                //.setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Salir
                        return;
                    }
                })
                .show();


    }

}
