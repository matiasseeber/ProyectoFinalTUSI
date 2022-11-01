package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Entidades.Producto;

public class DBModificarProducto extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Producto producto;
    private Activity activity;

    public DBModificarProducto() {
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean modfiyProduct() {
        int insertedRows = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Productos set nombre = ?, descripcion = ?, img = ?, stock = ?, precio = ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setString(3, producto.getImg());
            preparedStatement.setInt(4, producto.getStock());
            preparedStatement.setFloat(5, producto.getPrecio());
            preparedStatement.setFloat(6, producto.getId());
            insertedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return modfiyProduct();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "Producto modificado exitosamente.", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo modificar el producto.", Toast.LENGTH_LONG).show();
        }
    }
}