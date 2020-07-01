package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

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
    private Intent intent;
    private Serviciu serviciuPrimit;
    private Serviciu serviciuEditat;

    private Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_serviciu);

        initComponents();
    }

    private void initComponents(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        intent = getIntent();
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

        butonAdaugareImagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, Const.REQUEST_CODE_CHOOSE_IMAGE);
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

        if(intent.hasExtra(Const.CHEIE_TRIMITERE_FLAG_MODIFICARE_SERVICIU)){
            editareServiciu();
        }
    }

    private boolean valid(){
        if(denumireServiciu.getText() == null || denumireServiciu.getText().toString().trim().isEmpty()){
            denumireServiciu.setError(getString(R.string.eroare_denumire_pred_serv));
            denumireServiciu.requestFocus();
            return false;
        }

        if(descriere.getText() == null || descriere.getText().toString().trim().isEmpty()){
            descriere.setError(getString(R.string.introduceti_o_descriere));
            descriere.requestFocus();
            return false;
        }

        if(pret.getText() == null || pret.getText().toString().trim().isEmpty() ||
                Integer.parseInt(String.valueOf(pret.getText())) < 1){
            pret.setError(getString(R.string.introduceti_pretul_));
            pret.requestFocus();
            return false;
        }

        if(denumireFurnizor.getText() == null || denumireFurnizor.getText().toString().trim().isEmpty()){
            denumireFurnizor.setError(getString(R.string.introduceti_denumirea_furnizorului));
            denumireFurnizor.requestFocus();
            return false;
        }

        return true;
    }

    private void editareServiciu(){
        if(intent.hasExtra(Const.CHEIE_TRIMITERE_FLAG_MODIFICARE_SERVICIU) &&
                intent.hasExtra(Const.CHEIE_TRIMITERE_SERVICIU_DE_MODIFICAT)){
            serviciuPrimit = intent.getParcelableExtra(Const.CHEIE_TRIMITERE_SERVICIU_DE_MODIFICAT);
            denumireServiciu.setText(serviciuPrimit.getDenumire());
            descriere.setText(serviciuPrimit.getDescriere());
            pret.setText(String.valueOf(serviciuPrimit.getPret()));
            denumireFurnizor.setText(serviciuPrimit.getNumeFurnizor());
            ArrayAdapter adapter = (ArrayAdapter) categorieAleasa.getAdapter();
            int pozitie = adapter.getPosition(serviciuPrimit.getCategorie());
            categorieAleasa.setSelection(pozitie);

        }

        butonSalvare.setClickable(true);

        butonSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    serviciuEditat = createServiciuFromView();
                    root.child("Servicii").child(serviciuPrimit.getCategorie()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot child:dataSnapshot.getChildren()){
                                if(serviciuEditat.getDenumire().equals(serviciuPrimit.getDenumire()) && serviciuEditat.getNumeFurnizor().equals(serviciuPrimit.getNumeFurnizor())) {
                                    String cheie = child.getKey();
                                    if(serviciuEditat.getCategorie().equals(serviciuPrimit.getCategorie())){
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("denumire").setValue(serviciuEditat.getDenumire());
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("descriere").setValue(serviciuEditat.getDescriere());
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("numeFurnizor").setValue(serviciuEditat.getNumeFurnizor());
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("pret").setValue(serviciuEditat.getPret());
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("categorie").setValue(serviciuEditat.getCategorie());
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).child("mailUtilizator").setValue(serviciuEditat.getMailUtilizator());
                                        finish();
                                    }
                                    else{
                                        root.child("Servicii").child(serviciuPrimit.getCategorie()).child(cheie).removeValue();
                                        root.child("Servicii").child(serviciuEditat.getCategorie()).push().setValue(serviciuEditat);
                                        finish();
                                    }
                                    Toast.makeText(AdaugareServiciuActivity.this, "Serviciu editat", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private Serviciu createServiciuFromView(){
        String numeServiciu = denumireServiciu.getText().toString();
        String desc = descriere.getText().toString();
        Integer pr = Integer.valueOf(pret.getText().toString());
        String numeFurnizor = denumireFurnizor.getText().toString();
        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Serviciu s = new Serviciu();
        s.setCategorie(categorieAleasaString);
        s.setDenumire(numeServiciu);
        s.setDescriere(desc);
        s.setPret(pr);
        s.setNumeFurnizor(numeFurnizor);
        s.setMailUtilizator(mail);
        return s;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Const.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            imagineServiciu.setImageURI(imageUri);
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagineServiciu.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!denumireServiciu.getText().toString().isEmpty()){
                String photoName = FirebaseAuth.getInstance().getCurrentUser().getEmail() + "/" + categorieAleasaString + "/" + denumireServiciu.getText().toString();
                StorageReference ref = storageReference.child(photoName);
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), R.string.photo_upload_succes, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), R.string.photo_upload_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(this, "Completati toate campurile inainte de incarcarea fotografiei", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
