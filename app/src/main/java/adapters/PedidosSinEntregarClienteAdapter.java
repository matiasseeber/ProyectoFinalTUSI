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
import com.example.tp_final.PopUp_Pedidos_Comercio;
import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.PedidoCabecera;

public class PedidosSinEntregarClienteAdapter extends BaseAdapter {
    private ArrayList<PedidoCabecera> elementos;
    private Context context;

    public PedidosSinEntregarClienteAdapter(Context context, ArrayList<PedidoCabecera> elementos) {
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
            view = inflater.inflate(R.layout.grid_template_pedidos_aentregar_usuarios,null);
        }

        PedidoCabecera pedidoCabecera = getItem(position);

        TextView txtNombreComercio = (TextView) view.findViewById(R.id.txtNombreClientePedidosComercio);
        TextView txtFechaPedido = (TextView) view.findViewById(R.id.txtFechaPedidoPendienteUsuario);
        TextView txtMetodoDePago = (TextView) view.findViewById(R.id.txtMetodoDePagoPedidosAEntregarClientes);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView48);
        TextView id = (TextView) view.findViewById(R.id.txtIdPedidoComercio);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopUp_Cabecera_Pedidos_Usuarios.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idPedidoCabecera", pedidoCabecera.getId());
                context.startActivity(intent);
            }
        });

        txtNombreComercio.setText(pedidoCabecera.getComercio().getName());
        txtFechaPedido.setText(pedidoCabecera.getFecha());
        imageView.setImageBitmap(pedidoCabecera.getComercio().getBitmap());
        id.setText(String.valueOf(pedidoCabecera.getId()));

        if(pedidoCabecera.isEfectivo()){
            txtMetodoDePago.setText("Efectivo");
        }else{
            txtMetodoDePago.setText(pedidoCabecera.getTarjeta().getTipoTarjeta());
        }

        return view;
    }
}
