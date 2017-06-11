package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by jhcc on 08/08/2016.
 */
public class PersonaDto {

    public PersonaDto(){

    }
    public PersonaDto(String idPersona, String cPersCod, String cPersNombre, String cDoi, String cDireccion, String dVersion, String nEstadoOpe) {
        this.IdPersona = idPersona;
        this.cPersCod = cPersCod;
        this.cPersNombre = cPersNombre;
        this.cDoi = cDoi;
        this.cDireccion = cDireccion;
        this.dVersion = dVersion;
        this.nEstadoOpe = nEstadoOpe;
    }

    public void setIdPersona(String idPersona) {
        IdPersona = idPersona;
    }

    public void setcPersCod(String cPersCod) {
        this.cPersCod = cPersCod;
    }

    public void setcPersNombre(String cPersNombre) {
        this.cPersNombre = cPersNombre;
    }

    public void setcDoi(String cDoi) {
        this.cDoi = cDoi;
    }

    public void setcDireccion(String cDireccion) {
        this.cDireccion = cDireccion;
    }

    public void setdVersion(String dVersion) {
        this.dVersion = dVersion;
    }

    public void setnEstadoOpe(String nEstadoOpe) {
        this.nEstadoOpe = nEstadoOpe;
    }

    private String IdPersona;
    private String cPersCod;
    private String cPersNombre ;
    private String cDoi ;
    private String cDireccion;
    private String dVersion  ;
    private String nEstadoOpe ;

    public String getnEstadoOpe() {
        return nEstadoOpe;
    }

    public String getIdPersona() {
        return IdPersona;
    }

    public String getcPersCod() {
        return cPersCod;
    }

    public String getcPersNombre() {
        return cPersNombre;
    }

    public String getcDoi() {
        return cDoi;
    }

    public String getcDireccion() {
        return cDireccion;
    }

    public String getdVersion() {
        return dVersion;
    }



}
