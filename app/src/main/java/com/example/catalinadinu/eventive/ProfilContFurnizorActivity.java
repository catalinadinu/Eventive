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

public class ProfilContFurnizorActivity extends AppCompatActivity {

    private CardView serviciileMele, setari, info, deconectare;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SharedPreferences sp;
    private TextView textWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_cont_furnizor);

        serviciileMele = findViewById(R.id.furnizor_listaServicii);
        setari = findViewById(R.id.furnizor_setari);
        info = findViewById(R.id.furnizor_info);
        deconectare = findViewById(R.id.furnizor_deconectare);

        textWelcome = findViewById(R.id.cont_textWelcomeClient);

        String stringWelcome = "Bun venit in cont, " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "! :)";

        textWelcome.setText(stringWelcome);

        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences(Const.SP_FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Const.SP_EMAIL_KEY);
                editor.remove(Const.SP_PASSWORD_KEY);
                editor.apply();

                FirebaseAuth.getInstance().signOut();

                Intent intentDeconectare = new Intent(ProfilContFurnizorActivity.this, LoginActivity.class);
                intentDeconectare.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentDeconectare);
                finish();

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
