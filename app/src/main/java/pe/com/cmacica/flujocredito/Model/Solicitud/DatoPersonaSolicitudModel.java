package pe.com.cmacica.flujocredito.Model.Solicitud;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.RccTotalULTModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaBusqModel;

/**
 * Created by faqf on 11/06/2017.
 */

public class DatoPersonaSolicitudModel {
    public int getnExisteRet() {
        return nExisteRet;
    }

    public void setnExisteRet(int nExisteRet) {
        this.nExisteRet = nExisteRet;
    }

    public Boolean getbMicroSeguroActivo() {
        return bMicroSeguroActivo;
    }

    public void setbMicroSeguroActivo(Boolean bMicroSeguroActivo) {
        this.bMicroSeguroActivo = bMicroSeguroActivo;
    }

    public int getnNumMSCliente() {
        return nNumMSCliente;
    }

    public void setnNumMSCliente(int nNumMSCliente) {
        this.nNumMSCliente = nNumMSCliente;
    }

    public int getnNumSolPend() {
        return nNumSolPend;
    }

    public void setnNumSolPend(int nNumSolPend) {
        this.nNumSolPend = nNumSolPend;
    }

    public PersonaBusqModel getDatoPersonal() {
        return DatoPersonal;
    }

    public void setDatoPersonal(PersonaBusqModel datoPersonal) {
        DatoPersonal = datoPersonal;
    }

    public List<RccTotalULTModel> getListaBaseNegativa() {
        return ListaBaseNegativa;
    }

    public void setListaBaseNegativa(List<RccTotalULTModel> listaBaseNegativa) {
        ListaBaseNegativa = listaBaseNegativa;
    }

    public RccTotalULTModel getUltimoRcc() {
        return UltimoRcc;
    }

    public void setUltimoRcc(RccTotalULTModel ultimoRcc) {
        UltimoRcc = ultimoRcc;
    }


    public DatoPersonaSolicitudModel(int nExisteRet, Boolean bMicroSeguroActivo, int nNumMSCliente, int nNumSolPend, PersonaBusqModel datoPersonal, List<RccTotalULTModel> listaBaseNegativa, RccTotalULTModel ultimoRcc) {
        this.nExisteRet = nExisteRet;
        this.bMicroSeguroActivo = bMicroSeguroActivo;
        this.nNumMSCliente = nNumMSCliente;
        this.nNumSolPend = nNumSolPend;
        DatoPersonal = datoPersonal;
        ListaBaseNegativa = listaBaseNegativa;
        UltimoRcc = ultimoRcc;
    }

    private int nExisteRet ;
    private Boolean bMicroSeguroActivo ;
    private int nNumMSCliente ;
    private int nNumSolPend ;
    private PersonaBusqModel DatoPersonal ;

    private List<RccTotalULTModel> ListaBaseNegativa ;
    private RccTotalULTModel UltimoRcc ;
    //Solicitudes Pendientes
    //public List<BaseCreditoNormalDto> SolicitudesPendientes ;
}
