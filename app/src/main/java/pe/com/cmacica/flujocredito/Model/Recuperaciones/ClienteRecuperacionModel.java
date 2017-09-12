package pe.com.cmacica.flujocredito.Model.Recuperaciones;

/**
 * Created by faqf on 26/07/2017.
 */

public class ClienteRecuperacionModel {


    public ClienteRecuperacionModel(String codigo, String documento, String nombres, String direccion
            , int Ntipocredito, int NdiasAtraso,double nCapital,String cCodcred ,String cUbigeodom,String cpersCodAna ,String fecha,String posicion,boolean seleccionado) {
        Codigo = codigo;
        Documento = documento;
        Nombres = nombres;
        Direccion = direccion;
        Seleccionado=seleccionado;
        ntipocredito=Ntipocredito;
        nDiasAtraso=NdiasAtraso;
        ncapital=nCapital;
        nOrdenVisita=posicion;
        dFechaVisita=fecha;
        ccodcred=cCodcred;
        cubigeodom=cUbigeodom;
        cPersCodAna=cpersCodAna;
    }

    public String getCodigo() {
        return Codigo;
    }

    public String getDocumento() {
        return Documento;
    }

    public String getNombres() {
        return Nombres;
    }

    public String getDireccion() {
        return Direccion;
    }

    public boolean isSeleccionado() {return Seleccionado;}
    public int getNtipocredito() {return ntipocredito;}

    public int getnDiasAtraso() {return nDiasAtraso;}
    public double getNcapital() {
        return ncapital;
    }

    public void setSeleccionado(boolean seleccionado) {
        Seleccionado = seleccionado;
    }

    public String getPosicion() {
        return nOrdenVisita;
    }

    public void setPosicion(String posicion) {
        nOrdenVisita = posicion;
    }
    public String getFechaRec() {
        return dFechaVisita;
    }

    public void setFechaRec(String fecha) {
        dFechaVisita = fecha;
    }

    public String getcCodcred() {
        return ccodcred;
    }
    public String getCubigeodom() {
        return cubigeodom;
    }

    public String getcPersCodAna() {
        return cPersCodAna;
    }

    private String Codigo ;
    private String Documento;
    private String Nombres ;
    private String Direccion;
    private boolean Seleccionado;
    private int ntipocredito;
    private int nDiasAtraso;
    private double ncapital;
    private String nOrdenVisita;
    private String ccodcred;
    private String dFechaVisita;
    private String cubigeodom;
    private String cPersCodAna;


}
