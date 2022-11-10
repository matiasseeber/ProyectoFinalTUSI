package Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_final.R;
import com.google.gson.Gson;
import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import Entidades.Comercio;
import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Producto;
import Entidades.Tarjeta;
import Helpers.Helpers;
import adapters.DetallePedidoClienteAdapter;
import adapters.DetallePedidosAdapter;
import adapters.PedidosSinEntregarClienteAdapter;

public class DBLoadPendingOrderInfoPopUp extends AsyncTask<Boolean, Void, Boolean> {

    private GridView grid;
    private Context context;
    private int idPedidoCabecera;
    private PedidoCabecera pedidoCabecera = new PedidoCabecera();
    private ArrayList<PedidoDetalle> pedidoDetalleArrayList = new ArrayList<>();
    private ImageView imgLogo;
    private TextView nombreComercio;
    private TextView fecha;
    private TextView metodoPago;
    private TextView total;
    private TextView direccion;
    private TextView Estado;

    public TextView getEstado() {
        return Estado;
    }

    public void setEstado(TextView estado) {
        Estado = estado;
    }

    public DBLoadPendingOrderInfoPopUp() {
    }

    public int getIdPedidoCabecera() {
        return idPedidoCabecera;
    }

    public void setIdPedidoCabecera(int idPedidoCabecera) {
        this.idPedidoCabecera = idPedidoCabecera;
    }

    public TextView getDireccion() {
        return direccion;
    }

    public void setDireccion(TextView direccion) {
        this.direccion = direccion;
    }

    public PedidoCabecera getPedidoCabecera() {
        return pedidoCabecera;
    }

    public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabecera = pedidoCabecera;
    }

    public ArrayList<PedidoDetalle> getPedidoDetalleArrayList() {
        return pedidoDetalleArrayList;
    }

    public void setPedidoDetalleArrayList(ArrayList<PedidoDetalle> pedidoDetalleArrayList) {
        this.pedidoDetalleArrayList = pedidoDetalleArrayList;
    }

    public ImageView getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(ImageView imgLogo) {
        this.imgLogo = imgLogo;
    }

    public TextView getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(TextView nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }

    public TextView getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(TextView metodoPago) {
        this.metodoPago = metodoPago;
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

            String query = "Select * from PedidosCabecera " +
                    "inner join PedidoDetalle on PedidoDetalle.idCabecera = PedidosCabecera.id" +
                    " inner join Productos on PedidoDetalle.id_Producto = Productos.id" +
                    " inner join Clientes on id_Cliente = Clientes.id" +
                    " inner join Comercios on PedidosCabecera.id_Comercio = Comercios.id" +
                    " left join Tarjetas on Tarjetas.id = PedidosCabecera.id_Tarjeta" +
                    " where PedidosCabecera.id = " + idPedidoCabecera + ";";

            ResultSet rs = st.executeQuery(
                    query
            );

            while (rs.next()) {
                if(rs.isFirst()){
                    pedidoCabecera.setId(idPedidoCabecera);
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
                    pedidoCabecera.setEstado(rs.getInt(8));
                }
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                Producto producto = new Producto();
                producto.setPrecio(rs.getFloat("precio"));
                pedidoDetalle.setCantidad(rs.getInt("cant"));
                producto.setNombre(rs.getString(16));
                pedidoDetalle.setProducto(producto);
                pedidoDetalleArrayList.add(pedidoDetalle);
            }
            pedidoCabecera.setPedidoDetalles(pedidoDetalleArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        CargarInfoPedido();
        return null;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        imgLogo.setImageBitmap(pedidoCabecera.getComercio().getBitmap());
        nombreComercio.setText(pedidoCabecera.getComercio().getName());
        fecha.setText(pedidoCabecera.getFecha());
        direccion.setText(pedidoCabecera.getComercio().getAddress());
        if(pedidoCabecera.isEfectivo()){
            metodoPago.setText("Efectivo");
        }else{
            metodoPago.setText(pedidoCabecera.getTarjeta().getTipoTarjeta());
        }
        switch (pedidoCabecera.getEstado()){
            case 1:
                Estado.setText("Pendiente");
                Estado.setBackgroundColor(context.getResources().getColor(R.color.Pendiente));
                break;
            case 2:
                Estado.setText("Rechazado");
                Estado.setBackgroundColor(context.getResources().getColor(R.color.Rechazado));
                break;
            case 3:
                Estado.setText("Confirmado");
                Estado.setBackgroundColor(context.getResources().getColor(R.color.Sinentregar));
                break;
            default:
                Estado.setText("Entregado");
                Estado.setBackgroundColor(context.getResources().getColor(R.color.Entregado));
                break;
        }
        total.setText("Total: $" + String.valueOf(pedidoCabecera.getTotal()));
        grid.setAdapter(new DetallePedidoClienteAdapter(context, pedidoCabecera));
    }
}