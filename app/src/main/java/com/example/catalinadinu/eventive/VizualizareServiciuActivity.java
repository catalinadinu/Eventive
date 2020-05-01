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
import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;

public class VizualizareServiciuActivity extends AppCompatActivity {
    private ImageView imagine;
    private TextView denumireServiciu;
    private TextView descriere;
    private TextView pret;
    private TextView denumireFurnizor;
    private TextView textView_rezerva;
    private TextView textView_data;
    private DatePicker calendar;
    private CardView butonRezervare;
    private Serviciu serviciuDeAfisat;
    private String tipUtilizator;
    private boolean vizualizareServiciuPropriu = false;

    private DatabaseReference root;

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

        final Rezervare r = new Rezervare();

        calendar.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                r.setNumeFurnizor(serviciuDeAfisat.getNumeFurnizor());
                r.setNumeServiciu(serviciuDeAfisat.getDenumire());
                r.setCategorie(serviciuDeAfisat.getCategorie());
                r.setMailClient(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                r.setZi(dayOfMonth);
                r.setLuna(monthOfYear + 1);
                r.setAn(year);
            }
        });

        butonRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.child("Rezervari").push().setValue(r);
                Toast.makeText(VizualizareServiciuActivity.this, "Serviciu rezervat cu succes", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void initComponents(){
        root = FirebaseDatabase.getInstance().getReference();

        imagine = findViewById(R.id.vizualizare_serviciu_imagine);
        denumireServiciu = findViewById(R.id.vizualizare_serviciu_denumire_serviciu);
        descriere = findViewById(R.id.vizualizare_serviciu_descriere_serviciu);
        pret = findViewById(R.id.vizualizare_serviciu_pret_serviciu);
        denumireFurnizor = findViewById(R.id.vizualizare_serviciu_nume_furnizor);
        textView_rezerva = findViewById(R.id.vizualizare_serviciu_textview1);
        textView_data = findViewById(R.id.vizualizare_serviciu_textview2);
        calendar = findViewById(R.id.vizualizare_serviciu_calendar);
        butonRezervare = findViewById(R.id.vizualizare_serviciu_buton_rezervare);

        calendar.setMinDate(System.currentTimeMillis() - 1000);

        Intent intentPreluareServiciu = getIntent();
        tipUtilizator = intentPreluareServiciu.getStringExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_VIZ_SERV);
        serviciuDeAfisat = intentPreluareServiciu.getParcelableExtra(Const.CHEIE_TRIMITERE_SERVICIU_VIZUALIZARE);

        if(intentPreluareServiciu.hasExtra(Const.CHEIE_TRMIITERE_FLAG_SERVICII_FILTRATE)){
            vizualizareServiciuPropriu = intentPreluareServiciu.getBooleanExtra(Const.CHEIE_TRMIITERE_FLAG_SERVICII_FILTRATE, true);
        }

        if(vizualizareServiciuPropriu || tipUtilizator.equals("furnizor")){
            textView_rezerva.setVisibility(View.GONE);
            textView_data.setVisibility(View.GONE);
            calendar.setVisibility(View.GONE);
            butonRezervare.setVisibility(View.GONE);
        }

        denumireServiciu.setText(serviciuDeAfisat.getDenumire());
        descriere.setText(serviciuDeAfisat.getDescriere());
        pret.setText(serviciuDeAfisat.getPret().toString() + " lei");
        denumireFurnizor.setText(serviciuDeAfisat.getNumeFurnizor());
    }
}
