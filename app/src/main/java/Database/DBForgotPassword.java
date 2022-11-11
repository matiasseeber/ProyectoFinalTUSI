package Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tp_final.LoginComercio;
import com.example.tp_final.LoginUsuario;
import com.example.tp_final.Recupero_Passw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Clientes;
import Entidades.Comercio;

public class DBForgotPassword extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private Comercio comercio;
    private String message;
    private String Accion;
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAccion() {
        return Accion;
    }

    public void setAccion(String accion) {
        Accion = accion;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public DBForgotPassword() {
    }

    public DBForgotPassword(Context context, Comercio comercio, String Accion){
        this.context = context;
        this.comercio = comercio;
        this.Accion=Accion;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean existeComercio(String query){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.beforeFirst();
            boolean exist = rs.next();
            if(exist){
                comercio.setId(rs.getInt("id"));
                comercio.setVatNumber(rs.getInt("cuil"));
            }
            return exist;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean UpdatePassword(){
        int Rows=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Comercios set contraseña= ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(comercio.getPassword()));
            preparedStatement.setInt(2, comercio.getId());
            Rows = preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Rows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {

      switch (this.getAccion()){
          case "Validation":
              return existeComercio(this.query);
          case "Update":
              return UpdatePassword();
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
                    message = "No hay ningún Comercio registrado con la combinacion de CUIL y Email ingresada.";
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
                if(response)  {
                    Intent intent = new Intent(context, Recupero_Passw.class);
                    intent.putExtra("ComercioID", comercio.getId());
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
                    Intent intent = new Intent(context, LoginComercio.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                break;
        }

    }


}
