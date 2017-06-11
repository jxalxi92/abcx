package pe.com.cmacica.flujocredito.Model;

/**
 * Created by jhcc on 05/08/2016.
 */
public class ResultOpe {

    private int estado;
    private String mensaje;

    public ResultOpe(int code, String body) {
        this.estado = code;
        this.mensaje = body;
    }

    public int getEstado() {
        return estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return "(" + getEstado() + "): " + getMensaje();
    }
}
