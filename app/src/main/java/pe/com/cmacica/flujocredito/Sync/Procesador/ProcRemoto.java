package pe.com.cmacica.flujocredito.Sync.Procesador;

import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFteIngresoDto;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;

/**
 * Created by jhcc on 04/08/2016.
 */
public class ProcRemoto {

    private static final String TAG = ProcRemoto.class.getSimpleName();

    // Campos JSON
    private static final String INSERCIONES = "inserciones";
    private static final String MODIFICACIONES = "Data";
    private static final String ELIMINACIONES = "eliminaciones";

    private  String cImei;

    private Gson gson = new Gson();



    public String crearPayload(ContentResolver cr,String cImei) {

        this.cImei = cImei;
        return  ObtenerModificacionJson(cr);
    }
    public  String ObtenerModificacionJson(ContentResolver cr){
        List<PersFteIngresoDto> ListaFteIngreso = new ArrayList<>();

        // Obtener FteDep Cabecera
        Cursor queryPersFteIngreo = cr.query(ContratoDbCmacIca.PersFteIngresoTable.URI_CONTENIDO,
                null,
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe1 + " = ? AND "+
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe2 + " = ? AND "+
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe3 + " = ? "
                ,
                new String[]{"1","1","1"},
                null);

        while (queryPersFteIngreo.moveToNext()){

            PersFteIngresoDto ftedto =UConsultas.ConverCursorToPersFteIngresoDto(queryPersFteIngreo);
            ftedto.cImei = cImei;
            ListaFteIngreso.add(ftedto );
        }

        //recorremos todo el cursor
        for(PersFteIngresoDto fteIngresoSel : ListaFteIngreso){{

            if(fteIngresoSel.nPersTipFte.equals("1")){
                //Fte Dependiente
                Cursor queryPersFiDep = cr.query(ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO,
                        null,
                        ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion + "= ? ",
                        new String[]{fteIngresoSel.IdDigitacion}, null);

                if (queryPersFiDep.getCount()>0){

                    Log.d(TAG, "Existen " + queryPersFiDep.getCount() + " modificaciones de fte dependiente");
                    queryPersFiDep.moveToPosition(0);
                    fteIngresoSel.FIDependiente = UConsultas.ConverCursorToPersFIDependienteDto(queryPersFiDep);
                    Cursor querygasto = cr.query(ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                            null,
                            /*ContratoDbCmacIca.PersFIGastoDetTable.IdDigitacion + " = ? AND " +
                                    ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " = ?"*/
                            ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? "
                            ,
                            //new String[]{fteIngresoSel.IdPersFteIngreso,"1"},
                            new String[]{fteIngresoSel.IdPersFteIngreso},
                            null);

                    if (querygasto.getCount()>0){

                        fteIngresoSel.FIDependiente.Gastos = new ArrayList<>();
                        while (querygasto.moveToNext()){
                            fteIngresoSel.FIDependiente.Gastos .add( UConsultas.ConverCursorToPersFIGastoDetDto(querygasto));
                        }
                    }

                }
            }else{

                //Fte Independiente
                Cursor queryPersFiInDep = cr.query(ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,
                        null,
                        ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion + "= ? ",
                        new String[]{fteIngresoSel.IdDigitacion}, null);

                if (queryPersFiInDep.getCount()>0){
                    Log.d(TAG, "Existen " + queryPersFiInDep.getCount() + " modificaciones de fte Independiente");
                    queryPersFiInDep.moveToPosition(0);
                    fteIngresoSel.FIIndependiente = UConsultas.ConverCursorToPersFIInDependienteDto(queryPersFiInDep);

                    Cursor querygastoind = cr.query(ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                            null,
                            ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " = ? "
                            ,
                            new String[]{fteIngresoSel.IdPersFteIngreso},
                            null);

                    if (querygastoind.getCount()>0){

                        fteIngresoSel.FIIndependiente.GastoFamiliares = new ArrayList<>();
                        while (querygastoind.moveToNext()){
                            fteIngresoSel.FIIndependiente.GastoFamiliares .add( UConsultas.ConverCursorToPersFIGastoDetDto(querygastoind));
                        }
                    }
                }

            }


        }}

        Gson gsonpojo = new GsonBuilder().create();
        String json = gsonpojo.toJson(ListaFteIngreso);
        return json;
    }

