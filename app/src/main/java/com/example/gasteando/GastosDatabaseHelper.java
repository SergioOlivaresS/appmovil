// Clase GastosDatabaseHelper.java

package com.example.gasteando;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        // Agrega más actualizaciones según sea necesario para futuras versiones.
    }

    public void updateData(String editedData) {
    }
}
