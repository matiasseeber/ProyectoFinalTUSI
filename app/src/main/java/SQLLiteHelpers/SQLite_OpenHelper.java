package SQLLiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    public SQLite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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

    public long Insert(String table, ContentValues Content){
        return this.getWritableDatabase().insert(table ,null , Content);
    }

    public boolean CheckIfRecordExists(String query){
        Cursor fila = this.getWritableDatabase().rawQuery(query, null);
        return fila.moveToFirst();
    }

    /*
    public long Insert(String matricula, String tiempo, String IdUsuario){

        ContentValues Content = new ContentValues();
        Content.put("matricula",matricula);
        Content.put("tiempo",tiempo);
        Content.put("IdUsuario",IdUsuario);

        return this.getWritableDatabase().insert("parqueosTable",null,Content);
    }*/
}
