package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBForgotPasswordUser;
import Entidades.Clientes;

public class PopUp_RecuperoContrasenia_usuario extends AppCompatActivity {

    private EditText txtDni;
    private EditText txtEmail;
    private Clientes clientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_recupero_contrasenia_usuario);

        txtDni = (EditText) findViewById(R.id.txtDni_Usuario_Recupero);
        txtEmail = (EditText) findViewById(R.id.txtEmail_Usuario_Recupero);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.85),(int) (metrics.heightPixels * 0.32));
    }

    public Boolean Validar(){
        if (txtEmail.getText().toString().isEmpty() || txtDni.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Debe completar todos los campos.",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void clickValidar(View view){
        if (Validar()){
            clientes = new Clientes();
            clientes.setDni(Integer.parseInt(txtDni.getText().toString()));
            clientes.setEmail(txtEmail.getText().toString());
            DBForgotPasswordUser db = new DBForgotPasswordUser();
            db.setContext(getApplicationContext());
            db.setClientes(clientes);
            db.setActivity(this);
            db.setQuery("Select * from Clientes where dni = '" + clientes.getDni() + "' and email = '" + clientes.getEmail() + "' and estado = 1");
            db.setAccion("Validation");
            db.execute();
        }
    }

}