package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Date;

public class Serviciu implements Parcelable {
    private String denumire;
    private String descriere;
    private Integer pret;
    private String numeFurnizor;
    private String categorie;
    private String mailUtilizator;
    private String[] dateOcupate;

    public Serviciu(String denumire, String descriere, Integer pret, String numeFurnizor, String categorie, String mailUtilizator) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.pret = pret;
        this.numeFurnizor = numeFurnizor;
        this.categorie = categorie;
        this.mailUtilizator = mailUtilizator;
    }

    public Serviciu(String denumire, String descriere, Integer pret, String numeFurnizor, String categorie) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.pret = pret;
        this.numeFurnizor = numeFurnizor;
        this.categorie = categorie;
    }

    public Serviciu(String denumire, String descriere, Integer pret, String numeFurnizor, String categorie, String[] dateOcupate) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.pret = pret;
        this.numeFurnizor = numeFurnizor;
        this.categorie = categorie;
        this.dateOcupate = dateOcupate;
    }

    public Serviciu() {
    }

    protected Serviciu(Parcel in) {
        denumire = in.readString();
        descriere = in.readString();
        if (in.readByte() == 0) {
            pret = null;
        } else {
            pret = in.readInt();
        }
        numeFurnizor = in.readString();
        categorie = in.readString();
        mailUtilizator = in.readString();
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
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

    public String getMailUtilizator() {
        return mailUtilizator;
    }

    public void setMailUtilizator(String mailUtilizator) {
        this.mailUtilizator = mailUtilizator;
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
                ", descriere='" + descriere + '\'' +
                ", pret=" + pret +
                ", numeFurnizor='" + numeFurnizor + '\'' +
                ", categorie='" + categorie + '\'' +
                ", mailUtilizator='" + mailUtilizator + '\'' +
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
        dest.writeString(descriere);
        if (pret == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pret);
        }
        dest.writeString(numeFurnizor);
        dest.writeString(categorie);
        dest.writeString(mailUtilizator);
        dest.writeStringArray(dateOcupate);
    }
}
