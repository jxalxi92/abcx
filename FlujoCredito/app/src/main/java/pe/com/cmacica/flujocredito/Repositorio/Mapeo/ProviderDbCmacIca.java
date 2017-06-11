package pe.com.cmacica.flujocredito.Repositorio.Mapeo;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by jhcc on 04/08/2016.
 */
public class ProviderDbCmacIca extends ContentProvider {

//public class ProviderDbCmacIca {

    public static final String TAG = ProviderDbCmacIca.class.getSimpleName();
    public static final String URI_NO_SOPORTADA = "Uri no soportada";

    private DbCmacIcaHelper manejadorDB;
    private ContentResolver resolver;

    public ProviderDbCmacIca(){
    }

    // Comparador de URIs de contenido
    public static final UriMatcher uriMatcher ;

    // Identificadores de tipos
    public static final int URIDIGITACION = 100;
    public static final int URIDIGITACION_ID = 101;

    public static final int URIPersFteIngreso = 200;
    public static final int URIPersFteIngreso_Id = 201;

    public static final int URIPersFIDependiente = 300;
    public static final int URIPersFIDependiente_Id = 301;

    public static final int URIPersFIInDependiente = 400;
    public static final int URIPersFIInDependiente_Id = 401;

    public static final int URIPersFIGastoDet = 500;
    public static final int URIPersFIGastoDet_Id = 501;
    public static final int URIPersonas = 600;
    public static final int URIPersonas_Id = 601;

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "Digitacion", URIDIGITACION);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "Digitacion/*" , URIDIGITACION_ID);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFteIngreso", URIPersFteIngreso);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFteIngreso/*" , URIPersFteIngreso_Id);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIDependiente",URIPersFIDependiente);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIDependiente/*" , URIPersFIDependiente_Id);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIInDependiente",URIPersFIInDependiente);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIInDependiente/*" , URIPersFIInDependiente_Id);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIGastoDet",URIPersFIGastoDet);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "PersFIGastoDet/*" , URIPersFIGastoDet_Id);

        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "Personas",URIPersonas);
        uriMatcher.addURI(ContratoDbCmacIca.AUTORIDAD, "Personas/*" , URIPersonas_Id);
   }

    //[Campos auxiliares
    private static final String DigitacionFteIngreso = "Digitacion INNER JOIN PersFteIngreso "+
            "ON Digitacion.IdDigitacion = PersFteIngreso.IdDigitacion";

    private final String [] proyDigitacionFteIngreso = new String[]{
            DbCmacIcaHelper.Tablas.Digitacion+".*",
            ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso,
            ContratoDbCmacIca.PersFteIngresoTable.cPersCod,
            ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte,
            ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso
    };


    @Override
    public boolean onCreate() {

        manejadorDB = new DbCmacIcaHelper(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {

        final SQLiteDatabase db = manejadorDB.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }


    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case URIDIGITACION:
                return ContratoDbCmacIca.generarMime("Digitacion");
            case URIDIGITACION_ID:
                return ContratoDbCmacIca.generarMimeItem("Digitacion");
           case URIPersFteIngreso:
                return ContratoDbCmacIca.generarMime("PersFteIngreso");
            case URIPersFteIngreso_Id:
                return ContratoDbCmacIca.generarMimeItem("PersFteIngreso");
            case URIPersFIDependiente:
                return ContratoDbCmacIca.generarMime("PersFIDependiente");
            case URIPersFIDependiente_Id:
                return ContratoDbCmacIca.generarMimeItem("PersFIDependiente");
            case URIPersFIInDependiente:
                return ContratoDbCmacIca.generarMime("PersFIInDependiente");
            case URIPersFIInDependiente_Id:
                return ContratoDbCmacIca.generarMimeItem("PersFIInDependiente");
            case URIPersFIGastoDet:
                return ContratoDbCmacIca.generarMime("PersFIGastoDet");
            case URIPersFIGastoDet_Id:
                return ContratoDbCmacIca.generarMimeItem("PersFIGastoDet");
            case URIPersonas:
                return ContratoDbCmacIca.generarMime("Personas");
            case URIPersonas_Id:
                return ContratoDbCmacIca.generarMimeItem("Personas");
            default:
                throw new UnsupportedOperationException("Uri desconocida => " + uri);

        }
    }

    //region CRUD DBCMACICA

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //Obtener Base de Datos
        SQLiteDatabase bd  = manejadorDB.getReadableDatabase();
        //Comparar URI
        int match = uriMatcher.match(uri);
        // string auxiliar para los ID
        String id;
        Cursor c;

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (match){

            case URIDIGITACION:
                //builder.setTables();
                c = bd.query( DbCmacIcaHelper.Tablas.Digitacion,
                         projection,
                        selection,selectionArgs,null,null,sortOrder);
                break;

            case URIDIGITACION_ID:
                id = ContratoDbCmacIca.DigitacionTable.ObtenerIdDigitacion(uri);
                c = bd.query(DbCmacIcaHelper.Tablas.Digitacion,projection,
                        ContratoDbCmacIca.DigitacionTable.IdDigitacion + " = ?",
                        new String[]{id},null,null,null);
                break;
            case URIPersFteIngreso:
                c=bd.query(DbCmacIcaHelper.Tablas.PersFteIngreso,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;

            case URIPersFteIngreso_Id:
                id = ContratoDbCmacIca.PersFteIngresoTable.obtenerIdPersFteIngreso(uri);
                c= bd.query(DbCmacIcaHelper.Tablas.PersFteIngreso,projection,
                        ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion + " = ?",
                        new String[]{id},null,null,null);
                break;
            case URIPersFIDependiente :
                //Log.d(TAG,selection);
                c = bd.query(DbCmacIcaHelper.Tablas.PersFIDependiente,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case URIPersFIDependiente_Id:
                id = ContratoDbCmacIca.PersFIDependienteTable.obtenerIdPersFIDependiente(uri);

                Log.d(TAG,id);
                c =bd.query(DbCmacIcaHelper.Tablas.PersFIDependiente,projection,ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion+ " = ?", new String[]{id},null,null,null);
                break;
            case URIPersFIInDependiente :
                c = bd.query(DbCmacIcaHelper.Tablas.PersFIInDependiente,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case URIPersFIInDependiente_Id:
                id = ContratoDbCmacIca.PersFIDependienteTable.obtenerIdPersFIDependiente(uri);
                c =bd.query(DbCmacIcaHelper.Tablas.PersFIInDependiente,projection,ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion+ " = ?", new String[]{id},null,null,null);
                break;
            case URIPersFIGastoDet:
                c=  bd.query(DbCmacIcaHelper.Tablas.PersFIGastoDet,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case URIPersFIGastoDet_Id:
                id = ContratoDbCmacIca.PersFIGastoDetTable.obtenerIdPersFIGastoDet(uri);
                c= bd.query(DbCmacIcaHelper.Tablas.PersFIGastoDet,projection,ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso+ " = ?", new String[]{id},null,null,null);
                break;
            case URIPersonas:
                Log.d(TAG,"abd:" +selection);
                c=  bd.query(DbCmacIcaHelper.Tablas.Personas,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case URIPersonas_Id:
                id = ContratoDbCmacIca.PersFIGastoDetTable.obtenerIdPersFIGastoDet(uri);
                Log.d(TAG,"adt:" +selection);
                c= bd.query(DbCmacIcaHelper.Tablas.Personas,projection,ContratoDbCmacIca.PersonaTable.IdPersona+ " = ?", new String[]{id},null,null,null);
                break;
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        c.setNotificationUri(resolver,uri);

        return c;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "Inserción en " + uri + "( " + values.toString() + " )\n");

        SQLiteDatabase bd = manejadorDB.getWritableDatabase();
        String id = null;

        switch (uriMatcher.match(uri)){

            case URIDIGITACION:
                if(null ==values.getAsString(ContratoDbCmacIca.DigitacionTable.IdDigitacion)){

                    id = ContratoDbCmacIca.DigitacionTable.generarIdDigitacion();
                    values.put(ContratoDbCmacIca.DigitacionTable.IdDigitacion,id);
                }
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.Digitacion,null,values);
                notificarCambio(uri);

                return ContratoDbCmacIca.DigitacionTable.CrearUriDigitacion(id);
            case URIPersFteIngreso:
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.PersFteIngreso ,null,values);
                notificarCambio(uri);

                return ContratoDbCmacIca.PersFteIngresoTable.crearUriPersFteIngreso(id);
            case URIPersFIDependiente:
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.PersFIDependiente ,null,values);
                notificarCambio(uri);

                return ContratoDbCmacIca.PersFIDependienteTable.crearUriPersFIDependiente(id);
            case URIPersFIInDependiente:
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.PersFIInDependiente ,null,values);
                notificarCambio(uri);
                return ContratoDbCmacIca.PersFIInDependienteTable.crearUriPersFIInDependiente(id);
            case URIPersFIGastoDet:
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.PersFIGastoDet ,null,values);
                notificarCambio(uri);
                return ContratoDbCmacIca.PersFIGastoDetTable.crearUriPersFIGastoDet(id);
            case URIPersonas:
                bd.insertOrThrow(DbCmacIcaHelper.Tablas.Personas ,null,values);
                notificarCambio(uri);
                return ContratoDbCmacIca.PersonaTable.CrearUriPersona(id);
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.d(TAG," ELIMINANDO: " + uri);

        SQLiteDatabase bd = manejadorDB.getWritableDatabase();

        String id;
        int afectados;

        switch (uriMatcher.match(uri)){

            case URIDIGITACION:
                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.Digitacion,null,
                        null);
                notificarCambio(uri);

                break;
            case URIPersFteIngreso:

                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.PersFteIngreso,null,
                        null);
                notificarCambio(uri);
                break;
            case URIPersFIDependiente:
                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.PersFIDependiente,null,
                        null);
                notificarCambio(uri);
                break;
            case URIPersFIInDependiente:
                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.PersFIInDependiente,null,
                        null);
                notificarCambio(uri);
                break;
            case URIPersFIGastoDet:
                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.PersFIInDependiente,null,
                        null);
                notificarCambio(uri);
                break;
            case URIPersonas:
                afectados = bd.delete(
                        DbCmacIcaHelper.Tablas.Personas,null,
                        null);
                notificarCambio(uri);
                break;
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        return  afectados;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        Log.d("ACTUALIZANDO",  selection+selectionArgs[0].toString());

        SQLiteDatabase bd = manejadorDB.getWritableDatabase();
        String id;
        int afectados = 0;

        switch (uriMatcher.match(uri)){
            case URIDIGITACION_ID:
                id = ContratoDbCmacIca.DigitacionTable.ObtenerIdDigitacion(uri);
                afectados = bd.update(DbCmacIcaHelper.Tablas.Digitacion,values,
                        ContratoDbCmacIca.DigitacionTable.IdDigitacion + " = ?", new String[]{id});
                notificarCambio(uri);
                break;
            case URIPersFteIngreso:
                bd.update(DbCmacIcaHelper.Tablas.PersFteIngreso,values,selection,selectionArgs);
                //notificarCambio(uri);
                break;
            case URIPersFteIngreso_Id:
                id = ContratoDbCmacIca.PersFteIngresoTable.obtenerIdPersFteIngreso(uri);
                afectados = bd.update(DbCmacIcaHelper.Tablas.PersFteIngreso,values,
                        ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso + " = ?",new String[]{id});
                //notificarCambio(uri);
                break;
            case URIPersFIDependiente:
                bd.update(DbCmacIcaHelper.Tablas.PersFIDependiente,values,selection,selectionArgs);
                //resolver.notifyChange(uri,null,false);
                break;
            case URIPersFIDependiente_Id:
                id= ContratoDbCmacIca.PersFIDependienteTable.obtenerIdPersFIDependiente(uri);
                afectados = bd.update(DbCmacIcaHelper.Tablas.PersFIDependiente,values,
                        ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion +" = ?",new String[]{id});
                notificarCambio(uri);
                break;
            case URIPersFIInDependiente:

                bd.update(DbCmacIcaHelper.Tablas.PersFIInDependiente,values,selection,selectionArgs);
                //notificarCambio(uri);
                //resolver.notifyChange(uri,null,false);
                break;
            case URIPersFIInDependiente_Id:
                id= ContratoDbCmacIca.PersFIDependienteTable.obtenerIdPersFIDependiente(uri);
                afectados = bd.update(DbCmacIcaHelper.Tablas.PersFIInDependiente,values,
                        ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion +" = ?",new String[]{id});
                notificarCambio(uri);
                break;
            case URIPersFIGastoDet:
                bd.update(DbCmacIcaHelper.Tablas.PersFIGastoDet,values,selection,selectionArgs);
                resolver.notifyChange(uri,null,false);
                break;
            case URIPersFIGastoDet_Id:
                id= ContratoDbCmacIca.PersFIGastoDetTable.obtenerIdPersFIGastoDet(uri);
                afectados = bd.update(DbCmacIcaHelper.Tablas.PersFIGastoDet,values,
                        ContratoDbCmacIca.PersFIGastoDetTable.IdDigitacion +" = ?",new String[]{id});
                notificarCambio(uri);
                break;

            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        return afectados;
    }

    //endregion

    //PROYECCIONES



    private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }

}
