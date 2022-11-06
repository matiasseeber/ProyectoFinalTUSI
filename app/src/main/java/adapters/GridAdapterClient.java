package adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tp_final.FragmentPedidoAEntregarCliente;
import com.example.tp_final.FragmentPedidoEntregado;
import com.example.tp_final.FragmentPedidoEntregadoCliente;
import com.example.tp_final.FragmentPedidoPendiente;
import com.example.tp_final.FragmentPedidoPendienteCliente;
import com.example.tp_final.FragmentPedidoRechazadoCliente;
import com.example.tp_final.FragmentPedidoSinEntregar;

public class GridAdapterClient extends FragmentStateAdapter {

    public GridAdapterClient(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    // AL MOMENTO DE CARGAR LOS ITEMS-TABS DEBE RECIBIR POR PARÁMETRO UN OBJETO DE CADA FRAGMENTO NECESITADO (YA HABIENDO CARGADO EL GRID DE CADA UNO PREVIAMENTE)
    // LUEGO, SE PASA A LA FUNCION "createFragment" ESOS OBJETOS EN LOS CASE, EN VEZ DE NUEVOS OBJETOS DE LOS FRAGMENTOS

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentPedidoPendienteCliente();
            case 1:
                return new FragmentPedidoAEntregarCliente();
            case 2:
                return new FragmentPedidoEntregadoCliente();
            default:
                return new FragmentPedidoRechazadoCliente();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
