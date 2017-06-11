package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 03/06/2017.
 */

public class MotivoNoPagoModel {
    public MotivoNoPagoModel(int MotnpCod,String MotnpDescripcion)
    {
        this.MotnpCod=MotnpCod;
        this.MotnpDescripcion=MotnpDescripcion;
    }
    private int MotnpCod;
    private String MotnpDescripcion;

    public int getMotnpCod() {
        return MotnpCod;
    }

    public String getMotnpDescripcion() {
        return MotnpDescripcion;
    }

    @Override
    public String toString() {
        return  MotnpDescripcion;

    }
}
