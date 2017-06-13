package pe.com.cmacica.flujocredito.Model.Solicitud;

/**
 * Created by faqf on 09/06/2017.
 */

public class SolCredClasifModel
{

    public SolCredClasifModel(){


    }
    public SolCredClasifModel(int nTipoCredito, String cTipoCredito, double mes6, double mes5, double mes4, double mes3, double mes2, double mes1, double ventas1, double ventas2, int tipoPersona, int ultimoMesClasif, String fecha, String doi, double monto) {
        this.nTipoCredito = nTipoCredito;
        this.cTipoCredito = cTipoCredito;
        Mes6 = mes6;
        Mes5 = mes5;
        Mes4 = mes4;
        Mes3 = mes3;
        Mes2 = mes2;
        Mes1 = mes1;
        Ventas1 = ventas1;
        Ventas2 = ventas2;
        TipoPersona = tipoPersona;
        UltimoMesClasif = ultimoMesClasif;
        Fecha = fecha;
        Doi = doi;
        Monto = monto;
    }

    public int nTipoCredito;

    public String cTipoCredito;

    public double Mes6 ;

    public double Mes5 ;

    public double Mes4 ;

    public double Mes3 ;

    public double Mes2;

    public int getnTipoCredito() {
        return nTipoCredito;
    }

    public String getcTipoCredito() {
        return cTipoCredito;
    }

    public double getMes6() {
        return Mes6;
    }

    public double getMes5() {
        return Mes5;
    }

    public double getMes4() {
        return Mes4;
    }

    public double getMes3() {
        return Mes3;
    }

    public double getMes2() {
        return Mes2;
    }

    public double getMes1() {
        return Mes1;
    }

    public double getVentas1() {
        return Ventas1;
    }

    public double getVentas2() {
        return Ventas2;
    }

    public int getTipoPersona() {
        return TipoPersona;
    }

    public int getUltimoMesClasif() {
        return UltimoMesClasif;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getDoi() {
        return Doi;
    }

    public double getMonto() {
        return Monto;
    }

    public double Mes1;

    public double Ventas1 ;

    public double Ventas2 ;

    public int TipoPersona ;

    public int UltimoMesClasif ;

    public  String Fecha;

    public  String Doi;

    public  double Monto ;

    public void setnTipoCredito(int nTipoCredito) {
        this.nTipoCredito = nTipoCredito;
    }

    public void setcTipoCredito(String cTipoCredito) {
        this.cTipoCredito = cTipoCredito;
    }

    public void setMes6(double mes6) {
        Mes6 = mes6;
    }

    public void setMes5(double mes5) {
        Mes5 = mes5;
    }

    public void setMes4(double mes4) {
        Mes4 = mes4;
    }

    public void setMes3(double mes3) {
        Mes3 = mes3;
    }

    public void setMes2(double mes2) {
        Mes2 = mes2;
    }

    public void setMes1(double mes1) {
        Mes1 = mes1;
    }

    public void setVentas1(double ventas1) {
        Ventas1 = ventas1;
    }

    public void setVentas2(double ventas2) {
        Ventas2 = ventas2;
    }

    public void setTipoPersona(int tipoPersona) {
        TipoPersona = tipoPersona;
    }

    public void setUltimoMesClasif(int ultimoMesClasif) {
        UltimoMesClasif = ultimoMesClasif;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public void setDoi(String doi) {
        Doi = doi;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }
}
