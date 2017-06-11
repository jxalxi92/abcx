package pe.com.cmacica.flujocredito.Utilitarios.Dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by jhcc on 14/07/2016.
 */
public class SimpleListDialog extends DialogFragment {

    public SimpleListDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSingleListDialog();
    }

    /**
     * Crea un Diálogo con una lista de selección simple
     *
     * @return La instancia del diálogo
     */
    public AlertDialog createSingleListDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final CharSequence[] items = new CharSequence[3];

        items[0] = "Naranja";
        items[1] = "Mango";
        items[2] = "Banano";

        builder.setTitle("Diálogo De Lista")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getActivity(),
                                "Seleccionaste:" + items[which],
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        return builder.create();
    }

}
