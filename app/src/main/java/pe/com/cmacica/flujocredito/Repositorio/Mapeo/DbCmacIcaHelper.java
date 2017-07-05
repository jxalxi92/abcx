package pe.com.cmacica.flujocredito.Repositorio.Mapeo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by jhcc on 03/08/2016.
 */
public class DbCmacIcaHelper extends SQLiteOpenHelper {

    private static final  String NombreBD= "dbcmacica.db";
    private static final  int VERSION_ACTUAL = 1;


    public interface Tablas{

        String Personas = "Personas";
        String Digitacion = "Digitacion";
        String PersFteIngreso = "PersFteIngreso";
        String PersFIDependiente = "PersFIDependiente";
        String PersFIInDependiente = "PersFIInDependiente";
        String PersFIGastoDet = "PersFIGastoDet";
        String Constantes="Constantes";
    }

    public DbCmacIcaHelper(Context  Contexto) {
        super(Contexto, NombreBD, null, VERSION_ACTUAL);
        this.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db){

        super.onOpen(db);
        if (!db.isReadOnly()){
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN){
                db.setForeignKeyConstraintsEnabled(true);
            }else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String cmd = "";
        //crear tabla de digitacion
        cmd = "CREATE TABLE " + Tablas.Digitacion + " ("+
                ContratoDbCmacIca.DigitacionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContratoDbCmacIca.DigitacionTable.IdDigitacion + " TEXT UNIQUE NOT NULL, "+
                ContratoDbCmacIca.DigitacionTable.cCodSolicitud + " TEXT, "+
                ContratoDbCmacIca.DigitacionTable.cCtaCod + " TEXT, "+
                ContratoDbCmacIca.DigitacionTable.dFechaSol + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito+ " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.CodigoPersona + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.NumeroDocumento + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.NombrePersona + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.nMonto + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.TipoProceso + " TEXT, " +
                ContratoDbCmacIca.DigitacionTable.cPersDireccDomicilio + " TEXT, "+
                ContratoDbCmacIca.DigitacionTable.cPersTelefono + " TEXT, "+
                ContratoDbCmacIca.DigitacionTable.cPersTelefono2 + " TEXT, "+
                ContratoDbCmacIca.DigitacionTable.nEstadoOpe + " TEXT "+
                " )" ;
        db.execSQL(cmd);

        //crear tabla de digitacion
        cmd = "CREATE TABLE " + Tablas.Personas + " ("+
                ContratoDbCmacIca.PersonaTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContratoDbCmacIca.PersonaTable.IdPersona + " TEXT UNIQUE NOT NULL, "+
                ContratoDbCmacIca.PersonaTable.cPersCod + " TEXT, "+
                ContratoDbCmacIca.PersonaTable.cPersNombre + " TEXT, "+
                ContratoDbCmacIca.PersonaTable.cDoi + " TEXT, " +
                ContratoDbCmacIca.PersonaTable.cDireccion + " TEXT, "+
                ContratoDbCmacIca.PersonaTable.dVersion + " TEXT, " +
                ContratoDbCmacIca.PersonaTable.nEstadoOpe + " TEXT "+
                " )" ;
        db.execSQL(cmd);

        //CREAR TABLA FUENTE DE INGRESOS PersFteIngreso

        cmd= "CREATE TABLE " + Tablas.PersFteIngreso + " ("+
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso + " TEXT UNIQUE NOT NULL , " +
                ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cNumFuente + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cPersCod + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cPersFICargo + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cRazSocUbiGeo + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cRazSocDirecc + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cRazSocTelef + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte + " TEXT, "+
                ContratoDbCmacIca.PersFteIngresoTable.bCostoprod + " TEXT, " +
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe + " TEXT, " +
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe1 + " TEXT, " +
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe2 + " TEXT, " +
                ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe3 + " TEXT, " +
                ContratoDbCmacIca.PersFteIngresoTable.dVersion + " TEXT " +
                //ContratoDbCmacIca.PersFteIngreso.IdDigitacion + " TEXT NOT NULL "+ Referencias.IDPersona+
                " )";
        db.execSQL(cmd);

        //CREAR TABLA FUENTE DE INGRESOS DEPENDIENTE

        cmd = "CREATE TABLE " + Tablas.PersFIDependiente + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.cNumFuente + " TEXT ,"+
                ContratoDbCmacIca.PersFIDependienteTable.dPersEval + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas + " TEXT, " +
                ContratoDbCmacIca.PersFIDependienteTable.dPersFICaduca + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.cPersFICargo + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.cPersFIAreaTrabajo + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.nCodFrecPago + " TEXT, "+
                ContratoDbCmacIca.PersFIDependienteTable.cComentario1 + " TEXT, " +
                ContratoDbCmacIca.PersFIDependienteTable.cComentario2 + " TEXT, " +
                ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe + " TEXT, " +
                ContratoDbCmacIca.PersFIDependienteTable.dVersion + " TEXT, " +
                ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso + " TEXT NOT NULL " + // + Referencias.IdPersFteIngreso +
                " )";
        db.execSQL(cmd);


        //CREAR TABLA FUENTE DE INGRESOS INDEPENDIENTE

        cmd = "CREATE TABLE " + Tablas.PersFIInDependiente + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.cNumFuente + " TEXT,"+
                ContratoDbCmacIca.PersFIInDependienteTable.dPersEval + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIPatrimonio + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nPersPagoCuotas + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.nCodSectorEcon + " TEXT, "+
                ContratoDbCmacIca.PersFIInDependienteTable.cComentario1 + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.cComentario2 + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.cComentario3 + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.nEstadoOpe + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.dVersion + " TEXT, " +
                ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso + " TEXT NOT NULL " + // + Referencias.IdPersFteIngreso +
                " )";
        db.execSQL(cmd);

        //CREAR TABLA DETALLE DE LOS GASTO DE LA FUENTE DE INGRESO DEP

        cmd = "CREATE TABLE "+ Tablas.PersFIGastoDet + " ("+
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoDbCmacIca.PersFIGastoDetTable.IdDigitacion + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.cNumFuente + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.dfecEval+ " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.cFIMoneda + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.nPrdConceptoCod + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.nMonto + " TEXT, "+
                ContratoDbCmacIca.PersFIGastoDetTable.nEstadoOpe + " TEXT, " +
                ContratoDbCmacIca.PersFIGastoDetTable.dVersion + " TEXT, " +
                ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " TEXT NOT NULL " + //+ Referencias.IdPersFteIngreso +
                " )";

        db.execSQL(cmd);

        //CREAR TABLA CONSTANTES

        cmd = "CREATE TABLE "+ Tablas.Constantes + " ("+

                ContratoDbCmacIca.ConstanteTable.nConsCod + " TEXT, "+
                ContratoDbCmacIca.ConstanteTable.nConsValor + " TEXT, "+
                ContratoDbCmacIca.ConstanteTable.cConsDescripcion+ " TEXT,  " +
                ContratoDbCmacIca.ConstanteTable.nConsEquivalente+ " TEXT  " +//
                " )";

        db.execSQL(cmd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.PersFteIngreso);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.PersFIDependiente);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.PersFIGastoDet);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Digitacion);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.PersFIInDependiente);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Personas);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Constantes);

            onCreate(db);
        }catch (SQLiteException e){
            Log.e("Contrato",e.getMessage());
        }


    }
}
