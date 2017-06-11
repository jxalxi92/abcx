package pe.com.cmacica.flujocredito.ViewModel.Calificacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pe.com.cmacica.flujocredito.R;

public class ActividadHistPlanPago extends AppCompatActivity {

    private static final String TAG = ActividadHistPlanPago.class.getSimpleName();


    String cCtaCod;

    public static void createInstance(Activity activity,String cCtaCod ) {

        Intent intent = getLaunchIntent(activity,cCtaCod );
        activity.startActivity(intent);
    }


    public static Intent getLaunchIntent(Context context ,String cCtaCod ) {
        Intent intent = new Intent(context, ActividadHistPlanPago.class);
        intent.putExtra("1000",cCtaCod);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_hist_plan_pago);
        // Retener instancia
        if (getIntent().getStringExtra("1000") != null)
            cCtaCod = getIntent().getStringExtra("1000");

        //agregarToolbar();
        if (savedInstanceState == null) {
            crearFullScreenDialog();
        }
    }
    private void crearFullScreenDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentoPlanPagoHist newFragment = new FragmentoPlanPagoHist();
        newFragment.CrearInstancia(cCtaCod,this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment, "FullScreenFragment").commit();
    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
