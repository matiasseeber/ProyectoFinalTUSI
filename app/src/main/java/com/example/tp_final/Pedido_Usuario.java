package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class Pedido_Usuario extends AppCompatActivity {

    private RadioButton Tarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_usuario);

        Tarjeta = (RadioButton) findViewById(R.id.rbtnTarjeta);
    }

    public void ClickBack(View view){
        this.finish();
    }

    public void Finish(View view){
        if(Tarjeta.isChecked()){
            startActivity(new Intent(this,Metodos_Pago.class));
            this.finish();
        }else{
            Toast.makeText(this,"Selecciona metodo tarjeta, bebe.",Toast.LENGTH_SHORT).show();
        }

    }
}