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
import com.example.tp_final.PopUp_Pedidos_Comercio_SinEntregar;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoCabecera;

public class PedidosSinEntregarComercioAdapter extends BaseAdapter {
    private ArrayList<PedidoCabecera> elementos;
    private Context context;

    public PedidosSinEntregarComercioAdapter(Context context, ArrayList<PedidoCabecera> elementos) {
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
            view = inflater.inflate(R.layout.grid_template_pedidos_sinentregar,null);
        }

        PedidoCabecera pedidoCabecera = getItem(position);

        TextView txtNombreClientePedidosComercio = (TextView) view.findViewById(R.id.txtNombreClientePedidosComercio);
        TextView txtIdPedidoComercio = (TextView) view.findViewById(R.id.txtIdPedidoComercio);
        TextView txtTotalPedidoComercio = (TextView) view.findViewById(R.id.txtTotalPedidoComercio);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopUp_Pedidos_Comercio_SinEntregar.class);
                intent.putExtra("idPedidoCabecera", pedidoCabecera.getId());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtNombreClientePedidosComercio.setText(pedidoCabecera.getCliente().getNombreUsuario());
        txtTotalPedidoComercio.setText(String.valueOf(pedidoCabecera.getTotal()));
        txtIdPedidoComercio.setText(String.valueOf(pedidoCabecera.getId()));

        return view;
    }
}
