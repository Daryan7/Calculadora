package com.example.juan.calculadora.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

        if (!cursor.moveToFirst()) {
            User.user = cursorToUser(cursor);
        }

        cursor.close();
    }

    public List<User> getAllUsers() {
        Cursor cursor = database.query(AppDB.TABLE_NAME, null, null, null, null, null, AppDB.Column.POINTS);

        ArrayList<User> list = new ArrayList<>(cursor.getCount());

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursorToUser(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    private User cursorToUser(Cursor cursor) {
        return new User(cursor.getLong(0), cursor.getString(1), cursor.getInt(2));
    }
}
