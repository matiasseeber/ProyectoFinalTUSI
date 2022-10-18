package Helpers;

import android.widget.EditText;

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

    public void emptyAllControls(ArrayList<EditText> editTexts){
        for(int i = 0; i < editTexts.size(); i++){
            EditText editText = editTexts.get(i);
            editText.setText("");
        }
    }
}
