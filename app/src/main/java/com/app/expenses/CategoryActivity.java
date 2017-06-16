package com.app.expenses;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.expenses.adapters.CategoryAdapter;
import com.app.expenses.listeners.OnCheckedChangeListener;
import com.app.expenses.listeners.OnDeleteListener;
import com.app.expenses.listeners.OnSubjectChangeListener;
import com.app.expenses.models.Category;
import com.app.expenses.presenters.CategoryPresenter;

import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    private CategoryPresenter presenter;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        presenter = new CategoryPresenter(this);

        final ListView listView = (ListView) findViewById(R.id.categories);
        categoryAdapter = new CategoryAdapter(this, android.R.layout.simple_list_item_1, presenter.getAll());
        listView.setAdapter(categoryAdapter);

        final FloatingActionButton buttonAdd = (FloatingActionButton) findViewById(R.id.button_add_category);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = presenter.create("");
                categoryAdapter.add(category);
                refreshData();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.Categories);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        categoryAdapter.setOnEventListener(new OnSubjectChangeListener() {
            @Override
            public void onSubjectChangeEvent(Integer id, String subject) {
                presenter.update(id, subject);
                System.out.println("subject change");
            }
        });

        categoryAdapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDeleteEvent(Integer id) {
                presenter.delete(id);
                refreshData();
                System.out.println("checked change");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        categoryAdapter.getCategories().clear();
        categoryAdapter.getCategories().addAll(presenter.getAll());
        categoryAdapter.notifyDataSetChanged();
    }
}
