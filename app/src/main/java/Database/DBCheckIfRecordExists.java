package Database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCheckIfRecordExists extends AsyncTask<Boolean, Void, Boolean> {
    private Context context;
    private String query;
    private String messageExists = null;
    private String messageNotExists = null;
    private Intent redirectionIntent = null;
    private String direccion = null;
    private String userName = null;

    public DBCheckIfRecordExists() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean doesRecordExist() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            boolean exist = rs.next();
            if (exist) {
                direccion = rs.getString("direccion");
            }
            return exist;
        } catch (Exception e) {
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
        if (response) {
            Toast.makeText(context, response ? messageExists : messageNotExists, Toast.LENGTH_LONG).show();

            if (userName != null) {
                SharedPreferences sharedPref = context.getSharedPreferences(
                        "MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPref.edit();
                myEdit.putString("username", userName);
                if (direccion != null) {
                    myEdit.putString("address", direccion);
                }
                myEdit.commit();
            }

            if (redirectionIntent != null) {
                context.startActivity(redirectionIntent);
            }
        } else {
            Toast.makeText(context, messageNotExists, Toast.LENGTH_LONG).show();
        }
    }
}

