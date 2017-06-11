package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 06/06/2017.
 */

public class CampañasModel {

    public CampañasModel(int idCampana, String cDescripcion) {
        IdCampana = idCampana;
        this.cDescripcion = cDescripcion;
    }
    private int IdCampana;
    private String cDescripcion;

    public int getIdCampana() {
        return IdCampana;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }

    @Override
    public String toString() {
        return cDescripcion;

    }
}
