package com.app.expenses;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.expenses.adapters.GroupAdapter;
import com.app.expenses.listeners.OnCheckedChangeListener;
import com.app.expenses.listeners.OnSubjectChangeListener;
import com.app.expenses.listeners.OnSumChangeListener;
import com.app.expenses.models.Category;
import com.app.expenses.models.Entry;
import com.app.expenses.models.Group;
import com.app.expenses.presenters.CategoryPresenter;
import com.app.expenses.presenters.Presenter;

import java.util.ArrayList;
import java.util.List;


public class PageActivity extends AppCompatActivity {
    private GroupAdapter groupAdapter;
    private int month;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        setToolbar();
        presenter = new Presenter(this, month);
        initEntryList();
        initButtonAdd();

        groupAdapter.setOnEventListener(new OnSubjectChangeListener() {
            @Override
            public void onSubjectChangeEvent(Integer id, String subject) {
                presenter.update(id, subject);
                System.out.println("subject change");
            }
        });

        groupAdapter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChangeEvent(Integer id, Boolean isChecked) {
                presenter.setChecked(id, isChecked);
                System.out.println("checked change");
            }
        });

        groupAdapter.setOnSumChangeListener(new OnSumChangeListener() {
            @Override
            public void onSumChangeEvent(Integer id, Double sum) {
                presenter.updateSum(id, sum);
                System.out.println("sum change");
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshData();
    }

    private void setToolbar() {
        String monthName = getIntent().getStringExtra("date");
        month = getIntent().getIntExtra("month", 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(monthName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initEntryList() {
        final ExpandableListView listView = (ExpandableListView) findViewById(R.id.entry_list);

        List<Group> groups = getGroups();
        groupAdapter = new GroupAdapter(this, groups);
        listView.setAdapter(groupAdapter);
    }

    @NonNull
    private List<Group> getGroups() {
        CategoryPresenter categoryPresenter = new CategoryPresenter(this);
        List<Category> categories = categoryPresenter.getAll();
        List<Entry> entries = presenter.getEntries();

        List<Group> groups = new ArrayList<>();

        for (Category category : categories) {
            Group group = new Group();
            group.setCategory(category);
            for (Entry entry : entries) {
                if (category.getId().equals(entry.getCategoryId())) {
                    group.getEntries().add(entry);
                }
            }

            groups.add(group);
        }

        List<Entry> nullEntries = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getCategoryId() == null) {
                nullEntries.add(entry);
            }
        }
        Category category = new Category();
        category.setName("Unknown");
        Group group = new Group();
        group.setCategory(category);
        group.setEntries(nullEntries);

        groups.add(group);
        return groups;
    }

    private void initButtonAdd() {
        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CreatingFragment creatingFragment = new CreatingFragment();
                creatingFragment.show(fm, "creation dialog");
                creatingFragment.setPresenter(presenter);
            }
        });


    }

    public void showUnpaidAmount(Double unpaidAmount) {
        final TextView unpaid = ((TextView) findViewById(R.id.unpaidAmount));
        unpaid.setText("Unpaid: " + unpaidAmount);
    }

    public void showPaidAmount(Double paidAmount) {
        final TextView paid = ((TextView) findViewById(R.id.paidAmount));
        paid.setText("Paid: " + paidAmount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_delete:
                presenter.delete();
                refreshData();
                return true;

            case R.id.action_done:
                presenter.done();
                refreshData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        groupAdapter.getGroups().clear();
        groupAdapter.getGroups().addAll(getGroups());
        groupAdapter.notifyDataSetChanged();
    }
}
