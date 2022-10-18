package Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tp_final.LoginUsuario;
import com.example.tp_final.R;
import com.example.tp_final.RegistroUsuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Clientes;

public class DBUsuariosInsert extends AsyncTask<Boolean, Void, Boolean>{
    private Context context;
    private Clientes usuario;
    private String message;

    private ArrayList<EditText> editTexts;

    public void setEditTexts(ArrayList<EditText> editTexts) {
        this.editTexts = editTexts;
    }

    public DBUsuariosInsert() {
    }

    public DBUsuariosInsert(Context context, Clientes cliente){
        this.context = context;
        this.usuario = cliente;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean existeUsuario(String query){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            boolean exist = rs.next();
            return exist;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUser(){
        int insertedRows = 0;
        try {
            if(existeUsuario("Select * from Clientes where dni = " + usuario.getDni())) {
                message = "Un usuario ya esta utizando ese dni.";
            }else if(existeUsuario("Select * from Clientes where email = '" + usuario.getEmail() + "'")) {
                message = "Un usuario ya esta utizando ese email.";
            }else{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                PreparedStatement preparedStatement = con.prepareStatement("insert into Clientes (dni, nombreUsuario, nombre, apellido, email, contraseña, sexo, edad, cod_localidad, estado) values (?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, usuario.getDni());
                preparedStatement.setString(2, usuario.getNombreUsuario());
                preparedStatement.setString(3, usuario.getNombre());
                preparedStatement.setString(4, usuario.getApellido());
                preparedStatement.setString(5, usuario.getEmail());
                preparedStatement.setString(6, usuario.getContraseña());
                preparedStatement.setString(7, usuario.getSexo());
                preparedStatement.setInt(8, usuario.getEdad());
                preparedStatement.setInt(9, usuario.getCod_localidad());
                preparedStatement.setBoolean(10, true);
                insertedRows = preparedStatement.executeUpdate();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return insertedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return insertUser();
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
            message = "El usuario fue dado de alta correctamente.";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        if(response)  {
            Intent intent = new Intent(context, LoginUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}

