package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 07/06/2017.
 */

public class DestinosModel {

    public DestinosModel(int nCodDestino, String cDescripcionDestino) {
        this.nCodDestino = nCodDestino;
        this.cDescripcionDestino = cDescripcionDestino;
    }

    private int nCodDestino;
    private String cDescripcionDestino;

    public int getnCodDestino() {
        return nCodDestino;
    }

    public String getcDescripcionDestino() {
        return cDescripcionDestino;
    }

    @Override
    public String toString() {
        return  cDescripcionDestino;
    }
}
