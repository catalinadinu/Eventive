package com.example.catalinadinu.eventive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Furnizor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetariContFurnizorActivity extends AppCompatActivity {
    private EditText numeFurnizor;
    private EditText adresa;
    private EditText telefon;
    private EditText email;
    private CardView butonSalvare;
    private DatabaseReference root;

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
                    root.child("Furnizori").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(furnizor);
                    Toast.makeText(SetariContFurnizorActivity.this, "Date actualizate cu succes.", Toast.LENGTH_SHORT).show();
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

        return new Furnizor(furnizor, adr, tel, mail);
    }

}
