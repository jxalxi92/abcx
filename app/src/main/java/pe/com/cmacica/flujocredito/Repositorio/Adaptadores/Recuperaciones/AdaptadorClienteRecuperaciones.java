package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Cobranza.ClienteCobranzaModel;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.ViewModel.Recuperaciones.fragmentoListaRecuperaciones;


/**
 * Created by faqf on 05/07/2017.
 */

public class AdaptadorClienteRecuperaciones extends RecyclerView.Adapter<AdaptadorClienteRecuperaciones.ViewHolder>
        implements ItemClickListener{


    private List<ClienteRecuperacionModel> ListaClienteRecuperaciones;
    public Context contexto;

    public AdaptadorClienteRecuperaciones(Context contexto,List<ClienteRecuperacionModel> ClienteRecuperaciones){

        this.contexto = contexto;
        this.ListaClienteRecuperaciones = ClienteRecuperaciones;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lblDocumento;
        public TextView lblNombre;
        public TextView lblDireccion;
        public CheckBox chck_Seleccionado;
        public ItemClickListener listener;


        public ViewHolder(View v,ItemClickListener  listener) {
            super(v);

            lblDocumento = (TextView) v.findViewById(R.id.lblDocumento);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
            chck_Seleccionado=(CheckBox) v.findViewById(R.id.chck_Seleccionado);
            this.listener = listener;
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
           listener.onItemClick(v, getAdapterPosition());

        }
    }
    @Override
    public AdaptadorClienteRecuperaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_cliente_recuperaciones,parent,false);
        return new AdaptadorClienteRecuperaciones.ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorClienteRecuperaciones.ViewHolder holder, int position) {
        holder.lblDocumento.setText(ListaClienteRecuperaciones.get(position).getDocumento());
        holder.lblNombre.setText(ListaClienteRecuperaciones.get(position).getNombres());
        holder.lblDireccion.setText(ListaClienteRecuperaciones.get(position).getDireccion());
        holder.chck_Seleccionado.setOnCheckedChangeListener(null);
        holder.chck_Seleccionado.setChecked(ListaClienteRecuperaciones.get(position).isSeleccionado());

        holder.chck_Seleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {

          fragmentoListaRecuperaciones frag=new fragmentoListaRecuperaciones();
            for (ClienteRecuperacionModel CliE : frag.ListClientes)
            {
                if(CliE.getDocumento()==ListaClienteRecuperaciones.get(position).getDocumento())
                {
                    CliE.setSeleccionado(isChecked);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
       return ListaClienteRecuperaciones.size();
    }


}
