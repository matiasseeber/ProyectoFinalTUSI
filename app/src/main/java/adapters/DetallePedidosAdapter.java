package adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tp_final.PopUp_Pedidos_Comercio;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;
import Entidades.Producto;

public class DetallePedidosAdapter extends BaseAdapter {
    private ArrayList<PedidoDetalle> elementos;
    private Context context;

    public DetallePedidosAdapter(Context context, ArrayList<PedidoDetalle> elementos) {
        this.context = context;
        this.elementos = elementos;
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
            view = inflater.inflate(R.layout.grid_template_pedido_usuario_comercio,null);
        }

        PedidoDetalle pedidoDetalle = getItem(position);

        Producto producto = pedidoDetalle.getProducto();

        TextView txtNombreProducto = (TextView) view.findViewById(R.id.txtNombreProducto);
        TextView txtCant = (TextView) view.findViewById(R.id.txtCantGrvPedidoUsuario);
        TextView txtPrecio = (TextView) view.findViewById(R.id.txtPrecioGrvPedidoUsuario);

        txtNombreProducto.setText(producto.getNombre());
        txtCant.setText(String.valueOf(pedidoDetalle.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        return view;
    }
}
