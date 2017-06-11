package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Calificacion;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Calificacion.DetCalifSbsModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.ViewModel.Calificacion.fragmentoCalifDeudaIfis;

/**
 * Created by JHON on 27/06/2016.
 */
public class AdaptadorCalifSbs extends RecyclerView.Adapter<AdaptadorCalifSbs.CalifViewHolder>
        implements ItemClickListener {


    private List<DetCalifSbsModel> ListaCalif;
    private Context context;
    private String CodSbs;
    private FragmentManager fragmentManager;


    public AdaptadorCalifSbs(Context context,List<DetCalifSbsModel> ListaCalif, FragmentManager fragmentManager,String CodSbs){
        this.context = context;
        this.ListaCalif = ListaCalif;
        this.fragmentManager = fragmentManager;
        this.CodSbs = CodSbs;
    }

    @Override
    public AdaptadorCalifSbs.CalifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_calif,parent,false);
        return new CalifViewHolder(vista,this);
    }

    public static class CalifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView Calif0;
        public TextView Calif1;
        public TextView Calif2;
        public TextView Calif3;
        public TextView Calif4;
        public TextView Fecha;
        public ItemClickListener listener;


        public CalifViewHolder(View v,ItemClickListener listener) {
            super(v);

            Calif0 = (TextView) v.findViewById(R.id.lblCalif0);
            Calif1 = (TextView) v.findViewById(R.id.lblCalif1);
            Calif2 = (TextView) v.findViewById(R.id.lblCalif2);
            Calif3 = (TextView) v.findViewById(R.id.lblCalif3);
            Calif4 = (TextView) v.findViewById(R.id.lblCalif4);
            Fecha = (TextView) v.findViewById(R.id.lblFechaDet);
            this.listener = listener;
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorCalifSbs.CalifViewHolder holder, int position) {
        holder.Calif0.setText("0: "+ ListaCalif.get(position).getCalif0().toString());
        holder.Calif1.setText("1: "+ ListaCalif.get(position).getCalif1().toString());
        holder.Calif2.setText("2: "+ ListaCalif.get(position).getCalif2().toString());
        holder.Calif3.setText("3: "+ ListaCalif.get(position).getCalif3().toString());
        holder.Calif4.setText("4: "+ ListaCalif.get(position).getCalif4().toString());
        holder.Fecha.setText(ListaCalif.get(position).getFecha().toString());
    }

    @Override
    public int getItemCount() {
        return ListaCalif.size();
    }

    @Override
    public void onItemClick(View view, int position) {

        fragmentoCalifDeudaIfis obj = new  fragmentoCalifDeudaIfis();
        obj.CrearInstancia(CodSbs, ListaCalif.get(position).getFecha().toString(),context);
        obj.show( fragmentManager,  "TAG_DET_IFIS12");

    }
}

