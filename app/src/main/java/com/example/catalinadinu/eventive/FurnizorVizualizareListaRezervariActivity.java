package com.example.catalinadinu.eventive;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.RezervariFurnizorAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FurnizorVizualizareListaRezervariActivity extends AppCompatActivity {
    private ListView listViewRezervari;
    private ArrayList<Rezervare> listaRezervari = new ArrayList<>();
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnizor_vizualizare_lista_rezervari);

        initComponents();
    }

    private void initComponents(){
        listViewRezervari = findViewById(R.id.furnizor_viz_lista_rezervari_listview);
        root = FirebaseDatabase.getInstance().getReference();

        citireListaRezervariDinFirebase();

        RezervariFurnizorAdapter adapter = new RezervariFurnizorAdapter(getApplicationContext(), R.layout.card_servicii, listaRezervari, getLayoutInflater());
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

                    Rezervare rez = new Rezervare(zi, luna, an, denumireProdus, descriere, pret,
                            categorie, numeFurnizor, mailClient, mailFurnizor);

                    if(mailFurnizor.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
