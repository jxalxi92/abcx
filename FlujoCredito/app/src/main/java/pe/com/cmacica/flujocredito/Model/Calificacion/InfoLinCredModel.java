package pe.com.cmacica.flujocredito.Model.Calificacion;

/**
 * Created by JHON on 5/10/2016.
 */

public class InfoLinCredModel {

    public InfoLinCredModel(String tipDoc, String numDoc, String cnsEntNomRazLN, String tipoCuenta, String linCred, String linNoUtil, String linUtil, String porLinUti) {
        TipDoc = tipDoc;
        NumDoc = numDoc;
        CnsEntNomRazLN = cnsEntNomRazLN;
        TipoCuenta = tipoCuenta;
        LinCred = linCred;
        LinNoUtil = linNoUtil;
        LinUtil = linUtil;
        PorLinUti = porLinUti;
    }

    public String getTipDoc() {
        return TipDoc;
    }

    public void setTipDoc(String tipDoc) {
        TipDoc = tipDoc;
    }

    public String getNumDoc() {
        return NumDoc;
    }

    public void setNumDoc(String numDoc) {
        NumDoc = numDoc;
    }

    public String getCnsEntNomRazLN() {
        return CnsEntNomRazLN;
    }

    public void setCnsEntNomRazLN(String cnsEntNomRazLN) {
        CnsEntNomRazLN = cnsEntNomRazLN;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        TipoCuenta = tipoCuenta;
    }

    public String getLinCred() {
        return LinCred;
    }

    public void setLinCred(String linCred) {
        LinCred = linCred;
    }

    public String getLinNoUtil() {
        return LinNoUtil;
    }

    public void setLinNoUtil(String linNoUtil) {
        LinNoUtil = linNoUtil;
    }

    public String getLinUtil() {
        return LinUtil;
    }

    public void setLinUtil(String linUtil) {
        LinUtil = linUtil;
    }

    public String getPorLinUti() {
        return PorLinUti;
    }

    public void setPorLinUti(String porLinUti) {
        PorLinUti = porLinUti;
    }

    private String TipDoc;
    private String NumDoc;
    private String CnsEntNomRazLN;
    private String TipoCuenta;
    private String LinCred;
    private String LinNoUtil;
    private String LinUtil;
    private String PorLinUti;
}
