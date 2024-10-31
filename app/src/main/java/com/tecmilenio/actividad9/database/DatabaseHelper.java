package com.tecmilenio.actividad9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_001.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "productos";
    private static final String COLUMN_CODIGO = "codigo";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_COSTO = "costo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_CODIGO + " TEXT PRIMARY KEY, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_CANTIDAD + " INTEGER, " +
                COLUMN_COSTO + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean agregarProducto(String codigo, String descripcion, int cantidad, double costo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODIGO, codigo);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_COSTO, costo);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean actualizarProducto(String codigo, String descripcion, int cantidad, double costo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_COSTO, costo);
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_CODIGO + " = ?", new String[]{codigo});
        return rowsAffected > 0;
    }

    public boolean eliminarProducto(String codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_CODIGO + " = ?", new String[]{codigo});
        return rowsDeleted > 0;
    }

    public Cursor obtenerProducto(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_CODIGO + " = ?", new String[]{codigo}, null, null, null);
    }
}
