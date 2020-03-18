package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;

import com.example.catalinadinu.eventive.Clase.Const;
import com.google.firebase.database.DatabaseReference;

public class ServiciiActivity extends AppCompatActivity {
    private String tipUtilizator;
    private ListView listViewServicii;
    private CardView butonAdaugare;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicii);

        initComponents();
    }

    private void initComponents(){
        Intent intentPreluareTipUtilizator = getIntent();
        tipUtilizator = intentPreluareTipUtilizator.getStringExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_DASH_LISTA_SERVICII);

        listViewServicii = findViewById(R.id.listview_lista_servicii_pe_categorii);
        butonAdaugare = findViewById(R.id.lista_servicii_adaugare);

        if(tipUtilizator.equals("client")){
            butonAdaugare.setVisibility(View.INVISIBLE);
        }
        else{
            butonAdaugare.setVisibility(View.VISIBLE);
        }

        if(tipUtilizator.equals("furnizor")){
            butonAdaugare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ServiciiActivity.this, AdaugareServiciuActivity.class);
                    startActivity(intent);
                }
            });
        }
    }


}
