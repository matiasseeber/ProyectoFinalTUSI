








package Database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Tarjeta;

public class DBDeleteCreditCard extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private Spinner spinner;
    private Tarjeta tarjeta;
    private ImageView deleteBtn;

    public DBDeleteCreditCard() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(ImageView deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean deleteRecord() {
        int insertedRows = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement preparedStatement = con.prepareStatement("update Tarjetas set estado = 0 where id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, tarjeta.getId());
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
            ArrayList<Tarjeta> tarjetas = new ArrayList<>();
            for (int i = 0; i < spinner.getCount(); i++){
                Tarjeta aux = (Tarjeta) spinner.getSelectedItem();
                if(tarjeta.getId() != aux.getId()){
                    tarjetas.add(aux);
                }
            }
            if(tarjetas.size() != 0){
                ArrayAdapter<Tarjeta> adapter =
                        new ArrayAdapter<Tarjeta>(context,  android.R.layout.simple_spinner_dropdown_item, tarjetas);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }else{
                spinner.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
            }
            Toast.makeText(context, "La tarjeta fue dada de baja exitosamente.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No se pudo dar de baja el producto.", Toast.LENGTH_LONG).show();
        }
    }
}