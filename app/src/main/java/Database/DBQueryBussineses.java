package Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Entidades.Comercio;
import Helpers.Helpers;

public class DBQueryBussineses extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;
    private String message = "";
    private ImageView imageView;
    private Comercio comercio;
    private Bitmap bitmap;

    public DBQueryBussineses() {
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private boolean setImage(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Comercios where id = " + comercio.getId());
            if(rs.next()){
                bitmap = Helpers.getBitmapFromBytes((Blob) rs.getBlob("logo"));
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected Boolean doInBackground(Boolean... Boolean) {
        return setImage();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response)
            imageView.setImageBitmap(bitmap);
    }
}
