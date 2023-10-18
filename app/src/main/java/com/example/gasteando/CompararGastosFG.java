package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CompararGastosFG extends Fragment {

  //  private Spinner spinnerCategorias;
    private EditText etFechaInicio;
    private EditText etFechaFin;
    private TextView tvResultados;
    private FirebaseFirestore db;

    private Calendar fechaInicio, fechaFin;

    public CompararGastosFG() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comparar_gastos_f_g, container, false);

        // Inicializar vistas
        etFechaInicio = rootView.findViewById(R.id.etFechaInicio);
        etFechaFin = rootView.findViewById(R.id.etFechaFin);
        Button btnMostrarDatos = rootView.findViewById(R.id.btnMostrarDatos);
        tvResultados = rootView.findViewById(R.id.tvResultados);
        db = FirebaseFirestore.getInstance();

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        etFechaInicio.setText(dateFormat.format(fechaInicio.getTime()));
        etFechaFin.setText(dateFormat.format(fechaFin.getTime()));
    }

    // Método para mostrar los datos según las selecciones
    @SuppressLint("SetTextI18n")
    private void mostrarDatos() {
        // Obtener la categoría seleccionada


        // Consultar el "Total" de todos los gastos para el día seleccionado
        Query totalQuery = db.collection("productos")
                .whereGreaterThanOrEqualTo("fecha", fechaInicio.getTime())
                .whereLessThanOrEqualTo("fecha", fechaFin.getTime());

        totalQuery.get().addOnCompleteListener(totalTask -> {
            if (totalTask.isSuccessful()) {
                double totalGastos = 0.0;
                for (QueryDocumentSnapshot document : totalTask.getResult()) {
                    double monto = document.getDouble("monto");
                    totalGastos += monto;
                }
                // Agregar el "Total" al resultado
                String resultadoActual = tvResultados.getText().toString();
                String resultadoTotal = resultadoActual + "\nGastos Total entre las fechas seleccionadas: " + totalGastos;
                tvResultados.setText(resultadoTotal);
            }
        });
    }
}
