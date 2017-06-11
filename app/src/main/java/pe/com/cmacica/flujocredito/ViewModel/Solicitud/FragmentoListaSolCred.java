package pe.com.cmacica.flujocredito.ViewModel.Solicitud;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.ViewModel.ActividadPrincipal;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoListaSolCred extends Fragment {


    FloatingActionButton btnNuevoSolCred;
    public FragmentoListaSolCred() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragmento_lista_sol_cred, container, false);

        btnNuevoSolCred = (FloatingActionButton) vista.findViewById(R.id.btnNuevoSolCred);

        btnNuevoSolCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FragmentoListaSolCred.this.getActivity(),ActividadMantSolCred.class);
                FragmentoListaSolCred.this.getActivity().startActivity(intent);
            }
        });
        return vista;
    }

}
