package SQLLiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Entidades.Provincias;

public class SQLite_OpenHelperStates extends SQLiteOpenHelper {

    public SQLite_OpenHelperStates(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void AbrirBD(){
        this.getWritableDatabase();
    }

    public void CerrarBD(){
        this.close();
    }

    public long Insert(String Nombre){

        ContentValues Content = new ContentValues();
        Content.put("Nombre",Nombre);

        return this.getWritableDatabase().insert("Provincias",null,Content);
    }

    public ArrayList<Provincias> GetAllStates(){
        ArrayList<Provincias> provincias = new ArrayList<>();
        Cursor fila = this.getWritableDatabase().rawQuery("select * from Provincias", null);
        if(fila.moveToFirst())
        {

        }
        return provincias;
    }
}
