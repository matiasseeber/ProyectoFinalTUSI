package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBForgotPassword;
import Entidades.Comercio;

public class PopUp_ValidacionComercio extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtCuil;
    private Comercio comercio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_validacion_comercio);

        txtEmail = (EditText) findViewById(R.id.txtEmail_Comercio_Recupero);
        txtCuil= (EditText) findViewById(R.id.txtCuil_Comercio_Recupero);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.85),(int) (metrics.heightPixels * 0.32));
    }


    public Boolean Validar(){
        if (txtEmail.getText().toString().isEmpty() || txtCuil.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Debe completar todos los campos.",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void clickValidate(View view){
        if (Validar()) {
            comercio = new Comercio();
            comercio.setEmail(txtEmail.getText().toString());
            comercio.setVatNumber(Integer.parseInt(txtCuil.getText().toString()));
            DBForgotPassword db = new DBForgotPassword();
            db.setContext(getApplicationContext());
            db.setComercio(comercio);
            db.setQuery("Select * from Comercios where Email = '" + comercio.getEmail() + "' and Cuil = " + comercio.getVatNumber() + " and estado = 1;");
            db.setAccion("Validation");
            db.execute();
            this.finish();
        }

    }
}