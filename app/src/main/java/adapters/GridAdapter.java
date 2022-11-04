package adapters;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tp_final.FragmentPedidoEntregado;
import com.example.tp_final.FragmentPedidoPendiente;
import com.example.tp_final.FragmentPedidoSinEntregar;

public class GridAdapter extends FragmentStateAdapter {

    public GridAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentPedidoPendiente();
            case 1:
                return new FragmentPedidoSinEntregar();
            default:
                return new FragmentPedidoEntregado();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
