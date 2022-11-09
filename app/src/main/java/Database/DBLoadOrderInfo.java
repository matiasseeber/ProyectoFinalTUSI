package Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import Entidades.Clientes;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Producto;
import adapters.DetallePedidosAdapter;
import adapters.PedidosPendientesComercioAdapter;
import adapters.PedidosSinEntregarComercioAdapter;

public class DBLoadOrderInfo extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int idPedidoCabecera;
    private ArrayList<PedidoDetalle> pedidoDetalles = new ArrayList<>();
    private PedidoCabecera pedidoCabecera = new PedidoCabecera();
    private TextView total;

    private ArrayList<Producto> productos;

    public DBLoadOrderInfo() {
    }

    public int getIdPedidoCabecera() {
        return idPedidoCabecera;
    }

    public void setIdPedidoCabecera(int idPedidoCabecera) {
        this.idPedidoCabecera = idPedidoCabecera;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
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

    public void CargarInfoPedido() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DataDB.urlMySQL,
                    DataDB.user,
                    DataDB.pass
            );
            Statement st = con.createStatement();
            String query = "Select * from PedidosCabecera inner join Clientes on id_Cliente = Clientes.id inner join PedidoDetalle on PedidosCabecera.id = " +
                    "PedidoDetalle.idCabecera inner join Productos on Productos.id = id_Producto where PedidosCabecera.id = "+ idPedidoCabecera +";";
            ResultSet rs = st.executeQuery(
                    query
            );

            while (rs.next()) {
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoCabecera.setId(idPedidoCabecera);
                pedidoCabecera.setTotal(rs.getFloat("total"));
                Producto producto = new Producto();
                producto.setNombre(rs.getString(28));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setId(rs.getInt("id_Producto"));
                pedidoDetalle.setProducto(producto);
                pedidoDetalle.setCantidad(rs.getInt("cant"));
                pedidoDetalles.add(pedidoDetalle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        CargarInfoPedido();
        return null;
    }

    public HashSet<String> jsonStringifyPedidoDetalles(){
        HashSet<String> json = new HashSet<>();
        for(int i = 0; i < pedidoDetalles.size(); i++){
            PedidoDetalle pedidoDetalle = pedidoDetalles.get(i);
            Gson gson = new Gson();
            String detalle = gson.toJson(pedidoDetalle);
            json.add(detalle);
        }
        return json;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        total.setText(String.valueOf(pedidoCabecera.getTotal()));
        SharedPreferences sharedPref = context.getSharedPreferences(
                "MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putStringSet("detallePedido", jsonStringifyPedidoDetalles());
        myEdit.commit();
        DetallePedidosAdapter pedidosSinEntregarComercioAdapter = new DetallePedidosAdapter(context, pedidoDetalles);
        grid.setAdapter(pedidosSinEntregarComercioAdapter);
    }
}