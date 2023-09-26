package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class ingresarproductos extends AppCompatActivity {

    private EditText etFecha, etDetalle, etMonto;
    private Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresarproductos);

        // Inicializar vistas
        etFecha = findViewById(R.id.etFecha);
        etDetalle = findViewById(R.id.etDetalle);
        etMonto = findViewById(R.id.etMonto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        Button btnIngresar = findViewById(R.id.btnIngresar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        //Spinner con opciones de categoría
        String[] categorias = {"Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        // Configurar el campo de fecha para mostrar el DatePickerDialog
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el botón de ingreso
        btnIngresar.setOnClickListener(v -> {

            Intent registrar = new Intent(this, ingresarproductos.class);
            startActivity(registrar);
            // Obtener los valores ingresados por el usuario
            String fecha = etFecha.getText().toString();
            String detalle = etDetalle.getText().toString();
            String monto = etMonto.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();

            // Realizar alguna acción con los datos ingresados
            // En este ejemplo, simplemente mostramos un Toast con los datos
            String mensaje = "Fecha: " + fecha + "\nDetalle: " + detalle + "\nMonto: " + monto + "\nCategoría: " + categoria;
            Toast.makeText(ingresarproductos.this, mensaje, Toast.LENGTH_LONG).show();
        });

        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener(v -> {
            // Cerrar la actividad actual
            finish();
        });
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            // Actualizar el campo de fecha con la fecha seleccionada
            etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    public void ingresoagasto(View v){
        Intent ingresogasto = new Intent(this, GastoRealizado.class);
        startActivity(ingresogasto);
    }
}