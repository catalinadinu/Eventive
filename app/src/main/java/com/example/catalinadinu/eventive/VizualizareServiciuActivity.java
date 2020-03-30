package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Serviciu;

import org.w3c.dom.Text;

public class VizualizareServiciuActivity extends AppCompatActivity {
    private ImageView imagine;
    private TextView denumireServiciu;
    private TextView descriere;
    private TextView pret;
    private TextView denumireFurnizor;
    private DatePicker calendar;
    private CardView butonRezervare;
    private Serviciu serviciuDeAfisat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_serviciu);

        initComponents();

        denumireFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VizualizareServiciuActivity.this, DetaliiFurnizorActivity.class);
                intent.putExtra(Const.CHEIE_TRIMITERE_DENUMIRE_FURNIZOR_DETALII, denumireFurnizor.getText().toString().trim());
                startActivity(intent);
            }
        });

        butonRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VizualizareServiciuActivity.this, "Serviciu rezervat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initComponents(){
        imagine = findViewById(R.id.vizualizare_serviciu_imagine);
        denumireServiciu = findViewById(R.id.vizualizare_serviciu_denumire_serviciu);
        descriere = findViewById(R.id.vizualizare_serviciu_descriere_serviciu);
        pret = findViewById(R.id.vizualizare_serviciu_pret_serviciu);
        denumireFurnizor = findViewById(R.id.vizualizare_serviciu_nume_furnizor);
        calendar = findViewById(R.id.vizualizare_serviciu_calendar);
        butonRezervare = findViewById(R.id.vizualizare_serviciu_buton_rezervare);

        Intent intentPreluareServiciu = getIntent();
        serviciuDeAfisat = intentPreluareServiciu.getParcelableExtra(Const.CHEIE_TRIMITERE_SERVICIU_VIZUALIZARE);

        denumireServiciu.setText(serviciuDeAfisat.getDenumire());
        descriere.setText(serviciuDeAfisat.getDescriere());
        pret.setText(serviciuDeAfisat.getPret().toString() + " lei");
        denumireFurnizor.setText(serviciuDeAfisat.getNumeFurnizor());
    }
}
