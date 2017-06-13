package pe.com.cmacica.flujocredito.Utilitarios;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

import pe.com.cmacica.flujocredito.Model.General.ConstanteModel;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIDependienteDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIGastoDetDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIInDependienteDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFteIngresoDto;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;

/**
 * Created by jhcc on 31/08/2016.
 */
public class UConsultas {

    public static String obtenerString(Cursor cursor, String columna) {
        return cursor.getString(cursor.getColumnIndex(columna));
    }

    public static int obtenerInt(Cursor cursor, String columna) {
        return cursor.getInt(cursor.getColumnIndex(columna));
    }

    public static float obtenerFloat(Cursor cursor, String columna) {
        return cursor.getFloat(cursor.getColumnIndex(columna));
    }

    public static void agregarStringAMapa(Map<String, Object> mapaGeneric, String columna, Cursor c) {
        mapaGeneric.put(columna, obtenerStringCursor(c, columna));
    }

    private static String obtenerStringCursor(Cursor c, String columna) {
        return c.getString(c.getColumnIndex(columna));
    }

    //region digitacion
    public static DigitacionDto ConverCursoToDigitacionDto(Cursor query){

        DigitacionDto result = new DigitacionDto();
        //query.moveToPosition(position);

        result.IdDigitacion = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.IdDigitacion));
        result.cCodSolicitud = query.getString(query.getColumnIndex(ContratoDbCmacIca.DigitacionTable.cCodSolicitud));
        //result.dFechaSol = Date.parse( obtenerString(query, ContratoDbCmacIca.DigitacionTable.dFechaSol));
        result.cDescripcionProductoCredito = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito));
        result.EquivalenteMoneda = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda));
        result.nMonto = Double.parseDouble(query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.nMonto)));
        result.cCtaCod = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cCtaCod));
        result.CodigoPersona = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.CodigoPersona));
        result.NumeroDocumento = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.NumeroDocumento));
        result.NombrePersona =query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.NombrePersona));
        result.cDescripcionTipoCredito = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito));
        result.cPersDireccDomicilio = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cPersDireccDomicilio));
        result.cPersTelefono = query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cPersTelefono));
        result.cPersTelefono2 =query.getString(query.getColumnIndex( ContratoDbCmacIca.DigitacionTable.cPersTelefono2));


        return result;
    }
    //endregion
    //region ConverCursorToPersFteIngresoDto
    public static PersFteIngresoDto ConverCursorToPersFteIngresoDto(Cursor query){

        PersFteIngresoDto result = new PersFteIngresoDto();
        result.IdDigitacion = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion));
        result.IdPersFteIngreso = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso));
        result.cNumFuente = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cNumFuente));
        result.cPersCod = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cPersCod));
        result.cPersFIPersCod = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod));
        result.cPersFICargo = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cPersFICargo));
        result.dPersFIinicio = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio));
        result.cRazSocUbiGeo = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cRazSocUbiGeo));
        result.cRazSocDirecc =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cRazSocDirecc));
        result.cRazSocTelef = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cRazSocTelef));
        result.cRazSocDescrip = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip));
        result.nPersTipFte = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte));
        result.bCostoprod =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.bCostoprod));
        result.nEstadoOpe =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe));

        return result;
    }

    //endregion
    //region ConverCursorToPersFIDependienteDto
    public static PersFIDependienteDto ConverCursorToPersFIDependienteDto(Cursor query){

        PersFIDependienteDto result = new PersFIDependienteDto();
        result.IdDigitacion = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion));
        result.IdPersFteIngreso = query.getString(query.getColumnIndex(ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso));
        result.cNumFuente = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cNumFuente));
        result.dPersEval = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.dPersEval));
        result.nPersIngCli = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli));
        result.nPersIngCon = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon));
        result.nPersOtrIng = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng));
        result.nPersGastoFam = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam));
        result.dPersFICaduca =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.dPersFICaduca));
        result.nPersPagoCuotas = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas));
        result.cPersFIMoneda =  query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda));
        result.cPersFICargo = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cPersFICargo));
        result.cPersFIAreaTrabajo = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cPersFIAreaTrabajo));
        result.nCodFrecPago = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nCodFrecPago));
        result.cComentario1 = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cComentario1));
        result.cComentario2 = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.cComentario2));
        result.nEstadoOpe =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe));

        return result;
    }
    //endregion
    //region ConvertCursorToPersFteIndependiente
    public static PersFIInDependienteDto ConverCursorToPersFIInDependienteDto(Cursor query){

        PersFIInDependienteDto result = new PersFIInDependienteDto();

        result.IdDigitacion = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion));
        result.IdPersFteIngreso = query.getString(query.getColumnIndex(ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso));
        result.cNumFuente = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.cNumFuente));
        result.dPersEval = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.dPersEval));
        result.nPersFIActivoDisp = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp));
        result.nPersFICtasxCobrar = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar));
        result.nPersFIInventarios = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios));
        result.nPersFIActivosFijos = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos));
        result.nPersFIProveedores = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores));
        result.nPersFICredCMACT = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT));
        result.nPersFICredOtros = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros));
        result.nPersFIVentas =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas));
        result.nPersFIPatrimonio = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIPatrimonio));
        result.nPersFICostoVentas = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas));
        result.nPersFIRecupCtasXCobrar = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar));
        result.nPersFIEgresosOtros = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros));
        result.nPersIngFam = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam));
        result.nPersEgrFam = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam));
        result.cPersFIMoneda =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda));
        result.nPersPagoCuotas = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPersPagoCuotas));
        result.nPasivoNoCorriente = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente));
        result.nCodSectorEcon = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nCodSectorEcon));
        result.cComentario1 = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.cComentario1));
        result.cComentario2 = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.cComentario2));
        result.cComentario3 = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.cComentario3));
        result.nEstadoOpe =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIInDependienteTable.nEstadoOpe));

        return result;
    }
    //endregion
    //region ConvertCursorToPersFIGastoDetDto
    public static PersFIGastoDetDto ConverCursorToPersFIGastoDetDto(Cursor query){

        PersFIGastoDetDto result = new PersFIGastoDetDto();

        result.IdDigitacion = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.IdDigitacion));
        result.IdPersFteIngreso = query.getString(query.getColumnIndex(ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso));
        result.cNumFuente = query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.cNumFuente));
        result.nTpoGasto = Integer.parseInt( query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto)));
        result.nPrdConceptoCod = Integer.parseInt( query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.nPrdConceptoCod)));
        result.nMonto = Double.parseDouble( query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.nMonto)));
        result.nEstadoOpe =query.getString(query.getColumnIndex( ContratoDbCmacIca.PersFIGastoDetTable.nEstadoOpe));

        return result;
    }
    //endregion

    //region ConverCursorToConstanteModel
    public static ConstanteModel ConverCursorToConstanteModel(Cursor query){

        ConstanteModel result = new ConstanteModel(
                query.getInt(query.getColumnIndex( ContratoDbCmacIca.ConstanteTable.nConsCod)),
                query.getInt(query.getColumnIndex( ContratoDbCmacIca.ConstanteTable.nConsValor)),
                query.getString(query.getColumnIndex( ContratoDbCmacIca.ConstanteTable.cConsDescripcion)),
                query.getInt(query.getColumnIndex( ContratoDbCmacIca.ConstanteTable.nConsEquivalente))

        );



        return result;
    }

    //endregion

    //region ConvertCursorToPersFIGastoDetDto
    public static PersonaDto ConverCursorToPersona(Cursor query){

        PersonaDto result = new PersonaDto();
        result.setcPersCod(query.getString(query.getColumnIndex( ContratoDbCmacIca.PersonaTable.cPersCod)));
        result.setcDoi(query.getString(query.getColumnIndex( ContratoDbCmacIca.PersonaTable.cDoi)));
        result.setcDireccion(query.getString(query.getColumnIndex( ContratoDbCmacIca.PersonaTable.cDireccion)));
        result.setcPersNombre(query.getString(query.getColumnIndex( ContratoDbCmacIca.PersonaTable.cPersNombre)));



        return result;
    }
    //endregion

    public static String CalifSBS (Integer IntCalif){
        String Result = "";
        switch (IntCalif){
            case 0:
                Result = "NORMAL";
                break;
            case 1:
                Result = "CPP";
                break;
            case 2:
                Result = "DEFICIENTE";
                break;
            case 3:
                Result = "DUDOSO";
                break;
            case 4:
                Result = "PÉRDIDA";
                break;
        }
        return Result;
    }
    public static float ConvierteTEMaTEA(float tem){

        float result = UGeneral.round (
                (float) ((Math.pow ( ((tem/100.00)+1),12))-1)*100
                ,2);

        return result;

    }

    //update gasto

    public static class TareaUpdateGasto extends AsyncTask<Uri, Void, Void> {
        private final ContentResolver resolver;


        private final List<PersFIGastoDetDto> gastoDet;

        public TareaUpdateGasto(ContentResolver resolver, List<PersFIGastoDetDto> GastoDet) {
            this.resolver = resolver;
            this.gastoDet = GastoDet;
        }

        @Override
        protected Void doInBackground(Uri... args) {

            for(PersFIGastoDetDto gastoSel: gastoDet){

                ContentValues valoresupd = new ContentValues();
                valoresupd.put(ContratoDbCmacIca.PersFIGastoDetTable.nMonto, UGeneral.RoundDouble( gastoSel.nMonto));
                resolver.update(
                        ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO,
                        valoresupd,
                        ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso + " =  ? AND "+
                                ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto+ " = ? AND "+
                                ContratoDbCmacIca.PersFIGastoDetTable.nPrdConceptoCod+ " = ?",
                        new String[]{gastoSel.IdPersFteIngreso,
                                String.valueOf(gastoSel.nTpoGasto),
                                String.valueOf(gastoSel.nPrdConceptoCod)
                        });
            }

            return null;
        }

    }

    public static class TareaUpdateFteDep extends AsyncTask<Uri, Void, Void> {
        private final ContentResolver resolver;
        private final ContentValues valores;
        private final String IdPersFteIngreso;

        public TareaUpdateFteDep(ContentResolver resolver, ContentValues valores, String idPersFteIngreso) {
            this.resolver = resolver;
            this.valores = valores;
            this.IdPersFteIngreso = idPersFteIngreso;
        }

        @Override
        protected Void doInBackground(Uri... args) {

            valores.put(ContratoDbCmacIca.PersFIDependienteTable.dVersion, UGeneral.obtenerTiempo());

            resolver.update(
                    ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO,
                    valores,
                    ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso + " = ?",
                    new String[]{IdPersFteIngreso});


            return null;
        }

    }
    public static class TareaUpdateFteInDep extends AsyncTask<Uri, Void, Void> {
        private final ContentResolver resolver;
        private final ContentValues valores;
        private final String IdPersFteIngreso;

        public TareaUpdateFteInDep(ContentResolver resolver, ContentValues valores, String idPersFteIngreso) {
            this.resolver = resolver;
            this.valores = valores;
            this. IdPersFteIngreso = idPersFteIngreso;
        }

        @Override
        protected Void doInBackground(Uri... args) {

            valores.put(ContratoDbCmacIca.PersFIInDependienteTable.dVersion, UGeneral.obtenerTiempo());
            resolver.update(
                    ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO,
                    valores,
                    ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso + " = ?",
                    new String[]{IdPersFteIngreso});


            return null;
        }

    }

    public static class TareaUpdateFteIngreso extends AsyncTask<Uri, Void, Void> {
        private final ContentResolver resolver;
        private final ContentValues valores;
        private final String IdFteIngreso;

        public TareaUpdateFteIngreso(ContentResolver resolver, ContentValues valores, String idFteIngreso) {
            this.resolver = resolver;
            this.valores = valores;
            this.IdFteIngreso = idFteIngreso;
        }

        @Override
        protected Void doInBackground(Uri... args) {

            valores.put(ContratoDbCmacIca.PersFteIngresoTable.dVersion, UGeneral.obtenerTiempo());

            resolver.update(
                    ContratoDbCmacIca.PersFteIngresoTable.URI_CONTENIDO,
                    valores,
                    ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso + " = ?",
                    new String[]{IdFteIngreso});

            return null;
        }

    }
}
