package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.R;


public class FragmentoPersonaFteIgrDet extends DialogFragment {


    ImageView  imagenLlamar;
    ImageView  imagenLlamar2;
    ImageView  imagenFteDep;
    ImageView  imagenFteIndep;
    TextView lblDirección;
    TextView lblTel;
    TextView lblCel;
    DigitacionDto ClienteSel;

    public interface  IOperacionTipoFuente{

        void  OnTipoFuenteOperacion(int operacion,DigitacionDto DatosCliente);
    }

    IOperacionTipoFuente listener;

    public FragmentoPersonaFteIgrDet() {
        // Required empty public constructor
    }

    public void CrearInstancia(DigitacionDto Cliente ){
        ClienteSel = Cliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_persona_fte_igr_det, container, false);

        imagenLlamar =(ImageView) view.findViewById(R.id.icono_llamar_persona);
        imagenLlamar2 =(ImageView) view.findViewById(R.id.icono_llamar_persona2);
        imagenFteDep =(ImageView) view.findViewById(R.id.icono_fuente_dep);
        imagenFteIndep =(ImageView) view.findViewById(R.id.icono_fuente_Ind);

        lblCel = (TextView) view.findViewById(R.id.lblCel);
        lblTel = (TextView) view.findViewById(R.id.lblTel);
        lblDirección= (TextView) view.findViewById(R.id.lblDirección);

        if(ClienteSel.cPersDireccDomicilio == null){
            lblDirección.setText("");
        }else{
            lblDirección.setText(ClienteSel.cPersDireccDomicilio);
        }
        if(ClienteSel.cPersTelefono == null){
            lblCel.setText("");
            ClienteSel.cPersTelefono = "";
        }else{
            lblCel.setText(ClienteSel.cPersTelefono);
        }
        if(ClienteSel.cPersTelefono2 == null){
            lblTel.setText("");
            ClienteSel.cPersTelefono2 = "";
        }else{
            if(ClienteSel.cPersTelefono2.equals("null")){
                lblTel.setText("");
                ClienteSel.cPersTelefono2 = "";
            }else {
                lblTel.setText(ClienteSel.cPersTelefono2);

            }
        }

        imagenLlamar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnTipoFuenteOperacion(1,ClienteSel);
                        dismiss();
                    }
                }
        );

        imagenLlamar2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnTipoFuenteOperacion(2,ClienteSel);
                        dismiss();
                    }
                }
        );

        imagenFteDep.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnTipoFuenteOperacion(3,ClienteSel);
                        dismiss();
                    }
                }
        );
        imagenFteIndep.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnTipoFuenteOperacion(4,ClienteSel);
                        dismiss();
                    }
                }
        );

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (IOperacionTipoFuente) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó Detalle gasto");

        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
