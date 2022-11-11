package com.example.tp_final;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DBForgotPasswordUser;
import Entidades.Clientes;

public class Recupero_Passw_USER extends AppCompatActivity {
    private EditText txtContrasenia_User_recupero;
    private EditText txtContrasenia_User_recupero_Conf;
    private Button btnModificarPass;



    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_recupero_passw_comercio);

        txtContrasenia_User_recupero = (EditText) findViewById(R.id.txtPasswComercio);
        txtContrasenia_User_recupero_Conf = (EditText) findViewById(R.id.txtPasswConfirmar_Comercio);
        btnModificarPass = (Button) findViewById(R.id.btnConfirmarRecuperoComercio);

        btnModificarPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {modificarContrasenia(v);}
        });
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        if(txtContrasenia_User_recupero.getText().toString().isEmpty()){
            txtContrasenia_User_recupero.setError("Este es un campo requerido.");
            isFormValid = false;
        }
        if(txtContrasenia_User_recupero_Conf.getText().toString().isEmpty()){
            txtContrasenia_User_recupero_Conf.setError("Este es un campo requerido.");
            isFormValid = false;
        }
        if(isFormValid){
            if(!txtContrasenia_User_recupero.getText().toString().equals(txtContrasenia_User_recupero_Conf.getText().toString())){
                Toast.makeText(getApplicationContext(), "Ambas contraseñas deben ser iguales", Toast.LENGTH_LONG).show();
                isFormValid = false;
            }
        }
        return isFormValid;

    }

    public void modificarContrasenia(View v){
        if(!isFormValid())
            return;
        DBForgotPasswordUser db = new DBForgotPasswordUser();
        db.setContext(getApplicationContext());
        db.setAccion("Update");
        Clientes clientes = new Clientes();
        clientes.setId(getIntent().getIntExtra("UsuarioID",0));
        clientes.setContraseña(txtContrasenia_User_recupero.getText().toString());
        db.setActivity(this);
        db.setClientes(clientes);
        db.execute();
    }

}
