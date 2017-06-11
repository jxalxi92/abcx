package pe.com.cmacica.flujocredito.Model.Cobranza;

/**
 * Created by jhcc on 04/06/2017.
 */

public class DetalleGestionModel {

    public DetalleGestionModel(int nIdBase,String Analista, String TipoCredito, String Producto, String NroCredito,
                               String Estado, double ValorCuota, int DiasAtraso,String Usuario) {
        this.nIdBase=nIdBase;
        this.Analista = Analista;
        this.TipoCredito = TipoCredito;
        this.Producto = Producto;
        this.NroCredito = NroCredito;
        this.Estado=Estado;
        this.ValorCuota = ValorCuota;
        this.DiasAtraso = DiasAtraso;
        this.Usuario=Usuario;
    }
    private int nIdBase;
    private String Analista;
    private String TipoCredito;
    private String Producto;
    private String NroCredito;
    private String Estado;
    private double ValorCuota;
    private int DiasAtraso;
    private String Usuario;

    public int getnIdBase() {return nIdBase; }

    public String getUsuario() {return Usuario; }

    public String getAnalista() {
        return Analista;
    }

    public String getTipoCredito() {
        return TipoCredito;
    }

    public String getProducto() {
        return Producto;
    }

    public String getNroCredito() {
        return NroCredito;
    }

    public String getEstadoDetalleGestion() {
        return Estado;
    }

    public double getValorCuota() {
        return ValorCuota;
    }

    public int getDiasAtraso() {
        return DiasAtraso;
    }

    @Override
    public String toString() {
        return NroCredito;
    }
}
