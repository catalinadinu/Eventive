package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
    private ArrayList<Rezervare> listaRezervari = new ArrayList<>();
    private Rezervare rez = new Rezervare();
    private boolean ziOcupata = false;

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

        citireValidareSiAdaugareRezervare();
    }

    private void citireValidareSiAdaugareRezervare(){
        root.child("Rezervari").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    int zi = child.getValue(Rezervare.class).getZi();
                    int luna = child.getValue(Rezervare.class).getLuna();
                    int an = child.getValue(Rezervare.class).getAn();
                    String categorie = child.getValue(Rezervare.class).getCategorie();
                    String numeServiciu = child.getValue(Rezervare.class).getNumeServiciu();
                    String numeFurnizor = child.getValue(Rezervare.class).getNumeFurnizor();
                    String mailClient = child.getValue(Rezervare.class).getMailClient();

                    Rezervare r = new Rezervare();
                    r.setZi(zi);
                    r.setLuna(luna);
                    r.setAn(an);
                    r.setCategorie(categorie);
                    r.setNumeServiciu(numeServiciu);
                    r.setNumeFurnizor(numeFurnizor);
                    r.setMailClient(mailClient);

                    if(r.getNumeServiciu().trim().equalsIgnoreCase(serviciuDeAfisat.getDenumire().trim())){
                        listaRezervari.add(r);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        calendar.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                for(Rezervare r:listaRezervari){
                    if(r.getZi() == dayOfMonth && (r.getLuna() - 1) == monthOfYear && r.getAn() == year){
                        ziOcupata = true;
                        Toast.makeText(VizualizareServiciuActivity.this, "Data selectata nu este disponibila. Alegeti alta data.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        ziOcupata = false;
                    }
                }

                if(!ziOcupata){
                    rez.setNumeFurnizor(serviciuDeAfisat.getNumeFurnizor());
                    rez.setNumeServiciu(serviciuDeAfisat.getDenumire());
                    rez.setCategorie(serviciuDeAfisat.getCategorie());
                    rez.setMailClient(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    rez.setZi(dayOfMonth);
                    rez.setLuna(monthOfYear + 1);
                    rez.setAn(year);
                }

                if(ziOcupata){
                    butonRezervare.setClickable(false);
                }
                else{
                    butonRezervare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root.child("Rezervari").push().setValue(rez);
                            Toast.makeText(VizualizareServiciuActivity.this, "Serviciu rezervat cu succes", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

    }
}
