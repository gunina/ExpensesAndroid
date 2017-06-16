package com.app.expenses.models;


import android.content.Context;


import com.app.expenses.db.CategoryDataSource;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private CategoryDataSource categoryDataSource;

    private EntryModel entryModel;

    public CategoryModel(Context context) {
        categoryDataSource = new CategoryDataSource(context);
        entryModel = new EntryModel(context);
    }

    public List<Category> getAll() {
        return categoryDataSource.getAll();
    }

    public Category create(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryDataSource.create(category);
    }

    public Category update(Integer id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return categoryDataSource.updateCategory(category);
    }

    public void delete(Integer id) {
        entryModel.deleteCategory(id);
        categoryDataSource.delete(id);
    }
}
