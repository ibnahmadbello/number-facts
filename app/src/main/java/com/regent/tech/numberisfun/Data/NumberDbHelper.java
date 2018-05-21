package com.regent.tech.numberisfun.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NumberDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "number.db";

    private static final int DATABASE_VERSION = 1;

    public NumberDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NUMBER_TABLE = "CREATE TABLE " +
                NumberContract.NumberEntry.TABLE_NAME + " (" +
                NumberContract.NumberEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NumberContract.NumberEntry.COLUMN_RESULT + " TEXT NOT NULL, " +
                NumberContract.NumberEntry.COLUMN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_NUMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NumberContract.NumberEntry.TABLE_NAME);
//        onCreate(sqLiteDatabase);
    }
}
