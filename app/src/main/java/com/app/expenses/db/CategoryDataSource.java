package com.app.expenses.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.app.expenses.models.Category;
import com.app.expenses.models.Entry;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataSource {

    public static final String TABLE_NAME = Category.TABLE_CATEGORY_NAME;
    public static final String COLUMN_ID = Category.COLUMN_CATEGORY_ID;
    private SQLiteDatabase database;
    private DBHelper dBHelper;
    private String[] allColumns = { Category.COLUMN_CATEGORY_ID, Category.COLUMN_CATEGORY_NAME};

    public CategoryDataSource(Context context) {
        dBHelper = new DBHelper(context);
    }

    private void open() throws SQLException {
        database = dBHelper.getWritableDatabase();
    }

    private void close() {
        dBHelper.close();
    }

    public Category create(Category category) {
        open();
        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_CATEGORY_NAME, category.getName());

        long insertId = database.insert(TABLE_NAME, null, values);

        Cursor cursor = database.query(TABLE_NAME,
                allColumns, COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();
        close();
        return newCategory;
    }


    public Category updateCategory(Category category) {
        open();
        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_CATEGORY_ID, category.getId());
        values.put(Category.COLUMN_CATEGORY_NAME, category.getName());

        String[] selectionArgs = { String.valueOf(category.getId()) };

        int count = database.update(Category.TABLE_CATEGORY_NAME,
                values, Category.COLUMN_CATEGORY_ID + " = ?", selectionArgs);
        close();

        return category;
    }

    public void delete(Integer id) {
        open();
        database.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);
        close();
    }

    public List<Category> getAll() {
        open();
        List<Category> categories = new ArrayList<Category>();

        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            categories.add(category);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return categories;
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setName(cursor.getString(1));
        return category;
    }
}
