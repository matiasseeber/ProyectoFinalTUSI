package adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_final.PopUp_Cabecera_Pedidos_Usuarios;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoCabecera;
import Entidades.PedidoDetalle;

public class DetallePedidoClienteAdapter extends BaseAdapter {
    private ArrayList<PedidoDetalle> elementos;
    private Context context;
    private PedidoCabecera pedidoCabecera;

    public DetallePedidoClienteAdapter(Context context, PedidoCabecera pedidoCabecera) {
        this.context = context;
        this.pedidoCabecera = pedidoCabecera;
        this.elementos = pedidoCabecera.getPedidoDetalles();
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

        TextView txtNombreProducto = (TextView) view.findViewById(R.id.txtNombreProducto);
        TextView txtCant = (TextView) view.findViewById(R.id.txtCantGrvPedidoUsuario);
        TextView txtPrecio = (TextView) view.findViewById(R.id.txtPrecioGrvPedidoUsuario);

        txtNombreProducto.setText(pedidoDetalle.getProducto().getNombre());
        txtCant.setText(String.valueOf(pedidoDetalle.getCantidad()));
        txtPrecio.setText(String.valueOf(pedidoDetalle.getProducto().getPrecio()));

        return view;
    }
}
