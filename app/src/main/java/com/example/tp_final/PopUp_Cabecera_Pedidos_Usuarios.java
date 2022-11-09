package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import Database.DBLoadPendingOrderInfoPopUp;

public class PopUp_Cabecera_Pedidos_Usuarios extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView nombreComercio;
    private TextView fecha;
    private TextView metodoPago;
    private TextView total;
    private GridView grvDetallePedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cabecera_pedidos_usuarios);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.72));

        imgLogo = (ImageView) findViewById(R.id.imageView51);
        nombreComercio = (TextView) findViewById(R.id.txtNombreComercioPopUpPedidoCabeceraUsuarios);
        fecha = (TextView) findViewById(R.id.textView9);
        metodoPago = (TextView) findViewById(R.id.textView11);
        total = (TextView) findViewById(R.id.textView8);
        grvDetallePedido = (GridView) findViewById(R.id.grvPopUpCabeceraPedidosUsuarios);

        DBLoadPendingOrderInfoPopUp dbLoadPendingOrderInfoPopUp = new DBLoadPendingOrderInfoPopUp();
        dbLoadPendingOrderInfoPopUp.setContext(getApplicationContext());
        dbLoadPendingOrderInfoPopUp.setIdPedidoCabecera(getIntent().getIntExtra("idPedidoCabecera", -1));
        dbLoadPendingOrderInfoPopUp.setGrid(grvDetallePedido);
        dbLoadPendingOrderInfoPopUp.setFecha(fecha);
        dbLoadPendingOrderInfoPopUp.setImgLogo(imgLogo);
        dbLoadPendingOrderInfoPopUp.setNombreComercio(nombreComercio);
        dbLoadPendingOrderInfoPopUp.setMetodoPago(metodoPago);
        dbLoadPendingOrderInfoPopUp.setTotal(total);
        dbLoadPendingOrderInfoPopUp.execute();

    }

    public void ClickClose(View view){
        this.finish();
    }
}