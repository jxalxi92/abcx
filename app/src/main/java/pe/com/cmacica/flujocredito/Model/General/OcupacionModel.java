package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by faqf on 30/06/2017.
 */

public class OcupacionModel {

    public OcupacionModel(int nCodigo, String cDescripcion) {
        this.nCodigo = nCodigo;
        this.cDescripcion = cDescripcion;
    }

    public int getnCodigo() {
        return nCodigo;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }

    private int nCodigo;

    private String cDescripcion;
    @Override
    public String toString() {
        return cDescripcion;
    }
}
