package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Comercio;
import Entidades.Localidad;

public class DBInsertBussines extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Comercio comercio;
    private String message = "";

    public DBInsertBussines() {
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean doesBussinessAlreadyExist(String query){
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

    public boolean insertBussiness(){
        int insertedRows = 0;
        try {
            if(doesBussinessAlreadyExist("Select * from Comercios where Cuil = " + comercio.getVatNumber())) {
                message = "Un usuario ya esta utizando ese dni.";
            }else if(doesBussinessAlreadyExist("Select * from Comercios where Email = '" + comercio.getEmail() + "'")) {
                message = "Un usuario ya esta utizando ese email.";
            }else{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                PreparedStatement preparedStatement = con.prepareStatement("insert into Comercios (Email, Cuil, nombre, Dirección, cod_localidad, logo, estado) values (?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, comercio.getEmail());
                preparedStatement.setInt(2, comercio.getVatNumber());
                preparedStatement.setString(3, comercio.getName());
                preparedStatement.setString(4, comercio.getAddress());
                preparedStatement.setInt(5, comercio.getDistrict());
                preparedStatement.setString(6, comercio.getImageValue());
                preparedStatement.setBoolean(7, true);
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
        return insertBussiness();
    }

    @Override
    protected void onPostExecute(Boolean response) {
    }
}
