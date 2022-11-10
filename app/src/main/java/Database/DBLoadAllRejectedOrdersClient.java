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

import Entidades.Comercio;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Producto;
import Entidades.Tarjeta;
import Helpers.Helpers;
import adapters.PedidosEntregadosClienteAdapter;
import adapters.PedidosPendientesClienteAdapter;

public class DBLoadAllRejectedOrdersClient extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int idCliente;
    private ArrayList<PedidoCabecera> pedidoCabeceras = new ArrayList<PedidoCabecera>();
    private View msg;
    private TextView msgText;
    private ArrayList<PedidoDetalle> pedidoDetalleArrayList = new ArrayList<>();

    public DBLoadAllRejectedOrdersClient() {
    }

    public TextView getMsgText() {
        return msgText;
    }

    public void setMsgText(TextView msgText) {
        this.msgText = msgText;
    }

    public View getMsg() {
        return msg;
    }

    public void setMsg(View msg) {
        this.msg = msg;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
            String query = "Select * from PedidosCabecera inner join PedidoDetalle on PedidoDetalle.idCabecera = PedidosCabecera.id inner join Productos on PedidoDetalle.id_Producto = Productos.id inner join Clientes on id_Cliente = Clientes.id inner join Comercios on PedidosCabecera.id_Comercio = Comercios.id left join Tarjetas on Tarjetas.id = PedidosCabecera.id_Tarjeta where Clientes.id = " + idCliente + " and PedidosCabecera.estado = 2;";

            ResultSet rs = st.executeQuery(
                    query
            );


            while (rs.next()) {
                PedidoCabecera pedidoCabecera = new PedidoCabecera();
                pedidoCabecera.setId(rs.getInt(1));
                pedidoCabecera.setFecha(rs.getString("fecha"));
                Comercio comercio = new Comercio();
                comercio.setName(rs.getString(35));
                comercio.setAddress(rs.getString(37));
                comercio.setBitmap(Helpers.getBitmapFromBytes((Blob) rs.getBlob(38)));
                pedidoCabecera.setComercio(comercio);
                pedidoCabecera.setTotal(rs.getFloat("total"));
                pedidoCabecera.setEfectivo(rs.getBoolean("efectivo"));
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setTipoTarjeta(rs.getString("TipoTarjeta"));
                pedidoCabecera.setTarjeta(tarjeta);
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                Producto producto = new Producto();
                producto.setPrecio(rs.getFloat("precio"));
                pedidoDetalle.setCantidad(rs.getInt("cant"));
                producto.setNombre(rs.getString(16));
                pedidoDetalle.setProducto(producto);
                pedidoDetalleArrayList.add(pedidoDetalle);
                pedidoCabecera.setPedidoDetalles(pedidoDetalleArrayList);
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
        if (pedidoCabeceras.size() == 0 && msg != null) {
            msg.setVisibility(View.VISIBLE);
            msgText.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.GONE);
            msgText.setVisibility(View.GONE);
        }
        grid.setAdapter(new PedidosPendientesClienteAdapter(context, pedidoCabeceras));
    }
}