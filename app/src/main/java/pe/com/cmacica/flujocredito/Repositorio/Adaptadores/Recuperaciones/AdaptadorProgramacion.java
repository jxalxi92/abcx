package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones;


import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.Dialogos.DateDialog;

/**
 * Created by faqf on 11/08/2017.
 */

public class AdaptadorProgramacion extends RecyclerView.Adapter<AdaptadorProgramacion.ViewHolder>
        implements ItemClickListener {

    private List<ClienteRecuperacionModel> ListaClienteRecuperaciones;
    public Context contexto;



    public AdaptadorProgramacion(Context contexto, List<ClienteRecuperacionModel> ClienteRecuperaciones) {

        this.contexto = contexto;
        this.ListaClienteRecuperaciones = ClienteRecuperaciones;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView lblDocumento;
        public TextView lblNombre;
        public TextView lblDireccion;
        public TextView lblFecha;
        public CheckBox chck_Seleccionado;
        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);

            lblDocumento = (TextView) v.findViewById(R.id.lblDocumento);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
            chck_Seleccionado = (CheckBox) v.findViewById(R.id.chck_Seleccionado);
            lblFecha = (TextView) v.findViewById(R.id.lblFecha);
            this.listener = listener;
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());

        }
    }

    @Override
    public AdaptadorProgramacion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_programacion, parent, false);
        return new AdaptadorProgramacion.ViewHolder(vista, this);
    }

    @Override
    public void onBindViewHolder(AdaptadorProgramacion.ViewHolder holder, int position) {
        holder.lblDocumento.setText(ListaClienteRecuperaciones.get(position).getDocumento());
        holder.lblNombre.setText(ListaClienteRecuperaciones.get(position).getNombres());
        holder.lblDireccion.setText(ListaClienteRecuperaciones.get(position).getDireccion());

        holder.lblFecha.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fecha fec=new Fecha();
                        fec.MostrarFecha();
                        if (fec.Resulado !=null)
                        {
                            holder.lblFecha.setText(fec.Resulado);
                        }

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return ListaClienteRecuperaciones.size();
    }


}