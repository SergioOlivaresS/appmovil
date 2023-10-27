package com.example.gasteando;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.firestore.Query;
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
    private FirebaseFirestore db;
    private String fechaSeleccionada;

    // Listener para editar datos
    private EditDataListener editDataListener;

    public GastorealizadoFG() {
        // Required empty public constructor
    }

    public interface EditDataListener {
        void onEditDataRequested(String dataToEdit);
    }

    public void setEditDataListener(EditDataListener listener) {
        this.editDataListener = listener;
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
        Button btnEditarDatos = view.findViewById(R.id.btnEditarDatos);
        dbHelper = new GastosDatabaseHelper(requireContext());
        db = FirebaseFirestore.getInstance();

        etFecha.setOnClickListener(v -> showDatePickerDialog());

        Bundle args = getArguments();
        if (args != null && args.containsKey("fechaSeleccionada")) {
            fechaSeleccionada = args.getString("fechaSeleccionada");
        }

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
            String fechaSeleccionada = etFecha.getText().toString();
            String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();

            // Obten el ID del usuario almacenado en SharedPreferences
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userID", null);

            if (userId == null) {

                return;
            }

            // Crea una consulta inicial con el filtro de fecha
            Query query = db.collection("productos")
                    .whereEqualTo("fecha", fechaSeleccionada)
                    .whereEqualTo("userId", userId);

            // Verifica la categoría y ajusta la consulta si no es "Total"
            if (!categoriaSeleccionada.equals("Total")) {
                query = query.whereEqualTo("categoria", categoriaSeleccionada);
            }

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        double totalGastos = 0.0;
                        StringBuilder detalles = new StringBuilder();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            double monto = document.getDouble("monto");
                            totalGastos += monto;

                            String detalle = document.getString("detalle");
                            detalles.append("Monto: ").append(monto).append(", Detalle: ").append(detalle).append("\n");
                        }

                        String resultadoTotal = "El gasto Total fue de: " + totalGastos;
                        String detallesText = "Detalles:\n" + detalles.toString();
                        txtTotalGastos.setText(resultadoTotal + "\n" + detallesText);
                    } else {
                        Log.e("Firestore", "Error al obtener datos: " + task.getException());
                    }
                }
            });
        });


        btnEditarDatos.setOnClickListener(v -> {
            EditardatosFG ed = new EditardatosFG();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor, ed)
                    .addToBackStack(null)
                    .commit();

            String dataToEdit = "Datos a editar"; // Reemplaza esto con los datos reales
            if (editDataListener != null) {
                editDataListener.onEditDataRequested(dataToEdit);
            }
        });

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
