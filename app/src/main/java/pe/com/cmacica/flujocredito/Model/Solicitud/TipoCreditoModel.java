package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by jhcc on 07/07/2016.
 */
public class TipoCreditoModel {

    public TipoCreditoModel(int nTipoCreditos, String cDescripcion) {
        this.nTipoCreditos = nTipoCreditos;
        this.cDescripcion = cDescripcion;
    }

    public int getnTipoCreditos() {
        return nTipoCreditos;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }
    @Override
    public String toString() {
        return cDescripcion;
    }
    private int nTipoCreditos;
    private String cDescripcion;

    /**
     * Created by jhcc on 29/05/2017.
     */


}
