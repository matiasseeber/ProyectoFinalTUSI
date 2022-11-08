package com.example.tp_final;

import android.content.Context;
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

import Database.DBSetUsuarioProfileInformation;
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
    private int idComercio;

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

        idComercio = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id",-1);

        View view =  inflater.inflate(R.layout.fragment_cuenta, container, false);

        btnModificar=(Button) view.findViewById(R.id.btnModificarUsuario_Cuenta);
        btnEliminar=(Button)  view.findViewById(R.id.btnEliminarCuenta_Cuenta);
        nombre=(TextView) view.findViewById(R.id.txtNombre_usuario);
        apellido=(TextView) view.findViewById(R.id.txtApellido_usuario);
        nombreDeUsuario=(TextView) view.findViewById(R.id.txtNombreUsuario_Cuenta);
        email=(TextView) view.findViewById(R.id.txtEmail_Cuenta);
        direccion=(EditText) view.findViewById(R.id.txtDireccion_Cuenta);
        contraseña=(EditText) view.findViewById(R.id.txtContraseña_Cuenta);
        validarContraseña = (EditText) view.findViewById(R.id.txtContraseñaValidar_cuenta);
        sexo=(TextView) view.findViewById(R.id.txtSexo_Cuenta);

        loadAllControls();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Editar_Usuario.class));

            }
        });

        return view;
    }

    public void loadAllControls(){
        DBSetUsuarioProfileInformation dbSetUsuarioProfileInformation = new DBSetUsuarioProfileInformation();
        Clientes clientes = new Clientes();
        clientes.setId(idComercio);
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
    }

    public void onResume() {
        super.onResume();
        loadAllControls();
    }
}