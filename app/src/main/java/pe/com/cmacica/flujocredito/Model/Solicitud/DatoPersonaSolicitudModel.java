package pe.com.cmacica.flujocredito.Model.Solicitud;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.RccTotalULTModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaBusqModel;

/**
 * Created by faqf on 11/06/2017.
 */

public class DatoPersonaSolicitudModel {
    public int nExisteRet ;
    public Boolean bMicroSeguroActivo ;
    public int nNumMSCliente ;
    public int nNumSolPend ;
    public PersonaBusqModel DatoPersonal ;

    //Base Negativa
    public List<RccTotalULTModel> ListaBaseNegativa ;
    //Ultimo RCC reportado del cliente
    public RccTotalULTModel UltimoRcc ;
    //Solicitudes Pendientes
    //public List<BaseCreditoNormalDto> SolicitudesPendientes ;
}
