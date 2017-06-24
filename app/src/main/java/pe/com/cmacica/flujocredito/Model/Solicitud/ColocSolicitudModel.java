package pe.com.cmacica.flujocredito.Model.Solicitud;

import java.util.List;

/**
 * Created by faqf on 22/06/2017.
 */

public class ColocSolicitudModel {

    public String UsuarioOperacion;

    public int CodigoMoneda ;

    public String CodigoAgenciaOperacion;

    public int nCodSolicitud;

    public String cCodSolicitud;

    public int nCaptado;

    public int nCuotas ;

    public double nMonto;

    public int nFrecPago;

    public int nDiasFrecuencia;

    public int nCondicion;

    public int nCondicion2;

    public int nEstado ;

    public String cCtaCod ;

    public int nCalSBS ;

    public double nMontoSBS ;

    public int nEstadoSBS;

    public String dFechaSBS;

    public int nDestino ;

    public String cEstadoSol ;

    public int IdCampana ;

    public int IdCampanaNew;

    public int nEnviado ;

    public int bEditado ;

    public String cRFA;

    public String cCodAgeBN;

    public String cPersCodInst;

    public String cCodModular;

    public int nTipoCredito ;

    public int nSubProducto ;

    public String sCalif0;

    public String sCalif1 ;

    public String sCalif2 ;

    public String sCalif3 ;

    public String sCalif4 ;

    public boolean bExpediente;
    public String cPersCodCaptado;

    public int nNumEntSBS;

    public int nCodProyInmob ;

    public int nIDCanalDesembolso;

    public boolean bPecuario;

    public int nCondicionAmpliacion ;

    public int nTipoPerCliente;

    public int nAplicacion;
    public List<ColocSolicitudEstadoModel> EstadoSolicitud;
    public List<ColocSolicitudPersonaModel> PersonasSolicitud;

    public boolean bMicroseguro;
}

