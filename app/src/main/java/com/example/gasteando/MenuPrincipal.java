package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setSupportActionBar();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tl = findViewById(R.id.tablayout);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //codificar tap al tocar
                int position = tab.getPosition();

                switch (position) {
                    case 0:
                        // Volver a la ventana principal
                        Intent f = new Intent(MenuPrincipal.this, MenuPrincipal.class);
                        startActivity(f);
                        break;

                    case 1:
                        //llamar al fragmento resumen
                        Resumen r = new Resumen();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, r).commit();
                        break;
                    case 2:
                        // llamar a categoria
                        Categoria c = new Categoria();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, c).commit();

                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // codificar tap al dejar de tocar

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //cuando se vuelve a seleccionar

            }
        });

    }

    public void ingresarproductos(View v){
        Intent producto = new Intent(this, ingresarproductos.class);
        startActivity(producto);
    }

    private void setSupportActionBar() {
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
