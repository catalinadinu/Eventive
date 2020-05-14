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
    private ArrayList<Serviciu> listaServiciiRezervate = new ArrayList<>();
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
                Serviciu serviciu = listaServiciiRezervate.get(position);

                Rezervare rez = null;
                for(Rezervare r:listaRezervari){
                    if(r.getNumeServiciu().equals(serviciu.getDenumire()) && r.getNumeFurnizor().equals(serviciu.getNumeFurnizor())){
                        rez = r;
                    }
                }

                intentVizualizareServiciuRezervat = new Intent(VizualizareListaRezervariActivity.this, VizualizareServiciuRezervatActivity.class);
                intentVizualizareServiciuRezervat.putExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_SERVICIU_REZERVAT, serviciu);
                intentVizualizareServiciuRezervat.putExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE, rez);
                startActivity(intentVizualizareServiciuRezervat);
            }
        });
    }

    private void initComponents(){
        listViewRezervari = findViewById(R.id.viz_lista_rezervari_listview);
        root = FirebaseDatabase.getInstance().getReference();

        citireListaRezervariDinFirebase();

        ServiciiAdapter adapter = new ServiciiAdapter(getApplicationContext(), R.layout.card_servicii, listaServiciiRezervate, getLayoutInflater());
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
                    String mailClient = rezervare.getValue(Rezervare.class).getMailClient();
                    String numeFurnizor = rezervare.getValue(Rezervare.class).getNumeFurnizor();
                    String numeServiciu = rezervare.getValue(Rezervare.class).getNumeServiciu();

                    Rezervare rez = new Rezervare(zi, luna, an, categorie, numeServiciu, numeFurnizor, mailClient);

                    if(mailClient.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        listaRezervari.add(rez);
                    }
                }
                citireServiciiDinFirebase(listaRezervari);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void citireServiciiDinFirebase(ArrayList<Rezervare> listaRezervari){
        for(final Rezervare r:listaRezervari){
            root.child("Servicii").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot categorie : dataSnapshot.getChildren()){
                        for(DataSnapshot serviciu : categorie.getChildren()){
                            String denumire = serviciu.getValue(Serviciu.class).getDenumire();
                            String descriere = serviciu.getValue(Serviciu.class).getDescriere();
                            Integer pret = serviciu.getValue(Serviciu.class).getPret();
                            String numeFurnizor = serviciu.getValue(Serviciu.class).getNumeFurnizor();
                            String categ = serviciu.getValue(Serviciu.class).getCategorie();
                            String mailUtiliz = serviciu.getValue(Serviciu.class).getMailUtilizator();

                            Serviciu serv = new Serviciu(denumire,descriere,pret,numeFurnizor,categ,mailUtiliz);

                            if(r.getNumeFurnizor().equals(serv.getNumeFurnizor()) && r.getNumeServiciu().equals(serv.getDenumire())){
                                listaServiciiRezervate.add(serv);
                            }
                        }
                    }

                    if(listaServiciiRezervate != null && !listaServiciiRezervate.isEmpty()){
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
}
