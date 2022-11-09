package com.example.tp_final;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Database.DBComercio;
import Database.DBLocalidades;
import Entidades.Localidad;

public class ComerciosFragment extends Fragment {
    private GridView gridView;
    private Spinner filtroComercioLocalidadesSpinner;
    private Spinner filtroComercioCalificacionesSpinner;


    public ComerciosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_comercio, container, false);
        filtroComercioLocalidadesSpinner = (Spinner) view.findViewById(R.id.filtroComercioLocalidadesSpinner);
        filtroComercioCalificacionesSpinner = (Spinner) view.findViewById(R.id.filtroComercioCalificacionesSpinner);
        ArrayList<String> filtro = new ArrayList<>();
        filtro.add("Elija una distancia...");
        filtro.add("< 10 KM");
        filtro.add("< 25 KM");
        filtro.add("< 50 KM");
        ArrayAdapter adapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, filtro);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        filtroComercioLocalidadesSpinner.setAdapter(adapter);

        filtro = new ArrayList<>();
        filtro.add("Elija una puntuacion");
        filtro.add("Malas"); // 1 a 2
        filtro.add("Buenas"); // 3 a 4
        filtro.add("Excelentes"); // 5
        adapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, filtro);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        filtroComercioCalificacionesSpinner.setAdapter(adapter);

        gridView = (GridView) view.findViewById(R.id.dgvComercios);
        gridView.setVerticalSpacing(5);
        DBComercio dbComercio = new DBComercio();
        dbComercio.setContext(view.getContext());
        dbComercio.setGrid(gridView);
        dbComercio.execute();
        filtroComercioLocalidadesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OnItemSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filtroComercioCalificacionesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OnItemSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    public void OnItemSelected(){
        gridView.setVerticalSpacing(5);
        DBComercio dbComercio = new DBComercio();
        dbComercio.setContext(getActivity().getApplicationContext());
        dbComercio.setGrid(gridView);
        int index = filtroComercioLocalidadesSpinner.getSelectedItemPosition();
        switch (index){
            case 1:
                dbComercio.setDistancia(10);
                break;
            case 2:
                dbComercio.setDistancia(25);
                break;
            case 3:
                dbComercio.setDistancia(50);
                break;
        }

        index = filtroComercioCalificacionesSpinner.getSelectedItemPosition();
        dbComercio.setPuntuacion(index);
        dbComercio.execute();
    }
}