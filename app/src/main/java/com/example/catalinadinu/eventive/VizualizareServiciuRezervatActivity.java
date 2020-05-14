package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.Serviciu;

public class VizualizareServiciuRezervatActivity extends AppCompatActivity {
    private ImageView imagine;
    private TextView denumireServiciu;
    private TextView descriere;
    private TextView pret;
    private TextView denumireFurnizor;
    private TextView dataRezervata;
    private Intent intentRezervare;
    private Serviciu serviciu;
    private Rezervare rezervare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_serviciu_rezervat);

        initComponents();
    }

    private void initComponents(){
        imagine = findViewById(R.id.vizualizare_serviciu_rezervat_imagine);
        denumireServiciu = findViewById(R.id.vizualizare_serviciu_rezervat_denumire_serviciu);
        descriere = findViewById(R.id.vizualizare_serviciu_rezervat_descriere_serviciu);
        pret = findViewById(R.id.vizualizare_serviciu_rezervat_pret_serviciu);
        denumireFurnizor = findViewById(R.id.vizualizare_serviciu_rezervat_nume_furnizor);
        dataRezervata = findViewById(R.id.vizualizare_serviciu_rezervat_data);

        intentRezervare = getIntent();
        if(intentRezervare.hasExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_SERVICIU_REZERVAT) &&
            intentRezervare.hasExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE)){
            serviciu = intentRezervare.getParcelableExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_SERVICIU_REZERVAT);
            rezervare = intentRezervare.getParcelableExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE);

            denumireServiciu.setText(serviciu.getDenumire());
            descriere.setText(serviciu.getDescriere());
            pret.setText(String.valueOf(serviciu.getPret()) + " lei");
            denumireFurnizor.setText(serviciu.getNumeFurnizor());

            String dataRezervataString = String.valueOf(rezervare.getZi()) + "/ " + String.valueOf(rezervare.getLuna()) +
                    "/ " + String.valueOf(rezervare.getAn());
            dataRezervata.setText(dataRezervataString);
        }
    }
}
