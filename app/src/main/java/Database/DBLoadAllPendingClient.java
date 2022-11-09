package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Clientes;
import Entidades.Comercio;
import Entidades.PedidoCabecera;
import Entidades.Producto;
import Entidades.Tarjeta;
import Helpers.Helpers;
import adapters.PedidosPendientesClienteAdapter;
import adapters.PedidosPendientesComercioAdapter;

public class DBLoadAllPendingClient extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int idCliente;
    private ArrayList<PedidoCabecera> pedidoCabeceras = new ArrayList<PedidoCabecera>();
    private View msg;
    private TextView textView;

    private ArrayList<Producto> productos;

    public DBLoadAllPendingClient() {
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setMsg(View msg) {
        this.msg = msg;
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

            String query = "Select * from PedidosCabecera inner join Clientes on id_Cliente = Clientes.id " +
                    "inner join Comercios on PedidosCabecera.id_Comercio = Comercios.id left join " +
                    "Tarjetas on Tarjetas.id = " +
                    "PedidosCabecera.id_Tarjeta where PedidosCabecera.estado = 1 and Clientes.id = " + idCliente + ";";

            ResultSet rs = st.executeQuery(
                    query
            );

            while (rs.next()) {
                PedidoCabecera pedidoCabecera = new PedidoCabecera();
                pedidoCabecera.setId(rs.getInt(1));
                pedidoCabecera.setFecha(rs.getString("fecha"));
                Comercio comercio = new Comercio();
                comercio.setName(rs.getString(22));
                comercio.setBitmap(Helpers.getBitmapFromBytes((Blob) rs.getBlob("image")));
                Clientes cliente = new Clientes();
                cliente.setNombreUsuario(rs.getString("nombreUsuario"));
                pedidoCabecera.setComercio(comercio);
                pedidoCabecera.setCliente(cliente);
                pedidoCabecera.setTotal(rs.getFloat("total"));
                pedidoCabecera.setEfectivo(rs.getBoolean("efectivo"));
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setTipoTarjeta(rs.getString("TipoTarjeta"));
                pedidoCabecera.setTarjeta(tarjeta);
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
        if (pedidoCabeceras.size() == 0 && msg != null){
            msg.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }else{
            msg.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        grid.setAdapter(new PedidosPendientesClienteAdapter(context, pedidoCabeceras));
    }
}