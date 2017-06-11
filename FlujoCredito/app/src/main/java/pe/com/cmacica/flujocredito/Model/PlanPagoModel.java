package pe.com.cmacica.flujocredito.Model;

/**
 * Created by jhcc on 05/07/2016.
 */
public class PlanPagoModel {


    public PlanPagoModel(String bonoCrediemprende,
                         String capital,
                         String cuota,
                         String desgravamen,
                         String fechaPago,
                         String gastos,
                         String interes,
                         String microseguro,
                         String nroCuota,
                         String salCap) {
        this.BonoCrediemprende = bonoCrediemprende;
        this.Capital = capital;
        this.Cuota = cuota;
        this.Desgravamen = desgravamen;
        this.FechaPago = fechaPago;
        this.Gastos = gastos;
        this.Interes = interes;
        this.Microseguro = microseguro;
        this.NroCuota = nroCuota;
        this.SalCap = salCap;
    }

    private String  BonoCrediemprende;
    private String  Capital;
    private String  Cuota;
    private String  Desgravamen;
    private String  FechaPago;
    private String  Gastos;
    private String  Interes;
    private String  Microseguro;
    private String  NroCuota;
    private String  SalCap;

    public String getBonoCrediemprende() {
        return BonoCrediemprende;
    }

    public String getCapital() {
        return Capital;
    }

    public String getCuota() {
        return Cuota;
    }

    public String getDesgravamen() {
        return Desgravamen;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public String getGastos() {
        return Gastos;
    }

    public String getInteres() {
        return Interes;
    }

    public String getMicroseguro() {
        return Microseguro;
    }

    public String getNroCuota() {
        return NroCuota;
    }

    public String getSalCap() {
        return SalCap;
    }






}
