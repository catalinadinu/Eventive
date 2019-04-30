package com.example.catalinadinu.eventive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView skipConectare;
    private TextView inregistrare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skipConectare = findViewById(R.id.login_skipConectare);
        inregistrare = findViewById(R.id.login_contNou);

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
    }
}
