package pe.com.cmacica.flujocredito.Utilitarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jhcc on 25/08/2016.
 */
public class UPreferencias {

    private static final String PreferenciaUser = "cmacica.user";
    private static final String PreferenciaNombreUsuario = "cmacica.NombreUsuario";
    private static final String PreferenciaPass = "cmacica.Pass";
    private static final String PreferenciaIndDesconectado = "cmacica.IndDesconectado";
    private static final String PreferenciacPersCod = "cmacica.cPersCod";
    private static final String PreferenciacAgencia = "cmacica.cAgeCod";
    private static final String PreferenciaImei = "cmacica.Imei";
    private static final String PreferenciaCodAgencia="cmacica.CodAgencia";


    private static SharedPreferences getDefaultSharedPreferences(Context contexto) {
        return PreferenceManager.getDefaultSharedPreferences(contexto);
    }

    //region Datos Generales de Logueo
    public static void GuardarDatosUsuarios(Context contexto, String Usuario,String Nombre , String CodigoPersona, String NombreAgencia,String CodAgencia ){

        SharedPreferences sp = getDefaultSharedPreferences(contexto);
        sp.edit().putString(PreferenciacPersCod,CodigoPersona).apply();
        sp.edit().putString(PreferenciaUser,Usuario).apply();
        sp.edit().putString(PreferenciaNombreUsuario,Nombre).apply();
        sp.edit().putString(PreferenciacAgencia,NombreAgencia).apply();
        sp.edit().putString(PreferenciaCodAgencia,CodAgencia).apply();
    }

    public static String ObtenerUserLogeo(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaUser,null);
    }

    public static String ObtenerNombreCompleto(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaNombreUsuario,null);
    }
    public static String ObtenerCodigoPersonaLogeo(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciacPersCod,null);
    }
    public static String ObtenerAgenciaLogeo(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciacAgencia,null);
    }
    public static String ObtenerCodAgencia(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaCodAgencia,null);
    }
    //endregion

    //region Contraseña
    public static void  SavePass(Context contexto, String pass ){
        SharedPreferences sp = getDefaultSharedPreferences(contexto);
        sp.edit().putString(PreferenciaPass,pass).apply();
    }
    public static String ObtenerPassDesc(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaPass,null);
    }
    //endregion

    //region Indicador Desconectado
    public static void SaveIndDesconectado(Context contexto, String indicador ){
        SharedPreferences sp = getDefaultSharedPreferences(contexto);
        sp.edit().putString(PreferenciaIndDesconectado,indicador).apply();
    }
    public static String ObtenerIndDesconectado(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaIndDesconectado,null);
    }


    public static void SaveImei(Context contexto, String cImei ){
        SharedPreferences sp = getDefaultSharedPreferences(contexto);
        sp.edit().putString(PreferenciaImei,cImei).apply();
    }
    public static String ObtenerImei(Context contexto){

        return getDefaultSharedPreferences(contexto).getString(PreferenciaImei,null);
    }
    //endregion




}
