package model;

//import controllers.DateSC;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Amanat {
    private String amtID;
    private String ktbID;
    private String ktbName;
    private String usrID;
    private String usrName;
    private String amtDateGet;
    private String amtDateRtrn;
    private String mohlat;
    private String amtDarkhastUsr = "0";
    private String amtEmkanTamdid = "1";

    public Amanat() {
    }

    public Amanat(String ktbID, String usrID, String amtDateGet) throws ParseException {
        this.ktbID = ktbID;
        this.usrID = usrID;
        this.amtDateGet = amtDateGet;
        // this.amtDateRtrn = DateSC.tamdidMohalat(amtDateGet, 10);
        //this.mohlat = DateSC.mohlatTahvil(this.amtDateRtrn);
    }


    public String getAmtID() {
        return amtID;
    }

    public void setAmtID(String amtID) {
        this.amtID = amtID;
    }

    public String getKtbID() {
        return ktbID;
    }

    public void setKtbID(String ktbID) {
        this.ktbID = ktbID;
    }

    public String getKtbName() {
        return ktbName;
    }

    public void setKtbName(String ktbName) {
        this.ktbName = ktbName;
    }

    public String getUsrID() {
        return usrID;
    }

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getAmtDateGet() {
        return amtDateGet;
    }

    public void setAmtDateGet(String amtDateGet) throws ParseException {
        this.amtDateGet = amtDateGet;
    }

    public String getAmtDateRtrn() {
        return amtDateRtrn;
    }

    public void setAmtDateRtrn(String amtDateRtrn) throws ParseException {
        this.amtDateRtrn = amtDateRtrn;
        setMohlat(amtDateRtrn);
    }

    public String getAmtDarkhastUsr() {
        return amtDarkhastUsr;
    }

    public void setAmtDarkhastUsr(String amtDarkhastUsr) {
        this.amtDarkhastUsr = amtDarkhastUsr;
    }

    public String getAmtEmkanTamdid() {
        return amtEmkanTamdid;
    }

    public void setAmtEmkanTamdid(String amtEmkanTamdid) {
        this.amtEmkanTamdid = amtEmkanTamdid;
    }

    public String getMohlat() {
        return mohlat;
    }

    public void setMohlat(String amtDateRtrn) throws ParseException {
        //    this.mohlat = DateSC.mohlatTahvil(amtDateRtrn);
    }


}
