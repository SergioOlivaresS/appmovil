package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
                        Intent Resumen = new Intent(MenuPrincipal.this, Resumen.class);
                        startActivity(Resumen);
                        break;
                    case 2:
                        // llamar a categoria
                        Intent Categoria = new Intent(MenuPrincipal.this, Categoria.class);
                        startActivity(Categoria);
                        break;

                }
                tl.getTabAt(position).select();

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

    public void gastorealizado(View v){
        Intent gasto = new Intent(this, GastoRealizado.class);
        startActivity(gasto);
    }


    private void setSupportActionBar() {
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
