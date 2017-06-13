package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by jhcc on 07/07/2016.
 */
public class ConstanteModel {

    public ConstanteModel(int codigoConstante, int codigoValor, String descripcion,int equivalente) {
        CodigoConstante = codigoConstante;
        CodigoValor = codigoValor;
        Descripcion = descripcion;
        Equivalente=equivalente;

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
    public int getEquivalente() {
        return Equivalente;
    }

    private int CodigoConstante;
    private int CodigoValor;
    private String Descripcion;
    private int Equivalente;


    @Override
    public String toString() {
        return Descripcion;
    }

}
