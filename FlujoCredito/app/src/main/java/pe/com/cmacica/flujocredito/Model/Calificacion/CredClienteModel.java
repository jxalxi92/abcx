package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by jhcc on 15/11/2016.
 */

public class CredClienteModel {

    public CredClienteModel(String numeroCuenta, String productoCuenta, String estadoCuenta) {
        NumeroCuenta = numeroCuenta;
        ProductoCuenta = productoCuenta;
        EstadoCuenta = estadoCuenta;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        NumeroCuenta = numeroCuenta;
    }

    public String getProductoCuenta() {
        return ProductoCuenta;
    }

    public void setProductoCuenta(String productoCuenta) {
        ProductoCuenta = productoCuenta;
    }

    public String getEstadoCuenta() {
        return EstadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        EstadoCuenta = estadoCuenta;
    }

    private String NumeroCuenta;
    private String ProductoCuenta;
    private String EstadoCuenta;
}
