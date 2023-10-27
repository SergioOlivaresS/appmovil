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


        Producto producto = obtenerDatosDelProducto();

        editTextCategoria.setText(producto.getCategoria());
        editTextDetalle.setText(producto.getDetalle());
        editTextFecha.setText(producto.getFecha());
        editTextMonto.setText(String.valueOf(producto.getMonto()));

        return view;
    }

    private void onSaveClicked() {
        String categoria = editTextCategoria.getText().toString();
        String detalle = editTextDetalle.getText().toString();
        String fecha = editTextFecha.getText().toString();
        double monto = Double.parseDouble(editTextMonto.getText().toString());

        if (getActivity() instanceof MenuPrincipal) {
            GastosDatabaseHelper dbHelper = ((MenuPrincipal) getActivity()).getDbHelper();
            dbHelper.updateData(categoria, detalle, fecha, monto);


            getParentFragmentManager().popBackStack();
        }
    }

    private Producto obtenerDatosDelProducto() {
        Producto producto = new Producto();
        producto.setCategoria("");
        producto.setDetalle("");
        producto.setFecha("");
        producto.setMonto(0);
        return producto;
    }
}
