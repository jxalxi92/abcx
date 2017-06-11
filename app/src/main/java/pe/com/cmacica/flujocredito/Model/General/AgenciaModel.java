package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by jhcc on 07/07/2016.
 */
public class AgenciaModel {

    public AgenciaModel(String codigoAgencia, String nombreAgencia) {
        CodigoAgencia = codigoAgencia;
        NombreAgencia = nombreAgencia;
    }
    public String getCodigoAgencia() {
        return CodigoAgencia;
    }

    public String getNombreAgencia() {
        return NombreAgencia;
    }
    @Override
    public String toString() {
        return NombreAgencia;
    }
    private String CodigoAgencia;

    private String NombreAgencia;

    public void setCodigoAgencia(String codigoAgencia) {
        CodigoAgencia = codigoAgencia;
    }
}
