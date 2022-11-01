package Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Base64;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {
    public static boolean doesStringMatchRegexp(String string, String regexp){
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(string);
        boolean doesMatch = matcher.find();
        return doesMatch;
    }

    public static void emptyAllControls(ArrayList<EditText> editTexts){
        for(int i = 0; i < editTexts.size(); i++){
            EditText editText = editTexts.get(i);
            editText.setText("");
        }
    }

    public static Bitmap getBitmapFromBytes(Blob blob) {
        try {
            int blobLength = (int) blob.length();
            byte[] bytes = blob.getBytes(1, blobLength);
            blob.free();
            if (bytes != null) {
                byte[] decodedString = Base64.decode(bytes, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                return decodedByte;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String returnImageValueFromBitmap(BitmapDrawable bitmapDrawable){
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        String imageValue = java.util.Base64.getEncoder().encodeToString(bArray);
        return imageValue;
    }

    public static int getUserId(Context context){
        return context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getInt("id",-1);
    }

    public static String getBussinesPassword(Context context){
        return context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString("password","");
    }

    public static float returnBussinessRatingAverage(ResultSet rs) throws SQLException {
        float average = 0;
        int cont = 0;
        rs.beforeFirst();
        while (rs.next()){
            if(rs.getFloat("puntuacion") != 0.0f)
                average += rs.getFloat("puntuacion");
            cont++;
        }
        return average /= cont;
    }
}
