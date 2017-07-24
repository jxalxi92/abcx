package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by jhcc on 07/07/2016.
 */
public class ProductoModel {

    public ProductoModel(String cCredProductos, String cDescripcion,int nCodCampana) {
        this.cCredProductos = cCredProductos;
        this.cDescripcion = cDescripcion;
        this.nCodCampana=nCodCampana;
    }

    public String getcCredProductos() {
        return cCredProductos;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }
    public int getnCodCampana() {
        return nCodCampana;
    }
    private String cCredProductos ;
    private String cDescripcion;
    private int nCodCampana;



    @Override
    public String toString() {
        return cDescripcion;
    }

}
