package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Comercio;
import adapters.BussinesAdapter;

public class DBComercio extends AsyncTask<Boolean, Void, String> {
    private Comercio comercio;
    private GridView grid;
    private Context context;
    private EditText txtEstrellas;
    private EditText txtNombreComercio;
    private EditText txtDistancia;
    private ImageView imgComercio;
    private ArrayList<Comercio> comercios;
    private Boolean isInsertion;
    private Boolean isIdSearch;
    private Boolean wasRecordInserted = false;

    private Boolean returnAllRecords = false;

    public DBComercio(Context context, EditText txtId, EditText txtNombreComercio, EditText txtDistancia,EditText txtEstrellas,  Boolean isInsertion, Boolean isIdSearch, Spinner spinner) {
        this.context = context;
        this. txtNombreComercio = txtNombreComercio;
        this.txtEstrellas= txtEstrellas;
        this.txtDistancia=txtDistancia;
        this.isInsertion = isInsertion;
        this.isIdSearch = isIdSearch;
    }

    public Boolean getIdSearch() {
        return isIdSearch;
    }

    public void setIdSearch(Boolean idSearch) {
        isIdSearch = idSearch;
    }

    public Boolean isInsertion(){
        return this.isInsertion;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }


    public void Cargar_Comercios(){
        comercios = new ArrayList<Comercio>();
        try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("Select * from comercios where id = " + comercio.getId());

                while (rs.next()){
                    comercio.setId(rs.getInt("id"));
                    comercio.setName(rs.getString("nombre"));
                    comercio.setImageValue(rs.getString("cod_imagen"));

                    comercios.add(comercio);
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected String doInBackground(Boolean... booleans) {
        return null;
    }

    @Override
    protected void onPostExecute(String msg) {
       grid.setAdapter(new BussinesAdapter(context,comercios));
    }


    }