package pe.com.cmacica.flujocredito.ViewModel.Solicitud;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import pe.com.cmacica.flujocredito.Model.Solicitud.DatoPersonaSolicitudModel;
import pe.com.cmacica.flujocredito.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoVisorSbs extends DialogFragment {

    private  View view;
    private TextView lbl_Mes0,lbl_Mes1,lbl_Mes2,lbl_Mes3,lbl_Mes4;
    private EditText txt_Calificacion,txt_saldo,txt_CantEntidades,txt_Resutlado;
    private Button btn_Aceptar;
    DatoPersonaSolicitudModel DatoPersonalSbs;
    public FragmentoVisorSbs( ) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragmento_visor_sbs,null);
        lbl_Mes0=(TextView)view.findViewById(R.id.lbl_Mes0);
        lbl_Mes1=(TextView)view.findViewById(R.id.lbl_Mes1);
        lbl_Mes2=(TextView)view.findViewById(R.id.lbl_Mes2);
        lbl_Mes3=(TextView)view.findViewById(R.id.lbl_Mes3);
        lbl_Mes4=(TextView)view.findViewById(R.id.lbl_Mes4);

        txt_Calificacion=(EditText)view.findViewById(R.id.txt_Calificacion);
        txt_saldo=(EditText)view.findViewById(R.id.txt_saldo);
        txt_CantEntidades=(EditText)view.findViewById(R.id.txt_CantEntidades);
        txt_Resutlado=(EditText)view.findViewById(R.id.txt_Resutlado);

        btn_Aceptar=(Button) view.findViewById(R.id.btn_Aceptar);

        lbl_Mes0.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCalif_0()));
        lbl_Mes1.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCalif_1()));
        lbl_Mes2.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCalif_2()));
        lbl_Mes3.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCalif_3()));
        lbl_Mes4.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCalif_4()));

        txt_Calificacion.setText(DatoPersonalSbs.getUltimoRcc().getCalif());
        txt_saldo.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getnMonto()));
        txt_CantEntidades.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getCan_Ents()));
        txt_Resutlado.setText(String.valueOf(DatoPersonalSbs.getUltimoRcc().getResult()));

        txt_Calificacion.setFocusable(false);
        txt_saldo.setFocusable(false);
        txt_CantEntidades.setFocusable(false);
        txt_Resutlado.setFocusable(false);

        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dismiss();
            }
        });
        return view;

    }
    public void Datos(DatoPersonaSolicitudModel Datos)
    {
        DatoPersonalSbs=Datos;
    }

}
