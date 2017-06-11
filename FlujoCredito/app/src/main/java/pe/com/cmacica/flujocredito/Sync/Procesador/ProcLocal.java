package pe.com.cmacica.flujocredito.Sync.Procesador;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIDependienteDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIGastoDetDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFIInDependienteDto;
import pe.com.cmacica.flujocredito.Model.Digitacion.PersFteIngresoDto;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * Created by jhcc on 04/08/2016.
 */
public class ProcLocal {

    private static final String TAG = ProcLocal.class.getSimpleName();


    private HashMap<String,DigitacionDto> DigitacionRemoto = new HashMap<>();
    public ProcLocal(){

    }
    //region Deserealizar ///

    public void procesar(JSONArray arrayJsonDigitacion){

        try{

            String FechaSync = UGeneral.obtenerTiempo();

            for (int i = 0; i < arrayJsonDigitacion.length(); i++) {

                JSONObject fila =  (JSONObject) arrayJsonDigitacion.get(i);

                try {

                    //region cargar datos de la digitacion
                    DigitacionDto digDto = new DigitacionDto();
                    digDto.IdDigitacion =  ContratoDbCmacIca.DigitacionTable.generarIdDigitacion();
                    digDto.cCodSolicitud= fila.getString("cCodSolicitud");
                    digDto.cDescripcionProductoCredito= fila.getString("cDescripcionProductoCredito");
                    digDto.EquivalenteMoneda= fila.getString("EquivalenteMoneda");
                    digDto.nMonto = fila.getDouble("nMonto");
                    digDto.cCtaCod= fila.getString("cCtaCod");
                    digDto.CodigoPersona= fila.getString("CodigoPersona");
                    digDto.NumeroDocumento= fila.getString("NumeroDocumento");
                    digDto.NombrePersona = fila.getString("NombrePersona");
                    digDto.cDescripcionTipoCredito= fila.getString("cDescripcionTipoCredito");
                    digDto.TipoProceso = fila.getString("TipoProceso");
                    digDto.cPersDireccDomicilio= fila.getString("cPersDireccDomicilio");
                    digDto.cPersTelefono= fila.getString("cPersTelefono");
                    digDto.cPersTelefono2= fila.getString("cPersTelefono2");
                    digDto.nEstadoOpe = "0";

                    //endregion

                    digDto.FteIngreso = new ArrayList<>();
                    JSONObject UltPersFteIngresoObject = fila.getJSONObject("FteIgreso");

                    PersFteIngresoDto FIDep = InicializarPersFteIngresoDto();
                    PersFteIngresoDto FIInDep  = InicializarPersFteIngresoDto();

                    int TipoFuente = UltPersFteIngresoObject.getInt("nPersTipFte");
                    String IdPersFteIngresoDep = ContratoDbCmacIca.PersFteIngresoTable.generarIdPersFteIngreso();
                    String IdPersFteIngresoInDep = ContratoDbCmacIca.PersFteIngresoTable.generarIdPersFteIngreso();

                    if (TipoFuente == 1){ //dependiente

                        //region cargamos los datos dela fte ingreso de la persona
                        FIDep.IdPersFteIngreso =IdPersFteIngresoDep;
                        FIDep.cNumFuente = UltPersFteIngresoObject.getString("cNumFuente");
                        FIDep.IdDigitacion = digDto.IdDigitacion;
                        FIDep.cPersCod = UltPersFteIngresoObject.getString("cPersCod");
                        FIDep.cPersFIPersCod = UltPersFteIngresoObject.getString("cPersFIPersCod");
                        FIDep.cPersFICargo = UltPersFteIngresoObject.getString("cPersFICargo");
                        FIDep.dPersFIinicio = UltPersFteIngresoObject.getString("dPersFIinicio");
                        FIDep.cRazSocUbiGeo = UltPersFteIngresoObject.getString("cRazSocUbiGeo");
                        FIDep.cRazSocDirecc = UltPersFteIngresoObject.getString("cRazSocDirecc");
                        FIDep.cPersOcupacion = UltPersFteIngresoObject.getString("cPersOcupacion");
                        FIDep.cRazSocTelef = UltPersFteIngresoObject.getString("cRazSocTelef");
                        FIDep.cRazSocDescrip = UltPersFteIngresoObject.getString("cRazSocDescrip");
                        FIDep.nPersTipFte = "1";// UltPersFteIngresoObject.getString("nPersTipFte");
                        FIDep.bCostoprod = UltPersFteIngresoObject.getString("bCostoprod");

                        FIDep.dVersion = FechaSync;
                        FIDep.nEstadoOpe = "0";
                        //endregion

                        //region Cargando Fte Dependiente
                        JSONObject FteDepObject = (JSONObject) UltPersFteIngresoObject.getJSONArray("FIDependiente").get(0);
                        FIDep.FIDependiente = new PersFIDependienteDto();
                        FIDep.FIDependiente.IdPersFteIngreso=IdPersFteIngresoDep;
                        FIDep.FIDependiente.IdDigitacion=digDto.IdDigitacion ;
                        if(FteDepObject.getString("cPersFIMoneda").equals("1")) {

                            FIDep.FIDependiente.cNumFuente = FteDepObject.getString("cNumFuente");
                            FIDep.FIDependiente.dPersEval = FteDepObject.getString("dPersEval");
                            FIDep.FIDependiente.nPersIngCli = FteDepObject.getString("nPersIngCli");
                            FIDep.FIDependiente.nPersIngCon = FteDepObject.getString("nPersIngCon");
                            FIDep.FIDependiente.nPersOtrIng = FteDepObject.getString("nPersOtrIng");
                            FIDep.FIDependiente.nPersGastoFam = FteDepObject.getString("nPersGastoFam");
                            FIDep.FIDependiente.dPersFICaduca = FteDepObject.getString("dPersFICaduca");
                            FIDep.FIDependiente.nPersPagoCuotas = FteDepObject.getString("nPersPagoCuotas");

                            FIDep.FIDependiente.cPersFIMoneda = FteDepObject.getString("cPersFIMoneda");
                            FIDep.FIDependiente.cPersFICargo = FteDepObject.getString("cPersFICargo");
                            FIDep.FIDependiente.cPersFIAreaTrabajo = FteDepObject.getString("cPersFIAreaTrabajo");
                            FIDep.FIDependiente.nCodFrecPago = FteDepObject.getString("nCodFrecPago");

                            FIDep.FIDependiente.cComentario1 = FteDepObject.getJSONObject("FIComentario").getString("cComentario1");
                            FIDep.FIDependiente.cComentario2 = FteDepObject.getJSONObject("FIComentario").getString("cComentario2");
                            FIDep.FIDependiente.dVersion = FechaSync;
                            FIDep.FIDependiente.nEstadoOpe = "0";
                            FIDep.FIDependiente.Gastos = ConvertArrayJsonTOListGastoDep(FteDepObject.getJSONArray("GastosSoles"), digDto.IdDigitacion, IdPersFteIngresoDep);

                        }else{ //Dolares

                            FIDep.FIDependiente.cNumFuente = FteDepObject.getString("cNumFuente");
                            FIDep.FIDependiente.dPersEval = FteDepObject.getString("dPersEval");
                            FIDep.FIDependiente.nPersIngCli = FteDepObject.getString("nPersIngCliD");
                            FIDep.FIDependiente.nPersIngCon = FteDepObject.getString("nPersIngConD");
                            FIDep.FIDependiente.nPersOtrIng = FteDepObject.getString("nPersOtrIngD");
                            FIDep.FIDependiente.nPersGastoFam = FteDepObject.getString("nPersGastoFamD");
                            FIDep.FIDependiente.dPersFICaduca = FteDepObject.getString("dPersFICaducaD");
                            FIDep.FIDependiente.nPersPagoCuotas = FteDepObject.getString("nPersPagoCuotasD");

                            FIDep.FIDependiente.cPersFIMoneda = FteDepObject.getString("cPersFIMoneda");
                            FIDep.FIDependiente.cPersFICargo = FteDepObject.getString("cPersFICargo");
                            FIDep.FIDependiente.cPersFIAreaTrabajo = FteDepObject.getString("cPersFIAreaTrabajo");
                            FIDep.FIDependiente.nCodFrecPago = FteDepObject.getString("nCodFrecPago");

                            FIDep.FIDependiente.cComentario1 = FteDepObject.getJSONObject("FIComentario").getString("cComentario1");
                            FIDep.FIDependiente.cComentario2 = FteDepObject.getJSONObject("FIComentario").getString("cComentario2");
                            FIDep.FIDependiente.dVersion = FechaSync;
                            FIDep.FIDependiente.nEstadoOpe = "0";
                            FIDep.FIDependiente.Gastos = ConvertArrayJsonTOListGastoDep(FteDepObject.getJSONArray("GastosDolares"), digDto.IdDigitacion, IdPersFteIngresoDep);
                        }
                        //endregion

                        //region inicializamos la fte independiente

                        FIInDep.IdPersFteIngreso =IdPersFteIngresoInDep;
                        FIInDep.IdDigitacion = digDto.IdDigitacion;
                        FIInDep.cPersCod = digDto.CodigoPersona;
                        FIInDep.cPersFIPersCod = digDto.CodigoPersona;
                        FIInDep.cRazSocDescrip = digDto.NombrePersona;
                        FIInDep.nPersTipFte = "2";
                        FIInDep.dVersion = FechaSync;
                        FIInDep.nEstadoOpe = "0";

                        FIInDep.FIIndependiente = InicializarFteIngresoInDep();
                        FIInDep.FIIndependiente.IdDigitacion=digDto.IdDigitacion ;
                        FIInDep.FIIndependiente.IdPersFteIngreso= IdPersFteIngresoInDep;
                        FIInDep.FIIndependiente.Disponible = ConvertArrayJsonTOListDisponible(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.Inventario = ConvertArrayJsonTOListInventario(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.ActivoFijo = ConvertArrayJsonTOListActivoFijo(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.GastoOpeAdm = ConvertArrayJsonTOListGastoOpeAdm(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.IgrFueraNeg = ConvertArrayJsonTOListIgrFueraNeg(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.GastoFamiliares = ConvertArrayJsonTOListGastoFam(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.PasivoNoCorriente = ConvertArrayJsonTOListPasivoNoCoriente(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        //endregion

                    }else if(TipoFuente == 2) { //car Independiente

                        //region cargamos los datos dela fte ingreso de la persona
                        FIInDep.IdPersFteIngreso =IdPersFteIngresoInDep;
                        FIInDep.cNumFuente = UltPersFteIngresoObject.getString("cNumFuente");
                        FIInDep.IdDigitacion = digDto.IdDigitacion;
                        FIInDep.cPersCod = UltPersFteIngresoObject.getString("cPersCod");
                        FIInDep.cPersFIPersCod = UltPersFteIngresoObject.getString("cPersFIPersCod");
                        FIInDep.cPersFICargo = UltPersFteIngresoObject.getString("cPersFICargo");
                        FIInDep.dPersFIinicio = UltPersFteIngresoObject.getString("dPersFIinicio");
                        FIInDep.cRazSocUbiGeo = UltPersFteIngresoObject.getString("cRazSocUbiGeo");
                        FIInDep.cRazSocDirecc = UltPersFteIngresoObject.getString("cRazSocDirecc");
                        FIInDep.cPersOcupacion = UltPersFteIngresoObject.getString("cPersOcupacion");
                        FIInDep.cRazSocTelef = UltPersFteIngresoObject.getString("cRazSocTelef");
                        FIInDep.cRazSocDescrip = UltPersFteIngresoObject.getString("cRazSocDescrip");
                        FIInDep.nPersTipFte = "2";// UltPersFteIngresoObject.getString("nPersTipFte");
                        FIInDep.bCostoprod = UltPersFteIngresoObject.getString("bCostoprod");

                        FIInDep.dVersion = FechaSync;
                        FIInDep.nEstadoOpe = "0";
                        //endregion

                        //region Cargando Fte InDependiente

                        JSONObject FteInDepObject = (JSONObject) UltPersFteIngresoObject.getJSONArray("FIInDependiente").get(0);
                        FIInDep.FIIndependiente = new PersFIInDependienteDto();

                        if(FteInDepObject.getString("cPersFIMoneda").equals("1")) {

                            FIInDep.FIIndependiente.IdDigitacion = digDto.IdDigitacion;
                            FIInDep.FIIndependiente.IdPersFteIngreso = IdPersFteIngresoInDep;
                            FIInDep.FIIndependiente.cNumFuente = FteInDepObject.getString("cNumFuente");
                            FIInDep.FIIndependiente.dPersEval = FteInDepObject.getString("dPersEval");
                            FIInDep.FIIndependiente.nPersFIActivoDisp = FteInDepObject.getString("nPersFIActivoDisp");
                            FIInDep.FIIndependiente.nPersFICtasxCobrar = FteInDepObject.getString("nPersFICtasxCobrar");
                            FIInDep.FIIndependiente.nPersFIInventarios = FteInDepObject.getString("nPersFIInventarios");
                            FIInDep.FIIndependiente.nPersFIActivosFijos = FteInDepObject.getString("nPersFIActivosFijos");
                            FIInDep.FIIndependiente.nPersFIProveedores = FteInDepObject.getString("nPersFIProveedores");
                            FIInDep.FIIndependiente.nPersFICredCMACT = FteInDepObject.getString("nPersFICredCMACT");
                            FIInDep.FIIndependiente.nPersFICredOtros = FteInDepObject.getString("nPersFICredOtros");
                            FIInDep.FIIndependiente.nPersFIVentas = FteInDepObject.getString("nPersFIVentas");
                            FIInDep.FIIndependiente.nPersFIPatrimonio = FteInDepObject.getString("nPersFIPatrimonio");
                            FIInDep.FIIndependiente.nPersFICostoVentas = FteInDepObject.getString("nPersFICostoVentas");
                            FIInDep.FIIndependiente.nPersFIRecupCtasXCobrar = FteInDepObject.getString("nPersFIRecupCtasXCobrar");
                            FIInDep.FIIndependiente.nPersFIEgresosOtros = FteInDepObject.getString("nPersFIEgresosOtros");
                            FIInDep.FIIndependiente.nPersIngFam = FteInDepObject.getString("nPersIngFam");
                            FIInDep.FIIndependiente.nPersEgrFam = FteInDepObject.getString("nPersEgrFam");
                            FIInDep.FIIndependiente.cPersFIMoneda = FteInDepObject.getString("cPersFIMoneda");
                            FIInDep.FIIndependiente.nPersPagoCuotas = FteInDepObject.getString("nPersPagoCuotas");
                            FIInDep.FIIndependiente.nPasivoNoCorriente = FteInDepObject.getString("nPasivoNoCorriente");
                            FIInDep.FIIndependiente.nCodSectorEcon = FteInDepObject.getString("nCodSectorEcon");

                            FIInDep.FIIndependiente.Disponible = ConvertArrayJsonTOListDisponible(FteInDepObject.getJSONArray("GastosSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.Inventario = ConvertArrayJsonTOListInventario(FteInDepObject.getJSONArray("InventarioSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.ActivoFijo = ConvertArrayJsonTOListActivoFijo(FteInDepObject.getJSONArray("ActivoFijosSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.GastoOpeAdm = ConvertArrayJsonTOListGastoOpeAdm(FteInDepObject.getJSONArray("GastosOperativosSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.IgrFueraNeg = ConvertArrayJsonTOListIgrFueraNeg(FteInDepObject.getJSONArray("IngresoNegocioSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.GastoFamiliares = ConvertArrayJsonTOListGastoFam(FteInDepObject.getJSONArray("GastosFamiliaresSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.PasivoNoCorriente = ConvertArrayJsonTOListPasivoNoCoriente(FteInDepObject.getJSONArray("PasivoSoles"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.cComentario1 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario1");
                            FIInDep.FIIndependiente.cComentario2 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario2");
                            FIInDep.FIIndependiente.cComentario3 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario3");

                        }else{

                            FIInDep.FIIndependiente.IdDigitacion = digDto.IdDigitacion;
                            FIInDep.FIIndependiente.IdPersFteIngreso = IdPersFteIngresoInDep;
                            FIInDep.FIIndependiente.cNumFuente = FteInDepObject.getString("cNumFuente");
                            FIInDep.FIIndependiente.dPersEval = FteInDepObject.getString("dPersEval");
                            FIInDep.FIIndependiente.nPersFIActivoDisp = FteInDepObject.getString("nPersFIActivoDispD");
                            FIInDep.FIIndependiente.nPersFICtasxCobrar = FteInDepObject.getString("nPersFICtasxCobrarD");
                            FIInDep.FIIndependiente.nPersFIInventarios = FteInDepObject.getString("nPersFIInventariosD");
                            FIInDep.FIIndependiente.nPersFIActivosFijos = FteInDepObject.getString("nPersFIActivosFijosD");
                            FIInDep.FIIndependiente.nPersFIProveedores = FteInDepObject.getString("nPersFIProveedoresD");
                            FIInDep.FIIndependiente.nPersFICredCMACT = FteInDepObject.getString("nPersFICredCMACTD");
                            FIInDep.FIIndependiente.nPersFICredOtros = FteInDepObject.getString("nPersFICredOtrosD");
                            FIInDep.FIIndependiente.nPersFIVentas = FteInDepObject.getString("nPersFIVentasD");
                            FIInDep.FIIndependiente.nPersFIPatrimonio = FteInDepObject.getString("nPersFIPatrimonioD");
                            FIInDep.FIIndependiente.nPersFICostoVentas = FteInDepObject.getString("nPersFICostoVentasD");
                            FIInDep.FIIndependiente.nPersFIRecupCtasXCobrar = FteInDepObject.getString("nPersFIRecupCtasXCobrarD");
                            FIInDep.FIIndependiente.nPersFIEgresosOtros = FteInDepObject.getString("nPersFIEgresosOtrosD");
                            FIInDep.FIIndependiente.nPersIngFam = FteInDepObject.getString("nPersIngFamD");
                            FIInDep.FIIndependiente.nPersEgrFam = FteInDepObject.getString("nPersEgrFamD");
                            FIInDep.FIIndependiente.cPersFIMoneda = FteInDepObject.getString("cPersFIMoneda");
                            FIInDep.FIIndependiente.nPersPagoCuotas = FteInDepObject.getString("nPersPagoCuotasD");
                            FIInDep.FIIndependiente.nPasivoNoCorriente = FteInDepObject.getString("nPasivoNoCorrienteD");
                            FIInDep.FIIndependiente.nCodSectorEcon = FteInDepObject.getString("nCodSectorEcon");

                            FIInDep.FIIndependiente.Disponible = ConvertArrayJsonTOListDisponible(FteInDepObject.getJSONArray("GastosDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.Inventario = ConvertArrayJsonTOListInventario(FteInDepObject.getJSONArray("InventarioDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.ActivoFijo = ConvertArrayJsonTOListActivoFijo(FteInDepObject.getJSONArray("ActivoFijosDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.GastoOpeAdm = ConvertArrayJsonTOListGastoOpeAdm(FteInDepObject.getJSONArray("GastosOperativosDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            if(!FteInDepObject.getString("IngresoNegocioDolares").equals("null")){
                                FIInDep.FIIndependiente.IgrFueraNeg = ConvertArrayJsonTOListIgrFueraNeg(FteInDepObject.getJSONArray("IngresoNegocioDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            } else{
                                FIInDep.FIIndependiente.IgrFueraNeg = ConvertArrayJsonTOListIgrFueraNeg(null, digDto.IdDigitacion, IdPersFteIngresoInDep);
                            }
                            FIInDep.FIIndependiente.GastoFamiliares = ConvertArrayJsonTOListGastoFam(FteInDepObject.getJSONArray("GastosFamiliaresDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.PasivoNoCorriente = ConvertArrayJsonTOListPasivoNoCoriente(FteInDepObject.getJSONArray("PasivoDolares"), digDto.IdDigitacion, IdPersFteIngresoInDep);
                            FIInDep.FIIndependiente.cComentario1 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario1");
                            FIInDep.FIIndependiente.cComentario2 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario2");
                            FIInDep.FIIndependiente.cComentario3 = FteInDepObject.getJSONObject("FIComentario").getString("cComentario3");

                        }
                        FIInDep.FIIndependiente.dVersion=FechaSync;
                        FIInDep.FIIndependiente.nEstadoOpe="0";

                        //endregion

                        //region Inicializamos la fte dependiente

                        FIDep.IdPersFteIngreso =IdPersFteIngresoDep;
                        FIDep.IdDigitacion = digDto.IdDigitacion;
                        FIDep.cPersCod = digDto.CodigoPersona;
                        FIDep.cPersFIPersCod = digDto.CodigoPersona;
                        FIDep.cRazSocDescrip = digDto.NombrePersona;
                        FIDep.nPersTipFte = "1";
                        FIDep.dVersion = FechaSync;
                        FIDep.nEstadoOpe = "0";

                        FIDep.FIDependiente = InicializarFteIngresoDep();
                        FIDep.FIDependiente.IdDigitacion= digDto.IdDigitacion;
                        FIDep.FIDependiente.IdPersFteIngreso=IdPersFteIngresoDep;
                        FIDep.FIDependiente.Gastos = ConvertArrayJsonTOListGastoDep(null,digDto.IdDigitacion,IdPersFteIngresoDep);
                        //endregion

                    }else { //si nunca tuvo una evaluación

                        //region Inicializamos la fte dependiente

                        FIDep.IdPersFteIngreso =IdPersFteIngresoDep;
                        FIDep.IdDigitacion = digDto.IdDigitacion;
                        FIDep.cPersCod = digDto.CodigoPersona;
                        FIDep.cPersFIPersCod = digDto.CodigoPersona;
                        FIDep.cRazSocDescrip = digDto.NombrePersona;
                        FIDep.nPersTipFte = "1";
                        FIDep.dVersion = FechaSync;
                        FIDep.nEstadoOpe = "0";

                        FIDep.FIDependiente = InicializarFteIngresoDep();
                        FIDep.FIDependiente.IdDigitacion= digDto.IdDigitacion;
                        FIDep.FIDependiente.IdPersFteIngreso=IdPersFteIngresoDep;
                        FIDep.FIDependiente.Gastos = ConvertArrayJsonTOListGastoDep(null,digDto.IdDigitacion,IdPersFteIngresoDep);
                        //endregion

                        //region inicializamos la fte independiente

                        FIInDep.IdPersFteIngreso =IdPersFteIngresoInDep;
                        FIInDep.IdDigitacion = digDto.IdDigitacion;
                        FIInDep.cPersCod = digDto.CodigoPersona;
                        FIInDep.cRazSocDescrip = digDto.NombrePersona;
                        FIInDep.cPersFIPersCod = digDto.CodigoPersona;
                        FIInDep.nPersTipFte = "2";
                        FIInDep.dVersion = FechaSync;
                        FIInDep.nEstadoOpe = "0";

                        FIInDep.FIIndependiente = InicializarFteIngresoInDep();
                        FIInDep.FIIndependiente.IdDigitacion=digDto.IdDigitacion ;
                        FIInDep.FIIndependiente.IdPersFteIngreso= IdPersFteIngresoInDep;
                        FIInDep.FIIndependiente.Disponible = ConvertArrayJsonTOListDisponible(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.Inventario = ConvertArrayJsonTOListInventario(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.ActivoFijo = ConvertArrayJsonTOListActivoFijo(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.GastoOpeAdm = ConvertArrayJsonTOListGastoOpeAdm(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.IgrFueraNeg = ConvertArrayJsonTOListIgrFueraNeg(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.GastoFamiliares = ConvertArrayJsonTOListGastoFam(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        FIInDep.FIIndependiente.PasivoNoCorriente = ConvertArrayJsonTOListPasivoNoCoriente(null,digDto.IdDigitacion,IdPersFteIngresoInDep);
                        //endregion

                    }

                    digDto.FteIngreso.add(FIDep);
                    digDto.FteIngreso.add(FIInDep);

                    DigitacionRemoto.put(digDto.IdDigitacion ,digDto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }


    }
    public void procesarOperaciones(ArrayList<ContentProviderOperation> ops, ContentResolver resolver){

        for(DigitacionDto dig : DigitacionRemoto.values() ){
            Log.d(TAG, "Programar Inserción de una nueva digitacion ID = " + dig.IdDigitacion );
            //insertando digitacion
            ops.add(construirOperacionInsertDigitacion(dig));
            //Insertando PersFteIngreso
            for (PersFteIngresoDto FISel:dig.FteIngreso){
                ops.add(construirOperacionInsertPersFteIngreso(FISel));
                // Log.d(TAG, "insertando fuente dependiente");
                if(FISel.nPersTipFte.equals("1")){
                    ops.add(construirOperacionInsertPersFIDependiente(FISel.FIDependiente));

                    if (FISel.FIDependiente.Gastos.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIDependiente.Gastos ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                }else {
                    // Log.d(TAG, "insertando fuente Independiente");
                    ops.add(construirOperacionInsertPersFIInDependiente(FISel.FIIndependiente));

                    if(FISel.FIIndependiente.PasivoNoCorriente.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.PasivoNoCorriente ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.GastoFamiliares.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.GastoFamiliares ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.IgrFueraNeg.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.IgrFueraNeg ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.GastoOpeAdm.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.GastoOpeAdm ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.ActivoFijo.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.ActivoFijo ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.Disponible.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.Disponible ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                    if(FISel.FIIndependiente.Inventario.size()>0){
                        for(PersFIGastoDetDto gastoSel: FISel.FIIndependiente.Inventario ){
                            ops.add(construirOperacionInsertPersFIGastoDet( gastoSel));
                        }
                    }
                }


            }

        }
    }

    //region inicializadores
    private PersFIDependienteDto InicializarFteIngresoDep(){

        PersFIDependienteDto FIDependiente = new PersFIDependienteDto();

        FIDependiente.IdPersFteIngreso="";
        FIDependiente.IdDigitacion="" ;
        FIDependiente.cNumFuente= "";
        FIDependiente.dPersEval="";
        FIDependiente.nPersIngCli="0";
        FIDependiente.nPersIngCon="0";
        FIDependiente.nPersOtrIng="0";
        FIDependiente.nPersGastoFam="0";
        FIDependiente.dPersFICaduca="";
        FIDependiente.nPersPagoCuotas="0";
        FIDependiente.cPersFIMoneda = "1";
        FIDependiente.cPersFICargo = "";
        FIDependiente.cPersFIAreaTrabajo = "";
        FIDependiente.nCodFrecPago = "0";
        FIDependiente.cComentario1="";
        FIDependiente.cComentario2="";
        FIDependiente.dVersion="";
        FIDependiente.nEstadoOpe="0";

        return FIDependiente;

    }
    private PersFIInDependienteDto InicializarFteIngresoInDep(){
        PersFIInDependienteDto FIIndependiente = new PersFIInDependienteDto();
        //region Cargando datos principales
        FIIndependiente.IdDigitacion="" ;
        FIIndependiente.IdPersFteIngreso= "";
        FIIndependiente.cNumFuente="";
        FIIndependiente.dPersEval="";
        FIIndependiente.nPersFIActivoDisp="0";
        FIIndependiente.nPersFICtasxCobrar="0";
        FIIndependiente.nPersFIInventarios="0";
        FIIndependiente.nPersFIActivosFijos="0";
        FIIndependiente.nPersFIProveedores="0";
        FIIndependiente.nPersFICredCMACT="0";
        FIIndependiente.nPersFICredOtros="0";
        FIIndependiente.nPersFIVentas="0";
        FIIndependiente.nPersFIPatrimonio="0";
        FIIndependiente.nPersFICostoVentas="0";
        FIIndependiente.nPersFIRecupCtasXCobrar="0";
        FIIndependiente.nPersFIEgresosOtros="0";
        FIIndependiente.nPersIngFam="0";
        FIIndependiente.nPersEgrFam="0";
        FIIndependiente.cPersFIMoneda="";
        FIIndependiente.nPersPagoCuotas="0";
        FIIndependiente.nPasivoNoCorriente="0";
        FIIndependiente.nCodSectorEcon="";
        FIIndependiente.cPersFIMoneda = "1";
        FIIndependiente.cComentario1="";
        FIIndependiente.cComentario2="";
        FIIndependiente.cComentario3="";
        FIIndependiente.dVersion="";
        FIIndependiente.nEstadoOpe="0";

        return FIIndependiente;
    }
    private PersFteIngresoDto InicializarPersFteIngresoDto(){

        PersFteIngresoDto FteIngreso = new PersFteIngresoDto();
        FteIngreso.IdPersFteIngreso ="";
        FteIngreso.cNumFuente = "0";
        FteIngreso.IdDigitacion = "";
        FteIngreso.cPersCod = "";
        FteIngreso.cPersFIPersCod = "";
        FteIngreso.cPersFICargo = "";
        FteIngreso.dPersFIinicio = "";
        FteIngreso.cRazSocUbiGeo = "";
        FteIngreso.cRazSocDirecc = "";
        FteIngreso.cRazSocTelef = "";
        FteIngreso.cRazSocDescrip = "";
        FteIngreso.cPersOcupacion = "";
        FteIngreso.nPersTipFte = "";
        FteIngreso.bCostoprod = "";

        FteIngreso.dVersion = "";
        FteIngreso.nEstadoOpe = "0";
        FteIngreso.nEstadoOpe1 = "0";
        FteIngreso.nEstadoOpe2 = "0";
        FteIngreso.nEstadoOpe3 = "0";
        return FteIngreso;
    }
    //endregion

    //region Conversion de Json a POJO Gasto Dep
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListGastoDep(JSONArray DetGasto,String pIdDigitacion,String pIdPersFteIngreso)  {

        List<PersFIGastoDetDto> result = new ArrayList<>();
        //Inicializando los gastos
        PersFIGastoDetDto gasto = new PersFIGastoDetDto();

        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 1;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "ALIMENTACION";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 2;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "EDUCACION";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 3;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "TRANSPORTE";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 4;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "SERVICIOS";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 5;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "OBLIGACIONES";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);
        gasto = new PersFIGastoDetDto();
        gasto.nTpoGasto = 1;
        gasto.nPrdConceptoCod = 6;
        gasto.nMonto = Double.parseDouble("0");
        gasto.cDescripcionGasto = "OTROS";
        gasto.IdDigitacion = pIdDigitacion;
        gasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(gasto);

        if( DetGasto != null){
            if (DetGasto.length()>0){

                for (int i =0;i<DetGasto.length();i++){
                    try {
                    JSONObject gastoJson = (JSONObject) DetGasto.get(i);

                        for(PersFIGastoDetDto gastosel : result){
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }

                    result.add(gasto);
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion

    //region Conversiones De Json a POJO Disponible
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListDisponible(JSONArray DetDisponible,String pIdDigitacion,String pIdPersFteIngreso)  {

        List<PersFIGastoDetDto> result = new ArrayList<>();
        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();

        FIGasto.nTpoGasto = 2;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.cDescripcionGasto = "EFECTIVO";
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 2;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.cDescripcionGasto = "AHORROS";
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 2;
        FIGasto.nPrdConceptoCod = 3;
        FIGasto.cDescripcionGasto = "CTA.CTE";
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetDisponible != null) {
            if (DetDisponible.length() > 0) {

                for (int i = 0; i < DetDisponible.length(); i++) {
                    JSONObject gastoJson = null;

                    try {
                        gastoJson = (JSONObject) DetDisponible.get(i);

                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion
    //region Conversion De Json POJO Activo Fijo
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListActivoFijo(JSONArray DetActivoFijo,String pIdDigitacion,String pIdPersFteIngreso)  {
        List<PersFIGastoDetDto> result = new ArrayList<>();
        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();

        FIGasto.nTpoGasto = 4;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "INMUEBLES";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 4;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "MUEBLES (MAQ. Y EQ.)";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetActivoFijo != null) {
            if (DetActivoFijo.length() > 0) {

                for (int i = 0; i < DetActivoFijo.length(); i++) {

                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetActivoFijo.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        return result;
    }
    //endregion
    //region Conversion de Json a POJOInventario
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListInventario(JSONArray DetInventario,String pIdDigitacion,String pIdPersFteIngreso)  {

        List<PersFIGastoDetDto> result = new ArrayList<>();

        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 3;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "MERCADERIA/INSUMOS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 3;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PRODUCTOS EN PROCESO";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 3;
        FIGasto.nPrdConceptoCod = 3;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PRODUCTOS TERMINADOS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetInventario != null) {

            if (DetInventario.length() > 0) {

                for (int i = 0; i < DetInventario.length(); i++) {
                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetInventario.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion
    //region Conversión de Json a POJO Gasto OPe Adm
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListGastoOpeAdm(JSONArray DetGastoOpeAdm,String pIdDigitacion,String pIdPersFteIngreso)  {
        List<PersFIGastoDetDto> result = new ArrayList<>();
        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();

        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PERSONAL";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "TRIBUTOS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 3;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "TRANSPORTE";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 4;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "ALQUILER";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 5;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "AGUA\\LUZ";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 6;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "DEUDAS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 5;
        FIGasto.nPrdConceptoCod = 7;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "OTROS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetGastoOpeAdm != null) {
            if (DetGastoOpeAdm.length() > 0) {

                for (int i = 0; i < DetGastoOpeAdm.length(); i++) {
                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetGastoOpeAdm.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion
    //region Conversion de Json to POJO Igr Fuera Neg
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListIgrFueraNeg(JSONArray DetIgrFueraNeg,String pIdDigitacion,String pIdPersFteIngreso)  {

        List<PersFIGastoDetDto> result = new ArrayList<>();
        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();

        FIGasto.nTpoGasto = 6;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PERMANENTE";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 6;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "EVENTUAL";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetIgrFueraNeg != null) {
            if (DetIgrFueraNeg.length() > 0) {

                for (int i = 0; i < DetIgrFueraNeg.length(); i++) {
                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetIgrFueraNeg.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        return result;
    }
    //endregion
    //region Conversion de Json to POJO Pasivo No Corriente
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListPasivoNoCoriente(JSONArray DetPasivoNoCorriente,String pIdDigitacion,String pIdPersFteIngreso)  {
        List<PersFIGastoDetDto> result = new ArrayList<>();
        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();

        FIGasto.nTpoGasto = 8;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PROVEEDORES";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 8;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "PRESTAMOS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 8;
        FIGasto.nPrdConceptoCod = 3;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "OTROS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetPasivoNoCorriente != null) {
            if (DetPasivoNoCorriente.length() > 0) {

                for (int i = 0; i < DetPasivoNoCorriente.length(); i++) {
                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetPasivoNoCorriente.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion
    //region Conversion de Json Gasto Familiares
    private List<PersFIGastoDetDto> ConvertArrayJsonTOListGastoFam(JSONArray DetGastoFam,String pIdDigitacion,String pIdPersFteIngreso)  {
        List<PersFIGastoDetDto> result = new ArrayList<>();

        PersFIGastoDetDto FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 1;
        FIGasto.nMonto =  Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "ALIMENTACION";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 2;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "EDUCACION ";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 3;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "ALQUILER";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 4;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "TRANSPORTE";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 5;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "SERVICIOS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 6;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "SERVICIO DOMESTICO";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 7;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "OBLIGACIONES";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);
        FIGasto = new PersFIGastoDetDto();
        FIGasto.nTpoGasto = 7;
        FIGasto.nPrdConceptoCod = 8;
        FIGasto.nMonto = Double.parseDouble("0");
        FIGasto.cDescripcionGasto = "OTROS";
        FIGasto.IdDigitacion = pIdDigitacion;
        FIGasto.IdPersFteIngreso = pIdPersFteIngreso;
        result.add(FIGasto);

        if(DetGastoFam != null) {
            if (DetGastoFam.length() > 0) {

                for (int i = 0; i < DetGastoFam.length(); i++) {
                    JSONObject gastoJson = null;
                    try {

                        gastoJson = (JSONObject) DetGastoFam.get(i);
                        for (PersFIGastoDetDto gastosel : result) {
                            if (gastosel.nPrdConceptoCod == gastoJson.getInt("nPrdConceptoCod")) {
                                gastosel.nMonto = gastoJson.getDouble("nMonto");
                                break;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    //endregion


    //endregion

    //region Construir POJO to ContentProviderOperation
    private ContentProviderOperation construirOperacionInsertDigitacion(DigitacionDto fila ){

        return ContentProviderOperation.newInsert(ContratoDbCmacIca.DigitacionTable.URI_CONTENIDO)
                .withValue(ContratoDbCmacIca.DigitacionTable.IdDigitacion, fila.IdDigitacion)
                .withValue(ContratoDbCmacIca.DigitacionTable.cCodSolicitud, fila.cCodSolicitud)
                .withValue(ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito, fila.cDescripcionProductoCredito)
                .withValue(ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda, fila.EquivalenteMoneda)
                .withValue(ContratoDbCmacIca.DigitacionTable.nMonto, fila.nMonto)
                .withValue(ContratoDbCmacIca.DigitacionTable.cCtaCod, fila.cCtaCod)
                .withValue(ContratoDbCmacIca.DigitacionTable.CodigoPersona, fila.CodigoPersona)
                .withValue(ContratoDbCmacIca.DigitacionTable.NumeroDocumento, fila.NumeroDocumento)
                .withValue(ContratoDbCmacIca.DigitacionTable.NombrePersona, fila.NombrePersona)
                .withValue(ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito, fila.cDescripcionTipoCredito)
                .withValue(ContratoDbCmacIca.DigitacionTable.TipoProceso, fila.TipoProceso)
                .withValue(ContratoDbCmacIca.DigitacionTable.cPersDireccDomicilio, fila.cPersDireccDomicilio)
                .withValue(ContratoDbCmacIca.DigitacionTable.cPersTelefono, fila.cPersTelefono)
                .withValue(ContratoDbCmacIca.DigitacionTable.cPersTelefono2, fila.cPersTelefono2)
                .withValue(ContratoDbCmacIca.DigitacionTable.nEstadoOpe, fila.nEstadoOpe)
                .build();
    }
    private ContentProviderOperation construirOperacionInsertPersFteIngreso(PersFteIngresoDto fila ){

        return ContentProviderOperation.newInsert(ContratoDbCmacIca.PersFteIngresoTable.URI_CONTENIDO)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.IdPersFteIngreso,fila.IdPersFteIngreso)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.IdDigitacion, fila.IdDigitacion)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cNumFuente, fila.cNumFuente)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cPersCod, fila.cPersCod)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod, fila.cPersFIPersCod)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cPersFICargo, fila.cPersFICargo)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.dPersFIinicio, fila.dPersFIinicio)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cRazSocUbiGeo, fila.cRazSocUbiGeo)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cRazSocDirecc, fila.cRazSocDirecc)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion, fila.cPersOcupacion)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cRazSocTelef, fila.cRazSocTelef)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip, fila.cRazSocDescrip)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.nPersTipFte, fila.nPersTipFte)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe1, fila.nEstadoOpe1)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe2, fila.nEstadoOpe2)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe3, fila.nEstadoOpe3)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.bCostoprod, fila.bCostoprod)
                .withValue(ContratoDbCmacIca.PersFteIngresoTable.dVersion, fila.dVersion)
                .build();
    }
    private ContentProviderOperation construirOperacionInsertPersFIDependiente(PersFIDependienteDto fila ){

        return ContentProviderOperation.newInsert(ContratoDbCmacIca.PersFIDependienteTable.URI_CONTENIDO)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.IdPersFteIngreso, fila.IdPersFteIngreso)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.IdDigitacion, fila.IdDigitacion)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cNumFuente, fila.cNumFuente)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.dPersEval, fila.dPersEval)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nPersIngCli, fila.nPersIngCli)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nPersIngCon, fila.nPersIngCon)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nPersOtrIng, fila.nPersOtrIng)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nPersGastoFam, fila.nPersGastoFam)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.dPersFICaduca, fila.dPersFICaduca)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nPersPagoCuotas, fila.nPersPagoCuotas)

                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cPersFIMoneda, fila.cPersFIMoneda)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cPersFICargo, fila.cPersFICargo)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cPersFIAreaTrabajo, fila.cPersFIAreaTrabajo)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nCodFrecPago, fila.nCodFrecPago)

                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cComentario1, fila.cComentario1)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.cComentario2, fila.cComentario2)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.dVersion, fila.dVersion)
                .withValue(ContratoDbCmacIca.PersFIDependienteTable.nEstadoOpe, fila.nEstadoOpe)
                .build();
    }
    private ContentProviderOperation construirOperacionInsertPersFIInDependiente(PersFIInDependienteDto fila ){

        return ContentProviderOperation.newInsert(ContratoDbCmacIca.PersFIInDependienteTable.URI_CONTENIDO)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.IdPersFteIngreso, fila.IdPersFteIngreso)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.IdDigitacion, fila.IdDigitacion)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.cNumFuente, fila.cNumFuente)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.dPersEval, fila.dPersEval)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivoDisp, fila.nPersFIActivoDisp)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICtasxCobrar, fila.nPersFICtasxCobrar)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIInventarios, fila.nPersFIInventarios)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIActivosFijos, fila.nPersFIActivosFijos)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIProveedores, fila.nPersFIProveedores)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredCMACT, fila.nPersFICredCMACT)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICredOtros, fila.nPersFICredOtros)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIVentas, fila.nPersFIVentas)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIPatrimonio, fila.nPersFIPatrimonio)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFICostoVentas, fila.nPersFICostoVentas)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIRecupCtasXCobrar, fila.nPersFIRecupCtasXCobrar)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersFIEgresosOtros, fila.nPersFIEgresosOtros)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersIngFam, fila.nPersIngFam)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersEgrFam, fila.nPersEgrFam)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.cPersFIMoneda, fila.cPersFIMoneda)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPersPagoCuotas, fila.nPersPagoCuotas)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nPasivoNoCorriente, fila.nPasivoNoCorriente)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nCodSectorEcon, fila.nCodSectorEcon)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.cComentario1, fila.cComentario1)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.cComentario2, fila.cComentario2)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.cComentario3, fila.cComentario3)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.dVersion, fila.dVersion)
                .withValue(ContratoDbCmacIca.PersFIInDependienteTable.nEstadoOpe, fila.nEstadoOpe)
                .build();
    }
    private ContentProviderOperation construirOperacionInsertPersFIGastoDet(PersFIGastoDetDto fila ){

        return ContentProviderOperation.newInsert(ContratoDbCmacIca.PersFIGastoDetTable.URI_CONTENIDO)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.IdPersFteIngreso, fila.IdPersFteIngreso)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.cNumFuente, fila.cNumFuente)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.IdDigitacion, fila.IdDigitacion)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.cFIMoneda, fila.cFIMoneda)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.nMonto, fila.nMonto)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.nTpoGasto, fila.nTpoGasto)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.nPrdConceptoCod, fila.nPrdConceptoCod)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.nMonto, fila.nMonto)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.dVersion, fila.dVersion)
                .withValue(ContratoDbCmacIca.PersFIGastoDetTable.nEstadoOpe, fila.nEstadoOpe)
                .build();
    }

    //endregion

}
