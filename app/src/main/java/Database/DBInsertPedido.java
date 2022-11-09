package Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Tarjeta;
import Helpers.Helpers;

public class DBInsertPedido extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private PedidoCabecera pedidoCabecera;
    private ArrayList<PedidoDetalle> pedidosDetalles;
    private Activity activity;

    public DBInsertPedido() {
    }

    public PedidoCabecera getPedidoCabecera() {
        return pedidoCabecera;
    }

    public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabecera = pedidoCabecera;
        pedidosDetalles = pedidoCabecera.getPedidoDetalles();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int returnIdTarjeta(Long numTarjeta){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Tarjetas where numTarjeta = " + numTarjeta);
            if(rs.next()){
                return rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertPedido() throws ClassNotFoundException, SQLException {
        int insertedRows = 0;

        int idUsuario = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id", -1);
        int idComercioSeleccionado = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("idComercioSeleccionado", -1);

        if (idUsuario == -1 || idUsuario == -1) return false;

        Connection con;
        PreparedStatement preparedStatement;
        if(pedidoCabecera.isEfectivo()){
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            preparedStatement = con.prepareStatement("Insert into PedidosCabecera (id_Cliente, id_Comercio, efectivo, total, estado, fecha) values (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idComercioSeleccionado);
            preparedStatement.setBoolean(3, pedidoCabecera.isEfectivo());
            preparedStatement.setFloat(4, pedidoCabecera.getTotal());
            preparedStatement.setInt(5, 1);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();

            preparedStatement.setString(6, dtf.format(now));
            insertedRows = preparedStatement.executeUpdate();
        }else{
            Tarjeta tarjeta = pedidoCabecera.getTarjeta();

            int idTarjeta = returnIdTarjeta(Long.parseLong(tarjeta.getNumTarjeta()));
            pedidoCabecera.getTarjeta().setId(idTarjeta);

            if(idTarjeta == -1) return false;

            if(pedidoCabecera.getTarjeta().getId() != -1){
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                preparedStatement = con.prepareStatement("Insert into PedidosCabecera (id_Cliente, id_Comercio, efectivo, total, estado, id_Tarjeta, fecha) values (?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.setInt(2, idComercioSeleccionado);
                preparedStatement.setBoolean(3, pedidoCabecera.isEfectivo());
                preparedStatement.setFloat(4, pedidoCabecera.getTotal());
                preparedStatement.setInt(5, 1);
                preparedStatement.setInt(6, pedidoCabecera.getTarjeta().getId());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();

                preparedStatement.setString(7, dtf.format(now));

                insertedRows = preparedStatement.executeUpdate();
            }

        }

        if (insertedRows == -1) return false;

        int insertedId = -1;

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from PedidosCabecera;");

        while(rs.next()){
            insertedId = rs.getInt("id");
        }

        if (insertedId == -1) return false;

        for (int i = 0; i < pedidosDetalles.size(); i++) {
            PedidoDetalle pedidoDetalle = pedidosDetalles.get(i);
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            preparedStatement = con.prepareStatement("Insert into PedidoDetalle (idCabecera, id_Producto, precio, cant) values (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, insertedId);
            preparedStatement.setInt(2, pedidoDetalle.getProducto().getId());
            preparedStatement.setFloat(3, pedidoDetalle.getProducto().getPrecio());
            preparedStatement.setFloat(4, pedidoDetalle.getCantidad());
            insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        try {
            return insertPedido();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "El pedido fue dado de alta exitosamente.", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo añadir el producto.", Toast.LENGTH_LONG).show();
        }

        SharedPreferences sharedPref = context.getSharedPreferences(
                "MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putBoolean("pedidoAñadido", true);
        myEdit.commit();
    }
}