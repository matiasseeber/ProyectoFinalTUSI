package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistroComercio extends AppCompatActivity {

    private Spinner spnLocalidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

        spnLocalidades=(Spinner) findViewById(R.id.spnLocalidad);

        String [] Localidades = {"Seleccione una localidad"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Localidades);
        spnLocalidades.setAdapter(adapter);
    }

    public void ClickBack(View view){
        Intent i = new Intent(this, LoginComercio.class);
        startActivity(i);
        this.finish();
    }
}