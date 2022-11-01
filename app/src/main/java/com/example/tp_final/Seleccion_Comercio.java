package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Seleccion_Comercio extends AppCompatActivity {
    private TextView txtCarrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_comercio);

        txtCarrito = (TextView) findViewById(R.id.btnCarrito);

        txtCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Pedido_Usuario.class));
            }
        });
    }

    public void ClickBack(View view){
        this.finish();
    }
}