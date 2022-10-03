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
        db.execSQL("create table Provincias (Id_Provincia integer primary key autoincrement, Nombre text)");
        db.execSQL("create table Localidades (Id_Localidad integer primary key autoincrement, Id_Provincia text, FOREIGN KEY(Id_Provincia) REFERENCES Provincias(Id_Provincia))");
        db.execSQL("create table Clientes (IdCliente integer primary key autoincrement, Nombre text, Apellido text, Email text unique, NombreDeUsuario text unique, Contraseña text, Sexo text, Edad integer, Localidad integer, Estado bit,FOREIGN KEY(Localidad) REFERENCES Localidades(Id_Localidad))");
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

    public boolean isUserNameLareadyInUse(String userName){
        Cursor fila = this.getWritableDatabase().rawQuery("select * from Clientes where NombreDeUsuario = '" + userName + "'", null);
        return fila.moveToFirst();
    }

    public long Insert(String matricula, String tiempo, String IdUsuario){

        ContentValues Content = new ContentValues();
        Content.put("matricula",matricula);
        Content.put("tiempo",tiempo);
        Content.put("IdUsuario",IdUsuario);

        return this.getWritableDatabase().insert("parqueosTable",null,Content);
    }
}
