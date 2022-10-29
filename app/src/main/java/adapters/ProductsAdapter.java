package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_final.R;

import java.util.ArrayList;

import Entidades.Comercio;
import Entidades.Producto;

public class ProductsAdapter extends BaseAdapter {
    private ArrayList<Producto> elementos;
    private Context context;
    private EditText txtEstrellas;

    public ProductsAdapter(Context context, ArrayList<Producto> elementos) {
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
            view = inflater.inflate(R.layout.grid_template_productos,null);
        }

        Producto producto = getItem(position);

        TextView txtNombreProducto = (TextView) view.findViewById(R.id.txtNombreProducto);
        TextView txtPrecioProducto = (TextView) view.findViewById(R.id.txtPrecioProducto);
        TextView txtIdProducto = (TextView) view.findViewById(R.id.txtIdProducto);
        ImageView ImgComercioSeleccion = (ImageView) view.findViewById(R.id.ImgComercioSeleccion);

        txtNombreProducto.setText(producto.getNombre());
        txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
        txtIdProducto.setText(String.valueOf(producto.getId()));
        ImgComercioSeleccion.setImageBitmap(producto.getBitmapImage());

        return view;
    }
}
