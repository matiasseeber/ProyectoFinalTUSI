package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_final.PopUpEliminar_Producto_Comercio;
import com.example.tp_final.Productos_ABM_Comercio;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoDetalle;
import Entidades.Producto;
import LocalDB.SQLite_OpenHelper;

public class PedidoClienteAdapter extends BaseAdapter {
    private ArrayList<PedidoDetalle> elementos;
    private Context context;
    SQLite_OpenHelper sqLite_openHelper;
    private Activity activity;
    public PedidoClienteAdapter(Context context, ArrayList<PedidoDetalle> elementos, Activity activity) {
        this.context = context;
        this.elementos = elementos;
        this.activity = activity;
        sqLite_openHelper = new SQLite_OpenHelper(context, "TPFINAL", null, 1);
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public PedidoDetalle getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = convertView;

        if (convertView == null){
            view = inflater.inflate(R.layout.grid_template_pedido_usuario,null);
        }

        PedidoDetalle pedidoDetalle = getItem(position);

        TextView txtNombreProducto = (TextView) view.findViewById(R.id.txtNombreProducto);
        TextView txtPrecioProducto = (TextView) view.findViewById(R.id.txtPrecioGrvPedidoUsuario);
        TextView txtCant = (TextView) view.findViewById(R.id.txtCantGrvPedidoUsuario);
        ImageView btnEliminar = (ImageView) view.findViewById(R.id.deleteProductBussiness);
        TextView txtId = (TextView) view.findViewById(R.id.txtGrvPedidoUsuarioIdProducto);

        Producto producto = pedidoDetalle.getProducto();

        txtNombreProducto.setText(producto.getNombre());
        txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
        txtCant.setText(String.valueOf(pedidoDetalle.getCantidad()));
        txtId.setText(String.valueOf(producto.getId()));

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filasAfectadas = sqLite_openHelper.deleteByID(producto.getId());
                if(filasAfectadas > 0){
                    Toast.makeText(context, "Se pudo eliminar el producto del pedido correctamente.", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }else{
                    Toast.makeText(context, "No se pudo eliminar el producto del pedido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
