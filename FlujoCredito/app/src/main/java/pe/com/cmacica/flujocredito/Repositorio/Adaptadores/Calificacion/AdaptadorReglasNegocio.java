package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.ReglasNegocioModel;
import pe.com.cmacica.flujocredito.R;


/**
 * Created by jhcc on 15/11/2016.
 */

public class AdaptadorReglasNegocio extends RecyclerView.Adapter<AdaptadorReglasNegocio.ViewHolder>{

    List<ReglasNegocioModel> Lista;

    public AdaptadorReglasNegocio(List<ReglasNegocioModel> lista){

        this.Lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblRegla;
        public TextView lblExcepcion;

        public ViewHolder(View v) {
            super(v);

            lblRegla = (TextView) v.findViewById(R.id.lblRegla);
            lblExcepcion = (TextView) v.findViewById(R.id.lblExcepcion);
        }

    }

    @Override
    public AdaptadorReglasNegocio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_reglas_negocio,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorReglasNegocio.ViewHolder holder, int position) {
        holder.lblExcepcion.setText(Lista.get(position).getcExcepcion());
        holder.lblRegla.setText(Lista.get(position).getcReglamento());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
