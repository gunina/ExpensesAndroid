package com.app.expenses.presenters;


import android.content.Context;


import com.app.expenses.models.Category;
import com.app.expenses.models.CategoryModel;

import java.util.List;

public class CategoryPresenter {

    private CategoryModel categoryModel;

    public CategoryPresenter(Context context) {
        categoryModel = new CategoryModel(context);
    }

    public List<Category> getAll() {
        return categoryModel.getAll();
    }

    public Category create(String name) {
        return categoryModel.create(name);
    }

    public Category update(Integer id, String name) {
        return categoryModel.update(id, name);
    }

    public void delete(Integer id) {
        categoryModel.delete(id);
    }
}
