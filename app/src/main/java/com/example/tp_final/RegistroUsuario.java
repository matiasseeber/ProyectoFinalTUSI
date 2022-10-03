package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import Helpers.Helpers;

public class RegistroUsuario extends AppCompatActivity {

    private Spinner spnGenero;
    private EditText txtUserName;
    private EditText txtName;
    private EditText txtSurname;
    private EditText txtDni;
    private EditText txtEmail;
    private EditText txtAge;
    private EditText txtPassword;
    private EditText txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        txtUserName = (EditText) findViewById(R.id.txtNombreUsu);
        txtName = (EditText) findViewById(R.id.txtNombre);
        txtSurname = (EditText) findViewById(R.id.txtApellido);
        txtDni = (EditText) findViewById(R.id.txtDni);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtContraseña);
        txtConfirmPassword = (EditText) findViewById(R.id.txtContraseñaConfirm);
        txtAge = (EditText) findViewById(R.id.txtEdad);

        String [] Genero = {"Seleccione un Genero","Femenino","Masculino"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Genero);
        spnGenero.setAdapter(adapter);
        spnGenero.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    public void ClickBack (View view){
        Intent i = new Intent(this,LoginUsuario.class);
        startActivity(i);
        this.finish();
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        String requiredError = "Este campo es requerido.";
        if(txtUserName.getText().toString().isEmpty()) {
            txtUserName.setError(requiredError);
            isFormValid = false;
        }
        //if(isUserNameAlreadyInUse(txtUserName.getText().toString().isEmpty()))
        //{
        //  txtUserName.setError("Este nombre de usuario ya esta en uso.");
        //  isFormValid = false;
        //}
        if(txtName.getText().toString().isEmpty()) {
            txtName.setError(requiredError);
            isFormValid = false;
        }
        if(txtSurname.getText().toString().isEmpty()) {
            txtSurname.setError(requiredError);
            isFormValid = false;
        }
        if(txtDni.getText().toString().isEmpty()) {
            txtDni.setError(requiredError);
            isFormValid = false;
        }
        if(txtAge.getText().toString().isEmpty()) {
            txtAge.setError(requiredError);
            isFormValid = false;
        }
        if(spnGenero.getSelectedItemPosition() == 0) {
            ((TextView)spnGenero.getSelectedView()).setError(requiredError);
            isFormValid = false;
        }
        if(txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError(requiredError);
            isFormValid = false;
        }else if(Helpers.doesStringMatchRegexp(txtEmail.getText().toString(), ".+@.+\\..+")) {
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
            if(txtPassword.getText().toString() != txtConfirmPassword.getText().toString())
            txtConfirmPassword.setError("Ambas contraseñas deben ser iguales");
            isFormValid = false;
        }
        return isFormValid;
    }

    public void OnClickSignUp(View view){
        if(!isFormValid())
            return;

    }
}