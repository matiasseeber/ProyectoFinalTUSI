package Database;

import Entidades.Comercio;
import Helpers.Helpers;
import adapters.BussinesAdapter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBComercio extends AsyncTask<Boolean, Void, Boolean> {

    private Comercio comercio;
    private GridView grid;
    private Context context;
    private ArrayList<Comercio> comercios;
    private int cod_localidad = -1;

    public DBComercio() {
    }

    public DBComercio(
            Context context
    ) {
        this.context = context;
    }

    public int getCod_localidad() {
        return cod_localidad;
    }

    public void setCod_localidad(int cod_localidad) {
        this.cod_localidad = cod_localidad;
    }

    public GridView getGrid() {
        return grid;
    }

    public void setGrid(GridView grid) {
        this.grid = grid;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void CargarComercios() {
        comercios = new ArrayList<Comercio>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DataDB.urlMySQL,
                    DataDB.user,
                    DataDB.pass
            );
            Statement st = con.createStatement();
            String query = "Select * from Comercios inner join Localidades on Comercios.cod_localidad = Localidades.id";
            if(cod_localidad != -1){
                query += " where cod_localidad = " + cod_localidad;
            }
            query += ";";

            ResultSet rs = st.executeQuery(
                query
            );

            while (rs.next()) {
                Comercio comercio1 = new Comercio();
                comercio1.setId(rs.getInt(1));
                comercio1.setName(rs.getString(2));
                comercio1.setBitmap(Helpers.getBitmapFromBytes((Blob) rs.getBlob(5)));
                comercio1.setAddress(rs.getString(12) + " - " + rs.getString(4));
                comercios.add(comercio1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        CargarComercios();
        return null;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        grid.setAdapter(new BussinesAdapter(context, comercios));
    }
}
