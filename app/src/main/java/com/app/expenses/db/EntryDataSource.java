package com.app.expenses.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.app.expenses.models.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { Entry.COLUMN_ENTRY_ID, Entry.COLUMN_ENTRY_SUBJECT,
            Entry.COLUMN_ENTRY_SUM, Entry.COLUMN_ENTRY_MONTH, Entry.COLUMN_ENTRY_IS_PAID, Entry.COLUMN_ENTRY_CATEGORY_ID};

    public EntryDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public Entry createEntry(Entry entry) {
        open();
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_ENTRY_SUBJECT, entry.getSubject());
        values.put(Entry.COLUMN_ENTRY_SUM, entry.getSum());
        values.put(Entry.COLUMN_ENTRY_MONTH, entry.getMonth());
        values.put(Entry.COLUMN_ENTRY_IS_PAID, entry.isPaid());
        values.put(Entry.COLUMN_ENTRY_CATEGORY_ID, entry.getCategoryId());

        long insertId = database.insert(Entry.TABLE_ENTRY_NAME, null, values);

        Cursor cursor = database.query(Entry.TABLE_ENTRY_NAME,
                allColumns, Entry.COLUMN_ENTRY_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Entry newEntry = cursorToEntry(cursor);
        cursor.close();
        close();
        return newEntry;
    }

    public Entry updateEntry(Entry entry) {
        open();
        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_ENTRY_ID, entry.getId());
        values.put(Entry.COLUMN_ENTRY_SUBJECT, entry.getSubject());
        values.put(Entry.COLUMN_ENTRY_SUM, entry.getSum());
        values.put(Entry.COLUMN_ENTRY_MONTH, entry.getMonth());
        values.put(Entry.COLUMN_ENTRY_IS_PAID, entry.isPaid());
        values.put(Entry.COLUMN_ENTRY_CATEGORY_ID, entry.getCategoryId());

        String[] selectionArgs = { String.valueOf(entry.getId()) };

        int count = database.update(Entry.TABLE_ENTRY_NAME,
                values, Entry.COLUMN_ENTRY_ID + " = ?", selectionArgs);
        close();

        return entry;
    }

    public void deleteEntry(Entry item) {
        open();
        long id = item.getId();
        database.delete(Entry.TABLE_ENTRY_NAME, Entry.COLUMN_ENTRY_ID + " = " + id, null);
        close();
    }

    public List<Entry> getAllForMonth(Integer month) {
        open();
        List<Entry> entries = new ArrayList<Entry>();

        Cursor cursor = database.query(Entry.TABLE_ENTRY_NAME, allColumns, "MONTH = " + month, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Entry entry = cursorToEntry(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return entries;
    }

    public List<Entry> getAll() {
        open();
        List<Entry> entries = new ArrayList<Entry>();

        Cursor cursor = database.query(Entry.TABLE_ENTRY_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Entry category = cursorToEntry(cursor);
            entries.add(category);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return entries;
    }

    private Entry cursorToEntry(Cursor cursor) {
        Entry entry = new Entry();
        entry.setId(cursor.getInt(0));
        entry.setSubject(cursor.getString(1));
        entry.setSum(cursor.getDouble(2));
        entry.setMonth(cursor.getInt(3));
        entry.setPaid(Integer.valueOf(1).equals(cursor.getInt(4)));

        if (cursor.isNull(5)) {
            entry.setCategoryId(null);
        } else {
            entry.setCategoryId(cursor.getInt(5));
        }

        return entry;
    }
}
