package pe.com.cmacica.flujocredito.Model.Digitacion;

import java.util.Date;
import java.util.List;

/**
 * Created by jhcc on 24/08/2016.
 */
public class DigitacionDto {

    public String IdDigitacion;
    public String cCodSolicitud;
    public Date dFechaSol;
    public String cDescripcionProductoCredito;
    public String EquivalenteMoneda;
    public Double nMonto;
    public String cCtaCod;
    public String CodigoPersona;
    public String NumeroDocumento;
    public String NombrePersona;
    public String cDescripcionTipoCredito;
    public String TipoProceso;
    public String cPersDireccDomicilio;
    public String cPersTelefono;
    public String cPersTelefono2;
    public String nEstadoOpe;

    public List<PersFteIngresoDto> FteIngreso;


}
