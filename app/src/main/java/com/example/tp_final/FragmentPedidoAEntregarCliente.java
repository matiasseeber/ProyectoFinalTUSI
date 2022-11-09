package com.example.tp_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.PrimitiveIterator;
import java.util.zip.GZIPInputStream;

import Database.DBLoadAllNotDeliveredOrdersClient;
import Database.DBLoadAllPendingClient;
import Helpers.Helpers;

public class FragmentPedidoAEntregarCliente extends Fragment {

    private GridView gridView;
    private ImageView img;
    private TextView txtWarning;

    public FragmentPedidoAEntregarCliente() {
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
        View view = inflater.inflate(R.layout.fragment_pedido_a_entregar_cliente, container, false);
        gridView = (GridView) view.findViewById(R.id.grvPedidosAEntregarClientes);
        img = (ImageView) view.findViewById(R.id.imageView50);
        txtWarning = (TextView) view.findViewById(R.id.textView13);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBLoadAllNotDeliveredOrdersClient dbLoadAllNotDeliveredOrdersClient = new DBLoadAllNotDeliveredOrdersClient();
        dbLoadAllNotDeliveredOrdersClient.setIdCliente(Helpers.getUserId(getContext()));
        dbLoadAllNotDeliveredOrdersClient.setContext(getContext());
        dbLoadAllNotDeliveredOrdersClient.setMsg(img);
        dbLoadAllNotDeliveredOrdersClient.setGrid(gridView);
        dbLoadAllNotDeliveredOrdersClient.setTextView(txtWarning);
        dbLoadAllNotDeliveredOrdersClient.execute();
    }
}