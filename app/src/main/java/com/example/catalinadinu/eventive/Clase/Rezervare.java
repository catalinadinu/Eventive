package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Rezervare implements Parcelable {
    private int zi;
    private int luna;
    private int an;
    private String denumireProdus;
    private String descriere;
    private String pret;
    private String categorie;
    private String numeFurnizor;
    private String mailClient;
    private String mailFurnizor;
    private String stareRezervare;

    public Rezervare() {
    }

    public Rezervare(int zi, int luna, int an, String denumireProdus, String descriere, String pret,
                     String categorie, String numeFurnizor, String mailClient, String mailFurnizor) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.denumireProdus = denumireProdus;
        this.descriere = descriere;
        this.pret = pret;
        this.categorie = categorie;
        this.numeFurnizor = numeFurnizor;
        this.mailClient = mailClient;
        this.mailFurnizor = mailFurnizor;
        this.stareRezervare = String.valueOf(StareRezervare.NEFINALIZAT);
    }

    public Rezervare(int zi, int luna, int an, String denumireProdus, String descriere, String pret, String categorie, String numeFurnizor, String mailClient, String mailFurnizor, String stareRezervare) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.denumireProdus = denumireProdus;
        this.descriere = descriere;
        this.pret = pret;
        this.categorie = categorie;
        this.numeFurnizor = numeFurnizor;
        this.mailClient = mailClient;
        this.mailFurnizor = mailFurnizor;
        this.stareRezervare = stareRezervare;
    }


    protected Rezervare(Parcel in) {
        zi = in.readInt();
        luna = in.readInt();
        an = in.readInt();
        denumireProdus = in.readString();
        descriere = in.readString();
        pret = in.readString();
        categorie = in.readString();
        numeFurnizor = in.readString();
        mailClient = in.readString();
        mailFurnizor = in.readString();
        stareRezervare = in.readString();
    }

    public static final Creator<Rezervare> CREATOR = new Creator<Rezervare>() {
        @Override
        public Rezervare createFromParcel(Parcel in) {
            return new Rezervare(in);
        }

        @Override
        public Rezervare[] newArray(int size) {
            return new Rezervare[size];
        }
    };

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNumeFurnizor() {
        return numeFurnizor;
    }

    public void setNumeFurnizor(String numeFurnizor) {
        this.numeFurnizor = numeFurnizor;
    }

    public String getMailClient() {
        return mailClient;
    }

    public void setMailClient(String mailClient) {
        this.mailClient = mailClient;
    }

    public String getMailFurnizor() {
        return mailFurnizor;
    }

    public void setMailFurnizor(String mailFurnizor) {
        this.mailFurnizor = mailFurnizor;
    }

    public String getStareRezervare() {
        return stareRezervare;
    }

    public void setStareRezervare(String stareRezervare) {
        this.stareRezervare = stareRezervare;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "zi=" + zi +
                ", luna=" + luna +
                ", an=" + an +
                ", denumireProdus='" + denumireProdus + '\'' +
                ", descriere='" + descriere + '\'' +
                ", pret='" + pret + '\'' +
                ", categorie='" + categorie + '\'' +
                ", numeFurnizor='" + numeFurnizor + '\'' +
                ", mailClient='" + mailClient + '\'' +
                ", mailFurnizor='" + mailFurnizor + '\'' +
                ", stareRezervare='" + stareRezervare + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(zi);
        dest.writeInt(luna);
        dest.writeInt(an);
        dest.writeString(denumireProdus);
        dest.writeString(descriere);
        dest.writeString(pret);
        dest.writeString(categorie);
        dest.writeString(numeFurnizor);
        dest.writeString(mailClient);
        dest.writeString(mailFurnizor);
        dest.writeString(stareRezervare);
    }
}
