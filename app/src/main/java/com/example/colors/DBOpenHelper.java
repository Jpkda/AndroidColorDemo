package com.example.colors;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String CREATE_TABLE_SQL = "create table colorType(" +
            "_id integer primary key autoincrement," +
            "time int,"+
            "colorHex text)";
    final static String CREATE_TABLE_SQL_COLLECT = "create table colorCollect(" +
            "_id integer primary key autoincrement," +
            "time int,"+
            "collectOne ,"+
            "collectTwo ,"+
            "collectThree ,"+
            "collectFour)";

    final static String CREATE_TABLE_SQL_ALL = "create table TypeColor(" +
            "_id integer primary key autoincrement," +
            "TYPE int,"+
            "colorHex ,"+
            "collectOne ,"+
            "collectTwo ,"+
            "collectThree ,"+
            "collectFour)";

    final static String CREATE_USER_TABLE_SQL = "create table colorType(" +
            "_id integer primary key autoincrement," +
            "time int,"+
            "colorHex text)";


    private Context mContext;
    public DBOpenHelper(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL_COLLECT);
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL_ALL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists colorType");
        onCreate(sqLiteDatabase);
    }
}
