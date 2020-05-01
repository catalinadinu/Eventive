package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Rezervare implements Parcelable {
    private int zi;
    private int luna;
    private int an;
    private String categorie;
    private String numeServiciu;
    private String numeFurnizor;
    private String mailClient;

    public Rezervare() {
    }

    public Rezervare(int zi, int luna, int an, String categorie, String numeServiciu, String numeFurnizor, String mailClient) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.categorie = categorie;
        this.numeServiciu = numeServiciu;
        this.numeFurnizor = numeFurnizor;
        this.mailClient = mailClient;
    }

    protected Rezervare(Parcel in) {
        zi = in.readInt();
        luna = in.readInt();
        an = in.readInt();
        categorie = in.readString();
        numeServiciu = in.readString();
        numeFurnizor = in.readString();
        mailClient = in.readString();
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNumeServiciu() {
        return numeServiciu;
    }

    public void setNumeServiciu(String numeServiciu) {
        this.numeServiciu = numeServiciu;
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

    @Override
    public String toString() {
        return "Rezervare{" +
                "zi=" + zi +
                ", luna=" + luna +
                ", an=" + an +
                ", categorie='" + categorie + '\'' +
                ", numeServiciu='" + numeServiciu + '\'' +
                ", numeFurnizor='" + numeFurnizor + '\'' +
                ", mailClient='" + mailClient + '\'' +
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
        dest.writeString(categorie);
        dest.writeString(numeServiciu);
        dest.writeString(numeFurnizor);
        dest.writeString(mailClient);
    }
}
