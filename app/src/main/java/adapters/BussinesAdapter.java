package adapters;

import android.content.Context;
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

import com.example.tp_final.R;
import Entidades.Comercio;

public class BussinesAdapter extends BaseAdapter {
    private ArrayList<Comercio> elementos;
    private Context context;
    private EditText txtEstrellas;

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

        TextView txtIdComercio = (TextView) view.findViewById(R.id.txtIdComercio);
        TextView txtNombreComercio = (TextView) view.findViewById(R.id.txtNombreComercio_ComerciosCliente);
        TextView txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgLogoComercio);
        TextView txtDistancia = (TextView) view.findViewById(R.id.txtDistanciaDeCliente);

        double latitud = 0, longitud = 0;

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocationName("Barrio privado santabarbara", 1);
            if(addresses.size() > 0){
                latitud = addresses.get(0).getLatitude();
                longitud = addresses.get(0).getLongitude();

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Location locationA = new Location("Comercio");

        locationA.setLatitude(comercio.getLatitude());
        locationA.setLongitude(comercio.getLongitude());

        Location locationB = new Location("Usuario");

        locationB.setLatitude(latitud);
        locationB.setLongitude(longitud);

        float distance = locationA.distanceTo(locationB);
        distance /= 1000;

        String distanceFormated = String.valueOf(distance).substring(0, 3);

        txtDistancia.setText(distanceFormated + " KM");

        txtIdComercio.setText(String.valueOf(comercio.getId()));
        txtNombreComercio.setText(String.valueOf(comercio.getName()));
        txtDireccion.setText(comercio.getAddress());
        imageView.setImageBitmap(comercio.getBitmap());

        return view;
    }
}
