package Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tp_final.LoginComercio;
import com.example.tp_final.LoginUsuario;

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

    private ArrayList<EditText> editTexts;

    public void setEditTexts(ArrayList<EditText> editTexts) {
        this.editTexts = editTexts;
    }

    public DBForgotPassword() {
    }

    public DBForgotPassword(Context context, Comercio comercio){
        this.context = context;
        this.comercio = comercio;
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

            boolean exist=false;

            while(rs.next()){
                comercio.setId(rs.getInt(0));
                exist=true;
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
            if(!existeComercio("Select * from Comercios where Email = " + comercio.getEmail())) {
                message = "No hay ningún Comercio registrado con el mail ingresado";
            }
            else{

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                PreparedStatement preparedStatement = con.prepareStatement("Update Comercios set = ? where Email = ?;", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, comercio.getId());
                preparedStatement.setString(2, comercio.getEmail());
                Rows = preparedStatement.executeUpdate();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Rows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return UpdatePassword();
    }

    public void emptyAllControls(){
        for(int i = 0; i < editTexts.size(); i++){
            EditText editText = editTexts.get(i);
            editText.setText("");
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response)  {
            emptyAllControls();
            message = "Tu contraseña ha sido restaurada exitosamente.";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        if(response)  {
            Intent intent = new Intent(context, LoginComercio.class);
            context.startActivity(intent);
        }
    }


}
