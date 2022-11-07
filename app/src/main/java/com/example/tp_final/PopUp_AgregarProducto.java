package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Set;

import Database.DBLoadSingleProductInfo;
import Entidades.Comercio;
import Entidades.PedidoDetalle;
import Entidades.Producto;
import LocalDB.SQLite_OpenHelper;

public class PopUp_AgregarProducto extends AppCompatActivity {

    private EditText txtCant;
    private TextView txtNombreProd;
    private Integer Cantidad;
    private TextView txtCantidadCarrito;
    private ImageView imgProducto;
    private TextView txtPrecio;
    private TextView txtDescripcion;
    private TextView txtStock;
    private int idProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_producto);

        txtCant= (EditText) findViewById(R.id.txtCantProdSeleccionado);
        txtNombreProd= (TextView) findViewById(R.id.txtNombreProductoSeleccionado);
        txtCantidadCarrito= (TextView) findViewById(R.id.txtCantCarrito);
        imgProducto = (ImageView) findViewById(R.id.imgProductoSeleccionado);
        txtPrecio = (TextView) findViewById(R.id.txtPrecioProdSeleccionado);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcionProdSeleccionado);
        txtStock = (TextView) findViewById(R.id.txtStockProdSeleccionado);

        idProducto = getIntent().getIntExtra("idProducto", -1);

        DBLoadSingleProductInfo dbLoadSingleProductInfo = new DBLoadSingleProductInfo();
        dbLoadSingleProductInfo.setNombre(txtNombreProd);
        dbLoadSingleProductInfo.setId(idProducto);
        dbLoadSingleProductInfo.setContext(getApplicationContext());
        dbLoadSingleProductInfo.setPrecio(txtPrecio);
        dbLoadSingleProductInfo.setStock(txtStock);
        dbLoadSingleProductInfo.setImageView(imgProducto);
        dbLoadSingleProductInfo.setDescripcion(txtDescripcion);
        dbLoadSingleProductInfo.execute();

        txtCant.setText("1");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.60));

        txtCant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String text = txtCant.getText().toString();
                if(text.isEmpty()){
                    return;
                }
                int cant = Integer.parseInt(text);
                if(cant == 0) txtCant.setText("1");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void ClickAdd(View view){
        Cantidad = Integer.parseInt(txtCant.getText().toString());
        Cantidad++;
        txtCant.setText(String.valueOf(Cantidad));
    }

    public void ClickRemove(View view){
        String text = txtCant.getText().toString();
        Cantidad = Integer.parseInt(text);
        if (Cantidad==1){
            return;
        }
        Cantidad--;
        txtCant.setText(String.valueOf(Cantidad));
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        if(Integer.parseInt(txtStock.getText().toString().replace("Stock: ", "")) < Integer.parseInt(txtCant.getText().toString())){
            txtCant.setError("La cantidad seleccionada debe ser menor o igual al stock.");
            isFormValid = false;
        }
        return isFormValid;
    }

    public void ClickAddProduct(View view){
        if(!isFormValid()) return;

        int cant = Integer.parseInt(txtCant.getText().toString());
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
        producto.setNombre(txtNombreProd.getText().toString());
        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setProducto(producto);
        pedidoDetalle.setCantidad(cant);
        SQLite_OpenHelper sqLite_openHelper = new SQLite_OpenHelper(this, "TPFINAL", null, 1);
        if(sqLite_openHelper.InsertarProductoEnPedido(pedidoDetalle)){
            Toast.makeText(getApplicationContext(), "Usted ha añadido al carrito " + cant + " unidades de " + txtNombreProd.getText().toString(), Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo agregar el producto al carrito.", Toast.LENGTH_SHORT).show();
        }
    }
}