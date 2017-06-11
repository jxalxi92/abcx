package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.InfoLinCredModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by JHON on 5/10/2016.
 */

public class AdaptadorLinCred extends RecyclerView.Adapter<AdaptadorLinCred.ViewHolder>{

    private List<InfoLinCredModel> Lista ;

    public AdaptadorLinCred(List<InfoLinCredModel> lista){
        Lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblInstituciones;
        public TextView lblLineaAprop;
        public TextView lblLineaNoUti;
        public TextView lblLineaUti;
        public TextView lblLinePorcenUti;

        public ViewHolder(View v) {
            super(v);

            lblInstituciones = (TextView) v.findViewById(R.id.lblInstituciones);
            lblLineaAprop = (TextView) v.findViewById(R.id.lblLineaAprop);
            lblLineaNoUti = (TextView) v.findViewById(R.id.lblLineaNoUti);
            lblLineaUti = (TextView) v.findViewById(R.id.lblLineaUti);
            lblLinePorcenUti = (TextView) v.findViewById(R.id.lblLinePorcenUti);
        }
    }

    @Override
    public AdaptadorLinCred.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_linea_credito,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorLinCred.ViewHolder holder, int position) {
        holder.lblInstituciones.setText(Lista.get(position).getCnsEntNomRazLN().toString());
        holder.lblLineaAprop.setText(Lista.get(position).getLinCred().toString().toString());
        holder.lblLineaNoUti.setText(Lista.get(position).getLinNoUtil().toString());
        holder.lblLineaUti.setText(Lista.get(position).getLinUtil().toString());
        holder.lblLinePorcenUti.setText(Lista.get(position).getPorLinUti().toString());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
