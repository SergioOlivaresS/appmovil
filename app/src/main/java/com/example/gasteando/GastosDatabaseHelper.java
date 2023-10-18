package com.example.gasteando;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GastosDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gastandoapp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GASTOS = "gastos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_MONTO = "monto";

    // Sentencia SQL para crear la tabla con la columna "fecha" como INTEGER
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_GASTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FECHA + " INTEGER, " + // Cambio de TEXT a INTEGER
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
}
