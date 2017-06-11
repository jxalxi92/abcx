package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.DbCmacIcaHelper;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.ViewModel.General.ActividadBusquedaPersona;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoDatosClienteFteIgrIndp extends Fragment {

    private static final String TAG = FragmentoDatosClienteFteIgrIndp.class.getSimpleName();

    EditText txtPersonName;
    EditText txtDoi;
    EditText txtActividad;
    EditText txtNomEmpresa;
    private SwipeRefreshLayout refreshLayout;
    FloatingActionButton btnSearchPersona;
    private DbCmacIcaHelper manejadorDB;
    static DigitacionDto DatosBaseCliente;
    String IdPersFteIngreso;

    public FragmentoDatosClienteFteIgrIndp() {
        // Required empty public constructor
    }
    public static FragmentoDatosClienteFteIgrIndp newInstance(DigitacionDto datosBaseCliente) {

        FragmentoDatosClienteFteIgrIndp fragment = new FragmentoDatosClienteFteIgrIndp();
        Bundle args = new Bundle();
        DatosBaseCliente = datosBaseCliente;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_datos_cliente_fte_igr_indp, container, false);

        manejadorDB = new DbCmacIcaHelper(getActivity());
        btnSearchPersona = (FloatingActionButton) view.findViewById(R.id.fabPersona) ;
        txtPersonName = (EditText) view.findViewById(R.id.txtCliente);
        txtDoi = (EditText) view.findViewById(R.id.txtDoi);
        txtActividad = (EditText) view.findViewById(R.id.txtActividad);
        txtNomEmpresa = (EditText) view.findViewById(R.id.txtNomEmpresa);

        txtPersonName.setText(DatosBaseCliente.NombrePersona);
        txtDoi.setText(DatosBaseCliente.NumeroDocumento);

        OnCargarDatosLocales();
        // Obtener el refreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragmentoDatosClienteFteIgrIndp);

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

        return view;
    }

    public void OnCargarDatosLocales(){

        try{
            Cursor query = GetFteIngreso ();
            Log.d("Tamaño cursor det gasto", " "+ query.getCount());

            query.moveToPosition(0);
            txtActividad.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion));
            txtNomEmpresa.setText(UConsultas.obtenerString(query, ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip));

            IdPersFteIngreso=UConsultas.obtenerString(query, ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso);

        }catch (Exception e){

            Log.e(TAG,e.getMessage(),e);
        }
    }

    public Cursor GetFteIngreso (){

        //Obtener Base de Datos
        SQLiteDatabase db  = manejadorDB.getReadableDatabase();

        String selectQuery = "SELECT "+
                "A." + ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip +","+
                "A." + ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion +","+
                "B.* FROM "+
                DbCmacIcaHelper.Tablas.PersFteIngreso +
                " A INNER JOIN "+
                DbCmacIcaHelper.Tablas.PersFIInDependiente +
                " B ON A.IdDigitacion = B.IdDigitacion " +
                " WHERE A."+ ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion+
                " = '"+ DatosBaseCliente.IdDigitacion +"' AND A."+
                ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte +" = '2'";


        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;

    }

    public void  ActualizaRazonsocial(PersonaDto PersonaSel){
        ContentValues valores2 = new ContentValues();
        valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod,PersonaSel.getcPersCod());

        valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe,"1");
        new UConsultas.TareaUpdateFteIngreso(getActivity().getContentResolver(),valores2,IdPersFteIngreso)
                .execute();
    }


    public void OnGuardar(){

        ContentValues valores2 = new ContentValues();

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

}
