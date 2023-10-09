package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GastorealizadoFG extends Fragment {

    private EditText etFecha;
    private Calendar selectedDate;

    public GastorealizadoFG() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_gastorealizado_f_g, container, false);

        // Inicializa vistas
        etFecha = view.findViewById(R.id.etFecha);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        view.findViewById(R.id.txtTotalGastos);
        Button btnAgregarProducto = view.findViewById(R.id.btnAgregarProducto);
        Button btnEditarProducto = view.findViewById(R.id.btnEditarProducto);

        // Botón para mostrar el selector de fecha
        etFecha.setOnClickListener(v -> showDatePickerDialog());

        // Botón para agregar producto
        btnAgregarProducto.setOnClickListener(v -> {
            // Obtén el administrador de fragmentos de la actividad principal
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Crea una instancia del fragmento IngresarproductosFG
            IngresarproductosFG ig = new IngresarproductosFG();

            // Realiza la transacción de fragmentos
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor, ig)
                    .addToBackStack(null) // Opcional: agrega la transacción a la pila de retroceso
                    .commit();
        });

        // Botón para editar producto
        btnEditarProducto.setOnClickListener(v -> {
            // Lógica para editar un producto
        });

        // Inicializar la fecha seleccionada
        selectedDate = Calendar.getInstance();
        updateDateEditText();

        // Spinner con las opciones de categoría
        String[] categorias = {"Total","Alimentación", "Entretenimiento", "Transporte", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        return view;
    }

    // Método para mostrar el selector de fecha
    private void showDatePickerDialog() {
        DialogFragment newFragment = DatePickerFragment.newInstance(selectedDate, date -> {
            selectedDate = date;
            updateDateEditText();
        });
        newFragment.show(getChildFragmentManager(), "datePicker");
    }

    // Método para actualizar el campo de fecha con la fecha seleccionada
    private void updateDateEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etFecha.setText(dateFormat.format(selectedDate.getTime()));
    }
}
