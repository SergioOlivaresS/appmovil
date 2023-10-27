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
                //codificar tap al tocar
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        MenuFG m = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,m).commit();
                        break;
                    case 1:
                        //llamar al fragmento resumen
                        ResumenFG h = new ResumenFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,h).commit();
                        break;
                    case 2:
                        // llamar a categoria
                        CategoriaFG c = new CategoriaFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,c).commit();
                        break;
                    case 3:
                        // Devolver al fragmento MenuFG
                        MenuFG menuFragment = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, menuFragment).commit();
                        break;

                }
                Objects.requireNonNull(tl.getTabAt(position)).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // codificar tap al dejar de tocar

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //cuando se vuelve a seleccionar
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        MenuFG m = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,m).commit();
                        break;
                    case 1:
                        //llamar al fragmento resumen
                        ResumenFG h = new ResumenFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,h).commit();
                        break;
                    case 2:
                        // llamar a categoria
                        CategoriaFG c = new CategoriaFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,c).commit();
                        break;
                    case 3:
                        // Devolver al fragmento MenuFG
                        MenuFG menuFragment = new MenuFG();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, menuFragment).commit();
                        break;

                }

            }
        });

        // Cargar el fragmento MenuFG al iniciar la actividad
        MenuFG m = new MenuFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, m).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    public void ingresarproductosfg(View v) {
        IngresarproductosFG ig = new IngresarproductosFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, ig).commit();
    }

    public void gastorealizadofg (View v) {
        GastorealizadoFG gr = new GastorealizadoFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, gr).commit();
    }

    public void calendariofg(View v) {
        CalendarioFG cl = new CalendarioFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, cl).commit();
    }

    public void comparadorfg(View v) {
        CompararGastosFG cg = new CompararGastosFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, cg).commit();
    }

    public void menu(View v) {
        MenuFG m = new MenuFG();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,m).commit();
    }

    public void gastorealizadocalendario(String fechaSeleccionada) {

        GastorealizadoFG gastorealizadoFragment = new GastorealizadoFG();
        Bundle args = new Bundle();
        args.putString("fechaSeleccionada", fechaSeleccionada);
        gastorealizadoFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, gastorealizadoFragment).commit();
    }

    public GastosDatabaseHelper getDbHelper() {
        return null;
    }


    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, fragment)
                .addToBackStack(null)
                .commit();
    }
}
