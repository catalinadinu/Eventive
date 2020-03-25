package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Categorii;
import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.ServiciiAdapter;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiciiActivity extends AppCompatActivity {
    private String tipUtilizator;
    private ListView listViewServicii;
    private CardView butonAdaugare;
    private ArrayList<Serviciu> listaServicii = new ArrayList<>();
    private String categorieAleasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicii);

        initComponents();

        listViewServicii.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentVizualizare = new Intent(ServiciiActivity.this, VizualizareServiciuActivity.class);
                startActivity(intentVizualizare);
            }
        });
    }

    private void initComponents(){
        Intent intentPreluareTipUtilizator = getIntent();
        tipUtilizator = intentPreluareTipUtilizator.getStringExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_DASH_LISTA_SERVICII);
        categorieAleasa = intentPreluareTipUtilizator.getStringExtra(Const.CHEIE_TRIMITERE_CATEGORIE_DASH_LISTA);

        listViewServicii = findViewById(R.id.listview_lista_servicii_pe_categorii);
        butonAdaugare = findViewById(R.id.lista_servicii_adaugare);

        if(tipUtilizator.equals("client")){
            butonAdaugare.setVisibility(View.GONE);
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

        citireServiciiPeCategorii();

        ServiciiAdapter adapter = new ServiciiAdapter(getApplicationContext(), R.layout.card_servicii, listaServicii,getLayoutInflater());
        listViewServicii.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void citireServiciiPeCategorii(){
        String numeCategorieFirebase = null;
        if(categorieAleasa.equals(Categorii.LOCATIE.toString())){
            numeCategorieFirebase = "Locatie";
        }
        if(categorieAleasa.equals(Categorii.CATERING.toString())){
            numeCategorieFirebase = "Catering";
        }
        if(categorieAleasa.equals(Categorii.DECORATIUNI.toString())){
            numeCategorieFirebase = "Decoratiuni";
        }
        if(categorieAleasa.equals(Categorii.MUZICA.toString())){
            numeCategorieFirebase = "Muzica";
        }
        if(categorieAleasa.equals(Categorii.FOTO_VIDEO.toString())){
            numeCategorieFirebase = "Foto - Video";
        }
        if(categorieAleasa.equals(Categorii.PERSONAL.toString())){
            numeCategorieFirebase = "Personal";
        }
        if(categorieAleasa.equals(Categorii.CEREMONII_RELIGIOASE.toString())){
            numeCategorieFirebase = "Ceremonii religioase";
        }
        if(categorieAleasa.equals(Categorii.ARANJAMENTE_FLORALE.toString())){
            numeCategorieFirebase = "Aranjamente florale";
        }
        if(categorieAleasa.equals(Categorii.PAPETARIE.toString())){
            numeCategorieFirebase = "Papetarie";
        }
        if(categorieAleasa.equals(Categorii.ANIMATIE_DIVERTISMENT.toString())){
            numeCategorieFirebase = "Animatie si divertisment";
        }
        if(categorieAleasa.equals(Categorii.MARTURII.toString())){
            numeCategorieFirebase = "Marturii si cadouri";
        }
        if(categorieAleasa.equals(Categorii.ALTELE.toString())){
            numeCategorieFirebase = "Altele";
        }

        final String finalNumeCategorieFirebase = numeCategorieFirebase;
        FirebaseDatabase.getInstance().getReference().child("Servicii").child(finalNumeCategorieFirebase).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String denumireServiciu = child.getValue(Serviciu.class).getDenumire();
                    String descriere = child.getValue(Serviciu.class).getDescriere();
                    Integer pret = child.getValue(Serviciu.class).getPret();
                    String denumireFurnizor = child.getValue(Serviciu.class).getNumeFurnizor();

                    Serviciu s = new Serviciu();
                    s.setDenumire(denumireServiciu);
                    s.setDescriere(descriere);
                    s.setPret(pret);
                    s.setNumeFurnizor(denumireFurnizor);
                    s.setCategorie(finalNumeCategorieFirebase);
                    listaServicii.add(s);
                }

                ArrayAdapter adapter = (ArrayAdapter) listViewServicii.getAdapter();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
