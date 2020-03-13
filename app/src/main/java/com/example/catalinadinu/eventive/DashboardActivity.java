package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.catalinadinu.eventive.Clase.Const;

public class DashboardActivity extends AppCompatActivity {
    private CardView contulMeu;
    private String tipUtilizator;
    private CardView cardLocatie, cardCatering, cardDecoretiuni, cardMuzica,
            cardFotoVideo, cardPersonal, cardCeremoniiReligioase, cardAranjamenteFlorale,
            cardPapetarie, cardAnimatie, cardMarturii, cardAltele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponents();


        Intent intentPreluareTipUtilizator = getIntent();
        tipUtilizator = intentPreluareTipUtilizator.getStringExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_LOGIN_DASH);

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
    }

    private void deschidereCategoriiServicii(){
        final Intent intent = new Intent(DashboardActivity.this, ServiciiActivity.class);
        intent.putExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_DASH_LISTA_SERVICII, tipUtilizator);

        cardLocatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardDecoretiuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardMuzica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardFotoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardCeremoniiReligioase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardAranjamenteFlorale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardPapetarie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardAnimatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardMarturii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        cardAltele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
