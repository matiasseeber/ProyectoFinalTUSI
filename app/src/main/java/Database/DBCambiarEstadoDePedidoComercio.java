package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

import Entidades.PedidoDetalle;

public class DBCambiarEstadoDePedidoComercio extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private int idPedido;
    private Activity activity;
    private int estado;
    private HashSet<String> detallePedido = null;

    public DBCambiarEstadoDePedidoComercio() {
    }

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public int getIdPedido() {
        return idPedido;
    }

    public int getEstado() {
        return estado;
    }

    public HashSet<String> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(HashSet<String> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Activity getActivity() {return activity;}

    public void setActivity(Activity activity) {this.activity = activity;}

    public boolean UpdateStock(){

        int afectedRows=0;
        try{
            for (String detalle : detallePedido) {
                Gson gson = new Gson();
                PedidoDetalle pedidoDetalle = gson.fromJson(detalle, PedidoDetalle.class);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                String query = "UPDATE Productos P" +
                        "    SET P.Stock = (P.Stock - ?)" +
                        "    WHERE P.Id = ?;";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, pedidoDetalle.getCantidad());
                preparedStatement.setInt(2, pedidoDetalle.getProducto().getId());
                afectedRows = preparedStatement.executeUpdate();
                if(afectedRows == 0)
                    return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean UpdateOrderState(){
        int afectedRows=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update PedidosCabecera set estado = ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, estado);
            preparedStatement.setInt(2, idPedido);
            afectedRows = preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(afectedRows == 1 && estado == 3){
            return UpdateStock();
        }
        return afectedRows > 0;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {return UpdateOrderState();}

    protected void onPostExecute(Boolean response) {
        if(response){
            activity.finish();
            switch (estado){
                case 2:
                    Toast.makeText(context, "Se rechazo el pedido exitosamente.", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(context, "Se confirmo el pedido exitosamente.", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(context, "Se marco el pedido como entregado exitosamente.", Toast.LENGTH_LONG).show();
                    break;
            }
        }else{
            switch (estado){
                case 2:
                    Toast.makeText(context, "No se pudo rechazar el pedido.", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(context, "No se pudo confirmar el pedido.", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(context, "No se pudo marcar el pedido como entregado.", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
