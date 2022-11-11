package com.example.tp_final;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import Database.DBInsertBussines;
import Database.DBLocalidades;
import Entidades.Comercio;
import Entidades.Localidad;
import Helpers.Helpers;

public class RegistroComercio extends AppCompatActivity {

    private Button btnRegistrarse;
    private EditText txtBussinesName;
    private EditText txtAddress;
    private Spinner spnLocalidades;
    private EditText txtEmail;
    private EditText txtVatNumber;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnPick;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

        imageView=(ImageView) findViewById(R.id.ImageComercio);
        btnPick=(Button) findViewById(R.id.btnImagenComercio);
        spnLocalidades=(Spinner) findViewById(R.id.spnLocalidad);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarseC);
        txtBussinesName = (EditText) findViewById(R.id.txtNombreComercio_ComerciosCliente);
        txtAddress = (EditText) findViewById(R.id.txtDireccionC);
        txtEmail = (EditText) findViewById(R.id.txtEmailC);
        txtVatNumber = (EditText) findViewById(R.id.txtCuilC);
        txtPassword = (EditText) findViewById(R.id.txtContraseñaComercio);
        txtConfirmPassword = (EditText) findViewById(R.id.txtContraseñaConfirmC);
        DBLocalidades dbLocalidades = new DBLocalidades();
        dbLocalidades.setContext(getApplicationContext());
        dbLocalidades.setSpinner(spnLocalidades);
        dbLocalidades.execute();

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
            }
        });
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
    }

    public void ClickBack(View view){
        Intent i = new Intent(this, LoginComercio.class);
        startActivity(i);
        this.finish();
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        String requiredError = "Este campo es requerido.";
        if(imageView.getDrawable() == null){
            Toast.makeText(getApplicationContext(), "Se debe de seleccionar una imagen.", Toast.LENGTH_LONG).show();
            isFormValid = false;
        }
        if(txtBussinesName.getText().toString().isEmpty()) {
            txtBussinesName.setError(requiredError);
            isFormValid = false;
        }
        if(txtAddress.getText().toString().isEmpty()) {
            txtAddress.setError(requiredError);
            isFormValid = false;
        }
        if(spnLocalidades.getSelectedItemPosition() == 0) {
            ((TextView)spnLocalidades.getSelectedView()).setError(requiredError);
            isFormValid = false;
        }
        if(txtVatNumber.getText().toString().isEmpty()) {
            txtVatNumber.setError(requiredError);
            isFormValid = false;
        }
        if(txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError(requiredError);
            isFormValid = false;
        }else if(!Helpers.doesStringMatchRegexp(txtEmail.getText().toString(), ".+@.+\\..+")) {
            txtEmail.setError("Este campo debe cumplir con el siguiente formato: ejemplo@gmail.com");
            isFormValid = false;
        }
        if(txtPassword.getText().toString().isEmpty()) {
            txtPassword.setError(requiredError);
            isFormValid = false;
        }
        if(txtConfirmPassword.getText().toString().isEmpty()) {
            txtConfirmPassword.setError(requiredError);
            isFormValid = false;
        }
        if(!txtPassword.getText().toString().isEmpty() && !txtConfirmPassword.getText().toString().isEmpty()){
            if(!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                txtConfirmPassword.setError("Ambas contraseñas deben ser iguales");
                isFormValid = false;
            }
        }
        return isFormValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SignUp(){
        if(!isFormValid())
            return;
        Bitmap bitmap =((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        String imageValue = Base64.getEncoder().encodeToString(bArray);
        Comercio comercio = new Comercio();
        comercio.setImageValue(imageValue);
        comercio.setAddress(txtAddress.getText().toString());
        comercio.setEmail(txtEmail.getText().toString());
        comercio.setName(txtBussinesName.getText().toString());
        comercio.setPassword(txtPassword.getText().toString());
        comercio.setVatNumber(Integer.parseInt(txtVatNumber.getText().toString()));
        comercio.setState(true);
        Localidad localidad = (Localidad)spnLocalidades.getSelectedItem();
        comercio.setLocalidad(localidad);
        DBInsertBussines dbInsertBussines = new DBInsertBussines();
        dbInsertBussines.setActivity(this);
        dbInsertBussines.setContext(getApplicationContext());
        dbInsertBussines.setComercio(comercio);
        dbInsertBussines.setMessage("El registro fue dado de alta correctamente.");
        dbInsertBussines.execute();
    }
}