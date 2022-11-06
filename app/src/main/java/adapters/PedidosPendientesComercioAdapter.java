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

import com.example.tp_final.PopUp_AgregarProducto;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoCabecera;
import Entidades.Producto;

public class PedidosPendientesComercioAdapter extends BaseAdapter {
    private ArrayList<PedidoCabecera> elementos;
    private Context context;

    public PedidosPendientesComercioAdapter(Context context, ArrayList<PedidoCabecera> elementos) {
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public PedidoCabecera getItem(int position) {
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
            view = inflater.inflate(R.layout.grid_template_pedidos_pendientes,null);
        }

        PedidoCabecera pedidoCabecera = getItem(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(context, PopUp_AgregarProducto.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idProducto", producto.getId());
                context.startActivity(intent);
                 */
            }
        });

        TextView txtNombreClientePedidosComercio = (TextView) view.findViewById(R.id.txtNombreClientePedidosComercio);
        TextView txtIdPedidoComercio = (TextView) view.findViewById(R.id.txtIdPedidoComercio);
        TextView txtTotalPedidoComercio = (TextView) view.findViewById(R.id.txtTotalPedidoComercio);

        txtNombreClientePedidosComercio.setText(pedidoCabecera.getCliente().getNombreUsuario());
        txtTotalPedidoComercio.setText(String.valueOf(pedidoCabecera.getTotal()));
        txtIdPedidoComercio.setText(String.valueOf(pedidoCabecera.getId()));

        return view;
    }
}
