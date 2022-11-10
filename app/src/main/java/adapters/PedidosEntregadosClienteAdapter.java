package adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_final.PopUp_Cabecera_Comprobante;
import com.example.tp_final.PopUp_Cabecera_Pedidos_Usuarios;
import com.example.tp_final.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import Entidades.PedidoCabecera;

public class PedidosEntregadosClienteAdapter extends BaseAdapter {
    private ArrayList<PedidoCabecera> elementos;
    private Context context;

    public PedidosEntregadosClienteAdapter(Context context, ArrayList<PedidoCabecera> elementos) {
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
            view = inflater.inflate(R.layout.grid_template_pedido_pendiente_usuario_cabecera,null);
        }

        PedidoCabecera pedidoCabecera = getItem(position);

        TextView txtNombreComercio = (TextView) view.findViewById(R.id.textView17);
        TextView txtFechaPedido = (TextView) view.findViewById(R.id.textView19);
        TextView txtMetodoDePago = (TextView) view.findViewById(R.id.textView18);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView52);
        TextView txtObjecto = (TextView) view.findViewById(R.id.textView20);
        Button comprobanteBtn = (Button) view.findViewById(R.id.button12);

        Gson gson = new Gson();
        String pedido = gson.toJson(pedidoCabecera);
        txtObjecto.setText(pedido);

        comprobanteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), PopUp_Cabecera_Comprobante.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pedido", pedido);
                context.startActivity(intent);
            }
        });

        txtNombreComercio.setText(pedidoCabecera.getComercio().getName());
        txtFechaPedido.setText(pedidoCabecera.getFecha());
        imageView.setImageBitmap(pedidoCabecera.getComercio().getBitmap());

        if(pedidoCabecera.isEfectivo()){
            txtMetodoDePago.setText("Efectivo");
        }else{
            txtMetodoDePago.setText(pedidoCabecera.getTarjeta().getTipoTarjeta());
        }

        return view;
    }
}
