package Database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Entidades.Producto;
import Helpers.Helpers;

public class DBLoadSingleProductInfo extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Producto producto = new Producto();
    private TextView nombre;
    private TextView descripcion;
    private TextView stock;
    private TextView precio;
    private ImageView imageView;
    private Bitmap bitmap;
    private int id;

    public DBLoadSingleProductInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(TextView descripcion) {
        this.descripcion = descripcion;
    }

    public TextView getStock() {
        return stock;
    }

    public void setStock(TextView stock) {
        this.stock = stock;
    }

    public TextView getPrecio() {
        return precio;
    }

    public void setPrecio(TextView precio) {
        this.precio = precio;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean modfiyProduct() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Productos where id = " + id + ";");
            rs.beforeFirst();
            if (rs.next()) {
                producto.setPrecio(rs.getFloat("precio"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setStock(rs.getInt("stock"));
                bitmap = Helpers.getBitmapFromBytes((Blob) rs.getBlob("img"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto != null ? true : false;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return modfiyProduct();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            if (nombre != null)
                nombre.setText(producto.getNombre());
            if (descripcion != null)
                descripcion.setText(producto.getDescripcion());
            if (stock != null)
                stock.setText(String.valueOf("Stock: " + producto.getStock()));
            if (precio != null)
                precio.setText(String.valueOf(producto.getPrecio()));
            if (imageView != null)
                imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "No se pudo cargar la informacion del producto seleccionado.", Toast.LENGTH_LONG).show();
        }
    }
}