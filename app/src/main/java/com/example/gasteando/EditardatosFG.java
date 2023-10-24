package com.example.gasteando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditardatosFG extends Fragment {

    private EditText editTextData;

    public EditardatosFG() {
        // Constructor vacío requerido.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editardatos_f_g, container, false);

        editTextData = view.findViewById(R.id.editTextData);

        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> onSaveClicked());

        // Aquí puedes cargar los datos existentes y mostrarlos en editTextData

        return view;
    }

    private void onSaveClicked() {
        // Obtén el dato editado
        String editedData = editTextData.getText().toString();

        // Actualiza los datos en la base de datos (esto puede variar según tu implementación)
        // Aquí asumiremos que tienes un método "updateData" en tu dbHelper
        if (getActivity() instanceof MenuPrincipal) {
            GastosDatabaseHelper dbHelper = ((MenuPrincipal) getActivity()).getDbHelper();
            dbHelper.updateData(editedData);

            // Regresa a la pantalla anterior (GastorealizadoFG)
            getParentFragmentManager().popBackStack();
        }
    }
}
