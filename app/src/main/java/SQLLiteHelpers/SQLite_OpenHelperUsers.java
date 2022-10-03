package SQLLiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_OpenHelperUsers extends SQLiteOpenHelper {

    public SQLite_OpenHelperUsers(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table clientes (IdCliente integer primary key autoincrement, NombreUsuario text, CorreoElectronico text, Contraseña text)");
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

    public long Insert(String Nombre, String Correo, String Contraseña){

        ContentValues Content = new ContentValues();
        Content.put("NombreUsuario",Nombre);
        Content.put("CorreoElectronico",Correo);
        Content.put("Contraseña",Contraseña);

        return this.getWritableDatabase().insert("usuarios",null,Content);
    }

    public boolean DoesUserExist(String userName, String password){
        Cursor fila = this.getWritableDatabase().rawQuery("select * from usuarios where NombreUsuario = '" + userName + "' AND Contraseña='" + password + "'", null);
        return fila.moveToFirst();
    }
}
