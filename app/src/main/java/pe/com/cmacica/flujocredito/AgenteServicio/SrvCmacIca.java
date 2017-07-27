package pe.com.cmacica.flujocredito.AgenteServicio;

/**
 * Created by JHON on 29/06/2016.
 */
public class SrvCmacIca {

   /*
    private static final String PUERTO_HOST = ":8080";
    private static final String HOST_WEBAPI = "http://www.cmacica.com.pe"+ PUERTO_HOST + "/CrediMovil_Des/api/";
    */

  private static final String PUERTO_HOST = "";
  private static final String HOST_WEBAPI = "http://172.20.10.97"+ PUERTO_HOST + "/optimusrest/api/";

  /* private static final String PUERTO_HOST = "";
   private static final String HOST_WEBAPI = "http://172.20.10.46"+ PUERTO_HOST + "/CrediMovil/api/";
*/
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
    public static final String GET_PERSONA_TIPOCREDITO = HOST_WEBAPI + "TipoCreditos/ListaTipoCreditos?nTipoPersona=%s";
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

    //region CONSTANTES--------------------------------------------------------------------------------------------
    public static final String URL_SYNC_BATCHConstante = HOST_WEBAPI +"persona/ObtenerConstante";
    //endregion-----------------------------------------------------------------------------------------------------

    //region PERSONA------------------------------------------------------------------------------------------------
    public static final String GET_OBTENERPERSONA= HOST_WEBAPI+"Persona/PersonaReniec?cNumDoc=%s";
    public static final String GET_OCUPACION=HOST_WEBAPI+"Persona/SelOcupacion";
   //endRegion------------------------------------------------------------------------------------------------------
    public static final String POST_PERSONA= HOST_WEBAPI+"Persona/GuardarPersonaReniec";
    //region SOLICITUD----------------------------------------------------------------------------------------------

    public static final String GET_PROCESO=HOST_WEBAPI+"Solicitud/ListaProcesosCreditos";
    public static final String GET_CAMPAÑAS=HOST_WEBAPI+"Solicitud/GetCampanasPorAge?CodAgencia=%s";
    public static final String GET_PROYECTOS=HOST_WEBAPI+"Solicitud/ListarProyectoAsocYGrupoOrg";
    public static final String GET_AGENCIASBN_AGE=HOST_WEBAPI+"Solicitud/ListaAgenciaBnPorAge?CodAgencia=%s";
    public static final String GET_AGROPECUARIOS=HOST_WEBAPI+"Solicitud/ListarActividadesAgropecuariasesPorProducto?CodProducto=%s";
    public static final String GET_DESTINOS=HOST_WEBAPI+"Solicitud/ListaCreddestinos?nTipoCredito=%s&CodProducto=%s";
    public static final String GET_PROYECTOS_INMOBILIARIOS=HOST_WEBAPI+"Solicitud/ListarProyectosInmobiliarios?cCodAgencia=%s&cCodProducto=%s";
    public static final String GET_INTS_CONVENIO=HOST_WEBAPI+"Solicitud/ListaIntsConvenio?nPerstipo=%s&nTipoSector=%s";
    public static final String GET_VERIF_EVA_MEN=HOST_WEBAPI+"Solicitud/ReclasificarEval?pnTipoCred=%s&pCodigoPersona=%s&nTipoPersona=%s&nMonto=%s&pbLineaCredito=%s&bFlag=%s";
    public static final String GET_DATO_CLIENTE_SOL=HOST_WEBAPI+"Solicitud/SelDatoClienteSolCred?cDoiCliente=%s&cUserOpe=%s&cAgeOpe=%s";
    public static final String GET_SEL_CONDICION_SOL=HOST_WEBAPI+"Solicitud/SelCondicionSolicitud?cCodCliente=%s";
    public static final String POST_INGRESO_VENTAS=HOST_WEBAPI+"Solicitud/InsEndeuPersonaSol";
    public static final String POST_REGISTRO_SOLICITUD=HOST_WEBAPI+"Solicitud/EjecutarSolicitudCreditoMovil";
    public static final String POST_MOTOR_EVA= HOST_WEBAPI+"Solicitud/ListaReglasValidacion";
    public static final String GET_CREDPRODUCTOS= HOST_WEBAPI+"Solicitud/SelCredProductos?cAgeCod=%s&nTipoCredito=%s";

    //endRegion-----------------------------------------------------------------------------------------------------

    //Region COBRANZAS---------------------------------------------------------------------------------------------
    public static final String GET_VALIDA_GESTOR=HOST_WEBAPI+"Cobranza/Validagestor?Usuario=%s";

    public static final String GET_TIPO_CONTACTO=HOST_WEBAPI+"Cobranza/SelTipoContatoGestor?TipoContacto=%s";

    public static final String GET_PERSONA_TELEFONO=HOST_WEBAPI+"Cobranza/ListaTelefonoPersona?Codigo=%s";

    public static final String GET_TIPO_GESTION=HOST_WEBAPI+"Cobranza/ListarTipogestion?TipoContacto=%s";

    public static final String GET_MOTIVONOPAGO=HOST_WEBAPI+"Cobranza/ListarMotivoNoPago";

    public static final String GET_RESULTADO=HOST_WEBAPI+"Cobranza/ListarResultados?TipoContacto=%s&TipoGestion=%s";

    public static final String GET_RESULTADOMK=HOST_WEBAPI+"Cobranza/ListarResultadoMK?TipoContacto=%s&TipoGestion=%s";

    public static final String GET_ESTGESTION=HOST_WEBAPI+"Cobranza/ListarEstgestion";

    public static final String GET_DETALLE_GESTION=HOST_WEBAPI+"Cobranza/ListarDetalleGestion?Dni=%s";

    public static final String POST_REGISTRO_GESTION=HOST_WEBAPI+"Cobranza/RegistroGestion";

    public static final String GET_CLIENTES_COBRANZA=HOST_WEBAPI+"Cobranza/ListarClientesCobranza?Codigoanalista=%s";

    public static final String GET_CLIENTES_RECUPERACIONES=HOST_WEBAPI+"Cobranza/ListarClientesRecuperaciones?Codigoanalista=%s";

    //endregion---------------------------------------------------------------------------------------------------------
}

