package com.example.tp_final;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

import Database.DBLoadPendingOrderInfoPopUp;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Producto;
import adapters.DetallePedidoClienteAdapter;

public class PopUp_Cabecera_Comprobante extends AppCompatActivity {

    private TextView NombreComercio;
    private TextView FechaPedido;
    private TextView MetodoPago;
    private Button btnPdf;
    private String pedido;
    private ImageView imageView;
    private GridView gridView;
    private TextView txtTotal;
    private PedidoCabecera formatedOrder;
    String Nombre;
    String Fecha;
    String Metodo;
    String Titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TP_FINAL_TemaPopUp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cabecera_comprobante);

        NombreComercio = (TextView) findViewById(R.id.txtNombreComercioComprobante);
        FechaPedido = (TextView) findViewById(R.id.txtFechaComprobante);
        MetodoPago = (TextView) findViewById(R.id.txtMetodoPagoComprobante);
        btnPdf = (Button) findViewById(R.id.btnGenerarPDF);
        imageView = (ImageView) findViewById(R.id.imageView51);
        gridView = (GridView) findViewById(R.id.grvPopUpCabeceraComporbante);
        txtTotal = (TextView) findViewById(R.id.txtTotalPopUpCabeceraComprobante);

        pedido = getIntent().getStringExtra("pedido");
        Gson gson = new Gson();
        formatedOrder = gson.fromJson(pedido, PedidoCabecera.class);

        gridView.setAdapter(new DetallePedidoClienteAdapter(getApplicationContext(), formatedOrder));

        txtTotal.setText("total: " + formatedOrder.getTotal());
        imageView.setImageBitmap(formatedOrder.getComercio().getBitmap());
        NombreComercio.setText(formatedOrder.getComercio().getName());
        FechaPedido.setText(formatedOrder.getFecha());
        if(formatedOrder.isEfectivo()){
            MetodoPago.setText("Efectivo");
        }else{
            MetodoPago.setText(formatedOrder.getTarjeta().getTipoTarjeta());
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.95), (int) (metrics.heightPixels * 0.77));

        if (!Permiso()){
            requestPermissions();
        }

        btnPdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                Nombre = NombreComercio.getText().toString();
                Fecha = FechaPedido.getText().toString();
                Metodo = MetodoPago.getText().toString();

                Titulo = "Tu compra del día " + Fecha + " en " + Nombre + "!";
                GenerarPdf();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void GenerarPdf() {
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        TextPaint titulo = new TextPaint();
        TextPaint Detalle = new TextPaint();
        TextPaint Producto = new TextPaint();
        TextPaint Cantidad = new TextPaint();
        TextPaint Precio = new TextPaint();
        TextPaint lblProducto = new TextPaint();
        TextPaint lblCantidad = new TextPaint();
        TextPaint lblPrecio = new TextPaint();
        TextPaint lblTotal = new TextPaint();
        TextPaint Total = new TextPaint();
        Paint Linea_A = new Paint();
        Paint Linea_B = new Paint();

        Bitmap bitmap, bitmapEscala;
        Bitmap imgC1, EscalaC1;
        Bitmap imgC2, EscalaC2;
        Bitmap imgC3, EscalaC3;

        PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page pagina = document.startPage(info);

        Canvas canvas = pagina.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logocomprobante);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 270, 270, false);
        canvas.drawBitmap(bitmapEscala, 280, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titulo.setTextSize(25);
        titulo.setTextAlign(Paint.Align.CENTER);
        titulo.setColor(getResources().getColor(R.color.ic_launcher_background));
        canvas.drawText(Titulo, 410, 320, titulo);

        Detalle.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        Detalle.setTextSize(20);

        lblProducto.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        lblProducto.setTextSize(18);
        canvas.drawText("Producto", 140, 380, lblProducto);

        lblCantidad.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        lblCantidad.setTextSize(18);
        canvas.drawText("Cantidad", 378, 380, lblCantidad);

        lblPrecio.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        lblPrecio.setTextSize(18);
        canvas.drawText("Precio", 616, 380, lblPrecio);

        Linea_A.setColor(getResources().getColor(R.color.ic_launcher_background));
        Linea_A.setTextSize(5);
        canvas.drawLine(100, 395, 716, 395, Linea_A);

        Linea_B.setColor(getResources().getColor(R.color.ic_launcher_background));
        Linea_B.setTextSize(10);
        canvas.drawLine(-1, 950, 817, 950, Linea_B);

        lblTotal.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        lblTotal.setTextSize(41);
        canvas.drawText("TOTAL", 120, 1020, lblTotal);

        imgC1 = BitmapFactory.decodeResource(getResources(), R.drawable.circle2);
        EscalaC1 = Bitmap.createScaledBitmap(imgC1, 300, 300, false);
        canvas.drawBitmap(EscalaC1, -100, -100, paint);

        imgC2 = BitmapFactory.decodeResource(getResources(), R.drawable.circle3);
        EscalaC2 = Bitmap.createScaledBitmap(imgC2, 320, 320, false);
        canvas.drawBitmap(EscalaC2, 650, 600, paint);

        imgC3 = BitmapFactory.decodeResource(getResources(), R.drawable.radius);
        EscalaC3 = Bitmap.createScaledBitmap(imgC3, 250, 250, false);
        canvas.drawBitmap(EscalaC3, -140, 500, paint);

        String[] Array;
        int x = 140;
        int y = 415;

        float TotalPedido = 0;

        for (int i = 0; i < formatedOrder.getPedidoDetalles().size(); i++) {
            PedidoDetalle pedidoDetalle = formatedOrder.getPedidoDetalles().get(i);
            Entidades.Producto producto = pedidoDetalle.getProducto();
            canvas.drawText(producto.getNombre(), x, y, Producto);
            canvas.drawText("x" +pedidoDetalle.getCantidad(), 378, y, Cantidad);
            canvas.drawText("$" + producto.getPrecio(), 616, y, Precio);
            TotalPedido += formatedOrder.getTotal();
            y += 50;
        }

        Total.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        Total.setTextSize(41);
        canvas.drawText("$ " + String.valueOf(TotalPedido), 590, 1020, Total);

        //PONER EN EL NOMBRE DEL PDF, FECHA Y NUMERO DE ORDEN
        Fecha = Fecha.replace("/", "");
        document.finishPage(pagina);
        String nombrePDF = "Pedido_" + formatedOrder.getId() + "_" + Fecha;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nombrePDF + ".pdf");
        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Su PDF fue guardado en descargas", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }

    public boolean Permiso() {
        int Permiso_A = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int Permiso_B = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);


        return Permiso_A == PackageManager.PERMISSION_GRANTED && Permiso_B == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                boolean escribir = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean leer = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (escribir && leer) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


}