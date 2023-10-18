package com.example.gasteando;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GastosDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Gastos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GASTOS = "gastos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_MONTO = "monto";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_GASTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FECHA + " TEXT, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_MONTO + " REAL);";

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
    }

    public void insertarGasto(String fecha, String categoria, double monto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_MONTO, monto);

        db.insert(TABLE_GASTOS, null, values);
        db.close(); // Cierra la base de datos después de realizar la operación.
    }

    public String consultarGastosPorFecha(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnas = {COLUMN_CATEGORIA, COLUMN_MONTO};
        String seleccion = COLUMN_FECHA + " = ?";
        String[] seleccionArgs = {fecha};

        Cursor cursor = db.query(TABLE_GASTOS, columnas, seleccion, seleccionArgs, null, null, null);

        StringBuilder resultado = new StringBuilder();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String categoria = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIA));
            @SuppressLint("Range") double monto = cursor.getDouble(cursor.getColumnIndex(COLUMN_MONTO));
            resultado.append("Categoría: ").append(categoria).append(", Monto: ").append(monto).append("\n");
        }

        cursor.close();
        db.close();
        return resultado.toString();
    }

    public String consultarDatosPorFechaYCategoria(String fechaSeleccionada, String categoriaSeleccionada) {
        return fechaSeleccionada;
    }
}
