package pe.com.cmacica.flujocredito.Model.Calificacion;


/**
 * Created by faqf on 11/06/2017.
 */

public class RccTotalULTModel {

    public String getFec_Rep() {
        return Fec_Rep;
    }

    public void setFec_Rep(String fec_Rep) {
        Fec_Rep = fec_Rep;
    }

    public String getCod_Sbs() {
        return Cod_Sbs;
    }

    public void setCod_Sbs(String cod_Sbs) {
        Cod_Sbs = cod_Sbs;
    }

    public String getTip_Det() {
        return Tip_Det;
    }

    public void setTip_Det(String tip_Det) {
        Tip_Det = tip_Det;
    }

    public String getTip_Doc_Trib() {
        return Tip_Doc_Trib;
    }

    public void setTip_Doc_Trib(String tip_Doc_Trib) {
        Tip_Doc_Trib = tip_Doc_Trib;
    }

    public String getCod_Doc_Trib() {
        return Cod_Doc_Trib;
    }

    public void setCod_Doc_Trib(String cod_Doc_Trib) {
        Cod_Doc_Trib = cod_Doc_Trib;
    }

    public String getTip_Doc_Id() {
        return Tip_Doc_Id;
    }

    public void setTip_Doc_Id(String tip_Doc_Id) {
        Tip_Doc_Id = tip_Doc_Id;
    }

    public String getCod_Doc_Id() {
        return Cod_Doc_Id;
    }

    public void setCod_Doc_Id(String cod_Doc_Id) {
        Cod_Doc_Id = cod_Doc_Id;
    }

    public String getTip_Pers() {
        return Tip_Pers;
    }

    public void setTip_Pers(String tip_Pers) {
        Tip_Pers = tip_Pers;
    }

    public String getTip_Emp() {
        return Tip_Emp;
    }

    public void setTip_Emp(String tip_Emp) {
        Tip_Emp = tip_Emp;
    }

    public int getCan_Ents() {
        return Can_Ents;
    }

    public void setCan_Ents(int can_Ents) {
        Can_Ents = can_Ents;
    }

    public Double getCalif_0() {
        return Calif_0;
    }

    public void setCalif_0(Double calif_0) {
        Calif_0 = calif_0;
    }

    public Double getCalif_1() {
        return Calif_1;
    }

    public void setCalif_1(Double calif_1) {
        Calif_1 = calif_1;
    }

    public Double getCalif_2() {
        return Calif_2;
    }

    public void setCalif_2(Double calif_2) {
        Calif_2 = calif_2;
    }

    public Double getCalif_3() {
        return Calif_3;
    }

    public void setCalif_3(Double calif_3) {
        Calif_3 = calif_3;
    }

    public Double getCalif_4() {
        return Calif_4;
    }

    public void setCalif_4(Double calif_4) {
        Calif_4 = calif_4;
    }

    public String getCalif() {
        return calif;
    }

    public void setCalif(String calif) {
        this.calif = calif;
    }

    public int getNcalif() {
        return ncalif;
    }

    public void setNcalif(int ncalif) {
        this.ncalif = ncalif;
    }

    public Double getnMonto() {
        return nMonto;
    }

    public void setnMonto(Double nMonto) {
        this.nMonto = nMonto;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    private String Fec_Rep ;
    private String Cod_Sbs ;
    private String Tip_Det ;
    private String Tip_Doc_Trib ;
    private String Cod_Doc_Trib ;
    private String Tip_Doc_Id ;
    private String Cod_Doc_Id ;
    private String Tip_Pers ;

    public RccTotalULTModel(String fec_Rep, String cod_Sbs, String tip_Det, String tip_Doc_Trib, String cod_Doc_Trib, String tip_Doc_Id, String cod_Doc_Id, String tip_Pers, String tip_Emp, int can_Ents, Double calif_0, Double calif_1, Double calif_2, Double calif_3, Double calif_4, String calif, int ncalif, Double nMonto, String result) {
        Fec_Rep = fec_Rep;
        Cod_Sbs = cod_Sbs;
        Tip_Det = tip_Det;
        Tip_Doc_Trib = tip_Doc_Trib;
        Cod_Doc_Trib = cod_Doc_Trib;
        Tip_Doc_Id = tip_Doc_Id;
        Cod_Doc_Id = cod_Doc_Id;
        Tip_Pers = tip_Pers;
        Tip_Emp = tip_Emp;
        Can_Ents = can_Ents;
        Calif_0 = calif_0;
        Calif_1 = calif_1;
        Calif_2 = calif_2;
        Calif_3 = calif_3;
        Calif_4 = calif_4;
        this.calif = calif;
        this.ncalif = ncalif;
        this.nMonto = nMonto;
        Result = result;
    }

    private String Tip_Emp ;
    private int Can_Ents ;
    private Double Calif_0 ;
    private Double Calif_1;
    private Double Calif_2 ;
    private Double Calif_3 ;
    private Double Calif_4 ;
    private String calif ;
    private int ncalif ;
    private Double nMonto ;
    private String Result ;
}
