package com.example.catalinadinu.eventive.Clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Furnizor implements Parcelable {
    private String deumire;
    private String adresa;
    private String telefon;
    private String email;
    private String mailContConectat;

    public Furnizor() {
    }

    public Furnizor(String deumire, String adresa, String telefon, String email) {
        this.deumire = deumire;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
    }

    public Furnizor(String deumire, String adresa, String telefon, String email, String mailContConectat) {
        this.deumire = deumire;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.mailContConectat = mailContConectat;
    }

    protected Furnizor(Parcel in) {
        deumire = in.readString();
        adresa = in.readString();
        telefon = in.readString();
        email = in.readString();
        mailContConectat = in.readString();
    }

    public static final Creator<Furnizor> CREATOR = new Creator<Furnizor>() {
        @Override
        public Furnizor createFromParcel(Parcel in) {
            return new Furnizor(in);
        }

        @Override
        public Furnizor[] newArray(int size) {
            return new Furnizor[size];
        }
    };

    public String getDeumire() {
        return deumire;
    }

    public void setDeumire(String deumire) {
        this.deumire = deumire;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailContConectat() {
        return mailContConectat;
    }

    public void setMailContConectat(String mailContConectat) {
        this.mailContConectat = mailContConectat;
    }

    @Override
    public String toString() {
        return "Furnizor{" +
                "deumire='" + deumire + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", mailContConectat='" + mailContConectat + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deumire);
        dest.writeString(adresa);
        dest.writeString(telefon);
        dest.writeString(email);
        dest.writeString(mailContConectat);
    }
}
