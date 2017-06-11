package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by faqf on 05/06/2017.
 */

public class ClienteCobranzaModel {


    public ClienteCobranzaModel(String codigo, String documento, String nombres, String direccion) {
        Codigo = codigo;
        Documento = documento;
        Nombres = nombres;
        Direccion = direccion;
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

    private String Codigo ;

    private String Documento;

    private String Nombres ;

    private String Direccion;
}
