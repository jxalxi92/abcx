package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 03/06/2017.
 */

public class TelefonoModel {
    public TelefonoModel(String cPersCod,String cNumero,boolean bVigente)
    {
        this.cPersCod=cPersCod;
        this.cNumero=cNumero;
        this.bVigente=bVigente;

    }
    private String cPersCod;
    private String cNumero;
    private boolean bVigente;


    public String getcNumero() {
        return cNumero;
    }

    public boolean isbVigente() {
        return bVigente;
    }

    @Override
    public String toString() {
        return cNumero;
    }
}
