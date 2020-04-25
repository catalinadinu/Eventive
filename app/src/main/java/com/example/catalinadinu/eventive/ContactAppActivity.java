package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.AppRating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactAppActivity extends AppCompatActivity{
    private TextView sendMail;
    private TextView visitSite;
    private TextView apelTelefon;
    private RatingBar ratingBar;
    private DatabaseReference root;

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
                String number = "tel:0732983274" ;
                Intent dial = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(dial);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(ContactAppActivity.this, "Rating: " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                AppRating appRating = new AppRating();
                appRating.setRating(ratingBar.getRating());
                appRating.setUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                root.child("Rating").push().setValue(appRating);
            }
        });

//        ratingBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float rating = ratingBar.getRating();
//                AppRating appRating = new AppRating();
//                appRating.setRating(rating);
//                appRating.setUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                root.child("Rating").push().setValue(appRating);
//                ratingBar.setRating(rating);
//            }
//        });
    }

    private void initComponents(){
        root = FirebaseDatabase.getInstance().getReference();
        visitSite = findViewById(R.id.contact_site);
        sendMail = findViewById(R.id.contact_app_mail);
        apelTelefon = findViewById(R.id.contact_app_phone);
        ratingBar = findViewById(R.id.contact_app_rating_bar);

        ratingBar.setRating(0);
        getRatingFromFirebase();
    }

    private void getRatingFromFirebase(){
        final ArrayList<AppRating> appRatings = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String user = child.getValue(AppRating.class).getUser();
                    float rating = child.getValue(AppRating.class).getRating();

                    AppRating appRating = new AppRating();
                    appRating.setUser(user);
                    appRating.setRating(rating);

                    if(user.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        appRatings.add(appRating);
                    }
                }
                if(appRatings != null && !appRatings.isEmpty()){
                    int pozitie = appRatings.size() - 1;
                    AppRating ar = appRatings.get(pozitie);
                    ratingBar.setRating(ar.getRating());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
