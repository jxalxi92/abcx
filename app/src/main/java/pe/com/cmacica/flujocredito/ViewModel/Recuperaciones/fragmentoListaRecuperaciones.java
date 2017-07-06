package pe.com.cmacica.flujocredito.ViewModel.Recuperaciones;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones.AdaptadorClienteRecuperaciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentoListaRecuperaciones extends Fragment {
    private static final String TAG = fragmentoListaRecuperaciones.class.getSimpleName();

    private ProgressDialog progressDialog;
    private Gson gson = new Gson();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorClienteRecuperaciones adaptador;

    public fragmentoListaRecuperaciones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragmento_lista_recuperaciones, container, false);

        recyclerView = (RecyclerView) vista.findViewById(R.id.rv_clienteCobranza);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
       return vista;

    }
}