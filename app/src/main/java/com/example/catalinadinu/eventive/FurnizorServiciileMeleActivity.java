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
import com.example.catalinadinu.eventive.Clase.ServiciiAdapter;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FurnizorServiciileMeleActivity extends AppCompatActivity {
    private ArrayList<Serviciu> listaServicii = new ArrayList<>();
    private ListView listView;
    private DatabaseReference root;
    private Serviciu serviciuModificat;
    private String cheie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furnizor_serviciile_mele);
        initComponents();

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(FurnizorServiciileMeleActivity.this, VizualizareServiciuActivity.class);
                 intent.putExtra(Const.CHEIE_TRIMITERE_SERVICIU_VIZUALIZARE, listaServicii.get(position));
                 intent.putExtra(Const.CHEIE_TRMIITERE_FLAG_SERVICII_FILTRATE, true);
                 startActivity(intent);
             }
         });

         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, final int pozitie, long id) {
                 serviciuModificat = listaServicii.get(pozitie);
                 AlertDialog.Builder builder = new AlertDialog.Builder(FurnizorServiciileMeleActivity.this);
                 builder.setTitle("Selectati actiunea");
                 builder.setMessage("Serviciul selectat poate fi editat sau sters");

                 builder.setPositiveButton("EDITARE", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                         Intent intent = new Intent(FurnizorServiciileMeleActivity.this, AdaugareServiciuActivity.class);
                         intent.putExtra(Const.CHEIE_TRIMITERE_FLAG_MODIFICARE_SERVICIU, Const.EDITARE);
                         intent.putExtra(Const.CHEIE_TRIMITERE_SERVICIU_DE_MODIFICAT, serviciuModificat);
                         startActivity(intent);
                     }
                 });

                 builder.setNegativeButton("STERGERE", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         root.child("Servicii").child(serviciuModificat.getCategorie()).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 for(DataSnapshot child:dataSnapshot.getChildren()){
                                     String denumire = child.getValue(Serviciu.class).getDenumire();
                                     String descriere = child.getValue(Serviciu.class).getDescriere();
                                     Integer pret = child.getValue(Serviciu.class).getPret();
                                     String numeFurnizor = child.getValue(Serviciu.class).getNumeFurnizor();
                                     String categ = child.getValue(Serviciu.class).getCategorie();
                                     String mailUtiliz = child.getValue(Serviciu.class).getMailUtilizator();

                                     Serviciu serv = new Serviciu(denumire,descriere,pret,numeFurnizor,categ,mailUtiliz);

                                     if(serv.getDenumire().equals(serviciuModificat.getDenumire()) && serv.getNumeFurnizor().equals(serviciuModificat.getNumeFurnizor())) {
                                         String cheie = child.getKey();
                                         root.child("Servicii").child(serviciuModificat.getCategorie()).child(cheie).removeValue();
                                         Toast.makeText(FurnizorServiciileMeleActivity.this, "Serviciu sters", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                     }
                 });

                 builder.setNeutralButton("ANULARE", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
                     }
                 });

                 AlertDialog alertDialog = builder.create();
                 alertDialog.show();
                 return true;
             }
         });

    }

    private void initComponents(){
        listView = findViewById(R.id.serviciile_mele_listview);
        root = FirebaseDatabase.getInstance().getReference();

        citireListaServiciiDinFirebase();

        ServiciiAdapter adapter = new ServiciiAdapter(getApplicationContext(), R.layout.card_servicii, listaServicii,getLayoutInflater());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void citireListaServiciiDinFirebase(){
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
                        String[] dateOcupate = serviciu.getValue(Serviciu.class).getDateOcupate();

                        Serviciu serv = new Serviciu(denumire,descriere,pret,numeFurnizor,categ,mailUtiliz);
                        serv.setDateOcupate(dateOcupate);

                        if(mailUtiliz.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            listaServicii.add(serv);
                        }
                    }
                }

                if(listaServicii != null && !listaServicii.isEmpty()){
                    ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
