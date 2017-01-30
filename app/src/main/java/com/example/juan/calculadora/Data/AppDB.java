package com.example.juan.calculadora.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDB extends SQLiteOpenHelper {
    static int BD_VERSION = 1;
    static String BD_NAME = "bd_project";
    static String TABLE_NAME = "ranking";
    static final class Column {
        static final String ID = "a";
        static final String NICK = "b";
        static final String POINTS = "c";
    }

    AppDB(Context context) {
        super(context, BD_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME +" ("+Column.ID+" INTEGER PRIMARY KEY,"+Column.NICK+" TEXT UNIQUE,"+Column.POINTS+" INTEGER)");
        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_NAME +" ("+Column.NICK+", "+Column.POINTS+") VALUES('jugador1','15'), ('jugador2', '30'), ('jugador3','8')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
