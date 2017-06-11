package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 03/06/2017.
 */

public class EstrategiaGestionModel {

    public EstrategiaGestionModel(int EstGesionCod,String EstGestionDescripcion)
    {
        this.EstGesionCod=EstGesionCod;
        this.EstGestionDescripcion=EstGestionDescripcion;
    }
    private int EstGesionCod;
    private String EstGestionDescripcion;

    public int getEstGesionCod() {
        return EstGesionCod;
    }

    public String getEstGestionDescripcion() {
        return EstGestionDescripcion;
    }

    @Override
    public String toString() {
        return EstGestionDescripcion;

    }
}
