package Database;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import Entidades.Clientes;
import Entidades.Localidad;
import Entidades.Tarjeta;
import Helpers.Helpers;

public class DBLoadCreditCardsInSpinner  extends AsyncTask<Boolean, Void, Boolean> {

    private ArrayList<Tarjeta> tarjetas;
    private Spinner spinner;
    private Context context;
    ArrayAdapter<Tarjeta> adapter;
    private boolean addNewCardOption = true;
    private ImageView deleteBtn;

    public DBLoadCreditCardsInSpinner() {
    }

    public boolean isAddNewCardOption() {
        return addNewCardOption;
    }

    public void setAddNewCardOption(boolean addNewCardOption) {
        this.addNewCardOption = addNewCardOption;
    }

    public ImageView getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(ImageView deleteBtn) {
        this.deleteBtn = deleteBtn;
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

        tarjetas = new ArrayList<Tarjeta>();
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(-1);
        if(addNewCardOption) tarjetas.add(tarjeta);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            int userId = Helpers.getUserId(context);
            ResultSet rs = st.executeQuery("Select * from Tarjetas inner join Clientes on id_Cliente = Clientes.id where Clientes.id = " + userId + " and Tarjetas.estado = 1;");
            rs.beforeFirst();
            while (rs.next()) {
                tarjeta = new Tarjeta();
                tarjeta.setId(rs.getInt("id"));
                tarjeta.setNumTarjeta(rs.getString("numTarjeta"));
                tarjeta.setTipoTarjeta(rs.getString("tipoTarjeta"));
                Clientes clientes = new Clientes();
                clientes.setNombre(rs.getString("nombre"));
                clientes.setApellido(rs.getString("apellido"));
                tarjeta.setCliente(clientes);
                tarjetas.add(tarjeta);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        spinner.setVisibility(View.GONE);
        if(deleteBtn != null) deleteBtn.setVisibility(View.GONE);
        if(tarjetas.size() > 1 && addNewCardOption || tarjetas.size() > 0 && !addNewCardOption){
            spinner.setVisibility(View.VISIBLE);
            if(deleteBtn != null) deleteBtn.setVisibility(View.VISIBLE);
            adapter =
                    new ArrayAdapter<Tarjeta>(context,  android.R.layout.simple_spinner_dropdown_item, tarjetas);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}

