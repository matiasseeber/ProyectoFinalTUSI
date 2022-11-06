package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import Database.DBInsertPedido;
import Entidades.Clientes;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Tarjeta;
import Helpers.Helpers;
import LocalDB.SQLite_OpenHelper;
import adapters.PedidoClienteAdapter;

public class Pedido_Usuario extends AppCompatActivity {

    private RadioButton Tarjeta;
    private RadioButton Efectivo;
    private ArrayList<PedidoDetalle> pedidoDetalleArrayList = new ArrayList<>();
    private GridView gridView;
    private ImageView delete;
    SQLite_OpenHelper sqLite_openHelper = new SQLite_OpenHelper(this, "TPFINAL", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_usuario);
        Tarjeta = (RadioButton) findViewById(R.id.rbtnTarjeta);
        Efectivo = (RadioButton) findViewById(R.id.radioButtonEfectivoPedidousuario);
        gridView = (GridView) findViewById(R.id.dgv_pedidosUsuario);
        pedidoDetalleArrayList = sqLite_openHelper.getPedidoDetalleArrayList();
        PedidoClienteAdapter pedidoClienteAdapter = new PedidoClienteAdapter(getApplicationContext(), pedidoDetalleArrayList, this);
        gridView.setAdapter(pedidoClienteAdapter);
    }
    public void ClickBack(View view){
        this.finish();
    }

    public float getTotalPedido(){
        float acum = 0;
        for (int i = 0; i < pedidoDetalleArrayList.size(); i++ ){
            PedidoDetalle pedidoDetalle = pedidoDetalleArrayList.get(i);
            acum += pedidoDetalle.getProducto().getPrecio() * pedidoDetalle.getCantidad();
        }
        return acum;
    }

    public ArrayList<String> jsonStringify(){
        Gson gson = new Gson();
        ArrayList<String> json = new ArrayList<>();
        for(int i = 0; i < pedidoDetalleArrayList.size(); i++){
            String stringifiedObject = gson.toJson(pedidoDetalleArrayList.get(i));
            json.add(stringifiedObject);
        }
        return json;
    }

    public String jsonStringifyPedidoCabecera(PedidoCabecera params){
        Gson gson = new Gson();
        String pedidoCabecera = gson.toJson(params);
        return pedidoCabecera;
    }

    public void Finish(View view){
        PedidoCabecera pedidoCabecera = new PedidoCabecera();
        pedidoCabecera.setPedidoDetalles(pedidoDetalleArrayList);
        pedidoCabecera.setEfectivo(false);
        pedidoCabecera.setTotal(getTotalPedido());
        Clientes clientes = new Clientes();
        clientes.setId(Helpers.getUserId(getApplicationContext()));
        pedidoCabecera.setCliente(clientes);
        if(pedidoDetalleArrayList.size() == 0){
            Toast.makeText(getApplicationContext(), "Debe de añadir al menos un producto para realizar una compra.", Toast.LENGTH_LONG).show();
            return;
        }
        if(Tarjeta.isChecked()){
            Intent intent = new Intent(this,Metodos_Pago.class);
            intent.putStringArrayListExtra("detallePedidos", jsonStringify());
            intent.putExtra("pedidoCabecera", jsonStringifyPedidoCabecera(pedidoCabecera));
            startActivity(intent);
            this.finish();
        }else if(Efectivo.isChecked()){
            if(pedidoDetalleArrayList.size() == 0){
                Toast.makeText(getApplicationContext(), "No se puede realizar un pedido sin ningun producto seleccionado.", Toast.LENGTH_LONG).show();
                return;
            }

            DBInsertPedido dbInsertPedido = new DBInsertPedido();
            dbInsertPedido.setActivity(this);
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setId(-1);
            pedidoCabecera.setTarjeta(tarjeta);
            pedidoCabecera.setEfectivo(true);
            pedidoCabecera.setTotal(getTotalPedido());
            dbInsertPedido.setPedidoCabecera(pedidoCabecera);
            dbInsertPedido.setContext(getApplicationContext());
            dbInsertPedido.execute();
        }else{
            Toast.makeText(getApplicationContext(), "Debe seleccionar un metodo de pago.", Toast.LENGTH_LONG).show();
        }
    }
}