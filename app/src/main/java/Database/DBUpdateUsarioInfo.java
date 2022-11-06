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
    private Activity activity;

    public DBUpdateUsarioInfo() {
    }

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public Clientes getClientes() {return clientes;}

    public void setClientes(Clientes clientes) {this.clientes = clientes;}

    public Activity getActivity() {return activity;}

    public void setActivity(Activity activity) {this.activity = activity;}

    public boolean UpdtaUserinfo(){
        int afectedRows=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Clientes set nombre = ? ,apellido = ? ,nombreUsuario = ? ,email = ?,direccion = ?,contraseña = ?, sexo = ?, where id = ?;", Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setString(1, comercio.getName());
            preparedStatement.setString(1,clientes.getNombre());
            preparedStatement.setString(2,clientes.getApellido());
            preparedStatement.setString(3,clientes.getNombreUsuario());
            preparedStatement.setString(4,clientes.getEmail());
            preparedStatement.setString(5,clientes.getDireccion());
            preparedStatement.setString(6,clientes.getContraseña());
            preparedStatement.setString(7,clientes.getSexo());
            preparedStatement.setInt(8, clientes.getId());

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
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo modificar la cuenta.", Toast.LENGTH_LONG).show();
        }
    }

}
