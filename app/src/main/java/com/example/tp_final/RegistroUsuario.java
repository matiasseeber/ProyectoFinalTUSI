package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import Database.DBLocalidades;
import Database.DBUsuariosInsert;
import Entidades.Clientes;
import Entidades.Localidad;
import Helpers.Helpers;
public class RegistroUsuario extends AppCompatActivity {

    private Spinner spnGenero;
    private Spinner spnLocalidades;
    private EditText txtUserName;
    private EditText txtName;
    private EditText txtSurname;
    private EditText txtDni;
    private EditText txtEmail;
    private EditText txtAge;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private ArrayList<EditText> editTexts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        spnLocalidades = (Spinner) findViewById(R.id.spnLocalidadUsuario);
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

        DBLocalidades dbLocalidades = new DBLocalidades();
        dbLocalidades.setContext(getApplicationContext());
        dbLocalidades.setSpinner(spnLocalidades);
        dbLocalidades.execute();

        spnLocalidades.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        editTexts = new ArrayList<>();
        editTexts.add(txtUserName);
        editTexts.add(txtName);
        editTexts.add(txtSurname);
        editTexts.add(txtDni);
        editTexts.add(txtAge);
        editTexts.add(txtEmail);
        editTexts.add(txtPassword);
        editTexts.add(txtConfirmPassword);
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

    public void OnClickSignUp(View view){
        if(!isFormValid())
            return;
        Clientes clientes = new Clientes();
        clientes.setNombreUsuario(txtUserName.getText().toString());
        clientes.setNombre(txtName.getText().toString());
        clientes.setApellido(txtSurname.getText().toString());
        clientes.setDni(Integer.parseInt(txtDni.getText().toString()));
        clientes.setSexo(spnGenero.getSelectedItem().toString());
        clientes.setEdad(Integer.parseInt(txtAge.getText().toString()));
        Localidad localidad = (Localidad) spnLocalidades.getSelectedItem();
        clientes.setCod_localidad(localidad.getId());
        clientes.setEmail(txtEmail.getText().toString());
        clientes.setContraseña(txtPassword.getText().toString());
        DBUsuariosInsert db = new DBUsuariosInsert(this.getApplicationContext(), clientes);
        db.setEditTexts(editTexts);
        db.execute();
    }
}