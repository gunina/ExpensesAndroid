package com.app.expenses.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.expenses.R;
import com.app.expenses.listeners.OnCheckedChangeListener;
import com.app.expenses.listeners.OnDeleteListener;
import com.app.expenses.listeners.OnSubjectChangeListener;
import com.app.expenses.models.Category;
import com.app.expenses.models.Entry;

import java.util.List;


public class CategoryAdapter extends ArrayAdapter<Category> implements OnSubjectChangeListener, OnDeleteListener {

    private List<Category> categories;

    private Context context;

    private OnSubjectChangeListener onSubjectChangeListener;
    private OnDeleteListener onDeleteListener;

    public CategoryAdapter(Context context, int textViewResourceId, List<Category> categories) {
        super(context, textViewResourceId);

        this.context = context;
        this.categories = categories;
        addAll(categories);
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Category getItem(int position) {
        if (position >= categories.size()) {
            return null;
        }
        return categories.get(position);
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.item_category, parent, false);
        }

        Category item = getItem(position);

        final EditText name = ((EditText) view.findViewById(R.id.category_name));
        final ImageButton cb = ((ImageButton) view.findViewById(R.id.category_checkBox));

        name.setText(item.getName());

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Category item = getItem(position);
                    if (item != null &&! name.getText().toString().equals(item.getName())) {
                        onSubjectChangeEvent(item.getId(), name.getText().toString());
                    }
                }
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category item = getItem(position);
                if (item != null) {
                    onDeleteEvent(item.getId());
                }
            }
        });
        return view;
    }

    public void setOnEventListener(OnSubjectChangeListener listener) {
        onSubjectChangeListener = listener;
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        onDeleteListener = listener;
    }

    @Override
    public void onSubjectChangeEvent(Integer id, String subject) {
        if (onSubjectChangeListener != null) {
            onSubjectChangeListener.onSubjectChangeEvent(id, subject);
        }
    }

    @Override
    public void onDeleteEvent(Integer id) {
        if (onDeleteListener != null) {
            onDeleteListener.onDeleteEvent(id);
        }
    }
}
