package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 07/06/2017.
 */

public class ProyectoInmobiliarioModel {



    public ProyectoInmobiliarioModel(int nCodProyecto, String cNomProyecto, String cNomAbrevProy, String cDescripcion) {
        this.nCodProyecto = nCodProyecto;
        this.cNomProyecto = cNomProyecto;
        this.cNomAbrevProy = cNomAbrevProy;
        this.cDescripcion = cDescripcion;
    }
    private int nCodProyecto;
    private String cNomProyecto;
    private String cNomAbrevProy;
    private String cDescripcion;

    public int getnCodProyecto() {
        return nCodProyecto;
    }

    public String getcNomProyecto() {
        return cNomProyecto;
    }

    public String getcNomAbrevProy() {
        return cNomAbrevProy;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }

}
