package com.example.catalinadinu.eventive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.catalinadinu.eventive.Clase.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContFurnizorActivity extends AppCompatActivity {

    private CardView serviciileMele, rezervarileMele, setari, info, deconectare;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView textWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_furnizor);

        serviciileMele = findViewById(R.id.furnizor_listaServicii);
        rezervarileMele = findViewById(R.id.furnizor_listaRezervari);
        setari = findViewById(R.id.furnizor_setari);
        info = findViewById(R.id.furnizor_info);
        deconectare = findViewById(R.id.furnizor_deconectare);
        textWelcome = findViewById(R.id.cont_textWelcome);

        String stringWelcome = "Bun venit in cont, " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "! :)";
        textWelcome.setText(stringWelcome);

        serviciileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContFurnizorActivity.this, FurnizorServiciileMeleActivity.class);
                startActivity(intent);
            }
        });

        rezervarileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContFurnizorActivity.this, FurnizorVizualizareListaRezervariActivity.class);
                startActivity(intent);
            }
        });

        setari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContFurnizorActivity.this, SetariContFurnizorActivity.class);
                startActivity(intent);
            }
        });

        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(Const.SP_NUME_FISIER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("tip_utilizator");
                editor.apply();

                FirebaseAuth.getInstance().signOut();
                Intent intentDeconectare = new Intent(ContFurnizorActivity.this, LoginActivity.class);
                intentDeconectare.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentDeconectare);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContFurnizorActivity.this, ContactAppActivity.class);
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
