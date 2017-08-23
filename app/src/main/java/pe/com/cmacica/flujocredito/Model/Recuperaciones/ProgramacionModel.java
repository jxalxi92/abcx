package pe.com.cmacica.flujocredito.Model.Recuperaciones;

import java.util.List;

import pe.com.cmacica.flujocredito.Model.Solicitud.ColocSolicitudEstadoModel;

/**
 * Created by faqf on 22/08/2017.
 */

public class ProgramacionModel {

    public String cPersCodAna;
    public String cUser;
    public String cImei;
    public String cAgeCod;
    public int nEstado;
    public List<ProgramacionDetModel> ProgramacionDetalle;
}
