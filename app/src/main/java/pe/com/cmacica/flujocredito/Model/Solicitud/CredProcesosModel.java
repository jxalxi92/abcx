package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 06/06/2017.
 */

public class CredProcesosModel {

    public CredProcesosModel(int nCodCredProceso, String cDescripcionProceso, String cUltimaActualizacion) {
        this.nCodCredProceso = nCodCredProceso;
        this.cDescripcionProceso = cDescripcionProceso;
        this.cUltimaActualizacion = cUltimaActualizacion;
    }
    private int nCodCredProceso;
    private String cDescripcionProceso;
    private String cUltimaActualizacion;

    public int getnCodCredProceso() {
        return nCodCredProceso;
    }

    public String getcDescripcionProceso() {
        return cDescripcionProceso;
    }

    public String getcUltimaActualizacion() {
        return cUltimaActualizacion;
    }

    @Override
    public String toString() {
        return  cDescripcionProceso;
    }
}
