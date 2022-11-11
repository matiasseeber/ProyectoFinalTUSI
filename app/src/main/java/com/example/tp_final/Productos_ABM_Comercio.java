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
import android.widget.Toast;

import Database.DBInsertProduct;
import Database.DBLoadSingleProductInfo;
import Database.DBModificarProducto;
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
    private int idProducto = -1;
    private Button btnAltaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_abm_comercio);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imgProductoAltaComercio);
        btnSeleccionarImg = (Button) findViewById(R.id.btnSeleccionarImagenAbmProducto);
        btnAltaProducto = (Button) findViewById(R.id.btnAltaProducto);
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
        idProducto = getIntent().getIntExtra("idProducto", -1);
        toolbar.setTitle(idProducto == -1 ? "Alta Producto" : "Modificar producto");

        if(idProducto != -1){
            btnAltaProducto.setText("Modificar");
            DBLoadSingleProductInfo dbLoadSingleProductInfo = new DBLoadSingleProductInfo();
            dbLoadSingleProductInfo.setContext(getApplicationContext());
            dbLoadSingleProductInfo.setPrecio(precio);
            dbLoadSingleProductInfo.setNombre(nombre);
            dbLoadSingleProductInfo.setDescripcion(descripcion);
            dbLoadSingleProductInfo.setImageView(imageView);
            dbLoadSingleProductInfo.setStock(stock);
            dbLoadSingleProductInfo.setId(idProducto);
            dbLoadSingleProductInfo.execute();
        }
    }

    public void ClickBack(View view){
        this.finish();
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        String requiredError = "Este campo es requerido.";
        if(imageView.getDrawable() == null){
            Toast.makeText(getApplicationContext(), "Se debe de seleccionar una imagen.", Toast.LENGTH_LONG).show();
            isFormValid = false;
        }
        if(nombre.getText().toString().isEmpty()) {
            nombre.setError(requiredError);
            isFormValid = false;
        }
        if(stock.getText().toString().isEmpty()) {
            stock.setError(requiredError);
            isFormValid = false;
        }
        if(descripcion.getText().toString().isEmpty()) {
            descripcion.setError(requiredError);
            isFormValid = false;
        }
        if(precio.getText().toString().isEmpty()) {
            precio.setError(requiredError);
            isFormValid = false;
        }
        return isFormValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClickAdd(View view){
        if(!isFormValid()) return;
        DBInsertProduct dbInsertProduct = new DBInsertProduct();
        Context context = view.getContext();
        Producto producto = new Producto();
        producto.setStock(Integer.parseInt(stock.getText().toString()));
        producto.setNombre(nombre.getText().toString());
        producto.setDescripcion(descripcion.getText().toString());
        producto.setPrecio(Float.parseFloat(precio.getText().toString()));
        producto.setImg(Helpers.returnImageValueFromBitmap((BitmapDrawable) imageView.getDrawable()));
        if(idProducto == -1){
            dbInsertProduct.setContext(context);
            Comercio comercio = new Comercio();
            comercio.setId(context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id",-1));
            producto.setComercio(comercio);
            dbInsertProduct.setActivity(this);
            dbInsertProduct.setProducto(producto);
            dbInsertProduct.execute();
        }
        else
        {
            DBModificarProducto dbModificarProducto = new DBModificarProducto();
            dbModificarProducto.setContext(view.getContext());
            producto.setId(idProducto);
            dbModificarProducto.setActivity(this);
            dbModificarProducto.setProducto(producto);
            dbModificarProducto.execute();
        }
    }
}