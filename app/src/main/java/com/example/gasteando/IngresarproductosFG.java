package com.example.gasteando;

import android.annotation.SuppressLint;
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

    public IngresarproductosFG() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();
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
            String montoStr = etMonto.getText().toString(); // Obtener el valor como cadena

            // Validar que el monto sea un número válido
            double monto; // Valor predeterminado en caso de que no se pueda analizar el número
            try {
                monto = Double.parseDouble(montoStr); // Intenta analizar el número
            } catch (NumberFormatException e) {
                // Mostrar un mensaje de error si el valor no es válido
                Toast.makeText(requireContext(), "El monto ingresado no es válido", Toast.LENGTH_LONG).show();
                return; // Salir de la función si el monto no es válido
            }

            String categoria = spinnerCategoria.getSelectedItem().toString();

            // Crear un nuevo objeto para representar un registro de producto
            Map<String, Object> producto = new HashMap<>();
            producto.put("fecha", fecha);
            producto.put("detalle", detalle);
            producto.put("monto", monto); // Aquí guardamos el monto como un número
            producto.put("categoria", categoria);

            // Agregar el registro a Firestore
            db.collection("productos")
                    .add(producto)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(requireContext(), "Producto ingresado exitosamente", Toast.LENGTH_LONG).show();
                        // Limpiar los campos después de guardar con éxito
                        etFecha.setText("");
                        etDetalle.setText("");
                        etMonto.setText("");
                        // Puedes reiniciar el spinner a una opción predeterminada si es necesario
                    })
                    .addOnFailureListener(e -> Toast.makeText(requireContext(), "Error al ingresar el producto", Toast.LENGTH_LONG).show());
        });

        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener(v -> {
            // Cerrar el fragmento
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
            // Actualizar el campo de fecha con la fecha seleccionada
            etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
        }, anio, mes, dia);
        datePickerDialog.show();
    }
}