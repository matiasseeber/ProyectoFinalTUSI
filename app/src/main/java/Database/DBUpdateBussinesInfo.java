package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Entidades.Comercio;
import Helpers.Helpers;

public class DBUpdateBussinesInfo extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private Comercio comercio;
    private Activity activity;

    public DBUpdateBussinesInfo() {
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

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public boolean deleteRecord() {
        int insertedRows = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("Update Comercios set nombre = ? ,direccion = ? ,contraseña = ? ,image = ? where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, comercio.getName());
            preparedStatement.setString(2, comercio.getAddress());
            if(comercio.getPassword().isEmpty())
                preparedStatement.setString(3, Helpers.getBussinesPassword(context));
            else
                preparedStatement.setString(3, comercio.getPassword());
            preparedStatement.setString(4, comercio.getImageValue());
            preparedStatement.setInt(5, comercio.getId());
            insertedRows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedRows == 1;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return deleteRecord();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(context, "La cuenta fue modificada exitosamente.", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo modificar la cuenta.", Toast.LENGTH_LONG).show();
        }
    }
}