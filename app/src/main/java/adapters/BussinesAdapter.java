package adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.tp_final.PopUpEliminar_Producto_Comercio;
import com.example.tp_final.R;
import com.example.tp_final.Seleccion_Comercio;

import Entidades.Comercio;

public class BussinesAdapter extends BaseAdapter {
    private ArrayList<Comercio> elementos;
    private Context context;

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


        Comercio comercio = getItem(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,Seleccion_Comercio.class);
                intent.putExtra("idComercio", comercio.getId());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        TextView txtIdComercio = (TextView) view.findViewById(R.id.txtIdComercio);
        TextView txtNombreComercio = (TextView) view.findViewById(R.id.txtNombreComercio_ComerciosCliente);
        TextView txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgLogoComercio);
        TextView txtDistancia = (TextView) view.findViewById(R.id.txtDistanciaDeCliente);
        TextView txtEstrellas = (TextView) view.findViewById(R.id.txtEstrellas);

        Float distancia = comercio.getDistancia() - comercio.getDistancia() % 1;
        String formatedDistance = String.valueOf(distancia).replace(".0", "");
        txtDistancia.setText(formatedDistance + " KM");

        String calificaciones = "";

        if(comercio.getPromedioCalificaciones() > 0){
            calificaciones = String.valueOf(comercio.getPromedioCalificaciones());
        }else{
            calificaciones = "Sin valoraciones";
        }

        txtEstrellas.setText(calificaciones);
        txtIdComercio.setText(String.valueOf(comercio.getId()));
        txtNombreComercio.setText(String.valueOf(comercio.getName()));
        txtDireccion.setText(comercio.getAddress());
        imageView.setImageBitmap(comercio.getBitmap());

        return view;
    }
}
