package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

public class EliminarProductoFG extends Fragment {

    private Spinner spinnerProductos;
    private Button btnEliminar;
    private List<Producto> listaProductos;
    private ArrayAdapter<Producto> productosAdapter;
    private GastosDatabaseHelper dbHelper;

    public EliminarProductoFG() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eliminar_producto_f_g, container, false);

        spinnerProductos = view.findViewById(R.id.spinnerProductos);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        dbHelper = new GastosDatabaseHelper(requireContext());

        // Obtén la lista de productos desde la base de datos y configura el Spinner
        listaProductos = dbHelper.obtenerProductos();

        if (listaProductos.isEmpty()) {
            // No hay productos en la base de datos, muestra un mensaje o realiza una acción apropiada.
            Toast.makeText(requireContext(), "No hay productos para mostrar", Toast.LENGTH_SHORT).show();
        } else {
            // Configura el adaptador y el Spinner.
            productosAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaProductos);
            productosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerProductos.setAdapter(productosAdapter);
        }

        btnEliminar.setOnClickListener(v -> onDeleteClicked());

        return view;
    }

    private void onDeleteClicked() {
        if (listaProductos.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtiene el producto seleccionado del Spinner
        Producto productoSeleccionado = (Producto) spinnerProductos.getSelectedItem();

        // Utiliza el DBHelper para eliminar el producto seleccionado
        dbHelper.eliminarProducto(productoSeleccionado);

        // Actualiza la lista de productos después de eliminar el producto
        listaProductos.remove(productoSeleccionado);
        productosAdapter.notifyDataSetChanged();
    }
}
