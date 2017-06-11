package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.InfoEstandarModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by JHON on 5/10/2016.
 */

public class AdaptadorInfoEstandar extends RecyclerView.Adapter<AdaptadorInfoEstandar.ViewHolder>{


    private List<InfoEstandarModel> Lista ;

    public AdaptadorInfoEstandar(List<InfoEstandarModel> lista){
        Lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblnombre;
        public TextView lblNrodoc;
        public TextView lblFechaProc;
        public TextView lblCalifSBS;
        //public TextView lblCalifCmac;
        public TextView lblDeudaSf;
        public TextView lblNroBco;
        public TextView lblRiesgo;


        public ViewHolder(View v) {
            super(v);

            lblnombre = (TextView) v.findViewById(R.id.lblnombre);
            lblNrodoc = (TextView) v.findViewById(R.id.lblNrodoc);
            lblFechaProc = (TextView) v.findViewById(R.id.lblFechaProc);
            //lblCalifSBS = (TextView) v.findViewById(R.id.lblCalifSBS);
            //lblCalifCmac = (TextView) v.findViewById(R.id.lblCalifCmac);
            lblDeudaSf = (TextView) v.findViewById(R.id.lblDeudaSf);
            lblNroBco = (TextView) v.findViewById(R.id.lblNroBco);
            lblRiesgo = (TextView) v.findViewById(R.id.lblRiesgo);

        }


    }

    @Override
    public AdaptadorInfoEstandar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_info_estandar,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorInfoEstandar.ViewHolder holder, int position) {

        holder.lblnombre.setText(Lista.get(position).getRazonSocial().toString());
        holder.lblNrodoc.setText(Lista.get(position).getTipoDocumento().toString()+"/"+Lista.get(position).getDocumento().toString());
        holder.lblFechaProc.setText(Lista.get(position).getFechaProceso().toString());
        //holder.lblCalifSBS.setText(Lista.get(position).getCalificativo().toString());
        holder.lblDeudaSf.setText(Lista.get(position).getDeudaTotal().toString());
        holder.lblNroBco.setText(Lista.get(position).getNroBancos().toString());
        holder.lblRiesgo.setText(Lista.get(position).getTotalRiesgo().toString());

    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
