package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by jhcc on 07/07/2016.
 */
public class ProductoModel {

    public ProductoModel(String cCredProductos, String cDescripcion) {
        this.cCredProductos = cCredProductos;
        this.cDescripcion = cDescripcion;
    }

    public String getcCredProductos() {
        return cCredProductos;
    }

    public String getcDescripcion() {
        return cDescripcion;
    }

    private String cCredProductos ;
    private String cDescripcion;

    @Override
    public String toString() {
        return cDescripcion;
    }

}
