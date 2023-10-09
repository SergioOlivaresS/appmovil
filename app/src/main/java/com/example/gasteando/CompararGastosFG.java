package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CompararGastosFG extends Fragment {

    private Spinner spinnerCategorias;
    private EditText etFechaInicio;
    private EditText etFechaFin;
    private TextView tvResultados;

    private Calendar fechaInicio, fechaFin;

    public CompararGastosFG() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comparar_gastos_f_g, container, false);

        // Inicializar vistas
        spinnerCategorias = rootView.findViewById(R.id.spinnerCategorias);
        etFechaInicio = rootView.findViewById(R.id.etFechaInicio);
        etFechaFin = rootView.findViewById(R.id.etFechaFin);
        Button btnMostrarDatos = rootView.findViewById(R.id.btnMostrarDatos);
        tvResultados = rootView.findViewById(R.id.tvResultados);

        // Configurar el Spinner con opciones de categoría
        String[] categorias = {"Total", "Alimentación", "Transporte", "Entretenimiento", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
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

        return rootView;
    }

    // Método para mostrar el DatePickerDialog de la fecha de inicio
    private void mostrarDatePickerInicio() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (view, year, month, dayOfMonth) -> {
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
                requireContext(), (view, year, month, dayOfMonth) -> {
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
        String resultado = "Categoría: " + categoriaSeleccionada +  "\n"
                + "Día " + fechaInicioSeleccionada + "\n"
                + "Día  " + fechaFinSeleccionada;

        tvResultados.setText(resultado);
    }
}
