package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 06/06/2017.
 */

public class ColocAgenciaBNModel {

    public ColocAgenciaBNModel(String cCodAgencia, String cCodAgeBN, String cAgeBnDesc, String cDistAgeBN, String cDirAgeBN) {
        this.cCodAgencia = cCodAgencia;
        this.cCodAgeBN = cCodAgeBN;
        this.cAgeBnDesc = cAgeBnDesc;
        this.cDistAgeBN = cDistAgeBN;
        this.cDirAgeBN = cDirAgeBN;
    }

    private String cCodAgencia;

    private String cCodAgeBN;

    private String cAgeBnDesc;

    private String cDistAgeBN;

    private String cDirAgeBN;

    public String getcCodAgencia() {
        return cCodAgencia;
    }

    public String getcCodAgeBN() {
        return cCodAgeBN;
    }

    public String getcAgeBnDesc() {
        return cAgeBnDesc;
    }

    public String getcDistAgeBN() {
        return cDistAgeBN;
    }

    public String getcDirAgeBN() {
        return cDirAgeBN;
    }

    @Override
    public String toString() {
        return cAgeBnDesc;
    }
}
