package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 27/06/2016.
 */
public class CabCalifModel {


    private String CodigoSbs;
    private String NombreDeudor;
    private String TipoPersona;
    private String SaldoCapital;
    private String CalifCmac;
    private String NumIfis;

    public CabCalifModel(String codigoSbs, String nombreDeudor, String tipoPersona, String saldoCapital, String califCmac, String numIfis) {
        CodigoSbs = codigoSbs;
        NombreDeudor = nombreDeudor;
        TipoPersona = tipoPersona;
        SaldoCapital = saldoCapital;
        CalifCmac = califCmac;
        NumIfis = numIfis;
    }



    public String getCodigoSbs() {
        return CodigoSbs;
    }

    public String getNombreDeudor() {
        return NombreDeudor;
    }

    public String getTipoPersona() {
        return TipoPersona;
    }

    public String getSaldoCapital() {
        return SaldoCapital;
    }

    public String getCalifCmac() {
        return CalifCmac;
    }

    public String getNumIfis() {
        return NumIfis;
    }



}
