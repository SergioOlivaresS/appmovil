package com.example.gasteando;

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

import java.util.ArrayList;

public class ResumenFG extends Fragment {

    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen_f_g, container, false);

        // Inicializa el PieChart desde el diseño XML
        pieChart = view.findViewById(R.id.pieChart);

        // Configura los datos del gráfico
        setupPieChart();

        return view;
    }

    private void setupPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(60f, "Alimentación"));
        entries.add(new PieEntry(25f, "Transporte"));
        entries.add(new PieEntry(10f, "Entretención"));
        entries.add(new PieEntry(5f, "Otros"));


        PieDataSet dataSet = new PieDataSet(entries, "Gastos por Categoría");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Colores para las secciones del gráfico

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false); // Deshabilita la descripción
        pieChart.setCenterText("Gastos Por Categoria"); // Texto en el centro del gráfico
        pieChart.animateY(1000); // Animación de entrada
    }
}
