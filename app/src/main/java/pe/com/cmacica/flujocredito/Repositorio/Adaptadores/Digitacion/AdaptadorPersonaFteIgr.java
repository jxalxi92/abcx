package pe.com.cmacica.flujocredito.Repositorio.Adaptadores.Digitacion;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.cmacica.flujocredito.Base.ItemClickListener;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Mapeo.ContratoDbCmacIca;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;
import pe.com.cmacica.flujocredito.ViewModel.Digitacion.FragmentoPersonaFteIgrDet;

/**
 * Created by jhcc on 08/08/2016.
 */
public class AdaptadorPersonaFteIgr extends RecyclerView.Adapter<AdaptadorPersonaFteIgr.ViewHolder>
        implements ItemClickListener {


    //List<DigitacionDto> ListaSolSugerir;
    private Cursor items;
    //public Context contexto;
    private FragmentManager fragmentManager;


    public AdaptadorPersonaFteIgr(FragmentManager fragmentManager){
        //this.contexto = contexto;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onItemClick(View view, int position) {

        try{
            FragmentoPersonaFteIgrDet obj = new FragmentoPersonaFteIgrDet();
            //Log.d("probando click:"+ items.getCount() ,UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.cCodSolicitud));
            items.moveToPosition(position);
            obj.CrearInstancia(UConsultas.ConverCursoToDigitacionDto(items));
            obj.show(fragmentManager,"TAG_DET_PERSONA");
        }catch (Exception e){
            Log.d("Error click:"+ items.getCount() +UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.cCodSolicitud),e.getMessage());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView lblNombre;
        public TextView lbldoi;
        public TextView lblEstadoOpe;
        public TextView lblTipoCredito;
        public TextView lblProducto;
        public TextView lblTipoMoneda;
        public TextView lblMonto;

        public ItemClickListener listener;

        public ViewHolder(View v ,ItemClickListener listener) {
            super(v);
            lblNombre = (TextView) v.findViewById(R.id.lblNombrePersFteIgr);
            lbldoi = (TextView) v.findViewById(R.id.lblDoiPersFteIgr);
            lblEstadoOpe = (TextView) v.findViewById(R.id.lblEstadoPersFteIgr);
            lblTipoCredito = (TextView) v.findViewById(R.id.lblTipoCredito) ;
            lblProducto = (TextView) v.findViewById(R.id.lblProducto);
            lblTipoMoneda = (TextView) v.findViewById(R.id.lblMoneda);
            lblMonto = (TextView) v.findViewById(R.id.lblMontoSol) ;
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public AdaptadorPersonaFteIgr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.elemento_persona_fteingreso,parent,false);
        return new ViewHolder(vista,this);
    }

    @Override
    public void onBindViewHolder(AdaptadorPersonaFteIgr.ViewHolder holder, int position) {

        items.moveToPosition(position);

        String Nombre,Doi,Monto,TipoMoneda,TipoCredito,Producto = "";
        Nombre = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.NombrePersona);
        Doi = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.NumeroDocumento);
        Monto = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.nMonto);
        TipoMoneda = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.EquivalenteMoneda);
        TipoCredito = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.cDescripcionTipoCredito);
        Producto = UConsultas.obtenerString(items, ContratoDbCmacIca.DigitacionTable.cDescripcionProductoCredito);

        holder.lbldoi.setText(Doi);
        holder.lblNombre.setText(Nombre);
        holder.lblProducto.setText(Producto);
        holder.lblTipoMoneda.setText(TipoMoneda);
        holder.lblMonto.setText(Monto);
        holder.lblTipoCredito.setText(TipoCredito);
        String EstadoCod= "1"; // ListaSolSugerir.get(position).getnEstadoOpe().toString();
        String EstadoDesc = "";

        switch(EstadoCod ){
            case "1":
                EstadoDesc = "Nuevo";
                break;
            case "2":
                EstadoDesc = "Pendiente";
                break;
            case "3":
                EstadoDesc = "Completo";
                break;
        }
        holder.lblEstadoOpe.setText(EstadoDesc);

    }

    @Override
    public int getItemCount() {
       // return ListaSolSugerir.size();
        if (items != null){
            return items.getCount();
        }
        return 0;

    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }
}
