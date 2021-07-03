package model;

public class Book {

    private String ktbID;
    private String ktbName;
    private String ktbNevisande;
    private String ktbEhdaKonandeh;
    private String ktbTedad;
    private String ktbVazeit;
    private String amtTarakoneshID = "";

    public Book() {
    }

    public Book(String ktbID, String ktbName, String ktbNevisande, String ktbEhdaKonandeh, String ktbTedad, String ktbVazeit) {
        this.ktbID = ktbID;
        this.ktbName = ktbName;
        this.ktbNevisande = ktbNevisande;
        this.ktbEhdaKonandeh = ktbEhdaKonandeh;
        this.ktbTedad = ktbTedad;
        this.ktbVazeit = ktbVazeit;
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

    public String getKtbNevisande() {
        return ktbNevisande;
    }

    public void setKtbNevisande(String ktbNevisande) {
        this.ktbNevisande = ktbNevisande;
    }

    public String getKtbTedad() {
        return ktbTedad;
    }

    public void setKtbTedad(String ktbTedad) {
        this.ktbTedad = ktbTedad;
    }

    public String getKtbVazeit() {
        return ktbVazeit;
    }

    public void setKtbVazeit(String ktbVazeit) {
        this.ktbVazeit = ktbVazeit;
    }

    public String getKtbEhdaKonandeh() {
        return ktbEhdaKonandeh;
    }

    public void setKtbEhdaKonandeh(String ktbEhdaKonandeh) {
        this.ktbEhdaKonandeh = ktbEhdaKonandeh;
    }

    public String getAmtTarakoneshID() {
        return amtTarakoneshID;
    }

    public void setAmtTarakoneshID(String amtTarakoneshID) {
        this.amtTarakoneshID = amtTarakoneshID;
    }
}