package com.example.gasteando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;
    private List<Producto> productosSeleccionados = new ArrayList<>();
    private Context context;

    public ProductosAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        // Muestra el detalle y la fecha en lugar de la categoría
        holder.txtNombreProducto.setText("Detalle: " + producto.getDetalle() + "\nFecha: " + producto.getFecha());
        holder.chkSeleccionado.setChecked(productosSeleccionados.contains(producto));

        // Manejar la selección de elementos
        holder.chkSeleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Agregar el producto a la lista de seleccionados
                productosSeleccionados.add(producto);
            } else {
                // Eliminar el producto de la lista de seleccionados
                productosSeleccionados.remove(producto);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public List<Producto> getSelectedItems() {
        return productosSeleccionados;
    }

    public int getSelectedItemCount() {
        return productosSeleccionados.size();
    }

    public void clearSelectedItems() {
        productosSeleccionados.clear();
    }


    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombreProducto;
        private CheckBox chkSeleccionado;

        ProductoViewHolder(View itemView) {
            super(itemView);
            txtNombreProducto = itemView.findViewById(R.id.txtNombreProducto);
            chkSeleccionado = itemView.findViewById(R.id.chkSeleccionado);
        }
    }
}
