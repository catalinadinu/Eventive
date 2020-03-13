package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Date;

public class Serviciu implements Parcelable {
    private String denumire;
    private String pret;
    private String numeFurnizor;
    private String categorie;
    private String[] dateOcupate;

    public Serviciu(String denumire, String pret, String numeFurnizor, String categorie, String[] dateOcupate) {
        this.denumire = denumire;
        this.pret = pret;
        this.numeFurnizor = numeFurnizor;
        this.categorie = categorie;
        this.dateOcupate = dateOcupate;
    }

    protected Serviciu(Parcel in) {
        denumire = in.readString();
        pret = in.readString();
        numeFurnizor = in.readString();
        categorie = in.readString();
        dateOcupate = in.createStringArray();
    }

    public static final Creator<Serviciu> CREATOR = new Creator<Serviciu>() {
        @Override
        public Serviciu createFromParcel(Parcel in) {
            return new Serviciu(in);
        }

        @Override
        public Serviciu[] newArray(int size) {
            return new Serviciu[size];
        }
    };

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getNumeFurnizor() {
        return numeFurnizor;
    }

    public void setNumeFurnizor(String numeFurnizor) {
        this.numeFurnizor = numeFurnizor;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String[] getDateOcupate() {
        return dateOcupate;
    }

    public void setDateOcupate(String[] dateOcupate) {
        this.dateOcupate = dateOcupate;
    }

    @Override
    public String toString() {
        return "Serviciu{" +
                "denumire='" + denumire + '\'' +
                ", pret='" + pret + '\'' +
                ", numeFurnizor='" + numeFurnizor + '\'' +
                ", categorie='" + categorie + '\'' +
                ", dateOcupate=" + Arrays.toString(dateOcupate) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeString(pret);
        dest.writeString(numeFurnizor);
        dest.writeString(categorie);
        dest.writeStringArray(dateOcupate);
    }
}
