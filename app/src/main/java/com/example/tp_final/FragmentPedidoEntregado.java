package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import Database.DBLoadAllDeliveredOrders;
import Database.DBLoadAllNotDeliveredOrders;
import Helpers.Helpers;

public class FragmentPedidoEntregado extends Fragment {

    private GridView grvPedidosSinEntregarComercio;
    private View msg;

    public FragmentPedidoEntregado() {
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
        View view = inflater.inflate(R.layout.fragment_pedido_entregado, container, false);
        msg = (View) view.findViewById(R.id.msgImgSinPedidosEntregadosComercio);
        msg.setVisibility(View.GONE);
        grvPedidosSinEntregarComercio = (GridView) view.findViewById(R.id.grvPedidosEntregadosComercios);
        LoadAllOrders();
        return view;
    }

    public void LoadAllOrders(){
        DBLoadAllDeliveredOrders dbLoadAllDeliveredOrders = new DBLoadAllDeliveredOrders();
        dbLoadAllDeliveredOrders.setId_comercio(Helpers.getUserId(getContext()));
        dbLoadAllDeliveredOrders.setGrid(grvPedidosSinEntregarComercio);
        dbLoadAllDeliveredOrders.setContext(getContext());
        dbLoadAllDeliveredOrders.setMsg(msg);
        dbLoadAllDeliveredOrders.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllOrders();
    }
}