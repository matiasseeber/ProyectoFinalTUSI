package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Entidades.Clientes;
import Helpers.Helpers;

public class DBUpdateUsarioInfo extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Clientes clientes;

    public DBUpdateUsarioInfo() {
    }

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public Clientes getClientes() {return clientes;}

    public void setClientes(Clientes clientes) {this.clientes = clientes;}

    public boolean UpdtaUserinfo(){
        int afectedRows=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Clientes set direccion = ?,contraseña = ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,clientes.getDireccion());
            preparedStatement.setString(2,clientes.getContraseña());
            preparedStatement.setInt(3, clientes.getId());

            afectedRows = preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return afectedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {return UpdtaUserinfo();}

    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "La cuenta fue modificada exitosamente.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No se pudo modificar la cuenta.", Toast.LENGTH_LONG).show();
        }
    }
}
