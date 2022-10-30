package com.example.tp_final;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Database.DBInsertProduct;
import Entidades.Comercio;
import Entidades.Producto;
import Helpers.Helpers;

public class Productos_ABM_Comercio extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView nombre;
    private TextView descripcion;
    private TextView stock;
    private TextView precio;
    private ImageView imageView;
    private Button btnSeleccionarImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_abm_comercio);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Alta Producto");
        imageView = (ImageView) findViewById(R.id.imgProductoAltaComercio);
        btnSeleccionarImg = (Button) findViewById(R.id.btnSeleccionarImagenAbmProducto);
        nombre = (TextView) findViewById(R.id.txtNombreProductoAlta);
        precio = (TextView) findViewById(R.id.txtPrecioAltaProducto);
        stock = (TextView) findViewById(R.id.txtStockAltaProducto);
        descripcion = (TextView) findViewById(R.id.txtDescripcionAltaProducto);

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
            }
        });
        btnSeleccionarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
    }

    public void ClickBack(View view){
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClickAdd(View view){
        Context context = view.getContext();
        DBInsertProduct dbInsertProduct = new DBInsertProduct();
        dbInsertProduct.setContext(context);
        Producto producto = new Producto();
        producto.setStock(Integer.parseInt(stock.getText().toString()));
        producto.setNombre(nombre.getText().toString());
        producto.setDescripcion(descripcion.getText().toString());
        producto.setPrecio(Float.parseFloat(precio.getText().toString()));
        producto.setImg(Helpers.returnImageValueFromBitmap((BitmapDrawable) imageView.getDrawable()));
        Comercio comercio = new Comercio();
        comercio.setId(context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id",-1));
        producto.setComercio(comercio);
        dbInsertProduct.setProducto(producto);
        dbInsertProduct.execute();
    }
}