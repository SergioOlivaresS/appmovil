package com.example.gasteando;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tl = findViewById(R.id.tablayout);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        MenuFG m = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, m).commit();
                        break;
                    case 1:
                        ResumenFG h = new ResumenFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, h).commit();
                        break;
                    case 2:
                        CategoriaFG c = new CategoriaFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, c).commit();
                        break;
                    case 3:
                        MenuFG menuFragment = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, menuFragment).commit();
                        break;

                }
                Objects.requireNonNull(tl.getTabAt(position)).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        MenuFG m = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, m).commit();
                        break;
                    case 1:
                        ResumenFG h = new ResumenFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, h).commit();
                        break;
                    case 2:
                        CategoriaFG c = new CategoriaFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, c).commit();
                        break;
                    case 3:
                        MenuFG menuFragment = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, menuFragment).commit();
                        break;
                }
            }
        });

        MenuFG m = new MenuFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, m).commit();
    }

    @Override
    public void onBackPressed() {
        // Obtén el fragmento actual en el contenedor
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contenedor);

        if (currentFragment instanceof GastorealizadoFG) {
            MenuFG menuFragment = new MenuFG();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, menuFragment).commit();
        } else {
            super.onBackPressed(); // Comportamiento predeterminado para otros fragmentos
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void ingresarproductosfg(View v) {
        IngresarproductosFG ig = new IngresarproductosFG();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, ig)
                .addToBackStack(null)
                .commit();
    }

    public void gastorealizadofg(View v) {
        GastorealizadoFG gr = new GastorealizadoFG();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, gr)
                .addToBackStack(null)
                .commit();
    }

    public void calendariofg(View v) {
        CalendarioFG cl = new CalendarioFG();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, cl)
                .addToBackStack(null)
                .commit();
    }

    public void comparadorfg(View v) {
        CompararGastosFG cg = new CompararGastosFG();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, cg)
                .addToBackStack(null)
                .commit();
    }

    public void setFechaSeleccionada(String fecha) {
        // Obtener una referencia al fragmento GastorealizadoFG
        GastorealizadoFG gastorealizadoFG = (GastorealizadoFG) getSupportFragmentManager().findFragmentByTag("gastorealizado");

        if (gastorealizadoFG != null) {
            gastorealizadoFG.setFechaSeleccionada(fecha);
            // Reemplazar o actualizar el fragmento GastorealizadoFG con la fecha seleccionada
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, gastorealizadoFG)
                    .commit();
        }
    }
}
