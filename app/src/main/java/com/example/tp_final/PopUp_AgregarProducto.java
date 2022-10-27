package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Entidades.Comercio;

public class PopUp_AgregarProducto extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtCuil;
    private Comercio comercio;
    private EditText txtCant;
    private TextView txtNombreProd;
    private Integer Cantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_producto);

        txtEmail = (EditText) findViewById(R.id.txtEmail_Comercio_Recupero);
        txtCuil= (EditText) findViewById(R.id.txtCuil_Comercio_Recupero);
        txtCant= (EditText) findViewById(R.id.txtCantProdSeleccionado);
        txtNombreProd= (TextView) findViewById(R.id.txtNombreProductoSeleccionado);

        txtCant.setText("1");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.60));
    }

    public void ClickAdd(View view){
        Cantidad = Integer.parseInt(txtCant.getText().toString());
        Cantidad++;
        txtCant.setText(String.valueOf(Cantidad));
    }

    public void ClickRemove(View view){
        Cantidad = Integer.parseInt(txtCant.getText().toString());
        if (Cantidad==0){
            return;
        }
        Cantidad--;
        txtCant.setText(String.valueOf(Cantidad));
    }

    public void ClickAddProduct(View view){
        Toast.makeText(getApplicationContext(), "Usted ha añadido al carrito " + Cantidad + " unidades de " + txtNombreProd.getText().toString(), Toast.LENGTH_SHORT).show();
        this.finish();
    }
}