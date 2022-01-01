package com.example.slow_postbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "slowPostbox", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists post ("
                + "_id integer primary key autoincrement,"
                + "title text, writer text, content text, openTime text);";

        String sql2 = "CREATE TABLE if not exists calendar ("
                + "_id integer primary key autoincrement,"
                + "date text, content text);";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists post";
        String sql2 = "DROP TABLE if exists calendar";

        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);
    }

}
