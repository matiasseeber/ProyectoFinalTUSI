package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import Database.DBComercio;
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
        View view = inflater.inflate(R.layout.fragment_comercio, container, false);
        gridView = (GridView) view.findViewById(R.id.dgvComercios);
        gridView.setVerticalSpacing(5);
        DBComercio dbComercio = new DBComercio();
        dbComercio.setContext(view.getContext());
        dbComercio.setGrid(gridView);
        dbComercio.execute();
        return view;
    }
}