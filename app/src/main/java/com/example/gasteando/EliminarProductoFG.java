package com.example.gasteando;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EliminarProductoFG extends Fragment {

    private RecyclerView recyclerViewProductos;
    private Button btnEliminar;
    private List<Producto> listaProductos = new ArrayList<>();
    private ProductosAdapter productosAdapter;
    private GastosDatabaseHelper dbHelper;

    public EliminarProductoFG() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eliminar_producto_f_g, container, false);

        recyclerViewProductos = view.findViewById(R.id.recyclerViewProductos);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        dbHelper = new GastosDatabaseHelper(requireContext());

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Obtén los productos de Firestore y llénalos en listaProductos
        obtenerProductosDesdeFirestore();

        // Configurar el adaptador
        productosAdapter = new ProductosAdapter(listaProductos);
        recyclerViewProductos.setAdapter(productosAdapter);

        btnEliminar.setOnClickListener(v -> onDeleteClicked());

        return view;
    }

    private void obtenerProductosDesdeFirestore() {
        // Obtiene una instancia de la base de datos de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obten el ID del usuario almacenado en SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userID", null);

        if (userId == null) {
            return;
        }

        // Realiza una consulta a Firestore para obtener los productos del usuario actual
        CollectionReference productosRef = db.collection("productos");

        productosRef
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Borra la lista actual de productos
                            listaProductos.clear();

                            // Itera a través de los documentos obtenidos y crea objetos Producto
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String categoria = document.getString("categoria");
                                String detalle = document.getString("detalle");
                                String fecha = document.getString("fecha");
                                double monto = document.getDouble("monto");

                                Producto producto = new Producto(categoria, detalle, fecha, monto);
                                listaProductos.add(producto);
                            }

                            // Notifica al adaptador que los datos han cambiado
                            productosAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("Firestore", "Error al obtener datos: " + task.getException());
                        }
                    }
                });
    }


    private void onDeleteClicked() {
        Log.d("EliminarProducto", "Botón Eliminar Producto clicado");

        if (productosAdapter.getSelectedItemCount() == 0) {
            // No se ha seleccionado ningún producto para eliminar.
            // Puedes mostrar un mensaje de error.
            Log.d("EliminarProducto", "No se han seleccionado productos para eliminar");
            return;
        }

        // Obtiene los productos seleccionados a eliminar del adaptador
        List<Producto> productosSeleccionados = productosAdapter.getSelectedItems();

        // Obtiene una instancia de la base de datos de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obten el ID del usuario almacenado en SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userID", null);

        if (userId == null) {
            return;
        }

        // Realiza la eliminación de los productos seleccionados en Firestore
        for (Producto producto : productosSeleccionados) {
            db.collection("productos")
                    .whereEqualTo("categoria", producto.getCategoria())
                    .whereEqualTo("detalle", producto.getDetalle())
                    .whereEqualTo("fecha", producto.getFecha())
                    .whereEqualTo("monto", producto.getMonto())
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Borra el documento correspondiente
                                    db.collection("productos").document(document.getId()).delete();
                                }
                            } else {
                                Log.e("Firestore", "Error al eliminar datos: " + task.getException());
                            }
                        }
                    });
        }

        // Limpia la selección después de eliminar los productos
        productosAdapter.clearSelectedItems();
    }


}
