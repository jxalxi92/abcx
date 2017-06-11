package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 03/06/2017.
 */

public class ResultadoModel {

    public ResultadoModel(int resCod,String resDescripcion,int TipoContacto,int TipoGestion,String Flag){
     this.resCod=resCod;
     this.resDescripcion=resDescripcion;
     this.TipoContacto=TipoContacto;
     this.TipoGestion= TipoGestion;
     this.Flag=Flag;
    }
    private int resCod;
    private String resDescripcion;
    private int TipoContacto;
    private int TipoGestion;
    private String Flag;

    public int getResCod() {
        return resCod;
    }

    public String getResDescripcion() {
        return resDescripcion;
    }

    public int getTipoContacto() {
        return TipoContacto;
    }

    public int getTipoGestion() {
        return TipoGestion;
    }

    public String getFlag() {
        return Flag;
    }

    @Override
    public String toString() {
        return  resDescripcion;
    }
}
