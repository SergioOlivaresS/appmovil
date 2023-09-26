package com.example.gasteando;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendario extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        CalendarView calendarView = findViewById(R.id.calendarView);

        // Configurar un listener para manejar la selección de fechas
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Convertir la fecha seleccionada a una cadena legible
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;

            // Mostrar la fecha seleccionada en un Toast (puedes personalizar esto)
            Toast.makeText(Calendario.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
        });
    }
}
