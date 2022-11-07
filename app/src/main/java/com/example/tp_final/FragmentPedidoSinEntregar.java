package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import Database.DBLoadAllNotDeliveredOrders;
import Database.DBLoadAllPendingOrders;
import Helpers.Helpers;

public class FragmentPedidoSinEntregar extends Fragment {

    private GridView grvPedidosSinEntregarComercio;
    private View msg;
    
    public FragmentPedidoSinEntregar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_sin_entregar, container, false);
        msg = (View) view.findViewById(R.id.msgNoTienesPedidosSinEntregarComercios);
        msg.setVisibility(View.GONE);
        grvPedidosSinEntregarComercio = (GridView) view.findViewById(R.id.grvPedidosSinEntregarComercio);
        LoadAllOrders();
        return view;
    }

    public void LoadAllOrders(){
        DBLoadAllNotDeliveredOrders dbLoadAllNotDeliveredOrders = new DBLoadAllNotDeliveredOrders();
        dbLoadAllNotDeliveredOrders.setId_comercio(Helpers.getUserId(getContext()));
        dbLoadAllNotDeliveredOrders.setGrid(grvPedidosSinEntregarComercio);
        dbLoadAllNotDeliveredOrders.setContext(getContext());
        dbLoadAllNotDeliveredOrders.setMsg(msg);
        dbLoadAllNotDeliveredOrders.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllOrders();
    }
}