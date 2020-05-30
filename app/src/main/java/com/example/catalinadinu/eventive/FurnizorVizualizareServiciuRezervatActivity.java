package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Rezervare;

public class FurnizorVizualizareServiciuRezervatActivity extends AppCompatActivity {
    private ImageView imagine;
    private TextView denumireServiciu;
    private TextView descriere;
    private TextView pret;
    private TextView denumireFurnizor;
    private TextView dataRezervata;
    private TextView stareRezervare;
    private TextView mailClient;
    private Intent intentRezervare;
    private Rezervare rezervare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnizor_vizualizare_serviciu_rezervat);

        initComponents();
    }

    private void initComponents(){
        imagine = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_imagine);
        denumireServiciu = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_denumire_serviciu);
        descriere = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_descriere_serviciu);
        pret = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_pret_serviciu);
        denumireFurnizor = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_nume_furnizor);
        dataRezervata = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_data);
        stareRezervare = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_stare);
        mailClient = findViewById(R.id.furnizor_vizualizare_serviciu_rezervat_mail_client);

        intentRezervare = getIntent();
        if(intentRezervare.hasExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE_FURNIZOR)){
            rezervare = intentRezervare.getParcelableExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE_FURNIZOR);

            denumireServiciu.setText(rezervare.getDenumireProdus());
            descriere.setText(rezervare.getDescriere());
            pret.setText(String.valueOf(rezervare.getPret()) + " lei");
            denumireFurnizor.setText(rezervare.getNumeFurnizor());

            String dataRezervataString = String.valueOf(rezervare.getZi()) + "/ " + String.valueOf(rezervare.getLuna()) +
                    "/ " + String.valueOf(rezervare.getAn());
            dataRezervata.setText(dataRezervataString);

            stareRezervare.setText(rezervare.getStareRezervare());
            mailClient.setText(rezervare.getMailClient());
        }
    }
}
