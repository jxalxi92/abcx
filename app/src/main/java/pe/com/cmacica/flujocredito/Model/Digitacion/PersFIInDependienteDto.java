package pe.com.cmacica.flujocredito.Model.Digitacion;

import java.util.List;

/**
 * Created by jhcc on 05/09/2016.
 */
public class PersFIInDependienteDto {

    public String IdDigitacion ;
    public String IdPersFteIngreso ;
    public String cNumFuente ;
    public String dPersEval ;
    public String nPersFIActivoDisp ;
    public String nPersFICtasxCobrar ;
    public String nPersFIInventarios;
    public String nPersFIActivosFijos ;
    public String nPersFIProveedores ;
    public String nPersFICredCMACT ;
    public String nPersFICredOtros ;
    public String nPersFIVentas ;
    public String nPersFIPatrimonio ;
    public String nPersFICostoVentas ;
    public String nPersFIRecupCtasXCobrar ;
    public String nPersFIEgresosOtros ;
    public String nPersIngFam ;
    public String nPersEgrFam ;
    public String cPersFIMoneda ;
    public String nPersPagoCuotas ;
    public String nPasivoNoCorriente ;
    public String nCodSectorEcon ;
    public String cComentario1 ;
    public String cComentario2 ;
    public String cComentario3 ;

    public String dVersion  ;
    public String nEstadoOpe ;

    public List<PersFIGastoDetDto> Disponible;
    public List<PersFIGastoDetDto> Inventario;
    public List<PersFIGastoDetDto> ActivoFijo;
    public List<PersFIGastoDetDto> GastoOpeAdm;
    public List<PersFIGastoDetDto> IgrFueraNeg;
    public List<PersFIGastoDetDto> GastoFamiliares;
    public List<PersFIGastoDetDto> PasivoNoCorriente;

}
