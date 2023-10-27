package com.example.gasteando;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IngresarproductosFG extends Fragment {

    private EditText etFecha, etDetalle, etMonto;
    private Spinner spinnerCategoria;
    private FirebaseFirestore db;

    private String userId;

    public IngresarproductosFG() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userID", null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresarproductos_f_g, container, false);

        etFecha = view.findViewById(R.id.etFecha);
        etDetalle = view.findViewById(R.id.etDetalle);
        etMonto = view.findViewById(R.id.etMonto);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        Button btnIngresar = view.findViewById(R.id.btnIngresar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        String[] categorias = {"Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        etFecha.setOnClickListener(v -> mostrarDatePicker());


        btnIngresar.setOnClickListener(v -> {

            String fechaStr = etFecha.getText().toString();
            String detalle = etDetalle.getText().toString();
            String montoStr = etMonto.getText().toString();
            double monto;
            try {
                monto = Double.parseDouble(montoStr);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "El monto ingresado no es válido", Toast.LENGTH_LONG).show();
                return;
            }
            String categoria = spinnerCategoria.getSelectedItem().toString();


            Map<String, Object> producto = new HashMap<>();
            producto.put("fecha", fechaStr);
            producto.put("detalle", detalle);
            producto.put("monto", monto);
            producto.put("categoria", categoria);


            producto.put("userId", userId);


            db.collection("productos")
                    .add(producto)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(requireContext(), "Producto ingresado exitosamente", Toast.LENGTH_LONG).show();
                        etFecha.setText("");
                        etDetalle.setText("");
                        etMonto.setText("");
                    })
                    .addOnFailureListener(e -> Toast.makeText(requireContext(), "Error al ingresar el producto", Toast.LENGTH_LONG).show());
        });


        btnCancelar.setOnClickListener(v -> {

            MenuFG mp = new MenuFG();
            getParentFragmentManager().beginTransaction().replace(R.id.contenedor, mp).commit();
        });

        return view;
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("DefaultLocale") android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {

            etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
        }, anio, mes, dia);
        datePickerDialog.show();
    }
}
