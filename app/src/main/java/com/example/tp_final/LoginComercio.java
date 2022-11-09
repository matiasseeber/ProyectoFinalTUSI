package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Database.DBForgotPassword;
import Database.DBCheckIfRecordExists;
import Database.DBQueryBussineses;

public class LoginComercio extends AppCompatActivity {

    private EditText txtIdentificador;
    private EditText txtContraseña;
    private Button btnIngresar;
    private TextView txtRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_comercio);

        txtContraseña=(EditText) findViewById(R.id.txtContraseñaC);
        txtIdentificador=(EditText) findViewById(R.id.txtIdentificador);
        btnIngresar=(Button) findViewById(R.id.btnIngresarC);
        txtRegistro= findViewById(R.id.txtRecuperoPsswC2);

        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),PopUp_ValidacionComercio.class);
                startActivity(i);
            }
        });

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
        if(!Validar())
            return;
        DBCheckIfRecordExists dbCheckIfRecordExists = new DBCheckIfRecordExists();
        dbCheckIfRecordExists.setContext(getApplicationContext());
        dbCheckIfRecordExists.setMessageExists("Inicio de sesion exitoso.");
        dbCheckIfRecordExists.setMessageNotExists("No existe ese usuario.");
        dbCheckIfRecordExists.setQuery("select * from Comercios where Cuil = " + txtIdentificador.getText().toString() + " and contraseña = '" + txtContraseña.getText().toString() + "' and estado = 1;");
        Intent intent = new Intent(getApplicationContext(), Navigation_Drawer_Comercio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dbCheckIfRecordExists.setRedirectionIntent(intent);
        dbCheckIfRecordExists.setUserName(txtIdentificador.getText().toString());
        dbCheckIfRecordExists.execute();
    }
}