package com.example.tp_final;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Database.DBDeleteUsuario;
import Database.DBSetUsuarioProfileInformation;
import Database.DBUpdateUsarioInfo;
import Entidades.Clientes;
import Helpers.Helpers;


public class Editar_Usuario extends AppCompatActivity {
    private Button btnModificar;
    private Button btnEliminar;
    private TextView nombre;
    private TextView apellido;
    private EditText nombreDeUsuario;
    private TextView email;
    private EditText direccion;
    private EditText contraseña;
    private TextView sexo;
    private EditText validarContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        btnModificar=(Button) findViewById(R.id.btnModificarUsuario_Cuenta);
        btnEliminar=(Button)  findViewById(R.id.btnEliminarCuenta_Cuenta);
        nombre=(TextView) findViewById(R.id.txtNombre_cuenta);
        apellido=(TextView) findViewById(R.id.txtApellido_Cuenta);
        nombreDeUsuario=(EditText) findViewById(R.id.txtNombreUsuario_Cuenta);
        email=(TextView) findViewById(R.id.txtEmail_Cuenta);
        direccion=(EditText) findViewById(R.id.txtDireccion_Cuenta);
        contraseña=(EditText) findViewById(R.id.txtContraseña_Cuenta);
        validarContraseña = (EditText) findViewById(R.id.txtVericarContraseña_Cuenta);
        sexo=(TextView) findViewById(R.id.txtSexo_Cuenta);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                modificarInformacion(v);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view){eliminarCuenta(view);}
        });


        /*

        DBSetUsuarioProfileInformation dbSetUsuarioProfileInformation = new DBSetUsuarioProfileInformation();
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getApplicationContext()));
        dbSetUsuarioProfileInformation.setClientes(clientes);
        dbSetUsuarioProfileInformation.setNombre(nombre);
        dbSetUsuarioProfileInformation.setApellido(apellido);
        dbSetUsuarioProfileInformation.setNombreDeUsuario(nombreDeUsuario);
        dbSetUsuarioProfileInformation.setEmail(email);
        dbSetUsuarioProfileInformation.setDireccion(direccion);
        dbSetUsuarioProfileInformation.setContraseña(contraseña);
        dbSetUsuarioProfileInformation.setValidarcontraseña(validarContraseña);
        dbSetUsuarioProfileInformation.setSexo(sexo);
        dbSetUsuarioProfileInformation.execute();

         */
    }

    public void ClickBack(View view){
        this.finish();
    }

    public boolean isFormValid(){
        boolean isFormValid = true;
        String requiredError = "Este campo es requerido.";

        if(nombre.getText().toString().isEmpty()){
            nombre.setError(requiredError);
            isFormValid = false;
        }
        if(apellido.getText().toString().isEmpty()){
            apellido.setError(requiredError);
            isFormValid = false;
        }

        //validar que el nombre de usuario ya existe
        if(nombreDeUsuario.getText().toString().isEmpty()){
            nombreDeUsuario.setError(requiredError);
            isFormValid = false;
        }

        //validar que el email no exista
        if(email.getText().toString().isEmpty()){
            email.setError(requiredError);
            isFormValid = false;
        }
        if(direccion.getText().toString().isEmpty()){
            direccion.setError(requiredError);
            isFormValid = false;
        }
        if((!contraseña.getText().toString().equals(validarContraseña.getText().toString()))){
            contraseña.setError("Ambas contraseñas deben coincidir");
            isFormValid = false;
        }
        if(sexo.getText().toString().isEmpty()){
            sexo.setError(requiredError);
            isFormValid = false;
        }
        return isFormValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void modificarInformacion(View view){
        if(!isFormValid()) return;
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getApplicationContext()));
        clientes.setNombre(nombre.getText().toString());
        clientes.setApellido(apellido.getText().toString());
        clientes.setNombreUsuario(nombreDeUsuario.getText().toString());
        clientes.setEmail(email.getText().toString());
        clientes.setDireccion(direccion.getText().toString());
        clientes.setContraseña(contraseña.getText().toString());
        clientes.setSexo(sexo.getText().toString());
        DBUpdateUsarioInfo DBUpdateUsarioInfo = new DBUpdateUsarioInfo();
        DBUpdateUsarioInfo.setContext(view.getContext());
        DBUpdateUsarioInfo.setClientes(clientes);
        DBUpdateUsarioInfo.execute();

    }

    public void eliminarCuenta(View view){
        //llamar dbdelet
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getApplicationContext()));

        DBDeleteUsuario delet = new DBDeleteUsuario();
        delet.setContext(view.getContext());
        delet.setId(clientes.getId());
        delet.setActivity(this);
        delet.execute();
    }

    public void clickBack (View view) {this.finish();}

}