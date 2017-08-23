package pe.com.cmacica.flujocredito.Model.Recuperaciones;

/**
 * Created by faqf on 26/07/2017.
 */

public class ClienteRecuperacionModel {


    public ClienteRecuperacionModel(String codigo, String documento, String nombres, String direccion
            , int Ntipocredito, int NdiasAtraso,double nCapital,String cCodcred ,boolean seleccionado,String posicion,String fecha) {
        Codigo = codigo;
        Documento = documento;
        Nombres = nombres;
        Direccion = direccion;
        Seleccionado=seleccionado;
        ntipocredito=Ntipocredito;
        nDiasAtraso=NdiasAtraso;
        ncapital=nCapital;
        Posicion=posicion;
        Fecha=fecha;
        ccodcred=cCodcred;
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
        return Posicion;
    }

    public void setPosicion(String posicion) {
        Posicion = posicion;
    }
    public String getFechaRec() {
        return Fecha;
    }

    public void setFechaRec(String fecha) {
        Fecha = fecha;
    }

    public String getcCodcred() {
        return ccodcred;
    }

    private String Codigo ;
    private String Documento;
    private String Nombres ;
    private String Direccion;
    private boolean Seleccionado;
    private int ntipocredito;
    private int nDiasAtraso;
    private double ncapital;
    private String Posicion;
    private String ccodcred;
    private String Fecha;


}
