package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditardatosFG extends Fragment {

    private EditText editTextCategoria;
    private EditText editTextDetalle;
    private EditText editTextFecha;
    private EditText editTextMonto;

    public EditardatosFG() {
        // Constructor vacío requerido.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editardatos_f_g, container, false);

        editTextCategoria = view.findViewById(R.id.editTextCategoria);
        editTextDetalle = view.findViewById(R.id.editTextDetalle);
        editTextFecha = view.findViewById(R.id.editTextFecha);
        editTextMonto = view.findViewById(R.id.editTextMonto);

        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> onSaveClicked());

        // Aquí debes cargar los datos existentes y mostrarlos en los campos de entrada

        // Por ejemplo, si tienes un objeto Producto con estos datos, puedes cargarlos así:
        Producto producto = obtenerDatosDelProducto(); // Implementa este método

        editTextCategoria.setText(producto.getCategoria());
        editTextDetalle.setText(producto.getDetalle());
        editTextFecha.setText(producto.getFecha());
        editTextMonto.setText(String.valueOf(producto.getMonto()));

        return view;
    }

    private void onSaveClicked() {
        // Obtén los datos editados
        String categoria = editTextCategoria.getText().toString();
        String detalle = editTextDetalle.getText().toString();
        String fecha = editTextFecha.getText().toString();
        double monto = Double.parseDouble(editTextMonto.getText().toString());

        // Actualiza los datos en la base de datos (esto puede variar según tu implementación)
        // Aquí asumiremos que tienes un método "updateData" en tu dbHelper
        if (getActivity() instanceof MenuPrincipal) {
            GastosDatabaseHelper dbHelper = ((MenuPrincipal) getActivity()).getDbHelper();
            dbHelper.updateData(categoria, detalle, fecha, monto);

            // Regresa a la pantalla anterior (GastorealizadoFG)
            getParentFragmentManager().popBackStack();
        }
    }

    // Método de ejemplo para obtener datos de un producto (debes implementarlo según tu lógica)
    private Producto obtenerDatosDelProducto() {
        Producto producto = new Producto();
        producto.setCategoria("Alimentación");
        producto.setDetalle("Compras de supermercado");
        producto.setFecha("2023-10-20");
        producto.setMonto(50.0);
        return producto;
    }
}
