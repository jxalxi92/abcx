package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Cobranza.ClienteCobranzaModel;
import pe.com.cmacica.flujocredito.R;


/**
 * Created by faqf on 05/07/2017.
 */

public class AdaptadorClienteRecuperaciones extends RecyclerView.Adapter<AdaptadorClienteRecuperaciones.ViewHolder>
        implements ItemClickListener{


    private List<ClienteCobranzaModel> ListaClienteRecuperaciones;
    public Context contexto;

    public AdaptadorClienteRecuperaciones(Context contexto,List<ClienteCobranzaModel> ClienteRecuperaciones){

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
        public ItemClickListener listener;

        public ViewHolder(View v,ItemClickListener listener) {
            super(v);

            lblDocumento = (TextView) v.findViewById(R.id.lblDocumento);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
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
    }

    @Override
    public int getItemCount() {
       return ListaClienteRecuperaciones.size();
    }
}
