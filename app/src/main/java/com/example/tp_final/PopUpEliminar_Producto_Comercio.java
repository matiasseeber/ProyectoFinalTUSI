package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class PopUpEliminar_Producto_Comercio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_eliminar_producto_comercio);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.88),(int) (metrics.heightPixels * 0.32));
    }

    public void ClickClose(View view){
        this.finish();
    }

    public void ClickDeleteProduct(View view){
        startActivity(new Intent(getApplicationContext(),PopUpEliminar_Producto_Comercio.class));
    }
}