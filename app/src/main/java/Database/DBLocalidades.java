package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Localidad;

public class DBLocalidades extends AsyncTask<Boolean, Void, Boolean> {

    private ArrayList<Localidad> localidades;
    private Spinner spinner;
    private Context context;
    ArrayAdapter<Localidad> adapter;

    public DBLocalidades() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {

        localidades = new ArrayList<Localidad>();

        if(localidades.size() > 0)
            return true;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Localidades");
            Localidad localidadDefault = new Localidad();
            localidadDefault.setDescripcion("Seleccione una localidad");
            localidades.add(localidadDefault);
            rs.beforeFirst();
            while (rs.next()) {
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id"));
                String descripcion = rs.getString("descripcion");
                localidad.setDescripcion(descripcion);
                localidades.add(localidad);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        adapter =
                new ArrayAdapter<Localidad>(context,  android.R.layout.simple_spinner_dropdown_item, localidades);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
