package com.app.expenses.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.expenses.models.Category;
import com.app.expenses.models.Entry;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EXPENSES.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_ENTRY_TABLE = "create table " + Entry.TABLE_ENTRY_NAME + "( "
            + Entry.COLUMN_ENTRY_ID + " integer primary key autoincrement, "
            + Entry.COLUMN_ENTRY_SUBJECT + " text not null, "
            + Entry.COLUMN_ENTRY_SUM + " real not null, "
            + Entry.COLUMN_ENTRY_MONTH + " integer not null, "
            + Entry.COLUMN_ENTRY_IS_PAID + " integer not null, "
            + Entry.COLUMN_ENTRY_CATEGORY_ID + " integer, foreign key (" + Entry.COLUMN_ENTRY_CATEGORY_ID + ") references "
            + Category.TABLE_CATEGORY_NAME + "(" + Category.COLUMN_CATEGORY_ID + "));";

    private static final String CREATE_CATEGORY_TABLE = "create table " + Category.TABLE_CATEGORY_NAME + "( "
            + Category.COLUMN_CATEGORY_ID + " integer primary key autoincrement, "
            + Category.COLUMN_CATEGORY_NAME + " text not null);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_ENTRY_TABLE);
        database.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_ENTRY_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Category.TABLE_CATEGORY_NAME);
        onCreate(db);
    }
}
