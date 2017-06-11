package pe.com.cmacica.flujocredito.Model.General;

/**
 * Created by faqf on 07/06/2017.
 */

public class PersonaBusqModel {



    private String CodigoPersona;

    public PersonaBusqModel(String codigoPersona, String nombrePersona, String nombrePersonaCompleto,
                            String direccion, String numeroDocumento, int codigoTipoDocumento, String depProvDis, String telefono, String telefono2,
                            String telefono3, String email, String cUbiGeoCod, String nacionalidad,
                            String cargocod, String ocupacion, String fechaNacimiento, String cPersNatSexo, int nPersTipo, int edad, int nPersPersoneria,String TipoPersona) {
        CodigoPersona = codigoPersona;
        NombrePersona = nombrePersona;
        NombrePersonaCompleto = nombrePersonaCompleto;
        Direccion = direccion;
        NumeroDocumento = numeroDocumento;
        CodigoTipoDocumento = codigoTipoDocumento;
        DepProvDis = depProvDis;
        Telefono = telefono;
        Telefono2 = telefono2;
        Telefono3 = telefono3;
        Email = email;
        this.cUbiGeoCod = cUbiGeoCod;
        Nacionalidad = nacionalidad;
        Cargocod = cargocod;
        Ocupacion = ocupacion;
        FechaNacimiento = fechaNacimiento;
        this.cPersNatSexo = cPersNatSexo;
        this.nPersTipo = nPersTipo;
        Edad = edad;
        this.nPersPersoneria = nPersPersoneria;
        this.TipoPersona=TipoPersona;
    }

    private String NombrePersona;
    private String NombrePersonaCompleto;
    private String Direccion;
    private String NumeroDocumento;
    private int    CodigoTipoDocumento;
    private String DepProvDis;
    private String Telefono;
    private String Telefono2;
    private String Telefono3;
    private String Email;
    private String cUbiGeoCod;
    private String Nacionalidad;
    private String Cargocod;
    private String Ocupacion;
    private String FechaNacimiento;
    private String cPersNatSexo;
    private int nPersTipo;
    private int Edad;
    private int nPersPersoneria;
    private String TipoPersona;

    public String getTipoPersona() {
        return TipoPersona;
    }

    public String getCodigoPersona() {
        return CodigoPersona;
    }

    public String getNombrePersona() {
        return NombrePersona;
    }

    public String getNombrePersonaCompleto() {
        return NombrePersonaCompleto;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public int getCodigoTipoDocumento() {
        return CodigoTipoDocumento;
    }

    public String getDepProvDis() {
        return DepProvDis;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public String getTelefono3() {
        return Telefono3;
    }

    public String getEmail() {
        return Email;
    }

    public String getcUbiGeoCod() {
        return cUbiGeoCod;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public String getCargocod() {
        return Cargocod;
    }

    public String getOcupacion() {
        return Ocupacion;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public String getcPersNatSexo() {
        return cPersNatSexo;
    }

    public int getnPersTipo() {
        return nPersTipo;
    }

    public int getEdad() {
        return Edad;
    }

    public int getnPersPersoneria() {
        return nPersPersoneria;
    }

    @Override
    public String toString() {
        return  NombrePersona;

    }
}
