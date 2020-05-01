package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Furnizor;
import com.example.catalinadinu.eventive.Clase.ServiciiAdapter;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetaliiFurnizorActivity extends AppCompatActivity {
    private TextView denumire;
    private TextView adresa;
    private TextView email;
    private TextView telefon;
    private ListView alteServicii;
    private String denumireFurnizorDinIntent;
    private ArrayList<Furnizor> listaDateFurnizori = new ArrayList<>();
    private ArrayList<Serviciu> listaServicii = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_furnizor);

        initComponents();

        alteServicii.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentVizualizare = new Intent(DetaliiFurnizorActivity.this, VizualizareServiciuActivity.class);
                intentVizualizare.putExtra(Const.CHEIE_TRIMITERE_SERVICIU_VIZUALIZARE, listaServicii.get(position));
                startActivity(intentVizualizare);
            }
        });
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

        citireListaServiciiDinFirebase();

        ServiciiAdapter adapter = new ServiciiAdapter(getApplicationContext(), R.layout.card_servicii, listaServicii,getLayoutInflater());
        alteServicii.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void citireDetaliiDinFirebase(){
        FirebaseDatabase.getInstance().getReference().child("Furnizori").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String den = child.getValue(Furnizor.class).getDeumire();
                    String adr = child.getValue(Furnizor.class).getAdresa();
                    String tel = child.getValue(Furnizor.class).getTelefon();
                    String mail = child.getValue(Furnizor.class).getEmail();
                    String mailConectat = child.getValue(Furnizor.class).getMailContConectat();
                    Furnizor f = new Furnizor(den,adr,tel,mail,mailConectat);

                    if(f.getDeumire().trim().equalsIgnoreCase(denumireFurnizorDinIntent.trim())){
                        listaDateFurnizori.add(f);
                    }

                    if(listaDateFurnizori != null && !listaDateFurnizori.isEmpty()) {
                        int pozitie = listaDateFurnizori.size() - 1;
                        Furnizor furnizor = listaDateFurnizori.get(pozitie);
                        String den2 = furnizor.getDeumire();
                        String adr2 = furnizor.getAdresa();
                        String tel2 = furnizor.getTelefon();
                        String m = furnizor.getEmail();

                        denumire.setText(den2);
                        email.setText(m);
                        adresa.setText(adr2);
                        telefon.setText(tel2);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void citireListaServiciiDinFirebase(){
        FirebaseDatabase.getInstance().getReference().child("Servicii").addValueEventListener(new ValueEventListener() {
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

                        if(serv.getNumeFurnizor().trim().equalsIgnoreCase(denumireFurnizorDinIntent.trim())){
                            listaServicii.add(serv);
                        }
                    }
                }

                if(listaServicii != null && !listaServicii.isEmpty()){
                    ArrayAdapter adapter = (ArrayAdapter) alteServicii.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
