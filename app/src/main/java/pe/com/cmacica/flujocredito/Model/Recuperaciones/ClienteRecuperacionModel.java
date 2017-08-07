package pe.com.cmacica.flujocredito.Model.Recuperaciones;

/**
 * Created by faqf on 26/07/2017.
 */

public class ClienteRecuperacionModel {


    public ClienteRecuperacionModel(String codigo, String documento, String nombres, String direccion
            , int Ntipocredito, int NdiasAtraso,double nCapital, boolean seleccionado) {
        Codigo = codigo;
        Documento = documento;
        Nombres = nombres;
        Direccion = direccion;
        Seleccionado=seleccionado;
        ntipocredito=Ntipocredito;
        nDiasAtraso=NdiasAtraso;
        ncapital=nCapital;
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

    private String Codigo ;
    private String Documento;
    private String Nombres ;
    private String Direccion;
    private boolean Seleccionado;
    private int ntipocredito;
    private int nDiasAtraso;
    private double ncapital;


}
