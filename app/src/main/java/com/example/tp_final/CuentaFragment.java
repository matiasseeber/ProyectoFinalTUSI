package com.example.tp_final;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Database.DBDeleteUsuario;
import Database.DBSetUsuarioProfileInformation;
import Database.DBUpdateUsarioInfo;
import Entidades.Clientes;
import Helpers.Helpers;

public class CuentaFragment extends Fragment {

    private Button btnModificar;
    private Button btnEliminar;
    private TextView nombre;
    private TextView apellido;
    private TextView nombreDeUsuario;
    private TextView email;
    private EditText direccion;
    private EditText contraseña;
    private TextView sexo;
    private EditText validarContraseña;
    private View view;

    public CuentaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cuenta, container, false);

        btnModificar=(Button) view.findViewById(R.id.btnModificarUsuario_Cuenta);
        btnEliminar=(Button)  view.findViewById(R.id.btnEliminarCuenta_Cuenta);
        nombre=(TextView) view.findViewById(R.id.txtNombre_cuenta);
        apellido=(TextView) view.findViewById(R.id.txtApellido_Cuenta);
        nombreDeUsuario=(TextView) view.findViewById(R.id.txtNombreUsuario_Cuenta);
        email=(TextView) view.findViewById(R.id.txtEmail_Cuenta);
        direccion=(EditText) view.findViewById(R.id.txtDireccion_Cuenta);
        contraseña=(EditText) view.findViewById(R.id.txtContraseña_Cuenta);
        validarContraseña = (EditText) view.findViewById(R.id.txtVericarContraseña_Cuenta);
        sexo=(TextView) view.findViewById(R.id.txtSexo_Cuenta);

        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

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

        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getContext()));

        DBSetUsuarioProfileInformation dbSetUsuarioProfileInformation = new DBSetUsuarioProfileInformation();

        dbSetUsuarioProfileInformation.setClientes(clientes);

        dbSetUsuarioProfileInformation.setNombreDeUsuario(nombreDeUsuario);
        dbSetUsuarioProfileInformation.setApellido(apellido);
        dbSetUsuarioProfileInformation.setNombre(nombre);
        dbSetUsuarioProfileInformation.setEmail(email);
        dbSetUsuarioProfileInformation.setDireccion(direccion);
        dbSetUsuarioProfileInformation.setSexo(sexo);
        dbSetUsuarioProfileInformation.setDireccion(direccion);
        dbSetUsuarioProfileInformation.setContraseña(contraseña);
        dbSetUsuarioProfileInformation.setBtnEliminar(btnEliminar);
        dbSetUsuarioProfileInformation.setBtnModificar(btnModificar);
        dbSetUsuarioProfileInformation.setContraseña2(validarContraseña);
        dbSetUsuarioProfileInformation.execute();

        return view;
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
        if(!contraseña.getText().toString().equals(validarContraseña.getText().toString())){
            contraseña.setError("Ambas contraseñas deben coincidir");
            isFormValid = false;
        }
        if(sexo.getText().toString().isEmpty()){
            sexo.setError(requiredError);
            isFormValid = false;
        }
        return isFormValid;
    }

    public void modificarInformacion(View view){
        if(!isFormValid()) return;
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getContext()));
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
        
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getContext()));

        DBDeleteUsuario delet = new DBDeleteUsuario();
        delet.setContext(view.getContext());
        delet.setId(clientes.getId());
        delet.execute();

    }
}