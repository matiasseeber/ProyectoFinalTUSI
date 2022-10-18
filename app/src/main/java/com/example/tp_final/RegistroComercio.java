package com.example.tp_final;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import Database.DBLocalidades;

public class RegistroComercio extends AppCompatActivity {

    private Spinner spnLocalidades;
    private ImageView photo;
    private Button btnPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

        photo=(ImageView) findViewById(R.id.ImageComercio);
        btnPick=(Button) findViewById(R.id.btnImagenComercio);
        spnLocalidades=(Spinner) findViewById(R.id.spnLocalidad);
        
        DBLocalidades dbLocalidades = new DBLocalidades();
        dbLocalidades.setContext(getApplicationContext());
        dbLocalidades.setSpinner(spnLocalidades);
        dbLocalidades.execute();

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                photo.setImageURI(result);
            }
        });

        btnPick.setOnClickListener(view -> launcher.launch("image/*"));
    }

    public void ClickBack(View view){
        Intent i = new Intent(this, LoginComercio.class);
        startActivity(i);
        this.finish();
    }
}