package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class RegistroUsuario extends AppCompatActivity {

    private Spinner spnGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        spnGenero = (Spinner) findViewById(R.id.spnGenero);

        String [] Genero = {"Seleccione un Genero","Femenino","Masculino"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Genero);
        spnGenero.setAdapter(adapter);
        spnGenero.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    public void ClickBack (View view){
        Intent i = new Intent(this,LoginUsuario.class);
        startActivity(i);
        this.finish();
    }
}