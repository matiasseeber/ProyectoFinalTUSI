package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import Database.DBSetBussinessProfileInformation;
import Entidades.Comercio;

public class Seleccion_Comercio extends AppCompatActivity {
    private int idComercio;
    private ImageView imageView;
    private TextView nombreComercio;
    private TextView calificacion;
    private GridView gridViewProductos;
    private TextView txtCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_comercio);

        idComercio = getIntent().getIntExtra("idComercio", -1);
        imageView = (ImageView) findViewById(R.id.txtImgComercioSeleccion);
        nombreComercio = (TextView) findViewById(R.id.txtComercioSeleccion);
        calificacion = (TextView) findViewById(R.id.txtCantidadEstrellasComercio);
        gridViewProductos = (GridView) findViewById(R.id.dgv_ProductosComercio);

        DBSetBussinessProfileInformation dbSetBussinessProfileInformation = new DBSetBussinessProfileInformation();
        dbSetBussinessProfileInformation.setImgLogo(imageView);
        Comercio comercio = new Comercio();
        comercio.setId(idComercio);
        dbSetBussinessProfileInformation.setComercio(comercio);
        dbSetBussinessProfileInformation.setTextViewNombre(nombreComercio);
        dbSetBussinessProfileInformation.setcalificaciones(calificacion);
        dbSetBussinessProfileInformation.execute();
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