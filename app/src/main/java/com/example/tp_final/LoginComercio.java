package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class LoginComercio extends AppCompatActivity {

    private EditText txtIdentificador;
    private EditText txtContraseña;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_comercio);

        txtContraseña=(EditText) findViewById(R.id.txtContraseñaC);
        txtIdentificador=(EditText) findViewById(R.id.txtIdentificador);
        btnIngresar=(Button) findViewById(R.id.btnIngresarC);

    }

    public void ClickBack(View view){
        Intent intent = new Intent(this,Seleccion_Usuario.class);
        startActivity(intent);
    }

    public void Registro(View view){
        Intent intent = new Intent(this,RegistroComercio.class);
        startActivity(intent);
    }

    public boolean Validar(){
        if (txtContraseña.getText().toString().length()==0 || txtIdentificador.getText().toString().length()==0){
            Snackbar.make(btnIngresar,"Debe ingresar sus credenciales.", BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            return false;
        }
        return true;
    }

    public void IniciarSesion(View view){
        if(Validar()){

        }
    }
}