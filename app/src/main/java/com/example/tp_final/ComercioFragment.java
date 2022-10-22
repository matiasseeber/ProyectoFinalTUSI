package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import Entidades.Comercio;
import adapters.BussinesAdapter;

public class ComercioFragment extends Fragment {
private GridView gridView;


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comercio, container, false);
        /*ArrayList<Comercio> elementos = new ArrayList<Comercio>();

        Comercio comercios = new Comercio();
        comercios.setName("MC'Donals");
        comercios.setId(123);

        elementos.add(comercios);

        View view = inflater.inflate(R.layout.fragment_comercio, container, false);
        gridView = (GridView) view.findViewById(R.id.dgvComercios);
        gridView.setVerticalSpacing(70);

        gridView.setAdapter(new BussinesAdapter(view.getContext(),elementos));

        return view;*/
    }
}