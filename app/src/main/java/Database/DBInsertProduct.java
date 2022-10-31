package Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tp_final.LoginComercio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Entidades.Producto;

public class DBInsertProduct extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Producto producto;
    private Activity activity;

    public DBInsertProduct() {
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean insertBussiness() {
        int insertedRows = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("insert into Productos (id_comercio, nombre, descripcion, stock, estado, img, precio) values (?,?,?,?,1,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, producto.getComercio().getId());
            preparedStatement.setString(2, producto.getNombre());
            preparedStatement.setString(3, producto.getDescripcion());
            preparedStatement.setInt(4, producto.getStock());
            preparedStatement.setString(5, producto.getImg());
            preparedStatement.setFloat(6, producto.getPrecio());
            insertedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return insertBussiness();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "Producto añadido exitosamente.", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo añadir el producto.", Toast.LENGTH_LONG).show();
        }
    }
}