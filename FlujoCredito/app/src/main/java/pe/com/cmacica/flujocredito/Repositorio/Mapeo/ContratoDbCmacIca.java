package pe.com.cmacica.flujocredito.Repositorio.Mapeo;

import android.net.Uri;
import android.provider.BaseColumns;
import android.text.StaticLayout;

import java.util.Date;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by jhcc on 03/08/2016.
 */
public class ContratoDbCmacIca {


    //region COLUMNAS PARA LA TABLA DE LA BD DBCMACICA

    interface ColumnasSincronizacion {

        String MODIFICADO = "modificado";
        String ELIMINADO = "eliminado";
        String INSERTADO = "insertado";
    }

    interface ColumnasPersonas{

        String IdPersona= "IdPersona";
        String cPersCod = "cPersCod";
        String cPersNombre = "cPersNombre";
        String cDoi = "cDoi";
        String cDireccion = "cDireccion";
        String dVersion  = "dVersion";
        String nEstadoOpe = "nEstadoOpe";
    }

    interface ColumnasDigitacion{

        String IdDigitacion = "IdDigitacion";
        String cCodSolicitud ="cCodSolicitud";
        String dFechaSol="dFechaSol";
        String cDescripcionProductoCredito = "cDescripcionProductoCredito";
        String EquivalenteMoneda = "EquivalenteMoneda";
        String nMonto = "nMonto";
        String cCtaCod = "cCtaCod";
        String CodigoPersona ="CodigoPersona";
        String NumeroDocumento = "NumeroDocumento";
        String NombrePersona = "NombrePersona";
        String cDescripcionTipoCredito = "cDescripcionTipoCredito";
        String TipoProceso = "TipoProceso";
        String cPersDireccDomicilio = "cPersDireccDomicilio";
        String cPersTelefono = "cPersTelefono";
        String cPersTelefono2 = "cPersTelefono2";
        String dVersion  = "dVersion";
        String nEstadoOpe = "nEstadoOpe";
    }

    interface ColumnasPersFteIngreso{

        String IdPersFteIngreso = "IdPersFteIngreso";
        String cNumFuente = "cNumFuente";
        String IdDigitacion = "IdDigitacion";
        String cPersCod = "cPersCod";
        String cPersFIPersCod = "cPersFIPersCod";
        String cPersFICargo = "cPersFICargo";
        String dPersFIinicio = "dPersFIinicio";
        String cRazSocUbiGeo = "cRazSocUbiGeo";
        String cRazSocDirecc = "cRazSocDirecc";
        String cPersOcupacion = "cPersOcupacion";
        String cRazSocTelef = "cRazSocTelef";
        String cRazSocDescrip = "cRazSocDescrip";
        String nPersTipFte = "nPersTipFte";
        String bCostoprod = "bCostoprod";
        String dVersion  = "dVersion";
        String nEstadoOpe1 = "nEstadoOpe1";
        String nEstadoOpe2 = "nEstadoOpe2";
        String nEstadoOpe3 = "nEstadoOpe3";
        String nEstadoOpe = "nEstadoOpe";
    }

    interface  ColumnasPersFIDependiente{

        String IdPersFteIngreso = "IdPersFteIngreso";
        String IdDigitacion = "IdDigitacion";
        String cNumFuente = "cNumFuente";
        String dPersEval = "dPersEval";
        String nPersIngCli = "nPersIngCli";
        String nPersIngCon = "nPersIngCon";
        String nPersOtrIng = "nPersOtrIng";
        String nPersGastoFam = "nPersGastoFam";
        String dPersFICaduca = "dPersFICaduca";
        String nPersPagoCuotas = "nPersPagoCuotas";
        String cPersFIMoneda = "cPersFIMoneda";
        String cPersFICargo = "cPersFICargo";
        String cPersFIAreaTrabajo = "cPersFIAreaTrabajo";
        String nCodFrecPago = "nCodFrecPago";
        String cComentario1 = "cComentario1";
        String cComentario2 = "cComentario2";
        String dVersion  = "dVersion";
        String nEstadoOpe = "nEstadoOpe";
    }

    interface  ColumnasPersFIInDependiente{

        String IdDigitacion = "IdDigitacion";
        String IdPersFteIngreso = "IdPersFteIngreso";
        String cNumFuente = "cNumFuente";
        String dPersEval = "dPersEval";
        String nPersFIActivoDisp = "nPersFIActivoDisp";
        String nPersFICtasxCobrar = "nPersFICtasxCobrar";
        String nPersFIInventarios = "nPersFIInventarios";
        String nPersFIActivosFijos = "nPersFIActivosFijos";
        String nPersFIProveedores = "nPersFIProveedores";
        String nPersFICredCMACT = "nPersFICredCMACT";
        String nPersFICredOtros = "nPersFICredOtros";
        String nPersPagoCuotas = "nPersPagoCuotas";
        String nPasivoNoCorriente = "nPasivoNoCorriente";

