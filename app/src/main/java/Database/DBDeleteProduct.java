package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Entidades.Producto;

public class DBDeleteProduct extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private int id;
    private Activity activity;

    public DBDeleteProduct() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean deleteRecord() {
        int insertedRows = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("update Productos set estado = 0 where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
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
            Toast.makeText(context, "El producto fue dado de baja exitosamente.", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {
            Toast.makeText(context, "No se pudo dar de baja el producto.", Toast.LENGTH_LONG).show();
        }
    }
}