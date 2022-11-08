package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import Database.DBLoadAllProducts;
import Database.DBSetBussinessProfileInformation;
import Entidades.Comercio;
import Entidades.PedidoDetalle;
import LocalDB.SQLite_OpenHelper;

public class Seleccion_Comercio extends AppCompatActivity {
    private int idComercio;
    private ImageView imageView;
    private TextView nombreComercio;
    private TextView calificacion;
    private GridView gridViewProductos;
    private TextView txtCarrito;
    private TextView txtCantidad;
    private ConstraintLayout warningNoProductsClientSide;
    SQLite_OpenHelper sqLite_openHelper = new SQLite_OpenHelper(this, "TPFINAL", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_comercio);

        idComercio = getIntent().getIntExtra("idComercio", -1);
        imageView = (ImageView) findViewById(R.id.txtImgComercioSeleccion);
        nombreComercio = (TextView) findViewById(R.id.txtComercioSeleccion);
        calificacion = (TextView) findViewById(R.id.txtCantidadEstrellasComercio);
        gridViewProductos = (GridView) findViewById(R.id.dgv_ProductosComercio);
        txtCantidad = (TextView) findViewById(R.id.txtCantCarrito);
        warningNoProductsClientSide = (ConstraintLayout) findViewById(R.id.warningNoProductsClientSide);
        warningNoProductsClientSide.setVisibility(View.GONE);

        DBSetBussinessProfileInformation dbSetBussinessProfileInformation = new DBSetBussinessProfileInformation();
        dbSetBussinessProfileInformation.setImgLogo(imageView);
        Comercio comercio = new Comercio();
        comercio.setId(idComercio);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putInt("idComercioSeleccionado", idComercio);
        myEdit.putBoolean("pedidoAñadido", false);
        myEdit.commit();

        dbSetBussinessProfileInformation.setComercio(comercio);
        dbSetBussinessProfileInformation.setTextViewNombre(nombreComercio);
        dbSetBussinessProfileInformation.setcalificaciones(calificacion);
        dbSetBussinessProfileInformation.execute();
        txtCarrito = (TextView) findViewById(R.id.btnCarrito);
        DBLoadAllProducts dbLoadAllProducts = new DBLoadAllProducts();
        dbLoadAllProducts.setPibitoDeHombros(warningNoProductsClientSide);
        dbLoadAllProducts.setGrid(gridViewProductos);
        dbLoadAllProducts.setId_comercio(idComercio);
        dbLoadAllProducts.setContext(getApplicationContext());
        dbLoadAllProducts.setClientSide(true);
        dbLoadAllProducts.execute();

        txtCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantCarrito = sqLite_openHelper.ReturnQuantity();
                if(cantCarrito == 0){
                    Toast.makeText(getApplicationContext(), "Debe de tener al menos un producto seleccionado para ver su pedido.", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),Pedido_Usuario.class);
                startActivity(intent);
            }
        });

        sqLite_openHelper.deleteAll();
    }

    public void ClickBack(View view){
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int cantCarrito = sqLite_openHelper.ReturnQuantity();
        txtCantidad.setText(String.valueOf(cantCarrito));
        if(getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getBoolean("pedidoAñadido", false)){
            this.finish();
        }
    }
}