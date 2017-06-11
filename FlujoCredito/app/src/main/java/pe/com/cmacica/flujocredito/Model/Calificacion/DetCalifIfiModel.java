package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 28/06/2016.
 */
public class DetCalifIfiModel {

    public String getNombreIfi() {
        return NombreIfi;
    }

    public String getSaldoCapital() {
        return SaldoCapital;
    }

    private String NombreIfi;
    private String SaldoCapital;

    public DetCalifIfiModel(String nombreIfi, String saldoCapital) {
        NombreIfi = nombreIfi;
        SaldoCapital = saldoCapital;
    }



}
