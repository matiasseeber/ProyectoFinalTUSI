package Database;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.GridView;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Entidades.Comercio;
import Entidades.Producto;
import Helpers.Helpers;
import adapters.BussinesAdapter;
import adapters.ProductsAdapter;

public class DBLoadAllProducts extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int id_comercio;

    private ArrayList<Producto> productos;

    public DBLoadAllProducts() {
    }

    public int getId_comercio() {
        return id_comercio;
    }

    public void setId_comercio(int id_comercio) {
        this.id_comercio = id_comercio;
    }

    public GridView getGrid() {
        return grid;
    }

    public void setGrid(GridView grid) {
        this.grid = grid;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void CargarComercios() {
        productos = new ArrayList<Producto>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DataDB.urlMySQL,
                    DataDB.user,
                    DataDB.pass
            );
            Statement st = con.createStatement();
            String query = "Select * from Productos where id_comercio = "+ id_comercio +";";
            ResultSet rs = st.executeQuery(
                    query
            );

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setNombre(rs.getString("nombre"));
                producto.setId(rs.getInt("id"));
                producto.setBitmapImage(Helpers.getBitmapFromBytes((Blob) rs.getBlob("img")));
                producto.setPrecio(rs.getFloat("precio"));
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        CargarComercios();
        return null;
    }

    @Override
    protected void onPostExecute(Boolean response) {
       grid.setAdapter(new ProductsAdapter(context, productos));
    }
}