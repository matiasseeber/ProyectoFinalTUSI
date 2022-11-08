package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Clientes;
import Entidades.PedidoCabecera;
import Entidades.Producto;
import adapters.PedidosPendientesComercioAdapter;

public class DBLoadAllPendingOrders extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int id_comercio;
    private ArrayList<PedidoCabecera> pedidoCabeceras = new ArrayList<PedidoCabecera>();
    private View msg;

    private ArrayList<Producto> productos;

    public DBLoadAllPendingOrders() {
    }

    public View getMsg() {
        return msg;
    }

    public void setMsg(View msg) {
        this.msg = msg;
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

    public void CargarPedidosPendientes() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DataDB.urlMySQL,
                    DataDB.user,
                    DataDB.pass
            );
            Statement st = con.createStatement();
            String query = "Select * from PedidosCabecera inner join Clientes on id_Cliente = Clientes.id where  PedidosCabecera.estado = 1 and PedidosCabecera.id_Comercio = " + id_comercio + ";";
            ResultSet rs = st.executeQuery(
                    query
            );

            while (rs.next()) {
                PedidoCabecera pedidoCabecera = new PedidoCabecera();
                pedidoCabecera.setId(rs.getInt(1));
                Clientes cliente = new Clientes();
                cliente.setNombreUsuario(rs.getString("nombreUsuario"));
                pedidoCabecera.setCliente(cliente);
                pedidoCabecera.setTotal(rs.getFloat("total"));
                pedidoCabeceras.add(pedidoCabecera);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        CargarPedidosPendientes();
        return null;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (pedidoCabeceras.size() == 0 && msg != null)
            msg.setVisibility(View.VISIBLE);
        grid.setAdapter(new PedidosPendientesComercioAdapter(context, pedidoCabeceras));
    }
}