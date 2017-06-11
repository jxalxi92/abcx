package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 5/10/2016.
 */

public class InfoDeuVencModel {

    public InfoDeuVencModel(String tipoDocumento, String nroDocumento, String cantidadDocs, String fuente, String entidad, String monto, String cantidad, String diasVencidos) {
        TipoDocumento = tipoDocumento;
        NroDocumento = nroDocumento;
        CantidadDocs = cantidadDocs;
        Fuente = fuente;
        Entidad = entidad;
        Monto = monto;
        Cantidad = cantidad;
        DiasVencidos = diasVencidos;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return NroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        NroDocumento = nroDocumento;
    }

    public String getCantidadDocs() {
        return CantidadDocs;
    }

    public void setCantidadDocs(String cantidadDocs) {
        CantidadDocs = cantidadDocs;
    }

    public String getFuente() {
        return Fuente;
    }

    public void setFuente(String fuente) {
        Fuente = fuente;
    }

    public String getEntidad() {
        return Entidad;
    }

    public void setEntidad(String entidad) {
        Entidad = entidad;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getDiasVencidos() {
        return DiasVencidos;
    }

    public void setDiasVencidos(String diasVencidos) {
        DiasVencidos = diasVencidos;
    }

    private String TipoDocumento;
    private String NroDocumento;
    private String CantidadDocs;
    private String Fuente;
    private String Entidad;
    private String Monto;
    private String Cantidad;
    private String DiasVencidos;
}
