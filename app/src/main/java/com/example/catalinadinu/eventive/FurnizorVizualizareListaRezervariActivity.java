package com.example.catalinadinu.eventive;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.RezervariFurnizorAdapter;
import com.example.catalinadinu.eventive.Clase.StareRezervare;
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
    private Rezervare rezervareModificata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnizor_vizualizare_lista_rezervari);

        initComponents();

        listViewRezervari.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                rezervareModificata = listaRezervari.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(FurnizorVizualizareListaRezervariActivity.this);
                builder.setTitle("Modificati starea rezervarii");
                builder.setMessage("Stergerea unei rezervari nu atrage dupa sine anularea acesteia, " +
                        "ci doar eliminarea acesteia din lista de vizualizare.");

                builder.setPositiveButton("FINALIZATA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        root.child("RezervariFurnizori").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot rezervare: dataSnapshot.getChildren()){
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

                                    if(rezervareModificata.getCategorie().equalsIgnoreCase(rez.getCategorie()) &&
                                            String.valueOf(rezervareModificata.getAn()).equalsIgnoreCase(String.valueOf(rez.getAn())) &&
                                            String.valueOf(rezervareModificata.getLuna()).equalsIgnoreCase(String.valueOf(rez.getLuna())) &&
                                            String.valueOf(rezervareModificata.getZi()).equalsIgnoreCase(String.valueOf(rez.getZi())) &&
                                            rezervareModificata.getDenumireProdus().equalsIgnoreCase(rez.getDenumireProdus()) &&
                                            rezervareModificata.getDescriere().equalsIgnoreCase(rez.getDescriere()) &&
                                            rezervareModificata.getPret().equalsIgnoreCase(rez.getPret()) &&
                                            rezervareModificata.getMailClient().equalsIgnoreCase(rez.getMailClient()) &&
                                            rezervareModificata.getNumeFurnizor().equalsIgnoreCase(rez.getNumeFurnizor()) &&
                                            rezervareModificata.getMailFurnizor().equalsIgnoreCase(rez.getMailFurnizor()) &&
                                            rezervareModificata.getCategorie().equalsIgnoreCase(rez.getCategorie())){
                                        String cheie = rezervare.getKey();
                                        root.child("RezervariFurnizori").child(cheie).child("stareRezervare").setValue(String.valueOf(StareRezervare.FINALIZAT));
                                        Toast.makeText(FurnizorVizualizareListaRezervariActivity.this, "Rezervarea a fost marcata ca FINALIZATA.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        root.child("Rezervari").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot rezervare: dataSnapshot.getChildren()){
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

                                    if(rezervareModificata.getCategorie().equalsIgnoreCase(rez.getCategorie()) &&
                                            String.valueOf(rezervareModificata.getAn()).equalsIgnoreCase(String.valueOf(rez.getAn())) &&
                                            String.valueOf(rezervareModificata.getLuna()).equalsIgnoreCase(String.valueOf(rez.getLuna())) &&
                                            String.valueOf(rezervareModificata.getZi()).equalsIgnoreCase(String.valueOf(rez.getZi())) &&
                                            rezervareModificata.getDenumireProdus().equalsIgnoreCase(rez.getDenumireProdus()) &&
                                            rezervareModificata.getDescriere().equalsIgnoreCase(rez.getDescriere()) &&
                                            rezervareModificata.getPret().equalsIgnoreCase(rez.getPret()) &&
                                            rezervareModificata.getMailClient().equalsIgnoreCase(rez.getMailClient()) &&
                                            rezervareModificata.getNumeFurnizor().equalsIgnoreCase(rez.getNumeFurnizor()) &&
                                            rezervareModificata.getMailFurnizor().equalsIgnoreCase(rez.getMailFurnizor()) &&
                                            rezervareModificata.getCategorie().equalsIgnoreCase(rez.getCategorie())){
                                        String cheie = rezervare.getKey();
                                        root.child("Rezervari").child(cheie).child("stareRezervare").setValue(String.valueOf(StareRezervare.FINALIZAT));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("ANULATA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNeutralButton("STERGERE REZERVARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
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
        root.child("RezervariFurnizori").addValueEventListener(new ValueEventListener() {
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
