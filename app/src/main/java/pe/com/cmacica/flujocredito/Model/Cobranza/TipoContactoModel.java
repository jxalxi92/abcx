package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 02/06/2017.
 */

public class TipoContactoModel {


    public TipoContactoModel(int TipoContacto,String Descripcion)
    {
        this.TipoContacto=TipoContacto;
        this.Descripcion=Descripcion;
    }

    public int getTipoContacto() {
        return TipoContacto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    @Override
    public String toString() {
        return Descripcion;
    }

    private int TipoContacto;
    private String Descripcion;
}
