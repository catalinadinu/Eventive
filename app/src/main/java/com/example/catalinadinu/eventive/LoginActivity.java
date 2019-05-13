package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView skipConectare;
    private TextView inregistrare;
    private CardView conectare;
    private EditText email;
    private EditText parola;

//    private Task<AuthResult> ar;

    FirebaseAuth mAuth;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("Utilizatori").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("tipUtilizator");

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("Utilizatori");

    String tipUtilizator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        skipConectare = findViewById(R.id.login_skipConectare);
        inregistrare = findViewById(R.id.login_contNou);
        conectare = findViewById(R.id.login_conectare);
        email = findViewById(R.id.login_username);
        parola = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.signup_progressBar);

        skipConectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(dashboardIntent);
            }
        });

        inregistrare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inregistrareIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(inregistrareIntent);
            }
        });

        conectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conectareUtilizator();
            }
        });
    }

    public void conectareUtilizator(){
        tipUtilizator=null;
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                String textMail = email.getText().toString().trim();
                String textParola = parola.getText().toString().trim();

                if(textMail.isEmpty()){
                    email.setError("Introduceti o adresa email.");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(textMail).matches()){
                    email.setError("Introduceti o adresa email valida.");
                    email.requestFocus();
                    return;
                }

                if(textParola.isEmpty()){
                    parola.setError("Introduceti o parola");
                    parola.requestFocus();
                    return;
                }

                if(textParola.length()<6){
                    parola.setError("Introduceti o parola de minim 6 caractere.");
                    parola.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(textMail, textParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

//                        ar.getResult().getUser().getUid();//cautare tip utilizator dupa tip dupa logare
                        tipUtilizator = dataSnapshot.child(task.getResult().getUser().getUid()).child("tipUtilizator").getValue().toString();

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            if(tipUtilizator.equals("client")){
                                Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboardIntent);
                            }
                            if(tipUtilizator.equals("furnizor")){
                                Intent dashboardFurnizorIntent = new Intent(LoginActivity.this, DashboardFurnizorActivity.class);
                                dashboardFurnizorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboardFurnizorIntent);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }
}
