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

import Entidades.Producto;

public class ProductsClientAdapter extends BaseAdapter {
    private ArrayList<Producto> elementos;
    private Context context;

    public ProductsClientAdapter(Context context, ArrayList<Producto> elementos) {
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Producto getItem(int position) {
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
            view = inflater.inflate(R.layout.grid_template_productos_usuario,null);
        }

        Producto producto = getItem(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopUp_AgregarProducto.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idProducto", producto.getId());
                context.startActivity(intent);
            }
        });

        TextView txtNombreProducto = (TextView) view.findViewById(R.id.txtNombreProducto);
        TextView txtPrecioProducto = (TextView) view.findViewById(R.id.txtCantGrvPedidoUsuario);
        ImageView ImgComercioSeleccion = (ImageView) view.findViewById(R.id.ImgComercioSeleccion);

        txtNombreProducto.setText(producto.getNombre());
        txtPrecioProducto.setText("Precio: " + String.valueOf(producto.getPrecio()));
        ImgComercioSeleccion.setImageBitmap(producto.getBitmapImage());

        return view;
    }
}
