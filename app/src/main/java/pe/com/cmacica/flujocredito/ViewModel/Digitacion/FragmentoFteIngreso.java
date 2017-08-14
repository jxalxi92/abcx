package pe.com.cmacica.flujocredito.ViewModel.Digitacion;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import pe.com.cmacica.flujocredito.Model.Digitacion.DigitacionDto;
import pe.com.cmacica.flujocredito.R;
import pe.com.cmacica.flujocredito.Utilitarios.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoFteIngreso extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    private String TipoFuente;

    static DigitacionDto DatosBaseCliente;
    //private String IdPersona;
    //private String NombrePersona;

    public static FragmentoFteIngreso createInstance(int TipoFteIgr,DigitacionDto datosBaseCliente ){
        FragmentoFteIngreso obj = new FragmentoFteIngreso();

        Bundle bundle = new Bundle();
        bundle.putString(Constantes.KeyTipoFuenteIngreso, String.valueOf(TipoFteIgr));
        DatosBaseCliente= datosBaseCliente;
        obj.setArguments( bundle);

        return obj;
    }

    public FragmentoFteIngreso() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_paginado,container,false);

        if(savedInstanceState == null){

            insertarTabs(container);
            TipoFuente = getArguments().getString(Constantes.KeyTipoFuenteIngreso);
            //IdPersona = getArguments().getString(Constantes.KeyIdPersona);
            //NombrePersona = getArguments().getString("123456");
            viewPager = (ViewPager) view.findViewById(R.id.pager);

           // OnObtenerFuenteIngreso ();
            //if (ResultResponse){
                if(TipoFuente.equals("1")){

                    poblarViewPagerFteDep(viewPager);
                }
                else{
                    poblarViewPagerFteInd(viewPager);
                }
                pestanas.setupWithViewPager(viewPager);
            //}
        }

        return view;
    }

    //endregion

    private void insertarTabs(ViewGroup container) {

        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.appbar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(pestanas);
    }

    private void poblarViewPagerFteDep(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());

        adapter.addFragment(new FragmentoDatosClienteFteIgrDep().newInstance(DatosBaseCliente), getString(R.string.tap_FteDatosCliente));
        adapter.addFragment(new FragmentoIgrEgrSolesDep().newInstance(DatosBaseCliente.IdDigitacion), getString(R.string.tap_FteIgrEgr));
        adapter.addFragment(new FragmentoComentarioFteIngreso().newInstance(DatosBaseCliente.IdDigitacion), getString(R.string.tap_FteComentario));

        viewPager.setAdapter(adapter);
    }

    private void poblarViewPagerFteInd(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());

        adapter.addFragment(new FragmentoDatosClienteFteIgrIndp().newInstance(DatosBaseCliente), getString(R.string.tap_FteDatosCliente));
        adapter.addFragment(new FragmentoBalanceFteIgr().newInstance(DatosBaseCliente.IdDigitacion), getString(R.string.tap_FteIgrBalance));
        adapter.addFragment(new FragmentoComentarioFteIndep().newInstance(DatosBaseCliente.IdDigitacion), getString(R.string.tap_FteComentario));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {

            fragmentos.add(fragment);

            titulosFragmentos.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }

}
