package com.example.tp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Database.DBLoadPendingOrderInfoPopUp;

public class PopUp_Cabecera_Pedidos_Usuarios extends AppCompatActivity {

    private ImageView imgLogo;
    private TextView nombreComercio;
    private TextView fecha;
    private TextView metodoPago;
    private TextView total;
    private GridView grvDetallePedido;
    private TextView direccion;
    private TextView txtEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cabecera_pedidos_usuarios);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.75));

        imgLogo = (ImageView) findViewById(R.id.imageView51);
        nombreComercio = (TextView) findViewById(R.id.txtNombreComercioComprobante);
        fecha = (TextView) findViewById(R.id.txtFechaComprobante);
        metodoPago = (TextView) findViewById(R.id.txtMetodoPagoComprobante);
        total = (TextView) findViewById(R.id.txtTotaPedidoPopUpCabeceraPedidosUsuario);
        grvDetallePedido = (GridView) findViewById(R.id.grvPopUpCabeceraPedidosUsuarios);
        direccion = (TextView) findViewById(R.id.textView9);

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = direccion.getText().toString();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(address, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                    double latitud = addresses.get(0).getLatitude();
                    double logitude = addresses.get(0).getLongitude();
                    Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + logitude + "?q=" + address);

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
        txtEstado = (TextView) findViewById(R.id.txtEstadoPedidoCabecera);

        DBLoadPendingOrderInfoPopUp dbLoadPendingOrderInfoPopUp = new DBLoadPendingOrderInfoPopUp();
        dbLoadPendingOrderInfoPopUp.setContext(getApplicationContext());
        dbLoadPendingOrderInfoPopUp.setIdPedidoCabecera(getIntent().getIntExtra("idPedidoCabecera", -1));
        dbLoadPendingOrderInfoPopUp.setGrid(grvDetallePedido);
        dbLoadPendingOrderInfoPopUp.setFecha(fecha);
        dbLoadPendingOrderInfoPopUp.setImgLogo(imgLogo);
        dbLoadPendingOrderInfoPopUp.setNombreComercio(nombreComercio);
        dbLoadPendingOrderInfoPopUp.setMetodoPago(metodoPago);
        dbLoadPendingOrderInfoPopUp.setTotal(total);
        dbLoadPendingOrderInfoPopUp.setDireccion(direccion);
        dbLoadPendingOrderInfoPopUp.setEstado(txtEstado);
        dbLoadPendingOrderInfoPopUp.execute();

    }

    public void ClickClose(View view){
        this.finish();
    }
}