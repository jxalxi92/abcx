package pe.com.cmacica.flujocredito.Model;

/**
 * Created by jhcc on 12/07/2016.
 */
public class FrecuenciaPagoModel {

    public int getnCodCredFrecPago() {
        return nCodCredFrecPago;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public int getnDias() {
        return nDias;
    }

    public FrecuenciaPagoModel(int nCodCredFrecPago, String descripcion, int nDias) {
        this.nCodCredFrecPago = nCodCredFrecPago;
        Descripcion = descripcion;
        this.nDias = nDias;
    }
    @Override
    public String toString() {
        return Descripcion;
    }
    private int nCodCredFrecPago;
    private  String Descripcion;
    private int nDias;

}
