package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class Productos_ABM_Comercio extends AppCompatActivity {

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_abm_comercio);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Alta Producto");
    }

    public void ClickBack(View view){
        this.finish();
    }
}