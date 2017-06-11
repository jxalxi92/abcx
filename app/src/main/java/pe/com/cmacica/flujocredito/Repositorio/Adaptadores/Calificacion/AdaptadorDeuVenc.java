package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.InfoDeuVencModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by JHON on 5/10/2016.
 */

public class AdaptadorDeuVenc extends RecyclerView.Adapter<AdaptadorDeuVenc.ViewHolder>{


    private List<InfoDeuVencModel> Lista ;
    public AdaptadorDeuVenc(List<InfoDeuVencModel> lista){
        Lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblEntidad;
        public TextView lblFuente;
        public TextView lblMonto;
        public TextView lbldiasvencidos;


        public ViewHolder(View v) {
            super(v);

            lblEntidad = (TextView) v.findViewById(R.id.lblEntidad);
            lblFuente = (TextView) v.findViewById(R.id.lblFuente);
            lblMonto = (TextView) v.findViewById(R.id.lblMonto);
            lbldiasvencidos = (TextView) v.findViewById(R.id.lbldiasvencidos);
        }
    }

    @Override
    public AdaptadorDeuVenc.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_info_deuvenc,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorDeuVenc.ViewHolder holder, int position) {
        holder.lblEntidad.setText(Lista.get(position).getEntidad().toString());
        holder.lblFuente.setText(Lista.get(position).getFuente().toString());
        holder.lblMonto.setText(Lista.get(position).getMonto().toString());
        holder.lbldiasvencidos.setText(Lista.get(position).getDiasVencidos().toString());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
