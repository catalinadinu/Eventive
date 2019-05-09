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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalinadinu.eventive.Clase.Utilizator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView conectare;
    private EditText mail;
    private EditText parola;
    private EditText confirmareParola;
    private CardView inregistrare;
    private RadioButton tipClient, tipFurnizor;
    private RadioGroup radioGroup;

    private TextView varianteCont;

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

        tipClient = findViewById(R.id.signup_client);
        tipFurnizor = findViewById(R.id.signup_furnizor);

        varianteCont = findViewById(R.id.signup_textTipUtilizator);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
//            user deja autentificat
        }

    }

    public void inregistrareUtilizator(){
        final String textMail = mail.getText().toString().trim();
        final String textParola = parola.getText().toString().trim();
        String textConfirmareParola = confirmareParola.getText().toString().trim();
        final String tipUtilizator;

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

        if (!tipClient.isChecked() && !tipFurnizor.isChecked())
        {
            varianteCont.setError("Selectati o varianta.");
            varianteCont.requestFocus();
            return;
        }

        if(tipClient.isChecked()){
            tipUtilizator="client";
        }
        else {
            tipUtilizator="furnizor";
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(textMail, textParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Utilizator user = new Utilizator(textMail, textParola, tipUtilizator);

                    FirebaseDatabase.getInstance().getReference("Utilizatori")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Inregistrarea s-a efectuat cu succes!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Acest email este deja asociat unui cont.", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

