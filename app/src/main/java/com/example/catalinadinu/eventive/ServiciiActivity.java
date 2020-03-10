package com.example.catalinadinu.eventive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ServiciiActivity extends AppCompatActivity {
    private ListView listViewServicii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicii);

        initComponents();
    }

    private void initComponents(){
        listViewServicii = findViewById(R.id.listview_lista_servicii_pe_categorii);
    }
}
