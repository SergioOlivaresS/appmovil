
package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GastosDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gastandoapp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GASTOS = "gastos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_MONTO = "monto";
    public static final String COLUMN_DETALLE = "detalle";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_GASTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FECHA + " INTEGER, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_MONTO + " REAL, " +
                    COLUMN_DETALLE + " TEXT);";

    public GastosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTOS);
        onCreate(db);
        if (oldVersion < 2) {
            // Si la versión anterior de la base de datos era 1, agrega la columna "detalle".
            try {
                db.execSQL("ALTER TABLE " + TABLE_GASTOS + " ADD COLUMN " + COLUMN_DETALLE + " TEXT");
                Log.d("DatabaseUpgrade", "Column 'detalle' added successfully.");
            } catch (SQLException e) {
                Log.e("DatabaseUpgrade", "Error adding column 'detalle': " + e.getMessage());
            }
        }
    }

    public void updateData(String categoria, String detalle, String editedData, double monto) {
    }

    public void deleteData(Producto producto) {
    }

    public void eliminarProducto(Producto productoSeleccionado) {
    }

    public List<Producto> obtenerProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GASTOS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String categoria = cursor.getString(cursor.getColumnIndex("categoria"));
                    @SuppressLint("Range") String detalle = cursor.getString(cursor.getColumnIndex("detalle"));
                    @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                    @SuppressLint("Range") double monto = cursor.getDouble(cursor.getColumnIndex("monto"));

                    // Imprimir registros para verificar si se obtienen datos
                    Log.d("DatabaseQuery", "Categoria: " + categoria + ", Detalle: " + detalle + ", Fecha: " + fecha + ", Monto: " + monto);

                    Producto producto = new Producto(categoria, detalle, fecha, monto);
                    listaProductos.add(producto);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return listaProductos;
    }

}