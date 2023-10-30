package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CompararGastosFG extends Fragment {

    private EditText etFechaInicio;
    private EditText etFechaFin;
    private TextView tvResultados;
    private FirebaseFirestore db;

    private Spinner spinnerCategoria;
    private Calendar fechaInicio, fechaFin;
    private String userId;

    public CompararGastosFG() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comparar_gastos_f_g, container, false);

        etFechaInicio = rootView.findViewById(R.id.etFechaInicio);
        etFechaFin = rootView.findViewById(R.id.etFechaFin);
        Button btnMostrarDatos = rootView.findViewById(R.id.btnMostrarDatos);
        tvResultados = rootView.findViewById(R.id.tvResultados);
        spinnerCategoria = rootView.findViewById(R.id.spinnerCategoria); // Agregado
        db = FirebaseFirestore.getInstance();

        // Configurar el Spinner con categorías
        String[] categorías = {"Total", "Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorías);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        etFechaInicio.setOnClickListener(v -> mostrarDatePickerInicio());
        etFechaFin.setOnClickListener(v -> mostrarDatePickerFin());

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userID", null);

        if (userId == null) {
            tvResultados.setText("Error: El usuario no ha iniciado sesión.");
            btnMostrarDatos.setEnabled(false);
            etFechaInicio.setEnabled(false);
            etFechaFin.setEnabled(false);
            spinnerCategoria.setEnabled(false);
        }

        btnMostrarDatos.setOnClickListener(v -> mostrarDatos());

        fechaInicio = Calendar.getInstance();
        fechaFin = Calendar.getInstance();
        updateFechaEditTexts();

        return rootView;
    }

    private void mostrarDatePickerInicio() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (view, year1, month1, dayOfMonth1) -> {
            fechaInicio.set(year1, month1, dayOfMonth1);
            updateFechaEditTexts();
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void mostrarDatePickerFin() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (view, year1, month1, dayOfMonth1) -> {
            fechaFin.set(year1, month1, dayOfMonth1);
            updateFechaEditTexts();
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void updateFechaEditTexts() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        etFechaInicio.setText(dateFormat.format(fechaInicio.getTime()));
        etFechaFin.setText(dateFormat.format(fechaFin.getTime()));
    }

    @SuppressLint("SetTextI18n")
    private void mostrarDatos() {
        tvResultados.setText("");

        String fechaInicioStr = etFechaInicio.getText().toString();
        String fechaFinStr = etFechaFin.getText().toString();
        String resultadoFechas = "Fechas seleccionadas: " + fechaInicioStr + " - " + fechaFinStr;
        tvResultados.setText(resultadoFechas);

        String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString(); // Obtener la categoría seleccionada

        Query totalQuery = db.collection("productos")
                .whereGreaterThanOrEqualTo("fecha", fechaInicioStr)
                .whereLessThanOrEqualTo("fecha", fechaFinStr)
                .whereEqualTo("userId", userId);

        if (!categoriaSeleccionada.equals("Total")) {
            totalQuery = totalQuery.whereEqualTo("categoria", categoriaSeleccionada);
        }

        totalQuery.get().addOnCompleteListener(totalTask -> {
            if (totalTask.isSuccessful()) {
                double totalGastos = 0.0;
                StringBuilder detalles = new StringBuilder();

                for (QueryDocumentSnapshot document : totalTask.getResult()) {
                    double monto = document.getDouble("monto");
                    totalGastos += monto;

                    String detalle = document.getString("detalle");
                    detalles.append("Monto: ").append(monto).append(", Detalle: ").append(detalle).append("\n");
                }

                String resultadoTotal = "Gastos Total entre las fechas seleccionadas: " + totalGastos;
                String detallesText = "Detalles:\n" + detalles.toString();
                tvResultados.setText(resultadoTotal + "\n" + detallesText);
            } else {
                String errorMessage = "Error al obtener datos: " + totalTask.getException().getMessage();
                tvResultados.setText(errorMessage);
            }
        });
    }
}
