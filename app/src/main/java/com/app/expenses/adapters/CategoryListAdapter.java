package com.app.expenses.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.expenses.R;
import com.app.expenses.models.Category;

import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categoryList;

    public CategoryListAdapter(Context context, int textViewResourceId, List<Category> categoryList) {
        super(context, textViewResourceId, categoryList);

        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public Category getItem(int position) {
//        if (position >= entries.size()) {
//            return null;
//        }
        return categoryList.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.spinner_item, parent, false);
        }

        Category item = getItem(position);

        final TextView text = ((TextView) view.findViewById(R.id.spinner_item));
        text.setText(item.getName());

        return view;
    }
}
