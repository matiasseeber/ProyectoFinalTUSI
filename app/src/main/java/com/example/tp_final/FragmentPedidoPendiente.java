package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import Database.DBLoadAllPendingOrders;
import Helpers.Helpers;

public class FragmentPedidoPendiente extends Fragment {

    private View viewNoTienesPedidosPendientesComercio;
    private GridView grvPedidosPendientesComercio;

    public FragmentPedidoPendiente() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_pendiente, container, false);
        viewNoTienesPedidosPendientesComercio = (View) view.findViewById(R.id.viewNoTienesPedidosPendientesComercio);
        viewNoTienesPedidosPendientesComercio.setVisibility(View.GONE);
        grvPedidosPendientesComercio = (GridView) view.findViewById(R.id.grvPedidosPendientesComercio);
        LoadAllOrders();
        return view;
    }

    public void LoadAllOrders(){
        DBLoadAllPendingOrders dbLoadAllPendingOrders = new DBLoadAllPendingOrders();
        dbLoadAllPendingOrders.setId_comercio(Helpers.getUserId(getContext()));
        dbLoadAllPendingOrders.setGrid(grvPedidosPendientesComercio);
        dbLoadAllPendingOrders.setContext(getContext());
        dbLoadAllPendingOrders.setMsg(viewNoTienesPedidosPendientesComercio);
        dbLoadAllPendingOrders.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllOrders();
    }
}