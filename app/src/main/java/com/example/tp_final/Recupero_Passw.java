package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBForgotPassword;
import Entidades.Comercio;

public class Recupero_Passw extends AppCompatActivity {
    private EditText txtContraseña_Comercio_Recupero;
    private EditText txtContraseña_Comercio_Recupero_Conf;
    private Button btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_passw);
        txtContraseña_Comercio_Recupero = (EditText) findViewById(R.id.txtContraseña_Comercio_Recupero);
        txtContraseña_Comercio_Recupero_Conf = (EditText) findViewById(R.id.txtContraseña_Comercio_Recupero_Conf);
        btnModificar = (Button) findViewById(R.id.btnConfirmarRecupero);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarContraseña(v);
            }
        });
    }

    public boolean isFormValid() {
        if (!txtContraseña_Comercio_Recupero.getText().toString().equals(txtContraseña_Comercio_Recupero_Conf.getText().toString())) {
            txtContraseña_Comercio_Recupero_Conf.setError("Ambas contraseñas deben ser iguales.");
            return false;
        }
        return true;
    }

    public void modificarContraseña(View v) {
        if (!isFormValid())
            return;
        DBForgotPassword dbForgotPassword = new DBForgotPassword();
        dbForgotPassword.setContext(getApplicationContext());
        dbForgotPassword.setAccion("Update");
        Comercio comercio = new Comercio();
        comercio.setId(getIntent().getIntExtra("ComercioID", 0));
        comercio.setPassword(txtContraseña_Comercio_Recupero.getText().toString());
        dbForgotPassword.setComercio(comercio);
        dbForgotPassword.execute();
    }
}