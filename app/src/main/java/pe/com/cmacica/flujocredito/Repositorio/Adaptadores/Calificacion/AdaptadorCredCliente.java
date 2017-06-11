package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Calificacion.CredClienteModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.ViewModel.Calificacion.ActividadHistPlanPago;

/**
 * Created by jhcc on 15/11/2016.
 */

public class AdaptadorCredCliente extends RecyclerView.Adapter<AdaptadorCredCliente.ViewHolder>
        implements ItemClickListener {

    private List<CredClienteModel> Lista;
    private Context context;
    FragmentManager fragmentManager;

    public AdaptadorCredCliente(List<CredClienteModel> lista,Context context,FragmentManager fragmentManager){
        Lista= lista;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onItemClick(View view, int position) {


        ActividadHistPlanPago.createInstance((Activity) context,
                Lista.get(position).getNumeroCuenta());
        //obj.show(fragmentManager,"PlanPagoHist");

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lblNroCredito;
        public TextView lblProducto;
        public TextView lblEstado;
        public ItemClickListener listener;


        public ViewHolder(View v,ItemClickListener listener) {
            super(v);

            lblNroCredito = (TextView) v.findViewById(R.id.lblNroCredito);
            lblProducto = (TextView) v.findViewById(R.id.lblProducto);
            lblEstado = (TextView) v.findViewById(R.id.lblEstado);

            this.listener = listener;
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
    @Override
    public AdaptadorCredCliente.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_cred_cliente,parent,false);
        return new ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorCredCliente.ViewHolder holder, int position) {
        holder.lblNroCredito.setText(Lista.get(position).getNumeroCuenta());
        holder.lblProducto.setText(Lista.get(position).getProductoCuenta());
        holder.lblEstado.setText(Lista.get(position).getEstadoCuenta());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
