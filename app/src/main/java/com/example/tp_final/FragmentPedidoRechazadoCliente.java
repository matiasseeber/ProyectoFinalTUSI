package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import Database.DBLoadAllRejectedOrdersClient;
import Helpers.Helpers;

public class FragmentPedidoRechazadoCliente extends Fragment {

    private ImageView imgWarning;
    private TextView txtWarning;
    private GridView gridView;

    public FragmentPedidoRechazadoCliente() {
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
        View view = inflater.inflate(R.layout.fragment_pedido_rechazado_cliente, container, false);

        gridView = (GridView) view.findViewById(R.id.grvPedidosRechazadosCliente);
        txtWarning = (TextView) view.findViewById(R.id.textView13);
        imgWarning = (ImageView) view.findViewById(R.id.imageView50);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBLoadAllRejectedOrdersClient dbLoadAllRejectedOrdersClient = new DBLoadAllRejectedOrdersClient();
        dbLoadAllRejectedOrdersClient.setIdCliente(Helpers.getUserId(getContext()));
        dbLoadAllRejectedOrdersClient.setGrid(gridView);
        dbLoadAllRejectedOrdersClient.setMsg(imgWarning);
        dbLoadAllRejectedOrdersClient.setMsgText(txtWarning);
        dbLoadAllRejectedOrdersClient.setContext(getContext());
        dbLoadAllRejectedOrdersClient.execute();
    }
}