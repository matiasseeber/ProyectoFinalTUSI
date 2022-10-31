package com.example.tp_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Database.DBLoadAllProducts;
import Database.DBSetBussinessProfileInformation;
import Entidades.Comercio;

public class ComercioFragment extends Fragment {

    private FloatingActionButton btnAdd;
    private TextView nombreComercio;
    private ImageView logo;
    private GridView gridView;
    private int idComercio;
    private ImageView editBtn;

    public ComercioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idComercio = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id",-1);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comercio_, container, false);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.fab);
        logo = (ImageView) view.findViewById(R.id.txtImgComercioSeleccion);
        nombreComercio = (TextView) view.findViewById(R.id.txtComercioSeleccion);
        gridView = (GridView) view.findViewById(R.id.dgv_ProductosComercio);
        editBtn = (ImageView) view.findViewById(R.id.editBussinessInformation);
        LoadAllProducts();
        LoadBussinessInfo();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Productos_ABM_Comercio.class));
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Editar_Comercio.class));
            }
        });

        return view;
    }

    public void LoadAllProducts(){
        DBLoadAllProducts dbLoadAllProducts = new DBLoadAllProducts();
        dbLoadAllProducts.setId_comercio(idComercio);
        dbLoadAllProducts.setContext(getContext());
        dbLoadAllProducts.setGrid(gridView);
        dbLoadAllProducts.execute();
    }

    public void LoadBussinessInfo(){
        DBSetBussinessProfileInformation DB = new DBSetBussinessProfileInformation();
        DB.setContext(getContext());
        Comercio comercio = new Comercio();
        comercio.setId(idComercio);
        DB.setComercio(comercio);
        DB.setTextViewNombre(nombreComercio);
        DB.setImgLogo(logo);
        DB.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllProducts();
        LoadBussinessInfo();
    }
}