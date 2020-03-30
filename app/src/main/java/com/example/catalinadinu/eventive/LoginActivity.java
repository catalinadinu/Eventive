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

import com.example.catalinadinu.eventive.Clase.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    FirebaseAuth mAuth;
//    FirebaseAuth user;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

        FirebaseAuth.getInstance().signOut();

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

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            tipUtilizator = dataSnapshot.child(task.getResult().getUser().getUid()).child("tipUtilizator").getValue().toString();

                            Intent intentDashboard = new Intent(LoginActivity.this, DashboardActivity.class);
                            intentDashboard.putExtra(Const.CHEIE_TRIMITERE_TIP_UTILIZATOR_LOGIN_DASH, tipUtilizator);
                            intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentDashboard);
                            finish();
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
