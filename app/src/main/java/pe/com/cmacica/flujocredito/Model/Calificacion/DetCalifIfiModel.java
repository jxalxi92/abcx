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
    public String getnDiasAtraso() {
        return nDiasAtraso;
    }

    public void setnDiasAtraso(String nDiasAtraso) {
        this.nDiasAtraso = nDiasAtraso;
    }
    private String NombreIfi;
    private String SaldoCapital;



    private String nDiasAtraso;

    public DetCalifIfiModel(String nombreIfi, String saldoCapital,String pnDiasAtraso) {
        NombreIfi = nombreIfi;
        SaldoCapital = saldoCapital;
        this.nDiasAtraso = pnDiasAtraso;
    }



}
