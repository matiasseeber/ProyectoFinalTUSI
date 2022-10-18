package Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_final.ComercioFragment;
import com.example.tp_final.LoginUsuario;
import com.example.tp_final.Navigation_drawer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Database.DataDB;

public class DBRedirectOnLogin extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private String query;
    private String messageExists;
    private String messageNotExists;
    private Intent redirectionIntent;

    public DBRedirectOnLogin() {
    }

    public DBRedirectOnLogin(Context context, String query, String messageExists, String messageNotExists){
        this.context = context;
        this.query = query;
        this.messageExists = messageExists;
        this.messageNotExists = messageNotExists;
    }

    public Intent getRedirectionIntent() {
        return redirectionIntent;
    }

    public void setRedirectionIntent(Intent redirectionIntent) {
        this.redirectionIntent = redirectionIntent;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMessageExists() {
        return messageExists;
    }

    public void setMessageExists(String messageExists) {
        this.messageExists = messageExists;
    }

    public String getMessageNotExists() {
        return messageNotExists;
    }

    public void setMessageNotExists(String messageNotExists) {
        this.messageNotExists = messageNotExists;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean doesRecordExist(){
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

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return doesRecordExist();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(context, response ? messageExists : messageNotExists, Toast.LENGTH_LONG).show();
            context.startActivity(redirectionIntent);
        }
        else
        {
            Toast.makeText(context, messageNotExists, Toast.LENGTH_LONG).show();
        }
    }
}

