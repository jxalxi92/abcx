package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 02/06/2017.
 */

public class TipoGestionModel {

    public TipoGestionModel(int Codigo,String Descripcion)
    {
       this.Codigo=Codigo;
       this.Descripcion=Descripcion;

    }
    private  int Codigo;
    private String Descripcion;

    public int getCodigo() {
        return Codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    @Override
    public String toString() {
        return Descripcion;
    }
}
