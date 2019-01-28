package com.example.jyang.navigationdrawer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarsSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Cars (VIN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, color TEXT)";

    public CarsSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cars");
        db.execSQL(sqlCreate);
    }
}
