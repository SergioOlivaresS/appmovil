package com.example.gasteando;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

    private EditText etFechaInicio;
    private EditText etFechaFin;
    private TextView tvResultados;
    private FirebaseFirestore db;

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
        db = FirebaseFirestore.getInstance();

        etFechaInicio.setOnClickListener(v -> mostrarDatePickerInicio());


        etFechaFin.setOnClickListener(v -> mostrarDatePickerFin());

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userID", null);


        if (userId == null) {

            tvResultados.setText("Error: El usuario no ha iniciado sesión.");
            btnMostrarDatos.setEnabled(false);
            etFechaInicio.setEnabled(false);
            etFechaFin.setEnabled(false);
        }
        btnMostrarDatos.setOnClickListener(v -> mostrarDatos());

        fechaInicio = Calendar.getInstance();
        fechaFin = Calendar.getInstance();
        updateFechaEditTexts();

        return rootView;
    }

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


        Query totalQuery = db.collection("productos")
                .whereGreaterThanOrEqualTo("fecha", fechaInicioStr)
                .whereLessThanOrEqualTo("fecha", fechaFinStr)
                .whereEqualTo("userId", userId);


        totalQuery.get().addOnCompleteListener(totalTask -> {
            if (totalTask.isSuccessful()) {
                double totalGastos = 0.0;
                for (QueryDocumentSnapshot document : totalTask.getResult()) {

                    double monto = document.getDouble("monto");
                    totalGastos += monto;
                }

                String resultadoActual = tvResultados.getText().toString();
                String resultadoTotal = resultadoActual + "\nGastos Total entre las fechas seleccionadas: " + totalGastos;
                tvResultados.setText(resultadoTotal);
            } else {

                String errorMessage = "Error al obtener datos: " + totalTask.getException().getMessage();
                tvResultados.setText(errorMessage);
            }
        });
    }
}