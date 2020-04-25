package com.example.catalinadinu.eventive;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Furnizor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetariContFurnizorActivity extends AppCompatActivity {
    private EditText numeFurnizor;
    private EditText adresa;
    private EditText telefon;
    private EditText email;
    private CardView butonSalvare;
    private DatabaseReference root;
    private ArrayList<Furnizor> listaFurnizori = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari_cont_furnizor);
        initComponents();

        butonSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    Furnizor furnizor = createFurnizorFromView();
                    root.child("Furnizori").push().setValue(furnizor);
                    Toast.makeText(SetariContFurnizorActivity.this, "Date actualizate cu succes.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private void initComponents(){
        root = FirebaseDatabase.getInstance().getReference();
        numeFurnizor = findViewById(R.id.setari_furnizor_denumire);
        adresa = findViewById(R.id.setari_furnizor_adresa);
        email = findViewById(R.id.setari_furnizor_email);
        telefon = findViewById(R.id.setari_furnizor_telefon);
        butonSalvare = findViewById(R.id.setari_furnizor_buton_salvare);

        readFurnzorDataFromFirebase();
    }

    private boolean valid(){
        if(numeFurnizor.getText() == null || numeFurnizor.getText().toString().trim().isEmpty()){
            numeFurnizor.setError(getString(R.string.introduceti_denumirea_furnizorului));
            numeFurnizor.requestFocus();
            return false;
        }

        if(adresa.getText() == null || adresa.getText().toString().trim().isEmpty()){
            adresa.setError(getString(R.string.introduceti_adresa));
            adresa.requestFocus();
            return false;
        }

        if(email.getText() == null || email.getText().toString().trim().isEmpty()){
            email.setError(getString(R.string.introduceti_email));
            email.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            email.setError("Introduceti o adresa email valida.");
            email.requestFocus();
            return false;
        }

        if(telefon.getText() == null || telefon.getText().toString().trim().isEmpty()){
            telefon.setError(getString(R.string.introduceti_nr_telefon));
            telefon.requestFocus();
            return false;
        }

        return true;
    }

    private Furnizor createFurnizorFromView(){
        String furnizor = numeFurnizor.getText().toString();
        String adr = adresa.getText().toString();
        String mail = email.getText().toString();
        String tel = telefon.getText().toString();
        String mailConectat = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return new Furnizor(furnizor, adr, tel, mail, mailConectat);
    }

    private void readFurnzorDataFromFirebase(){
        root.child("Furnizori").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    String denumire = child.getValue(Furnizor.class).getDeumire();
                    String adresa = child.getValue(Furnizor.class).getAdresa();
                    String telefon = child.getValue(Furnizor.class).getTelefon();
                    String mail = child.getValue(Furnizor.class).getEmail();
                    String mailConectat = child.getValue(Furnizor.class).getMailContConectat();
                    Furnizor f = new Furnizor(denumire,adresa,telefon,mail,mailConectat);

                    if(mailConectat.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        listaFurnizori.add(f);
                    }
                }

                if(listaFurnizori != null && !listaFurnizori.isEmpty()){
                    int pozitie = listaFurnizori.size() - 1;
                    Furnizor furnizor = listaFurnizori.get(pozitie);
                    String den = furnizor.getDeumire();
                    String adr = furnizor.getAdresa();
                    String tel = furnizor.getTelefon();
                    String m = furnizor.getEmail();

                    numeFurnizor.setText(den);
                    adresa.setText(adr);
                    telefon.setText(tel);
                    email.setText(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
