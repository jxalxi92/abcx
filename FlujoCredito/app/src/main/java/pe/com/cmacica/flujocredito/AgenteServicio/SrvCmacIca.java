package pe.com.cmacica.flujocredito.AgenteServicio;

import java.security.PublicKey;

/**
 * Created by JHON on 29/06/2016.
 */
public class SrvCmacIca {


   /*
    private static final String PUERTO_HOST = ":8080";
    private static final String HOST_WEBAPI = "http://www.cmacica.com.pe"+ PUERTO_HOST + "/CrediMovil_Des/api/";
    */
    /*
    private static final String PUERTO_HOST = "";
    private static final String HOST_WEBAPI = "http://172.20.10.97"+ PUERTO_HOST + "/optimusrest/api/";
   */

    private static final String PUERTO_HOST = "";
    private static final String HOST_WEBAPI = "http://172.20.10.46"+ PUERTO_HOST + "/CrediMovil/api/";

    /**
     * URLs del Web Service
     */

    //region Calificacion
    //public static final String GET_CALIF_SBS = HOST_WEBAPI + "Calificacion/GetCalifPorDoi?doi=";
    public static final String GET_DEUDA_IFIS = HOST_WEBAPI + "Calificacion/GetDeudaIfis?codsbs=%s&fecha=%s";
    public static final String GET_PLANPAGO_HIST= HOST_WEBAPI + "Calificacion/ListarPlanPago?cCtaCod=%s&cImei=%s";
    public static final String GET_INFOCLIENTE = HOST_WEBAPI + "Persona/SelInfoCliente?cNumDoc=%s&cTipoDoc=%s&cImei=%s";
    //endregion

    //region Simulador
    //public static final String GET_PLAN_PAGO = HOST_WEBAPI + "PlanPago/GetPlanPago?nTipoCredito=%s&MontoSol=%s&NroCuotas=%s&TipoPeriocidad=%s&TEM=%s&nMoneda=%s&nDiaFijo=%s&cPeriodoFijo=%s&nPeriodicidadEnDias=%s&dFechaDesembolso=%s&nDiasGracia=%s&bProximoMes=%s";
    public static final String POST_PLAN_PAGO = HOST_WEBAPI + "PlanPago/GetPlanPago";
    public static final String GET_ALL_AGENCIAS = HOST_WEBAPI+ "Agencia/GetAllAgencias";
    public static final String GET_ALL_TIPOCREDITO = HOST_WEBAPI + "TipoCreditos/SelTipoCreditos";
    public static final String GET_FILTER_PRODUCTO = HOST_WEBAPI +"CredProductos/SelCredProductos?cAgeCod=%s&nTipoCredito=%s";
    public static final String GET_FRECUENCIA_PAGO= HOST_WEBAPI + "CredProductos/GetFrecPagoPorProducto?cCredProducto=";
    //endregion

    //region Seguridad
    public static final String GET_VALIDA_DEVICE = HOST_WEBAPI+"Auth/ValidadDevice";
    public static final String GET_VALIDA_USUARIO = HOST_WEBAPI+"Auth/LogingMovil";
    //endregion

    //region Fuentes de ingreso
    public static final String URL_SYNC_BATCH_Fte_Igr = HOST_WEBAPI + "FuenteIngreso/";
    public static final String URL_SYNC_BATCH_FTEIGR_REQUEST = HOST_WEBAPI+"FuenteIngreso/ListFteIgrClixAna?cPersCodAna=%s&cImei=%s";
    public static final String URL_SYNC_BATCH_FTEIGR_RESPONSE = HOST_WEBAPI+"FuenteIngreso/SyncFteIgrMovil";
    //endregion

    //region Fuentes de Persona
    public static final String URL_SYNC_BATCHPersona = HOST_WEBAPI +"Persona/PersonaSyncBatch?filtro=%s&cImei=%s";
    //endregion

}

