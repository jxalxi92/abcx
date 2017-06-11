package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 07/06/2017.
 */

public class ActividadesAgropecuariasModel {


    public ActividadesAgropecuariasModel(String cCredProducto, int nCodActividad, String cDescripcionActiv, int nCodTipoAct) {
        this.cCredProducto = cCredProducto;
        this.nCodActividad = nCodActividad;
        this.cDescripcionActiv = cDescripcionActiv;
        this.nCodTipoAct = nCodTipoAct;
    }

    private String cCredProducto;

    private int nCodActividad;

    private String cDescripcionActiv;

    private int nCodTipoAct;

    public String getcCredProducto() {
        return cCredProducto;
    }

    public int getnCodActividad() {
        return nCodActividad;
    }

    public String getcDescripcionActiv() {
        return cDescripcionActiv;
    }

    public int getnCodTipoAct() {
        return nCodTipoAct;
    }

    @Override
    public String toString() {
        return cDescripcionActiv;
    }


}
