package com.example.tp_final;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBSetBussinessProfileInformation;
import Database.DBUpdateBussinesInfo;
import Entidades.Comercio;
import Helpers.Helpers;

public class Editar_Comercio extends AppCompatActivity {

    private Button btnPick;
    private ImageView imgV;
    private EditText nombre;
    private TextView email;
    private TextView cuil;
    private EditText direccion;
    private EditText contraseña;
    private EditText confirmarContraseña;
    private Button btnModificarInformacionLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_comercio);

        btnPick=(Button) findViewById(R.id.btnSeleccionarImg);
        imgV = (ImageView) findViewById(R.id.imgViewComercioEdit);
        nombre = (EditText) findViewById(R.id.txtNombreUsuario_Cuenta);
        email = (TextView) findViewById(R.id.textViewEmail);
        cuil = (TextView) findViewById(R.id.textViewCUIL);
        direccion = (EditText) findViewById(R.id.editTextDireccion);
        contraseña = (EditText) findViewById(R.id.editTextTextPassword);
        confirmarContraseña = (EditText) findViewById(R.id.editTextConfirmPassword);
        btnModificarInformacionLocal = (Button) findViewById(R.id.btnModificarInformacionLocal);

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

        btnModificarInformacionLocal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                modificarInformacion(v);
            }
        });

        DBSetBussinessProfileInformation dbSetBussinessProfileInformation = new DBSetBussinessProfileInformation();
        Comercio comercio = new Comercio();
        comercio.setId(Helpers.getUserId(getApplicationContext()));
        dbSetBussinessProfileInformation.setComercio(comercio);
        dbSetBussinessProfileInformation.setImgLogo(imgV);
        dbSetBussinessProfileInformation.setTxtNombre(nombre);
        dbSetBussinessProfileInformation.setContext(getApplicationContext());
        dbSetBussinessProfileInformation.setEmail(email);
        dbSetBussinessProfileInformation.setCuil(cuil);
        dbSetBussinessProfileInformation.setDireccion(direccion);
        dbSetBussinessProfileInformation.execute();
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        String requiredError = "Este campo es requerido.";
        if(imgV.getDrawable() == null){
            Toast.makeText(getApplicationContext(), "Se debe de seleccionar una imagen.", Toast.LENGTH_LONG).show();
            isFormValid = false;
        }
        if(nombre.getText().toString().isEmpty()) {
            nombre.setError(requiredError);
            isFormValid = false;
        }
        if(direccion.getText().toString().isEmpty()) {
            direccion.setError(requiredError);
            isFormValid = false;
        }
        if(confirmarContraseña.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
            if(!contraseña.getText().toString().equals(confirmarContraseña.getText().toString())){
                confirmarContraseña.setError("Ambas contraseñas deben ser iguales");
                isFormValid = false;
            }
        }
        return isFormValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void modificarInformacion(View view){
        if(!isFormValid()) return;
        Comercio comercio = new Comercio();
        comercio.setId(Helpers.getUserId(getApplicationContext()));
        comercio.setName(nombre.getText().toString());
        comercio.setAddress(direccion.getText().toString());
        comercio.setPassword(contraseña.getText().toString());
        comercio.setImageValue(Helpers.returnImageValueFromBitmap((BitmapDrawable) imgV.getDrawable()));
        DBUpdateBussinesInfo dbUpdateBussinesInfo = new DBUpdateBussinesInfo();
        dbUpdateBussinesInfo.setContext(view.getContext());
        dbUpdateBussinesInfo.setComercio(comercio);
        dbUpdateBussinesInfo.setActivity(this);
        dbUpdateBussinesInfo.execute();
    }

    public void ClickBack(View view){
        this.finish();
    }
}