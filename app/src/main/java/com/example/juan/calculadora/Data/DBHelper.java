package com.example.juan.calculadora.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
    private SQLiteDatabase database;
    private AppDB appDB;

    public DBHelper(Context context) {
        appDB = new AppDB(context);
        database = appDB.getWritableDatabase();
    }

    public void setUserWithNick(String name) {
        String[] selectionArgs = {name};
        Cursor cursor = database.query(AppDB.TABLE_NAME, null, AppDB.Column.NICK + "=?", selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            User.user = new User(cursor.getLong(0), cursor.getString(1), cursor.getInt(2));
        }

        cursor.close();
    }
}
