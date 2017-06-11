package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 5/10/2016.
 */

public class InfoEstandarModel {
    public InfoEstandarModel(String tipoDocumento, String documento, String razonSocial, String fechaProceso, String nroBancos, String deudaTotal, String calificativo, String deudaSBS, String totalRiesgo, String peorCalificacion, String porCalNormal) {
        TipoDocumento = tipoDocumento;
        Documento = documento;
        RazonSocial = razonSocial;
        FechaProceso = fechaProceso;
        NroBancos = nroBancos;
        DeudaTotal = deudaTotal;
        Calificativo = calificativo;
        DeudaSBS = deudaSBS;
        TotalRiesgo = totalRiesgo;
        PeorCalificacion = peorCalificacion;
        PorCalNormal = porCalNormal;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getFechaProceso() {
        return FechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        FechaProceso = fechaProceso;
    }

    public String getNroBancos() {
        return NroBancos;
    }

    public void setNroBancos(String nroBancos) {
        NroBancos = nroBancos;
    }

    public String getDeudaTotal() {
        return DeudaTotal;
    }

    public void setDeudaTotal(String deudaTotal) {
        DeudaTotal = deudaTotal;
    }

    public String getCalificativo() {
        return Calificativo;
    }

    public void setCalificativo(String calificativo) {
        Calificativo = calificativo;
    }

    public String getDeudaSBS() {
        return DeudaSBS;
    }

    public void setDeudaSBS(String deudaSBS) {
        DeudaSBS = deudaSBS;
    }

    public String getTotalRiesgo() {
        return TotalRiesgo;
    }

    public void setTotalRiesgo(String totalRiesgo) {
        TotalRiesgo = totalRiesgo;
    }

    public String getPeorCalificacion() {
        return PeorCalificacion;
    }

    public void setPeorCalificacion(String peorCalificacion) {
        PeorCalificacion = peorCalificacion;
    }

    public String getPorCalNormal() {
        return PorCalNormal;
    }

    public void setPorCalNormal(String porCalNormal) {
        PorCalNormal = porCalNormal;
    }

    private String TipoDocumento;
    private String Documento;
    private String RazonSocial;
    private String FechaProceso;
    private String NroBancos;
    private String DeudaTotal;
    private String Calificativo;
    private String DeudaSBS;
    private String TotalRiesgo;
    private String PeorCalificacion;
    private String PorCalNormal;


    public String getCalifCmac() {
        return CalifCmac;
    }

    public void setCalifCmac(String califCmac) {
        CalifCmac = califCmac;
    }

    private String CalifCmac;
}
