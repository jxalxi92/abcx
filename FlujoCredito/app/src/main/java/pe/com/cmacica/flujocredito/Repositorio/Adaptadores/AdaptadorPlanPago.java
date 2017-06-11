package pe.com.cmacica.flujocredito.Repositorio.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.PlanPagoModel;
import pe.com.cmacica.flujocredito.R;

/**
 * Created by jhcc on 05/07/2016.
 */
public class AdaptadorPlanPago extends RecyclerView.Adapter<AdaptadorPlanPago.ViewHolder>{


    private List<PlanPagoModel> planPago;
    public Context contexto;

    public AdaptadorPlanPago(Context contexto,List<PlanPagoModel> planPago){

        this.contexto = contexto;
        this.planPago = planPago;
    }

    @Override
    public AdaptadorPlanPago.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_calendario,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorPlanPago.ViewHolder holder, int position) {

        holder.lblNroCuota.setText(planPago.get(position).getNroCuota());
        holder.lblFechaPago.setText(planPago.get(position).getFechaPago());
        holder.lblCapital.setText(planPago.get(position).getCapital());
        holder.lblInteres.setText(planPago.get(position).getInteres());
        //holder.lblSegDesgravament.setText(planPago.get(position).getDesgravamen());
        holder.lblSaldoCapital.setText(planPago.get(position).getSalCap());
        holder.lblMontoCuota.setText(planPago.get(position).getCuota());
    }

    @Override
    public int getItemCount() {
        return planPago.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblNroCuota;
        public TextView lblFechaPago;
        public TextView lblCapital;
        public TextView lblInteres;
      //  public TextView lblSegMultiriesgo;
        public TextView lblMontoCuota;
        public TextView lblSegDesgravament;
        public TextView lblSaldoCapital;

        public ViewHolder(View v) {
            super(v);
            lblNroCuota = (TextView) v.findViewById(R.id.lblNroCuota);
            lblFechaPago = (TextView) v.findViewById(R.id.lblFechaPago);
            lblCapital = (TextView) v.findViewById(R.id.lblCapital);
            lblInteres = (TextView) v.findViewById(R.id.lblInteres);
           // lblSegMultiriesgo = (TextView) v.findViewById(R.id.lblSegMultiriesgo);
           // lblSegDesgravament = (TextView) v.findViewById(R.id.lblSegDesgravament);
            lblSaldoCapital = (TextView) v.findViewById(R.id.lblSaldoCapital);
            lblMontoCuota = (TextView) v.findViewById(R.id.lblMontoCuota);
        }
    }


}
