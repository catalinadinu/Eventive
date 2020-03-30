package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Furnizor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetaliiFurnizorActivity extends AppCompatActivity {
    private TextView denumire;
    private TextView adresa;
    private TextView email;
    private TextView telefon;
    private ListView alteServicii;
    private String denumireFurnizorDinIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_furnizor);

        initComponents();
    }

    private void initComponents(){
        Intent intent = getIntent();
        denumireFurnizorDinIntent = intent.getStringExtra(Const.CHEIE_TRIMITERE_DENUMIRE_FURNIZOR_DETALII);
        denumire = findViewById(R.id.detalii_furnizor_denumire_furnizor);
        adresa = findViewById(R.id.detalii_furnizor_adresa);
        email = findViewById(R.id.detalii_furnizor_email);
        telefon = findViewById(R.id.detalii_furnizor_telefon);
        alteServicii = findViewById(R.id.detalii_furnizor_alte_servicii);
        denumire.setText(denumireFurnizorDinIntent);

        citireDetaliiDinFirebase();
    }

    private void citireDetaliiDinFirebase(){
        FirebaseDatabase.getInstance().getReference().child("Furnizori")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String den = dataSnapshot.getValue(Furnizor.class).getDeumire();
                String mail = dataSnapshot.getValue(Furnizor.class).getEmail();
                String adr = dataSnapshot.getValue(Furnizor.class).getAdresa();
                String tel = dataSnapshot.getValue(Furnizor.class).getTelefon();

                denumire.setText(den);
                email.setText(mail);
                adresa.setText(adr);
                telefon.setText(tel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
