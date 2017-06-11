package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 28/06/2016.
 */
public class DetCalifSbsModel {


    public String getCalif0() {
        return Calif0;
    }

    public String getCalif1() {
        return Calif1;
    }

    public String getCalif2() {
        return Calif2;
    }

    public String getCalif3() {
        return Calif3;
    }

    public String getCalif4() {
        return Calif4;
    }

    public String getFecha() {
        return Fecha;
    }
    private String Calif0;
    private String Calif1;
    private String Calif2;
    private String Calif3;
    private String Calif4;
    private String Fecha;
    public DetCalifSbsModel(String calif0, String calif1, String calif2, String calif3, String calif4, String fecha) {
        Calif0 = calif0;
        Calif1 = calif1;
        Calif2 = calif2;
        Calif3 = calif3;
        Calif4 = calif4;
        Fecha = fecha;
    }


}
