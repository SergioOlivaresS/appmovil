package com.example.gasteando;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

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
                        //llamar al fragmento resumen
                        Toast.makeText(getApplicationContext(),"resumen gastos" , Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // volver al menu
                        Toast.makeText(getApplicationContext(),"menu" , Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        // llamar a categoria
                        Toast.makeText(getApplicationContext(),"categoria" , Toast.LENGTH_SHORT).show();

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

    private void setSupportActionBar() {
    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
