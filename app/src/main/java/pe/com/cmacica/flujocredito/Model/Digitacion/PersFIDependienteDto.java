package pe.com.cmacica.flujocredito.Model.Digitacion;

import java.util.List;

/**
 * Created by jhcc on 08/08/2016.
 */
public class PersFIDependienteDto {


    public String IdDigitacion ;
    public String IdPersFteIngreso ;
    public String cNumFuente ;
    public String dPersEval ;
    public String nPersIngCli ;
    public String nPersIngCon ;
    public String nPersOtrIng ;
    public String nPersGastoFam ;
    public String dPersFICaduca ;
    public String nPersPagoCuotas ;
    public String cPersFIMoneda;
    public String cPersFICargo;
    public String cPersFIAreaTrabajo;
    public String nCodFrecPago;
    public String cComentario1 ;
    public String cComentario2 ;

    public String dVersion ;
    public String nEstadoOpe ;

    public List<PersFIGastoDetDto> Gastos;


}
