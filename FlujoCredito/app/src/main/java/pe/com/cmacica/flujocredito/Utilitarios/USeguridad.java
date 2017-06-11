package pe.com.cmacica.flujocredito.Utilitarios;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import pe.com.cmacica.flujocredito.R;

/**
 * Created by jhcc on 31/08/2016.
 */
public class USeguridad {

    public static Account obtenerCuentaActiva(final Context context) {

        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account cuenta = new Account(context.getString(R.string.app_name),
                context.getString(R.string.tipo_cuenta));

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(cuenta)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(cuenta, "", null))
                return null;

        }
        return cuenta;
    }
}
