package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import adapters.GridAdapter;

public class PedidosFragment_Comercio extends Fragment {

    private TabLayout Tab;
    private ViewPager2 ViewPager;

    public PedidosFragment_Comercio() {
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
        View view = inflater.inflate(R.layout.fragment_pedidos__comercio, container, false);

        Tab = (TabLayout) view.findViewById(R.id.TabPedidos);
        ViewPager = (ViewPager2) view.findViewById(R.id.ViewPager);

        GridAdapter gridAdapter = new GridAdapter(getActivity().getSupportFragmentManager(), getLifecycle());

        ViewPager.setAdapter(gridAdapter);

        new TabLayoutMediator(Tab, ViewPager,(tab, position) -> {
                switch (position){
                    case 0:
                        tab.setText("Pendientes");
                        break;
                    case 1:
                        tab.setText("Sin entregar");
                        break;
                    default:
                        tab.setText("Entregados");
                }
        }).attach();

    Tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
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