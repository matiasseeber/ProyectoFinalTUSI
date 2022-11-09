package Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tp_final.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBDeleteUsuario extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private int id;
    private Activity activity;
    private String message;

    public DBDeleteUsuario(){

    }

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Activity getActivity() {return activity;}

    public void setActivity(Activity activity) {this.activity = activity;}

    public boolean isDeletable(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            String query = "Select * from PedidosCabecera where id_Cliente = " + id + " and (estado = 1 or estado = 3);";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            return !rs.next();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteRecordU(){
        int afectedRows = 0;

        if(!isDeletable()) {
            message = "No se puede dar de baja la cuenta porque tiene pedidos pendientes.";
            return false;
        }

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("update Clientes set estado = 0 where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,id);

            afectedRows = preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        return afectedRows == 1;
    }


    protected Boolean doInBackground(Boolean... Boolean) {
        return deleteRecordU();
    }

    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "El usuario fue dado de baja exitosamente.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
