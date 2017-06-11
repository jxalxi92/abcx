package pe.com.cmacica.flujocredito.Model;

/**
 * Created by jhcc on 07/07/2016.
 */
public class ConstanteModel {

    public ConstanteModel(int codigoConstante, int codigoValor, String descripcion) {
        CodigoConstante = codigoConstante;
        CodigoValor = codigoValor;
        Descripcion = descripcion;
    }

    public int getCodigoConstante() {
        return CodigoConstante;
    }

    public int getCodigoValor() {
        return CodigoValor;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    private int CodigoConstante;
    private int CodigoValor;
    private String Descripcion;

    @Override
    public String toString() {
        return Descripcion;
    }

}
