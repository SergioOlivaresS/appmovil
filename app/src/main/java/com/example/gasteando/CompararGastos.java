package com.example.gasteando;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CompararGastos extends AppCompatActivity {

    private Spinner spinnerCategorias;
    private EditText etFechaInicio, etFechaFin;
    private TextView tvResultados;

    private Calendar fechaInicio, fechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparar_gastos);

        // Inicializar vistas
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaFin = findViewById(R.id.etFechaFin);
        Button btnMostrarDatos = findViewById(R.id.btnMostrarDatos);
        tvResultados = findViewById(R.id.tvResultados);

        // Configurar el Spinner con opciones de categoría
        String[] categorias = {"Total", "Alimentación", "Transporte", "Entretenimiento", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        // Configurar el campo de fecha de inicio para mostrar el DatePickerDialog
        etFechaInicio.setOnClickListener(v -> mostrarDatePickerInicio());

        // Configurar el campo de fecha de fin para mostrar el DatePickerDialog
        etFechaFin.setOnClickListener(v -> mostrarDatePickerFin());

        // Configurar el botón para mostrar los datos según las selecciones
        btnMostrarDatos.setOnClickListener(v -> mostrarDatos());

        // Inicializar las fechas de inicio y fin con la fecha actual
        fechaInicio = Calendar.getInstance();
        fechaFin = Calendar.getInstance();
        updateFechaEditTexts();
    }

    // Método para mostrar el DatePickerDialog de la fecha de inicio
    private void mostrarDatePickerInicio() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, year, month, dayOfMonth) -> {
            fechaInicio.set(year, month, dayOfMonth);
            updateFechaEditTexts();
        },
                fechaInicio.get(Calendar.YEAR),
                fechaInicio.get(Calendar.MONTH),
                fechaInicio.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // Método para mostrar el DatePickerDialog de la fecha de fin
    private void mostrarDatePickerFin() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, year, month, dayOfMonth) -> {
            fechaFin.set(year, month, dayOfMonth);
            updateFechaEditTexts();
        },
                fechaFin.get(Calendar.YEAR),
                fechaFin.get(Calendar.MONTH),
                fechaFin.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    // Método para actualizar los campos de fecha con las fechas seleccionadas
    private void updateFechaEditTexts() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etFechaInicio.setText(dateFormat.format(fechaInicio.getTime()));
        etFechaFin.setText(dateFormat.format(fechaFin.getTime()));
    }

    // Método para mostrar los datos según las selecciones
    private void mostrarDatos() {
        // Obtener la categoría seleccionada
        String categoriaSeleccionada = spinnerCategorias.getSelectedItem().toString();

        // Obtener las fechas de inicio y fin
        String fechaInicioSeleccionada = etFechaInicio.getText().toString();
        String fechaFinSeleccionada = etFechaFin.getText().toString();

        // Realizar alguna acción con las selecciones
        String resultado = "Categoría: " + categoriaSeleccionada + "\n"
                + "Dia " + fechaInicioSeleccionada + "\n"
                + "Dia " + fechaFinSeleccionada;

        tvResultados.setText(resultado);
    }
}
