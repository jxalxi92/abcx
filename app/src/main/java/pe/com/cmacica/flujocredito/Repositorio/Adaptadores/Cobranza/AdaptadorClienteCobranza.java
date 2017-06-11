package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Cobranza;

import android.app.Activity;
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
import pe.com.cmacica.flujocredito.ViewModel.Cobranza.ActividadGestionCobranza;

/**
 * Created by faqf on 05/06/2017.
 */

public class AdaptadorClienteCobranza extends RecyclerView.Adapter<AdaptadorClienteCobranza.ViewHolder>
        implements ItemClickListener {

    private List<ClienteCobranzaModel> ListaClienteCobranza;
    public Context contexto;

    public AdaptadorClienteCobranza(Context contexto,List<ClienteCobranzaModel> ClienteCobranza){

        this.contexto = contexto;
        this.ListaClienteCobranza = ClienteCobranza;
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
    public AdaptadorClienteCobranza.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_cliente_cobranza,parent,false);
        return new AdaptadorClienteCobranza.ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorClienteCobranza.ViewHolder holder, int position) {

        holder.lblDocumento.setText(ListaClienteCobranza.get(position).getDocumento());
        holder.lblNombre.setText(ListaClienteCobranza.get(position).getNombres());
        holder.lblDireccion.setText(ListaClienteCobranza.get(position).getDireccion());

    }

    @Override
    public int getItemCount() {
        return ListaClienteCobranza.size();
    }

    @Override
    public void onItemClick(View view, int position) {

        ActividadGestionCobranza.createInstance(
                (Activity) contexto
                ,ListaClienteCobranza.get(position).getCodigo()
                ,ListaClienteCobranza.get(position).getDocumento());
    }

}
