package com.example.gasteando;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GastorealizadoFG extends Fragment {

    private EditText etFecha;
    private Calendar selectedDate;
    private GastosDatabaseHelper dbHelper;
    private TextView txtTotalGastos;
    private FirebaseFirestore db; // Agrega esta línea para usar Firestore
    private String fechaSeleccionada;

    public GastorealizadoFG() {
        // Required empty public constructor
    }

    // Método para configurar la fecha seleccionada
    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gastorealizado_f_g, container, false);

        etFecha = view.findViewById(R.id.etFecha);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        txtTotalGastos = view.findViewById(R.id.txtTotalGastos);
        Button btnAgregarProducto = view.findViewById(R.id.btnAgregarProducto);
        Button btnBuscarDatos = view.findViewById(R.id.btnBuscarDatos);
        dbHelper = new GastosDatabaseHelper(requireContext());
        db = FirebaseFirestore.getInstance(); // Inicializa Firestore

        etFecha.setOnClickListener(v -> showDatePickerDialog());

        btnAgregarProducto.setOnClickListener(v -> {
            String fecha = etFecha.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            double monto = 0;

            // Inserta el gasto en la base de datos

            IngresarproductosFG ig = new IngresarproductosFG();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor, ig)
                    .addToBackStack(null)
                    .commit();
        });

        btnBuscarDatos.setOnClickListener(v -> {
            // Usar la fecha seleccionada
            String fechaSeleccionada = etFecha.getText().toString();

            // Consulta los datos en Firestore para el día seleccionado
            db.collection("productos")
                    .whereEqualTo("fecha", fechaSeleccionada)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                double totalGastos = 0.0;
                                StringBuilder detalles = new StringBuilder();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    double monto = document.getDouble("monto");
                                    totalGastos += monto;

                                    // Obtener el detalle (ajusta el campo según tu estructura de Firestore)
                                    String detalle = document.getString("detalle");
                                    detalles.append("Monto: ").append(monto).append(", Detalle: ").append(detalle).append("\n");
                                }

                                String resultadoTotal = "El gasto Total fue de: " + totalGastos;
                                String detallesText = "Detalles:\n" + detalles.toString();
                                txtTotalGastos.setText(resultadoTotal + "\n" + detallesText);
                            } else {
                                // Maneja errores
                                Log.e("Firestore", "Error al obtener datos: " + task.getException());
                            }
                        }
                    });
        });

        // Cargar la fecha seleccionada si se pasó desde el Calendario
        if (fechaSeleccionada != null && !fechaSeleccionada.isEmpty()) {
            etFecha.setText(fechaSeleccionada);
        }

        selectedDate = Calendar.getInstance();
        updateDateEditText();

        String[] categorias = {"Total", "Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        return view;
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = DatePickerFragment.newInstance(selectedDate, date -> {
            selectedDate = date;
            updateDateEditText();
        });
        newFragment.show(getChildFragmentManager(), "datePicker");
    }

    private void updateDateEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        etFecha.setText(dateFormat.format(selectedDate.getTime()));
    }

    public GastosDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(GastosDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
