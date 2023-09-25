package com.example.gasteando;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gasteando.R;

import java.util.Calendar;

public class ingresarproductos extends AppCompatActivity {

    private EditText etFecha, etDetalle, etMonto;
    private Spinner spinnerCategoria;
    private Button btnIngresar, btnCancelar;
    private int año, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresarproductos);

        // Inicializar vistas
        etFecha = findViewById(R.id.etFecha);
        etDetalle = findViewById(R.id.etDetalle);
        etMonto = findViewById(R.id.etMonto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Configurar el Spinner con opciones de categoría (personaliza las opciones según tus necesidades)
        String[] categorias = {"Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        // Configurar el campo de fecha para mostrar el DatePickerDialog
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });

        // Configurar el botón de ingreso
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String fecha = etFecha.getText().toString();
                String detalle = etDetalle.getText().toString();
                String monto = etMonto.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();

                // Realizar alguna acción con los datos ingresados (por ejemplo, guardarlos en una base de datos)
                // En este ejemplo, simplemente mostramos un Toast con los datos
                String mensaje = "Fecha: " + fecha + "\nDetalle: " + detalle + "\nMonto: " + monto + "\nCategoría: " + categoria;
                Toast.makeText(ingresarproductos.this, mensaje, Toast.LENGTH_LONG).show();
            }
        });

        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la actividad actual
                finish();
            }
        });
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        año = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Actualizar el campo de fecha con la fecha seleccionada
                etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
            }
        }, año, mes, dia);
        datePickerDialog.show();
    }
}
