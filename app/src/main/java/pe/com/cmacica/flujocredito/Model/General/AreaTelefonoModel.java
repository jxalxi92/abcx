package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by faqf on 26/07/2017.
 */

public class AreaTelefonoModel {

    public AreaTelefonoModel(String codigo, String departamento, String codigoTelefonico) {
        Codigo = codigo;
        Departamento = departamento;
        CodigoTelefonico = codigoTelefonico;
    }

    private  String Codigo;
    private String Departamento;
    private String CodigoTelefonico;



    public String getCodigo() {
        return Codigo;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public String getCodigoTelefonico() {
        return CodigoTelefonico;
    }



}
