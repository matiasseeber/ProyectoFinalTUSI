package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import Database.DBLoadAllPendingClient;
import Helpers.Helpers;

public class FragmentPedidoPendienteCliente extends Fragment {

    private GridView gridView;
    private View warning;
    private TextView warningText;

    public FragmentPedidoPendienteCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedido_pendiente_cliente, container, false);

        gridView = (GridView) view.findViewById(R.id.grvPedidosPendientesCliente);
        warning = (View) view.findViewById(R.id.imageView50);
        warningText = (TextView) view.findViewById(R.id.textView13);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBLoadAllPendingClient dbLoadAllPendingOrdersClient = new DBLoadAllPendingClient();
        dbLoadAllPendingOrdersClient.setIdCliente(Helpers.getUserId(getContext()));
        dbLoadAllPendingOrdersClient.setContext(getContext());
        dbLoadAllPendingOrdersClient.setMsg(warning);
        dbLoadAllPendingOrdersClient.setGrid(gridView);
        dbLoadAllPendingOrdersClient.setTextView(warningText);
        dbLoadAllPendingOrdersClient.execute();
    }
}