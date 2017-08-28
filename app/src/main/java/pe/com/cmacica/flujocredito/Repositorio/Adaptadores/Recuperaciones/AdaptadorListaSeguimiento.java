package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.ViewModel.Cobranza.ActividadGestionCobranza;

/**
 * Created by faqf on 25/08/2017.
 */

public class AdaptadorListaSeguimiento extends RecyclerView.Adapter<AdaptadorListaSeguimiento.ViewHolder>
        implements ItemClickListener {
    private List<ClienteRecuperacionModel> ListaClienteSeguimiento;
    public Context contexto;
    public AdaptadorListaSeguimiento(Context contexto, List<ClienteRecuperacionModel> ClienteRecuperaciones) {

        this.contexto = contexto;
        this.ListaClienteSeguimiento = ClienteRecuperaciones;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lblDocumento;
        public TextView lblNombre;
        public TextView lblDireccion;
        public TextView lblFecha;
        public TextView lblNumero;

        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);
            lblDocumento = (TextView) v.findViewById(R.id.lblDocumento);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
            lblFecha = (TextView) v.findViewById(R.id.lblFecha);
            lblNumero=(TextView)v.findViewById(R.id.lblNumero);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
    @Override
    public AdaptadorListaSeguimiento.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_cliente_seguimiento, parent, false);
        return new AdaptadorListaSeguimiento.ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaSeguimiento.ViewHolder holder, int position) {
        holder.lblDocumento.setText(ListaClienteSeguimiento.get(position).getDocumento());
        holder.lblNombre.setText(ListaClienteSeguimiento.get(position).getNombres());
        holder.lblDireccion.setText(ListaClienteSeguimiento.get(position).getDireccion());
        holder.lblFecha.setText(ListaClienteSeguimiento.get(position).getFechaRec().substring(0,10));
        holder.lblNumero.setText(String.valueOf("Orden de Visita: "+ListaClienteSeguimiento.get(position).getPosicion()));
    }

    @Override
    public int getItemCount() {
        return  ListaClienteSeguimiento.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        ActividadGestionCobranza.createInstance(
                (Activity) contexto
                ,ListaClienteSeguimiento.get(position).getCodigo()
                ,ListaClienteSeguimiento.get(position).getDocumento());
    }
}
