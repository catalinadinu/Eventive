package com.example.catalinadinu.eventive.Clase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.catalinadinu.eventive.R;

import java.util.List;

public class ServiciiAdapter extends ArrayAdapter<Serviciu> {
    private Context context;
    private int resource;
    private List<Serviciu> servicii;
    private LayoutInflater inflater;


    public ServiciiAdapter(@NonNull Context context, int resource, List<Serviciu> servicii, LayoutInflater inflater ) {
        super(context, resource, servicii);
        this.context = context;
        this.resource = resource;
        this.servicii = servicii;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Serviciu serviciu = servicii.get(position);
        if(serviciu != null){
            adaugaNumeServiciu(view, serviciu.getDenumire());
            adaugaNumeFurnizor(view, serviciu.getNumeFurnizor());
            adaugaPret(view, serviciu.getPret());
        }
        return view;
    }

    private void adaugaNumeServiciu(View view, String numeServiciu){
        TextView tv = view.findViewById(R.id.card_servicii_denumire);
        if(numeServiciu != null && !numeServiciu.trim().isEmpty()){
            tv.setText(numeServiciu);
        }
        else {
            tv.setText(R.string.nu_exista_date);
        }
    }

    private void adaugaNumeFurnizor(View view, String numeFurnizor){
        TextView tv = view.findViewById(R.id.card_servicii_nume_furnizor);
        if(numeFurnizor != null && !numeFurnizor.trim().isEmpty()){
            tv.setText(numeFurnizor);
        }
        else {
            tv.setText(R.string.nu_exista_date);
        }
    }

    private void adaugaPret(View view, Integer pretServiciu){
        TextView tv = view.findViewById(R.id.card_servicii_pret);
        if(pretServiciu != null){
            String pret = "Pret: " + pretServiciu + " lei";
            tv.setText(pret);
        }
        else{
            tv.setText(R.string.nu_exista_date);
        }
    }
}
