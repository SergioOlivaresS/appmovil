package com.example.gasteando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GastoRealizado extends AppCompatActivity {

    private EditText etFecha;

    private Calendar selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto_realizado);

        // Inicializa vistas
        etFecha = findViewById(R.id.etFecha);
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategoria);
        findViewById(R.id.txtTotalGastos);
        Button btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        Button btnEditarProducto = findViewById(R.id.btnEditarProducto);

        // botón para mostrar el selector de fecha
        etFecha.setOnClickListener(v -> showDatePickerDialog());

        //botón para agregar producto
        btnAgregarProducto.setOnClickListener(v -> {
            // Lógica para agregar un producto
            Intent ingreso = new Intent(this, ingresarproductos.class);
            startActivity(ingreso);
        });

        // botón para editar producto
        btnEditarProducto.setOnClickListener(v -> {
            // Lógica para editar un producto
        });

        // Inicializar la fecha seleccionada
        selectedDate = Calendar.getInstance();
        updateDateEditText();

        //Spinner con las opciones de categoría
        String[] categorias = {"Total","Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    // Método para mostrar el selector de fecha
    private void showDatePickerDialog() {
        DialogFragment newFragment = DatePickerFragment.newInstance(selectedDate, date -> {
            selectedDate = date;
            updateDateEditText();
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // Método para actualizar fecha con la fecha seleccionada
    private void updateDateEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etFecha.setText(dateFormat.format(selectedDate.getTime()));
    }

    public void volveraingreso(View v){
        Intent gastoaingreso = new Intent(this, ingresarproductos.class);
        startActivity(gastoaingreso);
    }
}
