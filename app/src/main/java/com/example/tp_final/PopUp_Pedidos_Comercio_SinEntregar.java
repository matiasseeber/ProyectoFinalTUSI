package com.example.tp_final;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Database.DBCambiarEstadoDePedidoComercio;
import Database.DBLoadOrderInfo;

public class PopUp_Pedidos_Comercio_SinEntregar extends AppCompatActivity {

    private GridView dgvPedidosUsuariosComercio;
    private TextView txtTotalPedidoPendienteComercio;
    private int idPedido;
    private Button btnEntregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_pedidos_comercio_sinentregar);

        dgvPedidosUsuariosComercio = (GridView) findViewById(R.id.grvDetallePedidoComercioSinEntregar);
        txtTotalPedidoPendienteComercio = (TextView) findViewById(R.id.txtTotalPedidoSinEntregarComercio);
        btnEntregar = (Button) findViewById(R.id.btnEntregarPedidosComercioSinEntregar);

        idPedido = getIntent().getIntExtra("idPedidoCabecera", -1);

        DBLoadOrderInfo dbLoadOrderInfo = new DBLoadOrderInfo();
        dbLoadOrderInfo.setContext(getApplicationContext());
        dbLoadOrderInfo.setIdPedidoCabecera(idPedido);
        dbLoadOrderInfo.setGrid(dgvPedidosUsuariosComercio);
        dbLoadOrderInfo.setTotal(txtTotalPedidoPendienteComercio);
        dbLoadOrderInfo.execute();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.93));

        btnEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickEntregar();
            }
        });
    }

    public void ModificarEstadoOrden(int estado){
        DBCambiarEstadoDePedidoComercio dbCambiarEstadoDePedidoComercio = new DBCambiarEstadoDePedidoComercio();
        dbCambiarEstadoDePedidoComercio.setIdPedido(idPedido);
        dbCambiarEstadoDePedidoComercio.setActivity(this);
        dbCambiarEstadoDePedidoComercio.setContext(getApplicationContext());
        dbCambiarEstadoDePedidoComercio.setEstado(estado);
        dbCambiarEstadoDePedidoComercio.execute();
    }

    public void OnClickEntregar(){
        ModificarEstadoOrden(4);
    }
}