package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Const;
import com.example.catalinadinu.eventive.Clase.Rezervare;
import com.example.catalinadinu.eventive.Clase.Serviciu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VizualizareServiciuRezervatActivity extends AppCompatActivity {
    private ImageView imagine;
    private TextView denumireServiciu;
    private TextView descriere;
    private TextView pret;
    private TextView denumireFurnizor;
    private TextView dataRezervata;
    private TextView stareRezervare;
    private TextView mailFurnizor;
    private Intent intentRezervare;
    private Rezervare rezervare;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_serviciu_rezervat);

        initComponents();
    }

    private void initComponents(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imagine = findViewById(R.id.vizualizare_serviciu_rezervat_imagine);
        denumireServiciu = findViewById(R.id.vizualizare_serviciu_rezervat_denumire_serviciu);
        descriere = findViewById(R.id.vizualizare_serviciu_rezervat_descriere_serviciu);
        pret = findViewById(R.id.vizualizare_serviciu_rezervat_pret_serviciu);
        denumireFurnizor = findViewById(R.id.vizualizare_serviciu_rezervat_nume_furnizor);
        dataRezervata = findViewById(R.id.vizualizare_serviciu_rezervat_data);
        stareRezervare = findViewById(R.id.vizualizare_serviciu_rezervat_stare);
        mailFurnizor = findViewById(R.id.vizualizare_serviciu_rezervat_mail_furnizor);

        intentRezervare = getIntent();
        if(intentRezervare.hasExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE)){
            rezervare = intentRezervare.getParcelableExtra(Const.CHEIE_TRIMITERE_VIZUALIZARE_REZERVARE);

            denumireServiciu.setText(rezervare.getDenumireProdus());
            descriere.setText(rezervare.getDescriere());
            pret.setText(String.valueOf(rezervare.getPret()) + " lei");
            denumireFurnizor.setText(rezervare.getNumeFurnizor());

            String dataRezervataString = String.valueOf(rezervare.getZi()) + "/ " + String.valueOf(rezervare.getLuna()) +
                    "/ " + String.valueOf(rezervare.getAn());
            dataRezervata.setText(dataRezervataString);

            stareRezervare.setText(String.valueOf(rezervare.getStareRezervare()));

            mailFurnizor.setText(rezervare.getMailFurnizor());
        }

        mailFurnizor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+ rezervare.getMailFurnizor()));
                startActivity(Intent.createChooser(sendEmail, "Choose an email client from..."));
            }
        });

        getImageFromFirebaseStorage();
    }

    private void getImageFromFirebaseStorage(){
        String photoName = rezervare.getMailFurnizor() + "/" + rezervare.getCategorie() + "/" + rezervare.getDenumireProdus();
        StorageReference ref = storageReference.child(photoName);
        try{
            final File file = File.createTempFile("image", "jpg");
            ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imagine.setImageBitmap(bitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
