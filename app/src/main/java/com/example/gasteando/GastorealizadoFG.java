package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GastorealizadoFG extends Fragment {

    private EditText etFecha;
    private Calendar selectedDate;
    private GastosDatabaseHelper dbHelper;

    public GastorealizadoFG() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_gastorealizado_f_g, container, false);

        // Inicializar vistas
        etFecha = view.findViewById(R.id.etFecha);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        TextView txtTotalGastos = view.findViewById(R.id.txtTotalGastos);
        Button btnAgregarProducto = view.findViewById(R.id.btnAgregarProducto);
        Button btnBuscarDatos = view.findViewById(R.id.btnBuscarDatos);
        dbHelper = new GastosDatabaseHelper(requireContext());

        // Botón para mostrar el selector de fecha
        etFecha.setOnClickListener(v -> showDatePickerDialog());

        // Botón para agregar gasto
        btnAgregarProducto.setOnClickListener(v -> {
            String fecha = etFecha.getText().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();
            double monto = 0; // Obtener el monto desde algún lugar

            // Insertar el gasto en la base de datos


            // Luego de insertar el gasto, realizar la transacción al fragmento IngresarproductosFG
            IngresarproductosFG ig = new IngresarproductosFG();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor, ig)
                    .addToBackStack(null)
                    .commit();
        });


        // Botón para buscar datos en la base de datos
        btnBuscarDatos.setOnClickListener(v -> {
            // Obtenemos la fecha seleccionada y la categoría del Spinner
            String fechaSeleccionada = etFecha.getText().toString();
            String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();

            // Consultar los datos de la base de datos


            // Actualizar el TextView con los datos obtenidos

        });


        Bundle args = getArguments();
        if (args != null) {
            String fechaSeleccionada = args.getString("fechaSeleccionada");
            if (fechaSeleccionada != null) {
                etFecha.setText(fechaSeleccionada);
            }
        }
        // Inicializar la fecha seleccionada
        selectedDate = Calendar.getInstance();
        updateDateEditText();

        // Spinner con las opciones de categoría
        String[] categorias = {"Total", "Alimentación", "Entretenimiento", "Transporte", "Otro"};
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
