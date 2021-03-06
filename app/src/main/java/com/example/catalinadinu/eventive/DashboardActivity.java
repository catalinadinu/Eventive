package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.catalinadinu.eventive.Clase.Categorii;
import com.example.catalinadinu.eventive.Clase.Const;

public class DashboardActivity extends AppCompatActivity {
//    private CardView contulMeu;
    private String tipUtilizator;
    private CardView cardLocatie, cardCatering, cardDecoretiuni, cardMuzica,
            cardFotoVideo, cardPersonal, cardCeremoniiReligioase, cardAranjamenteFlorale,
            cardPapetarie, cardAnimatie, cardMarturii, cardAltele;
    private TextView afisareTipUtilizator;
    private LinearLayout contulMeu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponents();


        Intent intentPreluareTipUtilizator = getIntent();
        tipUtilizator = intentPreluareTipUtilizator.getStringExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_LOGIN_DASH);

        if(tipUtilizator.toLowerCase().equals("client")){
            afisareTipUtilizator.setText(getString(R.string.client_caps));
        }
        else{
            afisareTipUtilizator.setText(getString(R.string.furnizor_caps));
        }

        contulMeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipUtilizator.toLowerCase().equals("client")){
                    Intent intentCont = new Intent(DashboardActivity.this, ContClientActivity.class);
                    startActivity(intentCont);
                }
                if(tipUtilizator.toLowerCase().equals("furnizor")){
                    Intent intentCont = new Intent(DashboardActivity.this, ContFurnizorActivity.class);
                    startActivity(intentCont);
                }
            }
        });

        deschidereCategoriiServicii();
    }

    private void initComponents(){
        contulMeu = findViewById(R.id.dash_contulMeu);
        cardLocatie = findViewById(R.id.dash_locatie);
        cardCatering = findViewById(R.id.dash_catering);
        cardDecoretiuni = findViewById(R.id.dash_decoratiuni);
        cardMuzica = findViewById(R.id.dash_muzica);
        cardFotoVideo = findViewById(R.id.dash_fotoVideo);
        cardPersonal = findViewById(R.id.dash_personal);
        cardCeremoniiReligioase = findViewById(R.id.dash_ceremonii);
        cardAranjamenteFlorale = findViewById(R.id.dash_aranjamenteFlorale);
        cardPapetarie = findViewById(R.id.dash_papetarie);
        cardAnimatie = findViewById(R.id.dash_animatie);
        cardMarturii = findViewById(R.id.dash_marturii);
        cardAltele = findViewById(R.id.dash_altele);
        afisareTipUtilizator = findViewById(R.id.dashboard_tip_utilizator);
    }

    private void deschidereCategoriiServicii(){
        final Intent intent = new Intent(DashboardActivity.this, ServiciiActivity.class);
        intent.putExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_DASH_LISTA_SERVICII, tipUtilizator);

        cardLocatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.LOCATIE.toString());
                startActivity(intent);
            }
        });

        cardCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.CATERING.toString());
                startActivity(intent);
            }
        });

        cardDecoretiuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.DECORATIUNI.toString());
                startActivity(intent);
            }
        });

        cardMuzica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.MUZICA.toString());
                startActivity(intent);
            }
        });

        cardFotoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.FOTO_VIDEO.toString());
                startActivity(intent);
            }
        });

        cardPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.PERSONAL.toString());
                startActivity(intent);
            }
        });

        cardCeremoniiReligioase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.CEREMONII_RELIGIOASE.toString());
                startActivity(intent);
            }
        });

        cardAranjamenteFlorale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.ARANJAMENTE_FLORALE.toString());
                startActivity(intent);
            }
        });

        cardPapetarie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.PAPETARIE.toString());
                startActivity(intent);
            }
        });

        cardAnimatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.ANIMATIE_DIVERTISMENT.toString());
                startActivity(intent);
            }
        });

        cardMarturii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.MARTURII.toString());
                startActivity(intent);
            }
        });

        cardAltele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA, Categorii.ALTELE.toString());
                startActivity(intent);
            }
        });
    }
}
