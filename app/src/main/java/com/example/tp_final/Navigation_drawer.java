package com.example.tp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Navigation_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    private TextView txtNombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.NavDrawer);
        navigationView = (NavigationView) findViewById(R.id.NavView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.content, new ComerciosFragment()).commit();
        this.setTitle("Comercios");

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        txtNombreUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtNombreUsuario);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        txtNombreUsuario.setText(sh.getString("username", ""));

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void SelectItem(MenuItem item){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.ComerciosItem:
                ft.replace(R.id.content,new ComerciosFragment()).commit();
                break;
            case R.id.pedidosItem:
                ft.replace(R.id.content,new PedidosFragment()).commit();
                break;
            case R.id.CuentaItem:
                ft.replace(R.id.content,new ComercioFragment()).commit();
                break;
        }
        this.setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        SelectItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}