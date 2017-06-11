package pe.com.cmacica.flujocredito.Model.Solicitud;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.cmacica.flujocredito.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_VisorSbs extends Fragment {


    public fragment_VisorSbs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visor_sbs, container, false);
    }

}
