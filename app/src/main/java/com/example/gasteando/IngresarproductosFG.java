package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class IngresarproductosFG extends Fragment {

    private EditText etFecha, etDetalle, etMonto;
    private Spinner spinnerCategoria;

    public IngresarproductosFG() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_ingresarproductos_f_g, container, false);

        // Inicializar vistas
        etFecha = view.findViewById(R.id.etFecha);
        etDetalle = view.findViewById(R.id.etDetalle);
        etMonto = view.findViewById(R.id.etMonto);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        Button btnIngresar = view.findViewById(R.id.btnIngresar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        // Spinner con opciones de categoría
        String[] categorias = {"Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        // Configurar el campo de fecha para mostrar el DatePickerDialog
        etFecha.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el botón de ingreso
        btnIngresar.setOnClickListener(v -> {
            // Obtener los valores ingresados por el usuario
            String fecha = etFecha.getText().toString();
            String detalle = etDetalle.getText().toString();
            String monto = etMonto.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();

            // Realizar alguna acción con los datos ingresados
            // En este ejemplo, simplemente mostramos un Toast con los datos
            String mensaje = "Fecha: " + fecha + "\nDetalle: " + detalle + "\nMonto: " + monto + "\nCategoría: " + categoria;
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
        });

        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener(v -> {
            // Cerrar el fragmento
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            // Actualizar el campo de fecha con la fecha seleccionada
            etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
        }, anio, mes, dia);
        datePickerDialog.show();
    }
}
