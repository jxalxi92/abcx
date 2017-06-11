package pe.com.cmacica.flujocredito.Model.Calificacion;

import java.util.Date;

import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * Created by jhcc on 15/11/2016.
 */

public class HistPlanPagoModel {


    public HistPlanPagoModel(int nCuota, String dVenc, String dPago, double cuota, double cuotaPag, String cColocCalendEstadoDes, int estadoCuota,String pnDiasAtraso) {
        this.nCuota = nCuota;
        this.dVenc = dVenc;
        this.dPago = dPago;
        Cuota = cuota;
        CuotaPag = cuotaPag;
        this.cColocCalendEstadoDes = cColocCalendEstadoDes;
        EstadoCuota = estadoCuota;
        this.nDiasAtraso = pnDiasAtraso;
    }

    public int getnCuota() {
        return nCuota;
    }

    public void setnCuota(int nCuota) {
        this.nCuota = nCuota;
    }

    public String getdVenc() {
        return dVenc;
    }

    public void setdVenc(String dVenc) {
        this.dVenc = dVenc;
    }

    public String getdPago() {
        return dPago;
    }

    public void setdPago(String dPago) {
        this.dPago = dPago;
    }

    public double getCuota() {
        return Cuota;
    }

    public void setCuota(double cuota) {
        Cuota = cuota;
    }

    public double getCuotaPag() {
        return CuotaPag;
    }

    public void setCuotaPag(double cuotaPag) {
        CuotaPag = cuotaPag;
    }

    public String getcColocCalendEstadoDes() {
        return cColocCalendEstadoDes;
    }

    public void setcColocCalendEstadoDes(String cColocCalendEstadoDes) {
        this.cColocCalendEstadoDes = cColocCalendEstadoDes;
    }

    public int getEstadoCuota() {
        return EstadoCuota;
    }

    public void setEstadoCuota(int estadoCuota) {
        EstadoCuota = estadoCuota;
    }
    public String getnDiasAtraso() {
        return nDiasAtraso;
    }

    public void setnDiasAtraso(String nDiasAtraso) {
        this.nDiasAtraso = nDiasAtraso;
    }


    private int nCuota;
    private String dVenc;
    private String dPago;
    private double Cuota;
    private double CuotaPag;
    private String cColocCalendEstadoDes;
    private int EstadoCuota;
    private String nDiasAtraso;




    public String getHex() {

        return hex  ;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    private String hex;
}
