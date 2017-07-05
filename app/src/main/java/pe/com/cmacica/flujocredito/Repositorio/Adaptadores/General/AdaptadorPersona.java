package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.General;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.Model.General.PersonaDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.ViewModel.General.ActividadBusquedaPersona;

/**
 * Created by Jhcc on 04/10/2016.
 */

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.ViewHolder>
        implements ItemClickListener {

    private Cursor items;
    String IdPersFteIngreso;
    Context Cotexto;
    PersonaDto persel;

    public AdaptadorPersona(Context Cotexto , String IdPersFteIngreso){
        this.IdPersFteIngreso = IdPersFteIngreso;
        this.Cotexto = Cotexto;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView lblNombre;
        public TextView lbldoi;

        public ItemClickListener listener;

        public ViewHolder(View v ,ItemClickListener listener) {
            super(v);
            lblNombre = (TextView) v.findViewById(R.id.txtNombre);
            lbldoi = (TextView) v.findViewById(R.id.txtNumeroDoc);

            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public AdaptadorPersona.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_persona,parent,false);
        return new ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorPersona.ViewHolder holder, int position) {

        items.moveToPosition(position);
        String Nombre,Doi;
        Nombre = UConsultas.obtenerString(items, ContratoDbCmacIca.PersonaTable.cPersNombre);
        Doi = UConsultas.obtenerString(items, ContratoDbCmacIca.PersonaTable.cDoi);

        holder.lbldoi.setText(Doi);
        holder.lblNombre.setText(Nombre);
    }

    @Override
    public int getItemCount() {
        if(items != null){
            return items.getCount();
        }
        return 0;
    }
    public void swapCursor(Cursor nuevoCursor) {
       // if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
       // }
    }

    public Cursor getCursor() {
        return items;
    }

    @Override
    public void onItemClick(View view, int position) {

        try{

            items.moveToPosition(position);
            persel = UConsultas.ConverCursorToPersona(items);

            new AlertDialog.Builder(Cotexto)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Aviso")
                    .setMessage("¿Está seguro de cambiar la razón social?")
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActividadBusquedaPersona actividad = (ActividadBusquedaPersona) Cotexto;
                            actividad.finish();
                        }
                    })//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            ActividadBusquedaPersona actividad = (ActividadBusquedaPersona) Cotexto;
                            actividad.finish();

                            ContentValues valores2 = new ContentValues();
                            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cPersFIPersCod,persel.getcPersCod());
                            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cRazSocDescrip,persel.getcPersNombre());
                            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cRazSocDirecc,persel.getcDireccion());
                            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.cPersOcupacion,"");

                            valores2.put(ContratoDbCmacIca.PersFteIngresoTable.nEstadoOpe,"1");
                            new UConsultas.TareaUpdateFteIngreso(Cotexto.getContentResolver(),valores2,IdPersFteIngreso)
                                    .execute();
                        }
                    })
                    .show();

            //listener.OnActualizaPersona(persel,TipoFtente);

        }catch (Exception e){
            Log.d("Error click:",e.getMessage());
        }
    }
}
