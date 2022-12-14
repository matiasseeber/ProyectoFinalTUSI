package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Tarjeta;
import Helpers.Helpers;

public class DBInsertCreditCardAndOrder extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private ArrayList<PedidoDetalle> pedidoDetalleArrayList;
    private PedidoCabecera pedidoCabecera;
    private Activity activity;

    public DBInsertCreditCardAndOrder() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<PedidoDetalle> getPedidoDetalleArrayList() {
        return pedidoDetalleArrayList;
    }

    public void setPedidoDetalleArrayList(ArrayList<PedidoDetalle> pedidoDetalleArrayList) {
        this.pedidoDetalleArrayList = pedidoDetalleArrayList;
    }

    public PedidoCabecera getPedidoCabecera() {
        return pedidoCabecera;
    }

    public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabecera = pedidoCabecera;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean insertCard() {
        int insertedRows = 0;
        Tarjeta tarjeta = pedidoCabecera.getTarjeta();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Insert into Tarjetas (id_Cliente, TipoTarjeta, numTarjeta, codSeguridad, estado) values (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, pedidoCabecera.getCliente().getId());
            preparedStatement.setString(2, tarjeta.getTipoTarjeta());
            preparedStatement.setString(3, tarjeta.getNumTarjeta());
            preparedStatement.setInt(4, tarjeta.getCodSeguridad());
            preparedStatement.setBoolean(5, true);
            insertedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return insertCard();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        DBInsertPedido insertPedido = new DBInsertPedido();
        insertPedido.setPedidoCabecera(pedidoCabecera);
        insertPedido.setContext(context);
        insertPedido.setActivity(activity);
        insertPedido.execute();
    }
}