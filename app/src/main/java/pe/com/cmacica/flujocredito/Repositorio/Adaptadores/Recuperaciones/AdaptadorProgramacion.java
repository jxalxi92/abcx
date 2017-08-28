package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Recuperaciones;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;
import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.Recuperaciones.ClienteRecuperacionModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.UGeneral;
import pe.com.cmacica.flujocredito.ViewModel.Recuperaciones.ActividadProgramacionRecuperaciones;

/**
 * Created by faqf on 11/08/2017.
 */

public class AdaptadorProgramacion extends RecyclerView.Adapter<AdaptadorProgramacion.ViewHolder>
        implements ItemClickListener {

    private List<ClienteRecuperacionModel> ListaClienteRecuperaciones;
    public Context contexto;
    int Contador=0;
    String date;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AdaptadorProgramacion.ViewHolder HolderFecha;

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
        public TextView lblNumero;
        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);

            lblDocumento = (TextView) v.findViewById(R.id.lblDocumento);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
            chck_Seleccionado = (CheckBox) v.findViewById(R.id.chck_Seleccionado);
            lblFecha = (TextView) v.findViewById(R.id.lblFecha);
            lblNumero=(TextView)v.findViewById(R.id.lblNumero);
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
        holder.chck_Seleccionado.setOnCheckedChangeListener(null);
        holder.lblFecha.setText(UGeneral.obtenerTiempoCorto());
        holder.chck_Seleccionado.setChecked(ListaClienteRecuperaciones.get(position).isSeleccionado());

        if (ListaClienteRecuperaciones.get(position).getPosicion()!="0")
        {
            holder.lblFecha.setText(ListaClienteRecuperaciones.get(position).getFechaRec());
            holder.lblNumero.setText(String.valueOf("Orden de Visita: "+ListaClienteRecuperaciones.get(position).getPosicion()));
            Contador=ListaClienteRecuperaciones.size();
        }

        holder.chck_Seleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {
         if (holder.chck_Seleccionado.isChecked())
         {
             Contador++;
             holder.lblNumero.setText(String.valueOf("Orden de Visita: "+Contador));
             ActividadProgramacionRecuperaciones Act=new ActividadProgramacionRecuperaciones();
             for (ClienteRecuperacionModel CliE : Act.ListaProgramados)
             {
                 if (CliE.getDocumento()==ListaClienteRecuperaciones.get(position).getDocumento())
                 {
                     CliE.setPosicion(String.valueOf(Contador));
                     CliE.setFechaRec(UGeneral.obtenerTiempoCorto());
                     CliE.setSeleccionado(true);
                 }
             }
         }
         else
         {
             holder.lblNumero.setText("");
             ActividadProgramacionRecuperaciones Act=new ActividadProgramacionRecuperaciones();
             for (ClienteRecuperacionModel CliE : Act.ListaProgramados)
             {
                 if (CliE.getDocumento()==ListaClienteRecuperaciones.get(position).getDocumento())
                 {   CliE.setSeleccionado(false);
                     CliE.setPosicion(null);
                 }
             }
         }
        });

        holder.lblFecha.setOnClickListener((View view)-> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    contexto,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            HolderFecha=holder;
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String cdia, cmes;

                if (day < 10) {
                    cdia = "0" + String.valueOf(day);
                } else {
                    cdia = String.valueOf(day);
                }
                if (month < 10) {
                    cmes = "0" + String.valueOf(month);
                } else {
                    cmes = String.valueOf(month);
                }
                date =year + "-" + cmes + "-" + cdia;
                HolderFecha.lblFecha.setText(date);
                ActividadProgramacionRecuperaciones Act=new ActividadProgramacionRecuperaciones();
                for (ClienteRecuperacionModel CliE : Act.ListaProgramados)
                {
                    if (CliE.getDocumento()==ListaClienteRecuperaciones.get(position).getDocumento())
                    {
                      CliE.setFechaRec(date);
                    }
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return ListaClienteRecuperaciones.size();
    }


}