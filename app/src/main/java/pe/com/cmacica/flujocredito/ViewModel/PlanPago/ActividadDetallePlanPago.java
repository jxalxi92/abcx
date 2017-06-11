package pe.com.cmacica.flujocredito.ViewModel.PlanPago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.PlanPagoModel;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Repositorio.Adaptadores.AdaptadorPlanPago;
import pe.com.cmacica.flujocredito.Utilitarios.DecoracionLineaDivisoria;
import pe.com.cmacica.flujocredito.Utilitarios.UConsultas;

public class ActividadDetallePlanPago extends AppCompatActivity {

    private static final String EXTRA_NAME = "pe.com.cmacica.flujocredito.name";
    private static final String EXTRA_DRAWABLE = "pe.com.cmacica.flujocredito.drawable";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorPlanPago adaptador;

    private static List<PlanPagoModel> ListaPlanPago;
    private static String TipoCredito, Producto, Monto,Moneda,NroCuota,FrecPago,FechaDesem, Gracia,  MontoTotalPagar;
    private static float TEM;

    public static void createInstance(Activity activity, List<PlanPagoModel> listaPlanPago, String tipoCredito, String producto,
                                      String monto, String moneda, String nroCuota,String frecPago, String fechaDesem,String gracia,
                                      String montoTotalPagar,float pTEM ) {
        ListaPlanPago = listaPlanPago;
        TipoCredito = tipoCredito;
        Producto = producto;
        Monto =monto;
        Moneda = moneda;
        NroCuota = nroCuota;
        FrecPago = frecPago;
        FechaDesem= fechaDesem;
        Gracia = gracia;
        MontoTotalPagar = montoTotalPagar;
        TEM = pTEM;

        Intent intent = getLaunchIntent(activity, listaPlanPago,TipoCredito, Producto,Monto,Moneda,NroCuota,FrecPago,FechaDesem,Gracia,MontoTotalPagar);
        activity.startActivity(intent);
    }


    public static Intent getLaunchIntent(Context context, List<PlanPagoModel> ListaPlanPago, String TipoCredito, String Produco,
                                         String Monto, String Moneda, String NroCuota, String FrecPago, String FechaDesem,String Gracia,
                                         String MontoTotalPagar) {
        Intent intent = new Intent(context, ActividadDetallePlanPago.class);
        intent.putExtra(EXTRA_NAME, "");
        // intent.putExtra(EXTRA_DRAWABLE, girl.getIdDrawable());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle_plan_pago);

        recyclerView = (RecyclerView) findViewById(R.id.rcvPlanPago);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.collapser);
        //collapser.setTitle("Nro Cuota | Fecha | Cuota | Capital | interés | Seg Deg | Sal Cap"); //Cambiando el nombre del Titulo
        collapser.setTitle("Plan de pago");


        TextView lblTipoCredito = (TextView) findViewById(R.id.lblTipoCredito);
        lblTipoCredito.setText(TipoCredito);
        TextView lblProducto = (TextView) findViewById(R.id.lblProducto);
        lblProducto.setText(Producto);
        TextView lblMonto = (TextView) findViewById(R.id.lblMonto);
        lblMonto.setText(Monto);
        TextView lblMoneda = (TextView) findViewById(R.id.lblMoneda);
        lblMoneda.setText(Moneda);
        TextView lblNroCuota = (TextView) findViewById(R.id.lblNroCuota);
        lblNroCuota.setText(NroCuota);
        TextView lblFrecPago = (TextView) findViewById(R.id.lblFrecPago);
        lblFrecPago.setText(FrecPago);
        TextView lblFechaDesm = (TextView) findViewById(R.id.lblFechaDesembolso);
        lblFechaDesm.setText(FechaDesem);
        TextView lblGracia = (TextView) findViewById(R.id.lblGracia);
        lblGracia.setText(Gracia);
        TextView lblMontoTotalPagar = (TextView) findViewById(R.id.lblTotalPagar);
        lblMontoTotalPagar.setText(MontoTotalPagar);

        TextView lblTEM = (TextView) findViewById(R.id.lblTEM);
        lblTEM.setText(String.valueOf(TEM));
        TextView lblTEA = (TextView) findViewById(R.id.lblTEA);
        lblTEA.setText(String.valueOf(UConsultas.ConvierteTEMaTEA(TEM)));

        adaptador = new AdaptadorPlanPago(this, ListaPlanPago);
        recyclerView.setAdapter(adaptador);
        recyclerView.addItemDecoration(new DecoracionLineaDivisoria(this));

        // Setear escucha al FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Protegemos la ubicación actual antes del cambio de configuración
        super.onSaveInstanceState(outState);
    }
}
