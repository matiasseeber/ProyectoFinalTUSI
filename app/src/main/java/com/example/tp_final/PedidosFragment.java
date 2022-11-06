package com.example.tp_final;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import adapters.GridAdapter;
import adapters.GridAdapterClient;

public class PedidosFragment extends Fragment {

    private TabLayout Tab;
    private ViewPager2 ViewPager;


    public PedidosFragment() {
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
        View view= inflater.inflate(R.layout.fragment_pedidos, container, false);

        Tab = (TabLayout) view.findViewById(R.id.TabPedidosCliente);
        ViewPager = (ViewPager2) view.findViewById(R.id.ViewPagerCliente);

        // SE CREAN NUEVOS OBJETOS DE LOS FRAGMENTOS NECESARIOS CARGANDO LA INFORMACION EN LOS GRID
        // SE PASAN POR PARAMETRO AL GRIDADAPTER LOS FRAGMENTOS CREADOS

        GridAdapterClient gridAdapter = new GridAdapterClient(getActivity().getSupportFragmentManager(), getLifecycle());

        ViewPager.setAdapter(gridAdapter);

        new TabLayoutMediator(Tab, ViewPager,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Pendientes");
                    break;
                case 1:
                    tab.setText("A entregar");
                    break;
                case 2:
                    tab.setText("Entregados");
                    break;
                default:
                    tab.setText("Rechazados");
            }
        }).attach();

        Tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //RELLENAR LAS
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}