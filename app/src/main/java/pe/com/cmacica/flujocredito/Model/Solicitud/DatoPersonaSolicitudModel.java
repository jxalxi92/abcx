package pe.com.cmacica.flujocredito.Model.Solicitud;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Calificacion.RccTotalULTModel;
import pe.com.cmacica.flujocredito.Model.General.PersonaBusqModel;

/**
 * Created by faqf on 11/06/2017.
 */

public class DatoPersonaSolicitudModel {
    private int nExisteRet ;
    private Boolean bMicroSeguroActivo ;
    private int nNumMSCliente ;
    private int nNumSolPend ;
    private PersonaBusqModel DatoPersonal ;

    //Base Negativa
    private List<RccTotalULTModel> ListaBaseNegativa ;
    //Ultimo RCC reportado del cliente
    private RccTotalULTModel UltimoRcc ;
    //Solicitudes Pendientes
    //public List<BaseCreditoNormalDto> SolicitudesPendientes ;
}
