package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 5/10/2016.
 */

public class InfoSBSModel {

    public InfoSBSModel(String tipoDocCPT, String nroDocCPT, String nomRazSocEnt, String calificacion, String montoDeuda, String diasVencidos, String fechaReporte) {
        TipoDocCPT = tipoDocCPT;
        NroDocCPT = nroDocCPT;
        NomRazSocEnt = nomRazSocEnt;
        Calificacion = calificacion;
        MontoDeuda = montoDeuda;
        DiasVencidos = diasVencidos;
        FechaReporte = fechaReporte;
    }

    public String getTipoDocCPT() {
        return TipoDocCPT;
    }

    public void setTipoDocCPT(String tipoDocCPT) {
        TipoDocCPT = tipoDocCPT;
    }

    public String getNroDocCPT() {
        return NroDocCPT;
    }

    public void setNroDocCPT(String nroDocCPT) {
        NroDocCPT = nroDocCPT;
    }

    public String getNomRazSocEnt() {
        return NomRazSocEnt;
    }

    public void setNomRazSocEnt(String nomRazSocEnt) {
        NomRazSocEnt = nomRazSocEnt;
    }

    public String getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(String calificacion) {
        Calificacion = calificacion;
    }

    public String getMontoDeuda() {
        return MontoDeuda;
    }

    public void setMontoDeuda(String montoDeuda) {
        MontoDeuda = montoDeuda;
    }

    public String getDiasVencidos() {
        return DiasVencidos;
    }

    public void setDiasVencidos(String diasVencidos) {
        DiasVencidos = diasVencidos;
    }

    public String getFechaReporte() {
        return FechaReporte;
    }

    public void setFechaReporte(String fechaReporte) {
        FechaReporte = fechaReporte;
    }

    private String TipoDocCPT;
    private String NroDocCPT;
    private String NomRazSocEnt;
    private String Calificacion;
    private String MontoDeuda;
    private String DiasVencidos;
    private String FechaReporte;
}
