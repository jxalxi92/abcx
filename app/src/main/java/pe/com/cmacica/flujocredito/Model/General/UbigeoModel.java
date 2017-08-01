package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by faqf on 31/07/2017.
 */

public class UbigeoModel {



    public UbigeoModel(String cUbigeoCod, String cUbigeoDesc, String cCodigoCiudad, String cUbigeoCodReniec) {
        this.cUbigeoCod = cUbigeoCod;
        this.cUbigeoDesc = cUbigeoDesc;
        this.cCodigoCiudad = cCodigoCiudad;
        this.cUbigeoCodReniec = cUbigeoCodReniec;
    }
    private String cUbigeoCod;
    private String cUbigeoDesc;
    private String cCodigoCiudad;
    private String cUbigeoCodReniec;

    public String getcUbigeoCod() {
        return cUbigeoCod;
    }

    public String getcUbigeoDesc() {
        return cUbigeoDesc;
    }

    public String getcCodigoCiudad() {
        return cCodigoCiudad;
    }

    public String getcUbigeoCodReniec() {
        return cUbigeoCodReniec;
    }



    @Override
    public String toString() {
        return cUbigeoDesc;
    }
}
