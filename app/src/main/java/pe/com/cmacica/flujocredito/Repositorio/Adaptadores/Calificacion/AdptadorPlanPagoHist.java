package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.HistPlanPagoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;

/**
 * Created by jhcc on 15/11/2016.
 */

public class AdptadorPlanPagoHist extends RecyclerView.Adapter<AdptadorPlanPagoHist.ViewHolder>{

    List<HistPlanPagoModel> Lista;
    View vista;
    Context contexto;

    public AdptadorPlanPagoHist( List<HistPlanPagoModel> lista,Context contexto){
        Lista = lista;
        this.contexto = contexto;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lblNroCuota;
        public TextView lblFechaVenc;
        public TextView lblFechaPago;
        public TextView lblMontoCuota;
        public TextView lblMontoCuotaPag;
        public TextView lblDiasAtraso;
       // public TextView lblEstado;
       // private View circleView;

        public ViewHolder(View v) {
            super(v);

            lblNroCuota = (TextView) v.findViewById(R.id.lblNroCuota);
            lblFechaVenc = (TextView) v.findViewById(R.id.lblFechaVenc);
            lblFechaPago = (TextView) v.findViewById(R.id.lblFechaPago);
            lblMontoCuota = (TextView) v.findViewById(R.id.lblMontoCuota);
            lblMontoCuotaPag = (TextView) v.findViewById(R.id.lblMontoCuotaPag);
            //lblEstado = (TextView) v.findViewById(R.id.lblEstado);
            lblDiasAtraso = (TextView)v.findViewById(R.id.lblDiasAtraso);

           // circleView = v.findViewById(R.id.circleView);
        }

    }


    @Override
    public AdptadorPlanPagoHist.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_hist_planpago,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdptadorPlanPagoHist.ViewHolder holder, int position) {
        holder.lblNroCuota.setText(String.valueOf( Lista.get(position).getnCuota()));
        String FechaVen =  Lista.get(position).getdVenc().substring(0,10).toString();
        holder.lblFechaVenc.setText( FechaVen );

       /* int ndiasAtraso =0;
        if (!Lista.get(position).getdPago().equals("null")){
            ndiasAtraso = UGeneral.DaysEntreFechas(Lista.get(position).getdPago().substring(0,10).toString(),Lista.get(position).getdVenc().substring(0,10).toString());
        }
        */
        holder.lblDiasAtraso.setText(Lista.get(position).getnDiasAtraso());
        if(Lista.get(position).getdPago()!= null){
            if(Lista.get(position).getdPago().length()>=10) {
                String FechaPago = Lista.get(position).getdPago().substring(0, 10).toString();
                holder.lblFechaPago.setText(FechaPago);
            }
        }else {
            holder.lblFechaPago.setText("");
        }
        holder.lblMontoCuota.setText(String.valueOf(Lista.get(position).getCuota()));
        holder.lblMontoCuotaPag.setText(String.valueOf(Lista.get(position).getCuotaPag()));

        //holder.lblEstado.setText(Lista.get(position).getcColocCalendEstadoDes());
        //int nEstado =Lista.get(position).getEstadoCuota();
        /*if (Lista.get(position).getHex() != null) {
            GradientDrawable gradientDrawable = (GradientDrawable) holder.circleView.getBackground();
            int colorId = android.graphics.Color.parseColor(Lista.get(position).getHex());
            gradientDrawable.setColor(colorId);
        }*/
        /*switch (nEstado){
            case 1:

                gradientDrawable.setColor(contexto.getResources().getColor( R.color.ColorHistPlanPagoAtra));

                //vista.setBackgroundColor( contexto.getResources().getColor( R.color.ColorHistPlanPagoAtra));
                break;
            case 2:

                gradientDrawable.setColor(contexto.getResources().getColor( R.color.ColorHistPlanPagoPag));
               // vista.setBackgroundColor(contexto.getResources().getColor( R.color.ColorHistPlanPagoPag));
                break;
            case 3:
                gradientDrawable.setColor(contexto.getResources().getColor( R.color.ColorHistPlanPagoVig));

                //vista.setBackgroundColor(contexto.getResources().getColor( R.color.ColorHistPlanPagoVig));
                break;
        }*/

    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
