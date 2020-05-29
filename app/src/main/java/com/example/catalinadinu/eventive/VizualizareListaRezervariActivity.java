package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.RezervariAdapter;
import com.example.catalinadinu.eventive.Clase.ServiciiAdapter;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VizualizareListaRezervariActivity extends AppCompatActivity {
    private ListView listViewRezervari;
    private ArrayList<Rezervare> listaRezervari = new ArrayList<>();
    private DatabaseReference root;
    private Intent intentVizualizareServiciuRezervat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_lista_rezervari);

        initComponents();

        listViewRezervari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rezervare rez = listaRezervari.get(position);

                intentVizualizareServiciuRezervat = new Intent(VizualizareListaRezervariActivity.this, VizualizareServiciuRezervatActivity.class);
                intentVizualizareServiciuRezervat.putExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE, rez);
                startActivity(intentVizualizareServiciuRezervat);
            }
        });
    }

    private void initComponents(){
        listViewRezervari = findViewById(R.id.viz_lista_rezervari_listview);
        root = FirebaseDatabase.getInstance().getReference();

        citireListaRezervariDinFirebase();

        RezervariAdapter adapter = new RezervariAdapter(getApplicationContext(), R.layout.card_servicii, listaRezervari, getLayoutInflater());
        listViewRezervari.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void citireListaRezervariDinFirebase(){
        root.child("Rezervari").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot rezervare : dataSnapshot.getChildren()){
                    String categorie = rezervare.getValue(Rezervare.class).getCategorie();
                    int an = rezervare.getValue(Rezervare.class).getAn();
                    int luna = rezervare.getValue(Rezervare.class).getLuna();
                    int zi = rezervare.getValue(Rezervare.class).getZi();
                    String denumireProdus = rezervare.getValue(Rezervare.class).getDenumireProdus();
                    String descriere = rezervare.getValue(Rezervare.class).getDescriere();
                    String pret = rezervare.getValue(Rezervare.class).getPret();
                    String mailClient = rezervare.getValue(Rezervare.class).getMailClient();
                    String numeFurnizor = rezervare.getValue(Rezervare.class).getNumeFurnizor();
                    String mailFurnizor = rezervare.getValue(Rezervare.class).getMailFurnizor();
                    String stare = rezervare.getValue(Rezervare.class).getStareRezervare();

                    Rezervare rez = new Rezervare(zi, luna, an, denumireProdus, descriere, pret,
                            categorie, numeFurnizor, mailClient, mailFurnizor);
                    rez.setStareRezervare(stare);

                    if(mailClient.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        listaRezervari.add(rez);
                    }
                }

                if(listaRezervari != null && !listaRezervari.isEmpty()){
                    ArrayAdapter adapter = (ArrayAdapter) listViewRezervari.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
