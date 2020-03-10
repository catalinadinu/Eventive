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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        contulMeu = findViewById(R.id.dash_contulMeu);
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
    }
}
