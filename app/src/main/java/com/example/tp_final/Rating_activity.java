package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import Database.DBInsertCalificacion;
import Entidades.PedidoCabecera;

public class Rating_activity extends AppCompatActivity {

    private RatingBar ratingBar;
    private PedidoCabecera pedidoCabecera;
    private ImageView imgComercioPopUp;
    private TextView txtNombreComercio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar= findViewById(R.id.ratingBarPopUp);

        ratingBar.setNumStars(5);

        imgComercioPopUp = (ImageView) findViewById(R.id.imgComercioPopUp);
        txtNombreComercio = (TextView) findViewById(R.id.ComercioPopUp);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.85),(int) (metrics.heightPixels * 0.55));

        String pedido = getIntent().getStringExtra("pedido");
        Gson gson = new Gson();
        pedidoCabecera = gson.fromJson(pedido, PedidoCabecera.class);

        imgComercioPopUp.setImageBitmap(pedidoCabecera.getComercio().getBitmap());
        txtNombreComercio.setText(pedidoCabecera.getComercio().getName());
    }


    public void clickClose(View view){
        this.finish();
    }

    public void clickSend(View view){
        this.finish();
        DBInsertCalificacion dbInsertCalificacion = new DBInsertCalificacion();
        dbInsertCalificacion.setActivity(this);
        dbInsertCalificacion.setContext(getApplicationContext());
        dbInsertCalificacion.setPuntuacion(ratingBar.getRating());
        dbInsertCalificacion.setPedidoCabecera(pedidoCabecera);
        dbInsertCalificacion.execute();
    }
}