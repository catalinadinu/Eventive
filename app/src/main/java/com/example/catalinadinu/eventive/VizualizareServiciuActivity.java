package com.example.catalinadinu.eventive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.catalinadinu.eventive.Clase.StareRezervare;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
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

    private FirebaseStorage storage;
    private StorageReference storageReference;

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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
        serviciuDeAfisat = intentPreluareServiciu.getParcelableExtra(Const.CHEIE_TRIMITERE_SERVICIU_VIZUALIZARE);

        SharedPreferences sp = getSharedPreferences(Const.SP_NUME_FISIER, Context.MODE_PRIVATE);
        tipUtilizator = sp.getString("tip_utilizator", null);

        if(intentPreluareServiciu.hasExtra(Const.CHEIE_TRMIITERE_FLAG_SERVICII_FILTRATE)){
            vizualizareServiciuPropriu = intentPreluareServiciu.getBooleanExtra(Const.CHEIE_TRMIITERE_FLAG_SERVICII_FILTRATE, true);
        }

        if(tipUtilizator.equals("furnizor") || vizualizareServiciuPropriu){
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

        getImageFromFirebaseStorage();
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
                    String numeFurnizor = child.getValue(Rezervare.class).getNumeFurnizor();
                    String mailClient = child.getValue(Rezervare.class).getMailClient();
                    String stare = child.getValue(Rezervare.class).getStareRezervare();

                    Rezervare r = new Rezervare();
                    r.setDenumireProdus(serviciuDeAfisat.getDenumire());
                    r.setDescriere(serviciuDeAfisat.getDescriere());
                    r.setPret(String.valueOf(serviciuDeAfisat.getPret()));
                    r.setMailFurnizor(serviciuDeAfisat.getMailUtilizator());
                    r.setZi(zi);
                    r.setLuna(luna);
                    r.setAn(an);
                    r.setCategorie(categorie);
                    r.setNumeFurnizor(numeFurnizor);
                    r.setMailClient(mailClient);
                    r.setStareRezervare(stare);

                    if(r.getDenumireProdus().trim().equalsIgnoreCase(serviciuDeAfisat.getDenumire().trim())){
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
                        butonRezervare.setClickable(false);
                        Toast.makeText(VizualizareServiciuActivity.this, "Data selectata nu este disponibila. Alegeti alta data.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        ziOcupata = false;
                    }
                }

                if(!ziOcupata){
                    rez.setDenumireProdus(serviciuDeAfisat.getDenumire());
                    rez.setDescriere(serviciuDeAfisat.getDescriere());
                    rez.setPret(String.valueOf(serviciuDeAfisat.getPret()));
                    rez.setNumeFurnizor(serviciuDeAfisat.getNumeFurnizor());
                    rez.setCategorie(serviciuDeAfisat.getCategorie());
                    rez.setMailFurnizor(serviciuDeAfisat.getMailUtilizator());
                    rez.setMailClient(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    rez.setZi(dayOfMonth);
                    rez.setLuna(monthOfYear + 1);
                    rez.setAn(year);
                    rez.setStareRezervare(String.valueOf(StareRezervare.NEFINALIZAT));
                }

                if(ziOcupata){
                    butonRezervare.setClickable(false);
                }
                else{
                    butonRezervare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            root.child("Rezervari").push().setValue(rez);
                            root.child("RezervariFurnizori").push().setValue(rez);
                            Toast.makeText(VizualizareServiciuActivity.this, "Serviciu rezervat cu succes", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

    }

    private void getImageFromFirebaseStorage(){
        String photoName = serviciuDeAfisat.getMailUtilizator() + "/" + serviciuDeAfisat.getCategorie() + "/" + serviciuDeAfisat.getDenumire();
        StorageReference ref = storageReference.child(photoName);
        try{
            final File file = File.createTempFile("image", "jpg");
            ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imagine.setImageBitmap(bitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
