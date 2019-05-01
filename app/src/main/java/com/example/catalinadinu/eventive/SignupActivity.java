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
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView conectare;
    private EditText mail;
    private EditText parola;
    private EditText confirmareParola;
    private CardView inregistrare;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.signup_username);
        parola = findViewById(R.id.signup_password);
        confirmareParola = findViewById(R.id.signup_confirmPassword);
        progressBar = findViewById(R.id.signup_progressBar);

        inregistrare = findViewById(R.id.signup_inregistrare);
        inregistrare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inregistrareUtilizator();
            }
        });

        conectare=findViewById(R.id.signup_conectare);
        conectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public void inregistrareUtilizator(){
        String textMail = mail.getText().toString().trim();
        String textParola = parola.getText().toString().trim();
        String textConfirmareParola = confirmareParola.getText().toString().trim();

        if(textMail.isEmpty()){
            mail.setError("Introduceti o adresa email.");
            mail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(textMail).matches()){
            mail.setError("Introduceti o adresa email valida.");
            mail.requestFocus();
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

        if(textConfirmareParola.isEmpty()){
            parola.setError("Introduceti parola pentru confirmare.");
            parola.requestFocus();
            return;
        }

        if(!textParola.matches(textConfirmareParola)){
            confirmareParola.setError("Parolele introduse nu coincid.");
            confirmareParola.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(textMail, textParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Inregistrarea s-a efectuat cu succes!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser); --nu merge functia asta, sa dea boala in ea
//    }
}
