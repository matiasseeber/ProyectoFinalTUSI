package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashSet;

import Database.DBCambiarEstadoDePedidoComercio;
import Database.DBLoadOrderInfo;
import android.widget.ImageButton;

public class PopUp_Pedidos_Comercio extends AppCompatActivity {

    private GridView dgvPedidosUsuariosComercio;
    private TextView txtTotalPedidoPendienteComercio;
    private int idPedido;
    private ImageButton btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_pedidos_comercio);

        dgvPedidosUsuariosComercio = (GridView) findViewById(R.id.grvDetallePedidoComercioSinEntregar);
        txtTotalPedidoPendienteComercio = (TextView) findViewById(R.id.txtTotalPedidoSinEntregarComercio);

        idPedido = getIntent().getIntExtra("idPedidoCabecera", -1);

        DBLoadOrderInfo dbLoadOrderInfo = new DBLoadOrderInfo();
        dbLoadOrderInfo.setContext(getApplicationContext());
        dbLoadOrderInfo.setIdPedidoCabecera(idPedido);
        dbLoadOrderInfo.setGrid(dgvPedidosUsuariosComercio);
        dbLoadOrderInfo.setTotal(txtTotalPedidoPendienteComercio);
        dbLoadOrderInfo.execute();
        btnCerrar = (ImageButton) findViewById(R.id.btncerrarPedidoComercioSinentregar);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.93));

    }

    public void Cerrar(View view){
        this.finish();
    }

    public void ModificarEstadoOrden(int estado){
        DBCambiarEstadoDePedidoComercio dbCambiarEstadoDePedidoComercio = new DBCambiarEstadoDePedidoComercio();

        if(estado == 3){
            SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            HashSet<String> detallePedido = (HashSet<String>) sh.getStringSet("detallePedido", null);
            dbCambiarEstadoDePedidoComercio.setDetallePedido(detallePedido);
        }

        dbCambiarEstadoDePedidoComercio.setIdPedido(idPedido);
        dbCambiarEstadoDePedidoComercio.setActivity(this);
        dbCambiarEstadoDePedidoComercio.setContext(getApplicationContext());
        dbCambiarEstadoDePedidoComercio.setEstado(estado);
        dbCambiarEstadoDePedidoComercio.execute();
    }

    public void OnClickAceptar(View view){
        ModificarEstadoOrden(3);
    }

    public void OnClickRechazar(View view){
        ModificarEstadoOrden(2);
    }
}