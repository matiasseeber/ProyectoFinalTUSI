package LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Entidades.PedidoDetalle;
import Entidades.Producto;

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    private ArrayList<PedidoDetalle> pedidoDetalleArrayList = new ArrayList<>();

    public SQLite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Productos(id Integer primary key autoincrement,nombre varchar(50),descripcion varchar(100),precio float, cantidad int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AbrirBD() {
        this.getWritableDatabase();
    }

    public void CerrarBD() {
        this.close();
    }

    public boolean InsertarProductoEnPedido(PedidoDetalle pedidoDetalle) {
        Producto producto = pedidoDetalle.getProducto();
        ContentValues Content = new ContentValues();
        Content.put("id", producto.getId());
        Content.put("nombre", producto.getNombre());
        Content.put("descripcion", producto.getDescripcion());
        Content.put("precio", producto.getPrecio());
        Cursor fila = this.getWritableDatabase().rawQuery("select * from Productos where id = '" + producto.getId() + "'", null);
        if (fila.moveToFirst()){
            float cantidad = fila.getColumnIndex("cantidad");
            Content.put("cantidad", cantidad + pedidoDetalle.getCantidad());
            return this.getWritableDatabase().update("Productos", Content, "id = " + producto.getId(), null) > 0;
        }
        Content.put("cantidad", pedidoDetalle.getCantidad());
        return this.getWritableDatabase().insert("Productos", null, Content) > 0;
    }

    public int ReturnQuantity() {
        Cursor fila = this.getWritableDatabase().rawQuery("select * from Productos", null);
        int cont = 0;
        while (fila.moveToNext()){
            cont++;
        }
        return cont;
    }

    public int deleteAll(){
        return this.getWritableDatabase().delete("Productos", null, null);
    }

    public int deleteByID(int id){
        return this.getWritableDatabase().delete("Productos", "id = " + id, null);
    }

    public ArrayList<PedidoDetalle> getPedidoDetalleArrayList(){
        Cursor fila = this.getWritableDatabase().rawQuery("select * from Productos", null);
        while (fila.moveToNext()){
            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            Producto producto = new Producto();
            pedidoDetalle.setCantidad(fila.getInt(4));
            producto.setId(fila.getInt(0));
            producto.setPrecio((float) fila.getFloat(3));
            producto.setNombre(fila.getString(1));
            pedidoDetalle.setProducto(producto);
            pedidoDetalleArrayList.add(pedidoDetalle);
        }
        return pedidoDetalleArrayList;
    }
}
