package Database;

import Entidades.Comercio;
import Helpers.Helpers;
import adapters.BussinesAdapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import java.util.List;
import java.util.Locale;

public class DBComercio extends AsyncTask<Boolean, Void, Boolean> {

    private Comercio comercio;
    private GridView grid;
    private Context context;
    private ArrayList<Comercio> comercios;
    private int distancia = 0;

    public DBComercio() {
    }

    public DBComercio(
            Context context
    ) {
        this.context = context;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
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
            String query = "Select * from Comercios inner join Localidades on Comercios.cod_localidad = Localidades.id;";
            ResultSet rs = st.executeQuery(
                query
            );

            while (rs.next()) {
                float distance = 0;
                Comercio comercio1 = new Comercio();
                comercio1.setId(rs.getInt(1));
                comercio1.setName(rs.getString(2));
                comercio1.setBitmap(Helpers.getBitmapFromBytes((Blob) rs.getBlob(5)));
                comercio1.setAddress(rs.getString(12) + " - " + rs.getString(4));
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try{
                    List<Address> addresses = geocoder.getFromLocationName(rs.getString(4), 1);
                    if(addresses.size() > 0){

                        double latitud = addresses.get(0).getLatitude();
                        double logitude = addresses.get(0).getLongitude();

                        String direccionCliente = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString("address","");
                        List<Address> direccionClienteList = geocoder.getFromLocationName(direccionCliente, 1);

                        if(direccionClienteList.size() > 0){
                            double latitudCliente = direccionClienteList.get(0).getLatitude();
                            double logitudeCliente = direccionClienteList.get(0).getLongitude();

                            Location locationA = new Location("Comercio");

                            locationA.setLatitude(latitud);
                            locationA.setLongitude(logitude);

                            Location locationB = new Location("Usuario");

                            locationB.setLatitude(latitudCliente);
                            locationB.setLongitude(logitudeCliente);

                            distance = locationA.distanceTo(locationB);
                            distance /= 1000;
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                if(distancia == 0 || distance <= distancia){
                    comercio1.setDistancia(distance);
                    comercios.add(comercio1);
                }
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
