package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import com.example.tp_final.R;
import Entidades.Comercio;

public class BussinesAdapter extends BaseAdapter {
    private ArrayList<Comercio> elementos;
    private Context context;
    private EditText txtEstrellas;
    private EditText txtNombreComercio;
    private EditText txtDistancia;
    private ImageView imgComercio;

    public BussinesAdapter(Context context, ArrayList<Comercio> elementos) {
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Comercio getItem(int position) {
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
            view = inflater.inflate(R.layout.grid_template_comercios,null);
        }

        TextView txtIdComercio = (TextView) view.findViewById(R.id.txtIdComercio);
        TextView txtNombreComercio = (TextView) view.findViewById(R.id.txtNombreComercio_ComerciosCliente);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgLogoComercio);

        txtIdComercio.setText(String.valueOf(getItem(position).getId()));
        txtNombreComercio.setText(String.valueOf(getItem(position).getName()));
        imageView.setImageBitmap(getItem(position).getBitmap());

        return view;
    }
}
