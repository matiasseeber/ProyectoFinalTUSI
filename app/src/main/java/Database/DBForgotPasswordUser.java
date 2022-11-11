package Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tp_final.LoginComercio;
import com.example.tp_final.LoginUsuario;
import com.example.tp_final.Recupero_Passw;
import com.example.tp_final.Recupero_Passw_USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Entidades.Clientes;

public class DBForgotPasswordUser extends AsyncTask<Boolean,Void,Boolean> {
    private Context context;
    private Clientes clientes;
    private String message;
    private String accion;
    private String query;

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public Clientes getClientes() {return clientes;}

    public void setClientes(Clientes clientes) {this.clientes = clientes;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public String getAccion() {return accion;}

    public void setAccion(String accion) {this.accion = accion;}

    public String getQuery() {return query;}

    public void setQuery(String query) {this.query = query;}

    public DBForgotPasswordUser(){
    }

    public DBForgotPasswordUser(Context context, Clientes clientes, String accion){
        this.context = context;
        this.clientes = clientes;
        this.accion = accion;
    }

    public boolean existeUser(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.beforeFirst();
            boolean exist = rs.next();
            if (exist) {
                clientes.setId(rs.getInt("id"));
                clientes.setDni(rs.getInt("dni"));
                clientes.setEmail(rs.getString("email"));
            }
            return exist;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean UpdatePasswordUser(){
        int rows = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Clientes set contraseña= ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(clientes.getContraseña()));
            preparedStatement.setInt(2, clientes.getId());
            rows = preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
         return rows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        switch (this.getAccion()){
            case "Validation":
                return existeUser(this.query);
            case "Update":
                return UpdatePasswordUser();
            default:
                return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        switch(this.getAccion()){
            case "Validation":
                if(!response)  {
                    //emptyAllControls();
                    message = "No hay ningún Cliente registrado con ese DNI y EMAIL ingresado.";
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                if(response)  {
                    Intent intent = new Intent(context, Recupero_Passw_USER.class);
                    intent.putExtra("UsuarioID", clientes.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
            case "Update":
                if(response)  {
                    //emptyAllControls();
                    message = "Tu contraseña ha sido restaurada exitosamente.";
                }
                else{
                    message = "Tu contraseña no pudo ser restaurada.";
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                if(response)  {
                    Intent intent = new Intent(context, LoginUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
        }

    }

}
