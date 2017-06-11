package pe.com.cmacica.flujocredito.ViewModel.Digitacion;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.DbCmacIcaHelper;
import pe.com.cmacica.flujocredito.Utilitarios.Dialogos.DateDialog;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.ViewModel.General.ActividadBusquedaPersona;


public class FragmentoDatosClienteFteIgrDep extends Fragment  {

    private static final String TAG = FragmentoDatosClienteFteIgrDep.class.getSimpleName();

    EditText txtPersonName;
    EditText txtDoi;
    EditText txtActividad;
    EditText txtNomEmpresa;
    EditText txtAreaTrabajo;
    EditText txtCargo;
    TextView lblFechaInicio;
    FloatingActionButton btnSearchPersona;
    Spinner FrecPago;
    ConstanteModel FrecPagoSel;
    View view;
    private SwipeRefreshLayout refreshLayout;
    private DbCmacIcaHelper manejadorDB;
    static DigitacionDto DatosBaseCliente;
    String IdPersFteIngreso;

    public FragmentoDatosClienteFteIgrDep() {
        // Required empty public constructor
    }

    public static FragmentoDatosClienteFteIgrDep newInstance(DigitacionDto datosBaseCliente) {

        FragmentoDatosClienteFteIgrDep fragment = new FragmentoDatosClienteFteIgrDep();
        Bundle args = new Bundle();
        DatosBaseCliente = datosBaseCliente;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_datos_cliente_fte_igr_dep, container, false);
        manejadorDB = new DbCmacIcaHelper(getActivity());
        btnSearchPersona = (FloatingActionButton) view.findViewById(R.id.fabPersona) ;
        txtPersonName = (EditText) view.findViewById(R.id.txtCliente);
        txtDoi = (EditText) view.findViewById(R.id.txtDoi);
        txtActividad = (EditText) view.findViewById(R.id.txtActividad);
        txtNomEmpresa = (EditText) view.findViewById(R.id.txtNomEmpresa);
        txtAreaTrabajo = (EditText) view.findViewById(R.id.txtAreaTrabajo);
        txtCargo = (EditText) view.findViewById(R.id.txtCargo);
        lblFechaInicio = (TextView) view.findViewById(R.id.lblFechaInicio);
        FrecPago = (Spinner) view.findViewById(R.id.spinnerFrecPago);
        FrecPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FrecPagoSel = (ConstanteModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtPersonName.setText(DatosBaseCliente.NombrePersona);
        txtDoi.setText(DatosBaseCliente.NumeroDocumento);
        OnCargarFrecPago();
        //getActivity().getSupportLoaderManager().restartLoader(1, null, this);
        // Obtener el refreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragmentoDatosClienteFteIgrDep);

        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //new HackingBackgroundTask().execute();
                        OnCargarDatosLocales();
                        // Parar la animación del indicador
                        refreshLayout.setRefreshing(false);
                    }
                }
        );
        OnCargarDatosLocales();

        btnSearchPersona.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción

                        ActividadBusquedaPersona.launch(getActivity(),IdPersFteIngreso);
                        /*getActivity().startActivityForResult(
                                new Intent(getActivity(), ActividadBusquedaPersona.class), 3);*/
                        //ActividadBusquedaPersona.launch(getActivity());
                    }
                }
        );
        lblFechaInicio.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DateDialog();
                        picker.show(getFragmentManager(),"DatePicker");
                    }
                }
        );
        return view;
    }



    public void OnCargarDatosLocales(){

        try{
            Cursor query = GetFteIngreso ();
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());
            /*if (!query.moveToNext()){
                return;
            }*/
            query.moveToPosition(0);
            txtActividad.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion));
            txtNomEmpresa.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip));

            if(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio).equals("")){
                lblFechaInicio.setText(UGeneral.obtenerTiempoCorto());
            }else{
                lblFechaInicio.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio));
            }

            txtAreaTrabajo.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.cPersFIAreaTrabajo));
            txtCargo.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.cPersFICargo));
            int nCredFrecPago =  Integer.parseInt(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.nCodFrecPago));
            FrecPago.setSelection(nCredFrecPago - 1);
            IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso);

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);
        }
    }

    public Cursor GetFteIngreso (){
        //Obtener Base de Datos
        SQLiteDatabase db  = manejadorDB.getReadableDatabase();

        String selectQuery = "SELECT "+
                "A."+ ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip +","+
                "A."+ ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion +","+
                "A."+ ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio +","+
                "B.* FROM "+
                DbCmacIcaHelper.Tablas.PersFteIngreso +
                " A INNER JOIN "+
                DbCmacIcaHelper.Tablas.PersFIDependiente +
                " B ON A.IdDigitacion = B.IdDigitacion " +
                " WHERE A."+ ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion+
                " = '"+ DatosBaseCliente.IdDigitacion +"' AND A."+
                ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte +" = '1'"
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;

    }

    private  List<ConstanteModel> ListaTipoPeriodo(){
        List<ConstanteModel> ListaTipoPeriodo = new ArrayList<ConstanteModel>();
        ListaTipoPeriodo.add(new ConstanteModel(9078,1,"DIARIA"));
        ListaTipoPeriodo.add(new ConstanteModel(9078,2,"SEMANAL"));
        ListaTipoPeriodo.add(new ConstanteModel(9078,3,"QUINCENAL"));
        ListaTipoPeriodo.add(new ConstanteModel(9078,4,"MENSUAL"));
        ListaTipoPeriodo.add(new ConstanteModel(9078,5,"OTROS"));

        return ListaTipoPeriodo;
    }

    private void OnCargarFrecPago(){
        try {
            // Obtener atributo "estado"

            ArrayAdapter<ConstanteModel> adpSpinnerTipoFrec = new ArrayAdapter<ConstanteModel>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    ListaTipoPeriodo()
            );
            adpSpinnerTipoFrec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            FrecPago.setAdapter(adpSpinnerTipoFrec);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void OnGuardar(){

        //Log.d(TAG,"Updade:" +DatosBaseCliente.IdDigitacion);
        java.util.Locale locInstancia = new java.util.Locale("es","PE");
        java.text.DateFormat dateformat;
        java.util.Date dFechaInicio;
        java.util.Date dFechaActual;
        dateformat = new java.text.SimpleDateFormat("yyyy-MM-dd",locInstancia);
        try {
            dFechaInicio = dateformat.parse(lblFechaInicio.getText().toString());
            dFechaActual = dateformat.parse(UGeneral.obtenerTiempoCorto());

            if(dFechaInicio.after(dFechaActual )){
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error")
                        .setMessage("La fecha de inicio no puede ser menor a la fecha actual.")
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir
                                return;
                            }
                        })
                        .show();
            }else{
                ContentValues valores = new ContentValues();
                valores.put(ContratoDbCmacIca.PersFIDependienteTable.nCodFrecPago,FrecPagoSel.getCodigoValor());
                valores.put(ContratoDbCmacIca.PersFIDependienteTable.cPersFICargo,txtCargo.getText().toString());
                valores.put(ContratoDbCmacIca.PersFIDependienteTable.cPersFIAreaTrabajo,txtAreaTrabajo.getText().toString());

                valores.put(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe,"1");

                new UConsultas.TareaUpdateFteDep(getActivity().getContentResolver(),valores,IdPersFteIngreso)
                        .execute();

                ContentValues valores2 = new ContentValues();
                valores2.put(ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio,lblFechaInicio.getText().toString());
                valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe1,"1");
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
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        mes +=1;
        String cdia,cmes;
        if(dia<10){
            cdia ="0" + String.valueOf(dia);
        }else{
            cdia =String.valueOf(dia);
        }
        if(mes<10){
            cmes ="0" + String.valueOf(mes);
        }else{
            cmes =String.valueOf(mes);
        }

        lblFechaInicio.setText(ano + "-" + cmes + "-" + cdia);
    }

    public void  ActualizaRazonsocial(PersonaDto PersonaSel){
        ContentValues valores2 = new ContentValues();
        valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod,PersonaSel.getcPersCod());

        valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe,"1");
        new UConsultas.TareaUpdateFteIngreso(getActivity().getContentResolver(),valores2,IdPersFteIngreso)
                .execute();
    }

}
