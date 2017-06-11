package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.InfoSBSModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by JHON on 5/10/2016.
 */

public class AdaptadorInfoSBS extends RecyclerView.Adapter<AdaptadorInfoSBS.ViewHolder>{


    private List<InfoSBSModel> Lista ;

    public AdaptadorInfoSBS (List<InfoSBSModel> lista){
        Lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblNomRazSocEnt;
        public TextView lblCalificacion;
        public TextView lblMontoDeuda;
        public TextView lblFechaReporte;

        public ViewHolder(View v) {
            super(v);

            lblNomRazSocEnt = (TextView) v.findViewById(R.id.lblNomRazSocEnt);
            lblCalificacion = (TextView) v.findViewById(R.id.lblCalificacion);
            lblMontoDeuda = (TextView) v.findViewById(R.id.lblMontoDeuda);
            lblFechaReporte = (TextView) v.findViewById(R.id.lblFechaReporte);
        }

    }
    @Override
    public AdaptadorInfoSBS.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_info_sbs,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorInfoSBS.ViewHolder holder, int position) {

        holder.lblNomRazSocEnt.setText(Lista.get(position).getNomRazSocEnt().toString());
        holder.lblCalificacion.setText(Lista.get(position).getCalificacion().toString().toString());
        holder.lblMontoDeuda.setText(Lista.get(position).getMontoDeuda().toString());
        holder.lblFechaReporte.setText(Lista.get(position).getFechaReporte().toString());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
