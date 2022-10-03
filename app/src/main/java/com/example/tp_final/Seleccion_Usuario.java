package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Seleccion_Usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_usuario);
    }

    public void ClickComercio(View view){
        Intent intent = new Intent(this,LoginComercio.class);
        startActivity(intent);
    }

    public void ClickUsuario(View view){
        Intent intent = new Intent(this,LoginUsuario.class);
        startActivity(intent);
    }
}