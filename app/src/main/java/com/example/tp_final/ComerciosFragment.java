package com.example.tp_final;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

import Database.DBComercio;
import Database.DBLocalidades;
import Entidades.Comercio;
import Entidades.Localidad;
import adapters.BussinesAdapter;

public class ComerciosFragment extends Fragment {
    private GridView gridView;
    private Spinner filtroComercioLocalidadesSpinner;


    public ComerciosFragment() {
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
        View view = inflater.inflate(R.layout.fragment_comercio, container, false);
        filtroComercioLocalidadesSpinner = (Spinner) view.findViewById(R.id.filtroComercioLocalidadesSpinner);
        DBLocalidades dbLocalidades = new DBLocalidades();
        dbLocalidades.setContext(getActivity().getApplicationContext());
        dbLocalidades.setSpinner(filtroComercioLocalidadesSpinner);
        dbLocalidades.execute();
        gridView = (GridView) view.findViewById(R.id.dgvComercios);
        gridView.setVerticalSpacing(5);
        DBComercio dbComercio = new DBComercio();
        dbComercio.setContext(view.getContext());
        dbComercio.setGrid(gridView);
        dbComercio.execute();
        filtroComercioLocalidadesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)
                    OnItemSelected((Localidad) filtroComercioLocalidadesSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void OnItemSelected(Localidad localidad){
        gridView.setVerticalSpacing(5);
        DBComercio dbComercio = new DBComercio();
        dbComercio.setContext(getActivity().getApplicationContext());
        dbComercio.setGrid(gridView);
        dbComercio.setCod_localidad(localidad.getId());
        dbComercio.execute();
    }
}