package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class PopUp_Cabecera_Pedidos_Usuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cabecera_pedidos_usuarios);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.65));
    }

    public void ClickClose(View view){
        this.finish();
    }
}