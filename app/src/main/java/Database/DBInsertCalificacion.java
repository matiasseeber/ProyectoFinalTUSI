package Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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

public class DBInsertCalificacion extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private PedidoCabecera pedidoCabecera;
    private Activity activity;
    private Float puntuacion;

    public DBInsertCalificacion() {
    }

    public PedidoCabecera getPedidoCabecera() {
        return pedidoCabecera;
    }

    public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabecera = pedidoCabecera;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
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

    public boolean insertCalificacion(){
        int insertedRows = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement("insert into Calificaciones (id_Pedido, id_Cliente, id_Comercio, puntuacion) values (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, pedidoCabecera.getId());
            preparedStatement.setInt(2, Helpers.getUserId(context));
            preparedStatement.setInt(3, pedidoCabecera.getComercio().getId());
            preparedStatement.setFloat(4, puntuacion);
            insertedRows = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return insertedRows > 0;
    }

    protected Boolean doInBackground(Boolean... Boolean) {
        return insertCalificacion();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "Gracias por tu calificacion!", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo calificar el pedido...", Toast.LENGTH_LONG).show();
        }
    }
}