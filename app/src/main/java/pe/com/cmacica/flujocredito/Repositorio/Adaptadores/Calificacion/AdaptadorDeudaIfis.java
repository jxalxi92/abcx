package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.DetCalifIfiModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by JHON on 30/06/2016.
 */
public class AdaptadorDeudaIfis extends RecyclerView.Adapter<AdaptadorDeudaIfis.DeudaIfiViewHolder> {

    private List<DetCalifIfiModel> ListaDeuda;
    public Context context;

    public AdaptadorDeudaIfis(Context context, List<DetCalifIfiModel> ListaDeuda){

        this.ListaDeuda=ListaDeuda;
        this.context =context;
    }

    @Override
    public int getItemCount() {
        return ListaDeuda.size();
    }

    @Override
    public DeudaIfiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_deuda_ifis,parent,false);
        return new DeudaIfiViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(DeudaIfiViewHolder holder, int position) {

        holder.SaldoIfi.setText(ListaDeuda.get(position).getSaldoCapital().toString());
        holder.NombreIfi.setText(ListaDeuda.get(position).getNombreIfi().toString());
        holder.nDiasAtraso.setText(ListaDeuda.get(position).getnDiasAtraso());
    }

    public static class DeudaIfiViewHolder extends RecyclerView.ViewHolder {

        public TextView NombreIfi;
        public TextView SaldoIfi;
        public TextView nDiasAtraso;

        public DeudaIfiViewHolder(View v) {
            super(v);
            NombreIfi = (TextView) v.findViewById(R.id.lblNomIfi);
            SaldoIfi = (TextView) v.findViewById(R.id.lblDeudaIfi);
            nDiasAtraso = (TextView) v.findViewById(R.id.lblnDiasAtraso);
        }
    }
}
