package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by jhcc on 15/11/2016.
 */

public class ReglasNegocioModel {

    public ReglasNegocioModel(int nCodAlinea, String cReglamento, String cExcepcion, boolean bAprueba, boolean bExcepcion) {
        this.nCodAlinea = nCodAlinea;
        this.cReglamento = cReglamento;
        this.cExcepcion = cExcepcion;
        this.bAprueba = bAprueba;
        this.bExcepcion = bExcepcion;
    }

    public int getnCodAlinea() {
        return nCodAlinea;
    }

    public void setnCodAlinea(int nCodAlinea) {
        this.nCodAlinea = nCodAlinea;
    }

    public String getcReglamento() {
        return cReglamento;
    }

    public void setcReglamento(String cReglamento) {
        this.cReglamento = cReglamento;
    }

    public String getcExcepcion() {
        return cExcepcion;
    }

    public void setcExcepcion(String cExcepcion) {
        this.cExcepcion = cExcepcion;
    }

    public boolean isbAprueba() {
        return bAprueba;
    }

    public void setbAprueba(boolean bAprueba) {
        this.bAprueba = bAprueba;
    }

    public boolean isbExcepcion() {
        return bExcepcion;
    }

    public void setbExcepcion(boolean bExcepcion) {
        this.bExcepcion = bExcepcion;
    }

    private int nCodAlinea;
    private String cReglamento;
    private String cExcepcion;
    private boolean bAprueba;
    private boolean bExcepcion;
}
