package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private CardView evenimenteleMele, setari, info, deconectare;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        evenimenteleMele = findViewById(R.id.cont_listaEvenimente);
        setari = findViewById(R.id.cont_setari);
        info = findViewById(R.id.cont_info);
        deconectare = findViewById(R.id.cont_deconectare);



        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intentDeconectare = new Intent(AccountActivity.this, LoginActivity.class);
                intentDeconectare.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentDeconectare);
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
                    Log.d("Tag", "onAuthStateChanged: signed_in" + user.getUid());

                }else{
                    Log.d("Tag", "onAuthStateChanged: signed_out" + user.getUid());
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
