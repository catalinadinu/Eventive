package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Serviciu implements Parcelable {
    private String denumire;
    private String pret;
    private String numeFurnizor;
    private String categorie;

    public Serviciu(String denumire, String pret, String numeFurnizor, String categorie) {
        this.denumire = denumire;
        this.pret = pret;
        this.numeFurnizor = numeFurnizor;
        this.categorie = categorie;
    }

    protected Serviciu(Parcel in) {
        denumire = in.readString();
        pret = in.readString();
        numeFurnizor = in.readString();
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

    @Override
    public String toString() {
        return "Serviciu{" +
                "denumire='" + denumire + '\'' +
                ", pret='" + pret + '\'' +
                ", numeFurnizor='" + numeFurnizor + '\'' +
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
    }
}
