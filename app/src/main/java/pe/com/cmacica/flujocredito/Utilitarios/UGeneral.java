package pe.com.cmacica.flujocredito.Utilitarios;

import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


import pe.com.cmacica.flujocredito.Model.ResultOpe;

/**
 * Created by jhcc on 31/08/2016.
 */
public class UGeneral {

    public static long obtenerTiempoEnMS() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String obtenerTiempo() {
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String obtenerTiempoCorto() {
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }


    public static boolean validaFechaMascara(String sFecha, String sMascara)
    {
        boolean retorno = false;
        try {
            //Convertir la fecha al Calendar
            java.util.Locale locInstancia = new java.util.Locale("es","PE");
            java.text.DateFormat dfInstancia;
            java.util.Date dInstancia;
            dfInstancia = new java.text.SimpleDateFormat(sMascara,locInstancia);
            dInstancia = (java.util.Date)dfInstancia.parse(sFecha);
            java.util.Calendar cal = java.util.Calendar.getInstance(locInstancia);
            cal.setTime(dInstancia); //setear la fecha en cuestion al calendario
            retorno = true;
        } catch (java.text.ParseException excep) {
            retorno = false; //unparseable date
        } finally {
            return retorno;
        }
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static double RoundDouble(double monto){
        return Math.round(monto*100.0)/100.0;
    }

    public static Date ConvertStringToDate(String fechaCadena){

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date FechaDate = null;
        try{
            FechaDate = formato.parse(fechaCadena);


        }catch(ParseException ex){
            Log.d("Utilitario",ex.getMessage());
        }
        return FechaDate;
    }

    public static int DaysEntreFechas (String DateStart, String DateEnd){
        Date FechaInicio = ConvertStringToDate(DateStart);
        Date FechaFin = ConvertStringToDate(DateEnd);

        long diff = FechaInicio.getTime()-FechaFin.getTime();
        int result  = (int) diff/(24 * 60 * 60 * 1000);
        return result;
    }

}
