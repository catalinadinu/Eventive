package com.example.catalinadinu.eventive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdaugareServiciuActivity extends AppCompatActivity {
    private ImageView imagineServiciu;
    private CardView butonAdaugareImagine;
    private EditText denumireServiciu;
    private EditText descriere;
    private EditText pret;
    private EditText denumireFurnizor;
    private Spinner categorieAleasa;
    private CardView butonSalvare;
    private String categorieAleasaString;

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_serviciu);

        initComponents();
    }

    private void initComponents(){
        root = FirebaseDatabase.getInstance().getReference();
        imagineServiciu = findViewById(R.id.adaugare_serviciu_imagine);
        butonAdaugareImagine = findViewById(R.id.adaugare_serviciu_buton_adaugare_imagine);
        denumireServiciu = findViewById(R.id.adaugare_serviciu_denumire_serviciu);
        descriere = findViewById(R.id.adaugare_serviciu_descriere);
        pret = findViewById(R.id.adaugare_serviciu_pret);
        denumireFurnizor = findViewById(R.id.adaugare_serviciu_nume_furnizor);
        categorieAleasa = findViewById(R.id.adaugare_serviciu_spinner_categorie);
        butonSalvare = findViewById(R.id.adaugare_serviciu_buton_salvare);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.categorii, R.layout.support_simple_spinner_dropdown_item);
        categorieAleasa.setAdapter(spinnerAdapter);

        categorieAleasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorieAleasaString = categorieAleasa.getSelectedItem().toString();
                Toast.makeText(AdaugareServiciuActivity.this, "Categorie aleasa: " + categorieAleasaString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        butonSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    Serviciu serviciu = createServiciuFromView();
                    root.child("Servicii").child(categorieAleasaString).push().setValue(serviciu);
                    Toast.makeText(AdaugareServiciuActivity.this, "Serviciu adaugat cu succes.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    private boolean valid(){
        if(denumireServiciu.getText() == null || denumireServiciu.getText().toString().trim().isEmpty()){
            denumireServiciu.setError(getString(R.string.eroare_denumire_pred_serv));
            denumireServiciu.requestFocus();
            return false;
        }

        if(descriere.getText() == null || descriere.getText().toString().trim().isEmpty()){
            descriere.setError("Introduceti o descriere!");
            descriere.requestFocus();
            return false;
        }

        if(pret.getText() == null || pret.getText().toString().trim().isEmpty() ||
                Integer.parseInt(String.valueOf(pret.getText())) < 1){
            pret.setError("Introduceti pretul!");
            pret.requestFocus();
            return false;
        }

        if(denumireFurnizor.getText() == null || denumireFurnizor.getText().toString().trim().isEmpty()){
            denumireFurnizor.setError("Introduceti denumirea furnizorului!");
            denumireFurnizor.requestFocus();
            return false;
        }

        return true;
    }

    private Serviciu createServiciuFromView(){
        String numeServiciu = denumireServiciu.getText().toString();
        String desc = descriere.getText().toString();
        Integer pr = Integer.valueOf(pret.getText().toString());
        String numeFurnizor = denumireFurnizor.getText().toString();

        return new Serviciu(numeServiciu, desc, pr, numeFurnizor, categorieAleasaString);
    }
}
