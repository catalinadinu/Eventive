package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContClientActivity extends AppCompatActivity {

    private CardView evenimenteleMele, setari, info, deconectare;
    private FirebaseAuth.AuthStateListener authStateListener;

    private TextView textWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_client);

        evenimenteleMele = findViewById(R.id.cont_listaEvenimente);
        setari = findViewById(R.id.cont_setari);
        info = findViewById(R.id.cont_info);
        deconectare = findViewById(R.id.cont_deconectare);
        textWelcome = findViewById(R.id.cont_textWelcome);

        String stringWelcome = "Bun venit in cont, " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "! :)";
        textWelcome.setText(stringWelcome);

        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intentDeconectare = new Intent(ContClientActivity.this, LoginActivity.class);
                intentDeconectare.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentDeconectare);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContClientActivity.this, ContactAppActivity.class);
                startActivity(intent);
            }
        });

        setupFirebaseListener();

    }

    private void setupFirebaseListener(){
        Log.d("Tag",  "setting up the auth state listener");
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d("Tag", "onAuthStateChanged: signed_in");

                }else{
                    Log.d("Tag", "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }
}
