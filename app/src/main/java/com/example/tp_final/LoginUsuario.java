package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Database.DBRedirectOnLogin;

public class LoginUsuario extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtContraseña;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        txtContraseña=(EditText) findViewById(R.id.txtContraseñaU);
        txtNombre=(EditText) findViewById(R.id.txtNombreU);
        btnIngresar=(Button) findViewById(R.id.btnIngresoU);

    }

    public void ClickBack(View view){
        Intent intent = new Intent(this,Seleccion_Usuario.class);
        startActivity(intent);
    }

    public void Registro(View view){
        Intent intent = new Intent(this,RegistroUsuario.class);
        startActivity(intent);
    }

    public boolean Validar(){
        if (txtContraseña.getText().toString().length()==0 || txtNombre.getText().toString().length()==0){
            Snackbar.make(btnIngresar,"Debe ingresar sus credenciales.", BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            return false;
        }
        return true;
    }

    public void IniciarSesion(View view){
        if(!Validar())
            return;
        DBRedirectOnLogin redirectOnLogin = new DBRedirectOnLogin();
        redirectOnLogin.setContext(getApplicationContext());
        String query = "Select * from Clientes where nombreUsuario = '" + txtNombre.getText().toString() + "' and contraseña = '" + txtContraseña.getText().toString() + "'";
        redirectOnLogin.setQuery(query);
        Intent intent = new Intent(getApplicationContext(), Navigation_drawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        redirectOnLogin.setRedirectionIntent(intent);
        redirectOnLogin.setMessageNotExists("No existe ese usuario.");
        redirectOnLogin.setMessageExists("Inicio de sesion exitoso.");
        redirectOnLogin.execute();
    }
}