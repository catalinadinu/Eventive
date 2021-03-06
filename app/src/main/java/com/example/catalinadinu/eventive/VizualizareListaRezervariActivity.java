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
import com.example.catalinadinu.eventive.Clase.RezervariAdapter;
import com.example.catalinadinu.eventive.Clase.ServiciiAdapter;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.example.catalinadinu.eventive.Clase.StareRezervare;
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

        listViewRezervari.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Rezervare rezervareSelectata = listaRezervari.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(VizualizareListaRezervariActivity.this);
                builder.setTitle("Doriti sa stergeti rezervarea?");
                builder.setMessage("Stergerea unei rezervari nu atrage dupa sine anularea acesteia, " +
                        "ci doar eliminarea acesteia din lista de vizualizare.");

                builder.setNegativeButton("ANULARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("STERGERE REZERVARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        root.child("Rezervari").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot rezervare: dataSnapshot.getChildren()) {
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

                                    if (rezervareSelectata.getCategorie().equalsIgnoreCase(rez.getCategorie()) &&
                                            String.valueOf(rezervareSelectata.getAn()).equalsIgnoreCase(String.valueOf(rez.getAn())) &&
                                            String.valueOf(rezervareSelectata.getLuna()).equalsIgnoreCase(String.valueOf(rez.getLuna())) &&
                                            String.valueOf(rezervareSelectata.getZi()).equalsIgnoreCase(String.valueOf(rez.getZi())) &&
                                            rezervareSelectata.getDenumireProdus().equalsIgnoreCase(rez.getDenumireProdus()) &&
                                            rezervareSelectata.getDescriere().equalsIgnoreCase(rez.getDescriere()) &&
                                            rezervareSelectata.getPret().equalsIgnoreCase(rez.getPret()) &&
                                            rezervareSelectata.getMailClient().equalsIgnoreCase(rez.getMailClient()) &&
                                            rezervareSelectata.getNumeFurnizor().equalsIgnoreCase(rez.getNumeFurnizor()) &&
                                            rezervareSelectata.getMailFurnizor().equalsIgnoreCase(rez.getMailFurnizor()) &&
                                            rezervareSelectata.getCategorie().equalsIgnoreCase(rez.getCategorie())) {
                                        String cheie = rezervare.getKey();
                                        root.child("Rezervari").child(cheie).removeValue();
                                        listaRezervari.remove(position);

                                        ArrayAdapter adapter = (ArrayAdapter) listViewRezervari.getAdapter();
                                        adapter.notifyDataSetChanged();

                                        Toast.makeText(VizualizareListaRezervariActivity.this, "Rezervarea a fost STEARSA din lista de vizualizare.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
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
                            categorie, numeFurnizor, mailClient, mailFurnizor, stare);

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
