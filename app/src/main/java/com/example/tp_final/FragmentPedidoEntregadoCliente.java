package com.example.tp_final;

import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import Database.DBLoadAllDeliveredOrdersClient;
import Helpers.Helpers;

public class FragmentPedidoEntregadoCliente extends Fragment {

    private GridView gridView;
    private ImageView imgWarning;
    private TextView txtWarning;

    public FragmentPedidoEntregadoCliente() {
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
        View view = inflater.inflate(R.layout.fragment_pedido_entregado_cliente, container, false);
        imgWarning = (ImageView) view.findViewById(R.id.imageView50);
        gridView = (GridView) view.findViewById(R.id.grvPedidosEntregadosCliente);
        txtWarning = (TextView) view.findViewById(R.id.textView13);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBLoadAllDeliveredOrdersClient dbLoadAllDeliveredOrdersClient = new DBLoadAllDeliveredOrdersClient();
        dbLoadAllDeliveredOrdersClient.setMsg(imgWarning);
        dbLoadAllDeliveredOrdersClient.setGrid(gridView);
        dbLoadAllDeliveredOrdersClient.setMsgText(txtWarning);
        dbLoadAllDeliveredOrdersClient.setContext(getContext());
        dbLoadAllDeliveredOrdersClient.setIdCliente(Helpers.getUserId(getContext()));
        dbLoadAllDeliveredOrdersClient.execute();
    }
}