        String nPersFIVentas = "nPersFIVentas";
        String nPersFIPatrimonio = "nPersFIPatrimonio";
        String nPersFICostoVentas = "nPersFICostoVentas";
        String nPersFIRecupCtasXCobrar = "nPersFIRecupCtasXCobrar";
        String nPersFIEgresosOtros = "nPersFIEgresosOtros";
        String nPersIngFam = "nPersIngFam";
        String nPersEgrFam = "nPersEgrFam";
        String cPersFIMoneda = "cPersFIMoneda";

        String nCodSectorEcon = "nCodSectorEcon";
        String cComentario1 = "cComentario1";
        String cComentario2 = "cComentario2";
        String cComentario3 = "cComentario3";
        String dVersion  = "dVersion";
        String nEstadoOpe = "nEstadoOpe";
    }

    interface ColumnasPersFIGastoDet{

        String IdDigitacion = "IdDigitacion";
        String IdPersFteIngreso = "IdPersFteIngreso";
        String cNumFuente = "cNumFuente";
        String dfecEval = "dfecEval";
        String cFIMoneda = "cFIMoneda";
        String nTpoGasto = "nTpoGasto";
        String nPrdConceptoCod = "nPrdConceptoCod";
        String nMonto = "nMonto";
        String dVersion  = "dVersion";
        String nEstadoOpe = "nEstadoOpe";
    }
    //endregion
    //region URIS

    public static final String AUTORIDAD = "pe.com.cmacica.flujocredito";
    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD);

    private static final String RUTA_Digitacion = "Digitacion";
    private static final String RUTA_PersFteIngreso = "PersFteIngreso";
    private static final String RUTA_PersFIDependiente = "PersFIDependiente";
    private static final String RUTA_PersFIInDependiente = "PersFIInDependiente";
    private static final String RUTA_PersFIGastoDet = "PersFIGastoDet";
    private static final String RUTA_Personas = "Personas";

    //endregion

    //region  TIPOS_MIME
    public static final String BASE_CONTENIDOS = "dbcmacica.";

    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + BASE_CONTENIDOS;

    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + BASE_CONTENIDOS;

    public static String generarMime(String id) {
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }
    //endregion

    //region IMPLEMENTANDO LAS TABLAS DE LA BD DBCMACICA

    public static class DigitacionTable implements BaseColumns, ColumnasDigitacion{

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_Digitacion).build();

        public static Uri CrearUriDigitacion(String id){
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String generarIdDigitacion() {
            return "DI-" + UUID.randomUUID().toString();
        }
        public static String ObtenerIdDigitacion(Uri uri){
            return uri.getLastPathSegment();
        }
    }
    public static class PersonaTable implements BaseColumns,ColumnasPersonas{
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_Personas).build();

        public static Uri CrearUriPersona (String id){
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String generarIdPersona() {
            return "PE-" + UUID.randomUUID().toString();
        }
        public static String ObtenerIdPersona(Uri uri){
            return uri.getLastPathSegment();
        }
    }
    public static class PersFteIngresoTable implements BaseColumns, ColumnasPersFteIngreso{

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PersFteIngreso).build();

        public static Uri crearUriPersFteIngreso(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String generarIdPersFteIngreso() {
            return "FI-" + UUID.randomUUID().toString();
        }
        public static String obtenerIdPersFteIngreso(Uri uri) {
            return uri.getLastPathSegment();
        }

    }
    public static class PersFIDependienteTable implements BaseColumns, ColumnasPersFIDependiente{

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PersFIDependiente).build();

        public static Uri crearUriPersFIDependiente(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String obtenerIdPersFIDependiente(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
    public static class PersFIInDependienteTable implements BaseColumns,ColumnasPersFIInDependiente{

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PersFIInDependiente).build();
        public static Uri crearUriPersFIInDependiente(String id ){
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
        public static String obtenerIdPersFIInDependiente(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class PersFIGastoDetTable implements BaseColumns,ColumnasPersFIGastoDet{

        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PersFIGastoDet).build();

        public static Uri crearUriPersFIGastoDet(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static Uri crearUriPersFIGastoDet(String IdDigitacion,String nTpoGasto) {
            return URI_CONTENIDO.buildUpon().appendPath(String.format("%s#%s",IdDigitacion,nTpoGasto)).build();
        }
        public static String obtenerIdPersFIGastoDet(Uri uri) {
            return uri.getLastPathSegment();
        }
        public static String[] obtenerIdPersFIGastoDetComplex(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    //endregion


}