    public List<Map<String, Object>> obtenerModificaciones(ContentResolver cr) {

        List<Map<String, Object>> ops = new ArrayList<>();
        List<PersFteIngresoDto> ListaFteIngreso = new ArrayList<>();

        // Obtener contactos donde 'modificado' = 1

        Cursor queryPersFteIngreo = cr.query(ContratoDbCmacIca.PersFteIngresoTable.URI_CONTENIDO,
                null,
                null,
                null,
                null);

        while (queryPersFteIngreo.moveToNext()){
            ListaFteIngreso.add( UConsultas.ConverCursorToPersFteIngresoDto(queryPersFteIngreo));
        }

        for(PersFteIngresoDto fteIngresoSel : ListaFteIngreso){{

            Cursor queryPersFiDep = cr.query(ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO,
                    null,
                    ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion + "= ? ",
                    new String[]{fteIngresoSel.IdDigitacion}, null);

            if (queryPersFiDep.getCount()>0){
                Log.d(TAG, "Existen " + queryPersFiDep.getCount() + " modificaciones de fte dependiente");
                queryPersFiDep.moveToPosition(0);
                fteIngresoSel.FIDependiente = UConsultas.ConverCursorToPersFIDependienteDto(queryPersFiDep);
            }
            //Fte Independiente
            Cursor queryPersFiInDep = cr.query(ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,
                    null,
                    ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion + "= ? ",
                    new String[]{fteIngresoSel.IdDigitacion}, null);

            if (queryPersFiInDep.getCount()>0){
                Log.d(TAG, "Existen " + queryPersFiInDep.getCount() + " modificaciones de fte Independiente");
                queryPersFiInDep.moveToPosition(0);
                fteIngresoSel.FIIndependiente = UConsultas.ConverCursorToPersFIInDependienteDto(queryPersFiInDep);
            }


        }}

       // Gson gsonpojo = new GsonBuilder().create();
        //String json = gsonpojo.toJson(ListaFteIngreso);

        // Comprobar si hay trabajo que realizar
       /* if (queryPersFiDep != null && queryPersFiDep.getCount() > 0) {

            Log.d(TAG, "Existen " + queryPersFteIngreo.getCount() + " modificaciones de fte dependiente");

            // Procesar operaciones
            while (queryPersFiDep.moveToNext()) {
                ops.add(mapearPersFIDependiente(queryPersFiDep));
            }
        }*/
        return ops;

       // } else {
       //     return null;
       // }

    }

    private Map<String, Object> mapearActualizacion(Cursor c) {

        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();
        // Añadir valores de columnas como atributos
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.IdDigitacion, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cCodSolicitud, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.nMonto, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.CodigoPersona, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.NombrePersona, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito, c);

        return mapaContacto;
    }

    private Map<String, Object> mapearPersFteIgreso(Cursor c) {
        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();
        // Añadir valores de columnas como atributos
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.IdDigitacion, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cCodSolicitud, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.nMonto, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.CodigoPersona, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.NombrePersona, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito, c);

        return mapaContacto;
    }

    private Map<String, Object> mapearPersFIDependiente(Cursor c) {
        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();
        // Añadir valores de columnas como atributos
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.cNumFuente, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.dPersEval, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.dPersFICaduca, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.cComentario1, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.cComentario2, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe, c);

        return mapaContacto;
    }

    private Map<String, Object> mapearPersFIIDependiente(Cursor c) {
        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();
        // Añadir valores de columnas como atributos
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.cNumFuente, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.dPersEval, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIPatrimonio, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPersPagoCuotas, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nCodSectorEcon, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.cComentario1, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.cComentario2, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.cComentario3, c);
        UConsultas.agregarStringAMapa(mapaContacto, ContratoDbCmacIca.PersFIInDependienteTable.nEstadoOpe, c);

        return mapaContacto;
    }





}
