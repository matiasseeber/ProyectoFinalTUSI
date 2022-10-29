package com.example.tp_final;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Editar_Comercio extends AppCompatActivity {

    private Button btnPick;
    private ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_comercio);

        btnPick=(Button) findViewById(R.id.btnSeleccionarImg);
        imgV = (ImageView) findViewById(R.id.imgViewComercioEdit);

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgV.setImageURI(result);
            }
        });
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
    }


}