package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ContactAppActivity extends AppCompatActivity{
    private TextView sendMail;
    private TextView visitSite;
    private TextView apelTelefon;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_app);

        initComponents();

        visitSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pdm.ase.ro/"));
                startActivity(intent);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+"help.eventive@gmail.com"));
                startActivity(Intent.createChooser(sendEmail, "Choose an email client from..."));
            }
        });

        apelTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:0732973274" ;
                Intent dial = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(dial);
            }
        });
    }

    private void initComponents(){
        visitSite = findViewById(R.id.contact_site);
        sendMail = findViewById(R.id.contact_app_mail);
        apelTelefon = findViewById(R.id.contact_app_phone);
        ratingBar = findViewById(R.id.contact_app_rating_bar);
    }


}
