package Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Base64;
import android.widget.EditText;

import com.mysql.jdbc.Blob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

}
