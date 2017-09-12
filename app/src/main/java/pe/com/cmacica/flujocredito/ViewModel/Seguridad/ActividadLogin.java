package pe.com.cmacica.flujocredito.ViewModel.Seguridad;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pe.com.cmacica.flujocredito.AgenteServicio.RESTService;
import pe.com.cmacica.flujocredito.Model.ResultOpe;
import pe.com.cmacica.flujocredito.Model.Seguridad.DeviceModel;
import pe.com.cmacica.flujocredito.Model.Seguridad.UsuarioModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.AgenteServicio.SrvCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UPreferencias;
import pe.com.cmacica.flujocredito.Utilitarios.UService;
import pe.com.cmacica.flujocredito.ViewModel.ActividadPrincipal;

/**
 * A login screen that offers login via email/password.
 */
public class ActividadLogin extends AppCompatActivity   {

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    private static final String TAG = ActividadLogin.class.getSimpleName();

    //region controles.
    private EditText txtUser;
    private EditText txtPassword;

    private Button btnLogin;
    private String IMEIM="";
    private int NroIntentos = 0;
    Gson gsonpojo ;
    private ProgressDialog progressDialog ;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            loadIMEI();
        }
        txtUser = (EditText) findViewById(R.id.txtUserLogueo);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        OnValidaHost();
        //btnLogin.isEnabled();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void OnLogin(View view){

        progressDialog = ProgressDialog.show(this,"Espere por favor","Validando usuario.");
        OnValidaUsuario();

    }

    private void OnValidaHost(){

        if (UService.hayConexion(this)) {

            UPreferencias.SaveIndDesconectado(this,"0");
            String IMEI ="";
            progressDialog = ProgressDialog.show(this,"Espere por favor","Validando dispositivo.");
            //IMEI = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                IMEI = IMEIM;
            }
            else{
                TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (mTelephony.getDeviceId() != null){
                    IMEI = mTelephony.getDeviceId();
                }
            }

            UPreferencias.SaveImei(this,IMEI);
            gsonpojo = new GsonBuilder().create();
            DeviceModel devpost = new DeviceModel();
            devpost.IMEI = IMEI;
            String json = gsonpojo.toJson(devpost);
            HashMap<String, String> cabeceras = new HashMap<>();

            new RESTService(this).post(SrvCmacIca.GET_VALIDA_DEVICE, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.cancel();
                            ProcesarValidaDevice(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error Volley: " + error.toString());
                            progressDialog.cancel();
                            errorservice(error);
                        }
                    }
                    , cabeceras);

        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("No existe conexión a internet, ¿Desea iniciar en modo desconectado?")
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface arg0) {
                           // ActividadLogin.this.finish();
                        }})
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActividadLogin.this.finish();
                        }
                    })//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir

                            txtUser.setEnabled(true);
                            ActualizaDesc("1");

                        }
                    })
                    .show();

        }

    }
    private void ActualizaDesc (String Estado){
        UPreferencias.SaveIndDesconectado(this,Estado);
    }

    private void ProcesarValidaDevice(JSONObject response){
        try {

            // Obtener atributo "estado"
            boolean EstadoOperacion = response.getBoolean("IsCorrect");
            if (EstadoOperacion) {

                txtUser.setText(response.getJSONObject("Data").getString("cUser"));
            }
            else{
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage(response.getString("Message"))
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                ActividadLogin.this.finish();
                            }})
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //Salir
                                ActividadLogin.this.finish();
                                //txtPassword.setText("");
                            }
                        })
                        .show();
            }

        } catch (JSONException e) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Dispositivo no registrado.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir
                            ActividadLogin.this.finish();
                            //txtPassword.setText("");
                        }
                    })
                    .show();
        }
    }

    private void OnValidaUsuario(){

        /*
        finish();
        Intent intent = new Intent(this,ActividadPrincipal.class);
        startActivity(intent);
        */
        if (txtUser.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Dispositivo no registrado.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            return;
                            //txtPassword.setText("");
                        }
                    })
                    .show();
            progressDialog.cancel();
            return;
        }
        if (txtPassword.getText().toString().length() < 6 || txtPassword.getText().toString().length() > 16) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("Vuelva a ingresar su contraseña mínimo 6 y máximo 16.")
                    //.setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            return;
                        }
                    })
                    .show();
            progressDialog.cancel();
            return;
        }

        if(UPreferencias.ObtenerIndDesconectado(this).equals("1")){
            if (UPreferencias.ObtenerUserLogeo(this) == null) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Aviso")
                        .setMessage("Tiene que iniciar sesión en línea una primera vez para poder utilizar de forma desconectado.")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface arg0) {
                                ActividadLogin.this.finish();
                            }})
                        //.setNegativeButton(android.R.string.cancel, null)//sin listener
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Salir
                                return;
                            }
                        })
                        .show();
                progressDialog.cancel();
                return;
            }
            if(UPreferencias.ObtenerUserLogeo(this).toUpperCase().equals(txtUser.getText().toString().toUpperCase()) &&
                    UPreferencias.ObtenerPassDesc(this).equals(txtPassword.getText().toString())){

                finish();
                Intent intent = new Intent(this,ActividadPrincipal.class);
                startActivity(intent);
            }else{
                progressDialog.cancel();
                NroIntentos += 1;

                if(NroIntentos < 3){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Contraseña incorrecta")
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    //ActividadLogin.this.finish();
                                    txtPassword.setText("");
                                }
                            })
                            .show();
                }else{

                    UPreferencias.SavePass(this,"#$%&?¡*fdg?54465#4gdfgfh%545g¿fh4");
                    AlertDialog.Builder msj=  new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Ha utilizado el máximo de intentos fallidos.")
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    ActividadLogin.this.finish();
                                }})
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    ActividadLogin.this.finish();
                                    txtPassword.setText("");
                                }
                            });
                    msj.show();

                }
            }

        }else {

            gsonpojo = new GsonBuilder().create();

            UsuarioModel devpost = new UsuarioModel();
            devpost.cUser = txtUser.getText().toString();
            devpost.Password = txtPassword.getText().toString();
            String json = gsonpojo.toJson(devpost);

            HashMap<String, String> cabeceras = new HashMap<>();

            new RESTService(this).post(SrvCmacIca.GET_VALIDA_USUARIO, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ProcesarLogging(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Error Volley: " + error.toString());
                            progressDialog.cancel();
                            errorservice(error);
                        }
                    }
                    , cabeceras);
        }

    }

    private void ProcesarLogging(JSONObject response){
        try {
            progressDialog.cancel();

            // Obtener atributo "estado"
            boolean EstadoOperacion = response.getBoolean("IsCorrect");

            if (EstadoOperacion ) {
                NroIntentos = 0;
                JSONObject data = response.getJSONObject("Data");
                UPreferencias.GuardarDatosUsuarios(this,
                        txtUser.getText().toString(),
                        data.getString("Nombres"),
                        data.getString("cPersCod"),
                        data.getString("NombreAgencia"),
                        data.getString("CodigoAgencia"));

                UPreferencias.SavePass(this,txtPassword.getText().toString());
                UPreferencias.SaveIndDesconectado(this,"0");
               //

                finish();
                Intent intent = new Intent(this,ActividadPrincipal.class);
                startActivity(intent);

            }else {
                NroIntentos += 1;

                if(NroIntentos < 3){
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage(response.getString("Message"))
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    //ActividadLogin.this.finish();
                                    txtPassword.setText("");
                                }
                            })
                            .show();
                }else{
                    AlertDialog.Builder msj=  new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Aviso")
                            .setMessage("Ha utilizado el máximo de intentos fallidos.")
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface arg0) {
                                    ActividadLogin.this.finish();
                                }})
                            //.setNegativeButton(android.R.string.cancel, null)//sin listener
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //Salir
                                    ActividadLogin.this.finish();
                                    txtPassword.setText("");
                                }
                            });
                            msj.show();
                }

            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            //progressDialog.cancel();
        }
    }

    private void errorservice (VolleyError error){


        ResultOpe result = UService.tratarErrores(error);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aviso")
                .setMessage(result.getMensaje())
                //.setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //Salir
                        ActividadLogin.this.finish();
                        //txtPassword.setText("");
                    }
                })
                .show();
    }

    //region Imei

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }

    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
           // For example if the user has previously denied the permission.
            new AlertDialog.Builder(this)
                    .setTitle("Permission Request")
                    .setMessage("¿Desea que Credi Móvil pueda leer datos del dispositivo?")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(ActividadLogin.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                doPermissionGrantedStuffs();
            } else {
                alertAlert("¿Desea que credi móvil pueda leer el estado del dispositivo?");
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Permiso requerido")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        String IMEINumber=tm.getDeviceId();
        IMEIM =IMEINumber;
        /************************************************
         * **********************************************
         * This is just an icing on the cake
         * the following are other children of TELEPHONY_SERVICE
         *
         //Get Subscriber ID
         String subscriberID=tm.getDeviceId();

         //Get SIM Serial Number
         String SIMSerialNumber=tm.getSimSerialNumber();

         //Get Network Country ISO Code
         String networkCountryISO=tm.getNetworkCountryIso();

         //Get SIM Country ISO Code
         String SIMCountryISO=tm.getSimCountryIso();

         //Get the device software version
         String softwareVersion=tm.getDeviceSoftwareVersion()

         //Get the Voice mail number
         String voiceMailNumber=tm.getVoiceMailNumber();


         //Get the Phone Type CDMA/GSM/NONE
         int phoneType=tm.getPhoneType();

         switch (phoneType)
         {
         case (TelephonyManager.PHONE_TYPE_CDMA):
         // your code
         break;
         case (TelephonyManager.PHONE_TYPE_GSM)
         // your code
         break;
         case (TelephonyManager.PHONE_TYPE_NONE):
         // your code
         break;
         }

         //Find whether the Phone is in Roaming, returns true if in roaming
         boolean isRoaming=tm.isNetworkRoaming();
         if(isRoaming)
         phoneDetails+="\nIs In Roaming : "+"YES";
         else
         phoneDetails+="\nIs In Roaming : "+"NO";


         //Get the SIM state
         int SIMState=tm.getSimState();
         switch(SIMState)
         {
         case TelephonyManager.SIM_STATE_ABSENT :
         // your code
         break;
         case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_PIN_REQUIRED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_PUK_REQUIRED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_READY :
         // your code
         break;
         case TelephonyManager.SIM_STATE_UNKNOWN :
         // your code
         break;

         }
         */
        // Now read the desired content to a textview.
        //loading_tv2 = (TextView) findViewById(R.id.loading_tv2);
        //loading_tv2.setText(IMEINumber);
    }
    //endregion




}

