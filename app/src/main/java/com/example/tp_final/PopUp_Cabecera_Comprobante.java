package com.example.tp_final;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import java.io.File;
import java.io.FileOutputStream;

public class PopUp_Cabecera_Comprobante extends AppCompatActivity {

    private TextView NombreComercio;
    private TextView FechaPedido;
    private TextView MetodoPago;
    private String[] pedidos = new String[]{};
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

        pedidos = getIntent().getStringArrayExtra("ArrayPedidosDetalle");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int) (metrics.widthPixels * 0.92),(int) (metrics.heightPixels * 0.65));


    }

    public void GeneratePDF(View view){

        if(Permiso()){
            Toast.makeText(this,"Permiso aceptado",Toast.LENGTH_LONG).show();
        }else{
            requestPermiso();
        }

        Nombre = NombreComercio.getText().toString();
        Fecha = FechaPedido.getText().toString();
        Metodo = MetodoPago.getText().toString();

        Titulo = "Tu compra del día " + Fecha + " en " + Nombre + "!";
        GenerarPdf();
    }

    public void GenerarPdf(){
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        TextPaint titulo = new TextPaint();
        TextPaint Detalle = new TextPaint();
        TextPaint Producto = new TextPaint();
        TextPaint Cantidad = new TextPaint();
        TextPaint Precio = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder(816,1054,1).create();
        PdfDocument.Page pagina = document.startPage(info);

        Canvas canvas = pagina.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap,100,100,false);
        canvas.drawBitmap(bitmapEscala,368,20,paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        titulo.setTextSize(30);
        titulo.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Titulo,300,150,titulo);

        Detalle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        String[] Array;
        int x = 10;
        int y = 200;
        for(int i = 0; i < pedidos.length; i++){

            Array = pedidos[i].split("-");

                canvas.drawText(Array[0],x,y,Producto);
                canvas.drawText("x" + Array[1],x,y + 20,Cantidad);
                canvas.drawText("$" + Array[2],x,y + 40,Precio);

                x += 10;
        }

        document.finishPage(pagina);
        File file = new File(Environment.getExternalStorageDirectory(),"Archivo.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(this,"PDF creado correctamente",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
        document.close();
    }

    public boolean Permiso(){
        int Permiso_A = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int Permiso_B = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);


        return Permiso_A == PackageManager.PERMISSION_GRANTED && Permiso_B == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermiso(){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 200){
            if (grantResults.length > 0){
                boolean escribir = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean leer = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(escribir && leer){
                    Toast.makeText(this,"Permiso concedido",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Permiso denegado",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}