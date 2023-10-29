package com.example.gasteando;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ResumenFG extends Fragment {

    private PieChart pieChart;
    private FirebaseFirestore db;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen_f_g, container, false);

        pieChart = view.findViewById(R.id.pieChart);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userID", null);

        if (userId != null) {
            db = FirebaseFirestore.getInstance();

            fetchAndSetupPieChart();
        } else {

        }

        return view;
    }

    private void fetchAndSetupPieChart() {
        // Realiza una consulta a Firestore para obtener los datos de gasto
        db.collection("productos")
                .whereEqualTo("userId", userId) // Filtra por el ID de usuario
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Procesa los datos para calcular los porcentajes
                    ArrayList<PieEntry> entries = processGastoData(queryDocumentSnapshots);

                    // Configura el gráfico con los porcentajes calculados
                    setupPieChart(entries);
                });
    }

    private ArrayList<PieEntry> processGastoData(QuerySnapshot queryDocumentSnapshots) {
        // Calcula los totales por categoría
        double totalAlimentacion = 0.0;
        double totalTransporte = 0.0;
        double totalEntretenimiento = 0.0;
        double totalOtros = 0.0;

        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
            double monto = document.getDouble("monto");
            String categoria = document.getString("categoria");

            if (categoria != null) {
                switch (categoria) {
                    case "Alimentación":
                        totalAlimentacion += monto;
                        break;
                    case "Transporte":
                        totalTransporte += monto;
                        break;
                    case "Entretenimiento":
                        totalEntretenimiento += monto;
                        break;
                    case "Otro":
                        totalOtros += monto;
                        break;
                }
            }
        }

        // Calcula los porcentajes
        double totalGastos = totalAlimentacion + totalTransporte + totalEntretenimiento + totalOtros;

        ArrayList<PieEntry> entries = new ArrayList<>();
        if (totalGastos > 0) {
            entries.add(new PieEntry((float) (totalAlimentacion / totalGastos * 100), "Alimentación"));
            entries.add(new PieEntry((float) (totalTransporte / totalGastos * 100), "Transporte"));
            entries.add(new PieEntry((float) (totalEntretenimiento / totalGastos * 100), "Entretenimiento"));
            entries.add(new PieEntry((float) (totalOtros / totalGastos * 100), "Otro"));
        }

        return entries;
    }

    private void setupPieChart(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Porcentaje de sus gastos totales");
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}
