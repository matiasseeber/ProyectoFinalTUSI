package com.example.tp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Navigation_Drawer_Comercio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    private TextView txtNombreUsuario;
    private TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_comercio);

        drawerLayout = (DrawerLayout) findViewById(R.id.NavDrawerComercio);
        navigationView = (NavigationView) findViewById(R.id.NavViewComercio);
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.contentComercio, new ComercioFragment()).commit();
        this.setTitle("Comercio");

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        txtNombreUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtNombreUsuario);

        logOut = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtLogOut);

        Activity activity = this;

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                ActivityCompat.finishAffinity(activity);
                startActivity(intent);
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        txtNombreUsuario.setText(sh.getString("username", ""));

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void SelectItem(MenuItem item){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.pedidosItemComercio:
                ft.replace(R.id.contentComercio,new PedidosFragment_Comercio()).commit();
                break;
            case R.id.CuentaitemComercio:
                ft.replace(R.id.contentComercio,new ComercioFragment()).commit();
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