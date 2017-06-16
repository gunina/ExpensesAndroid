package com.app.expenses.models;

import android.content.Context;

import com.android.internal.util.Predicate;
import com.app.expenses.db.EntryDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntryModel {

    private EntryDataSource entryDataSource;

    private List<Entry> entries = new ArrayList<>();

    private int month;

    public EntryModel(Context context, int month) {
        entryDataSource = new EntryDataSource(context);
        entries = entryDataSource.getAllForMonth(month);
        this.month = month;
    }

    EntryModel(Context context) {
        entryDataSource = new EntryDataSource(context);
        entries = entryDataSource.getAll();
    }

    public List<Entry> getEntries() {
        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry entry1, Entry entry2) {
                if (entry1.isPaid()) {
                    return 1;
                }

                if (!entry2.isPaid()) {
                    return -1;
                }

                return 0;
            }
        });
        return entries;
    }

    public void delete() {
        List<Entry> removed = new ArrayList<>();

        for (Entry entry : entries) {
            if (entry.isChecked()) {
                entryDataSource.deleteEntry(entry);
                removed.add(entry);
            }
        }
        entries.removeAll(removed);
    }

    public void setChecked(Integer id, Boolean checked) {
        for (Entry entry : entries) {
            if (entry.getId().equals(id)) {
                entry.setChecked(checked);
            }
        }
    }

    public Entry addNewEntry() {
        Entry entry = new Entry();
        entry.setSubject("");
        entry.setSum(0.0);
        entry.setNew(true);
        entry.setMonth(month);
        entry.setCategoryId(1);

        Entry savedEntry = entryDataSource.createEntry(entry);
        entries.add(savedEntry);
        return savedEntry;
    }


    public Entry create(String subject, Double sum, Integer categoryId) {
        Entry entry = new Entry();
        entry.setSubject(subject);
        entry.setSum(sum);
        entry.setNew(true);
        entry.setMonth(month);
        entry.setCategoryId(categoryId);

        Entry savedEntry = entryDataSource.createEntry(entry);
        entries.add(savedEntry);
        return savedEntry;
    }

    public void done() {
        for (Entry entry : entries) {
            if (entry.isChecked()) {
                entry.setPaid(true);
                entry.setChecked(false);
                entryDataSource.updateEntry(entry);
            }
        }
    }

    public void update(Integer id, String subject) {
        for (Entry entry : entries) {
            if (entry.getId().equals(id)) {
                entry.setSubject(subject);

                entryDataSource.updateEntry(entry);
            }
        }
    }

    public void updateSum(Integer id, Double sum) {
        for (Entry entry : entries) {
            if (entry.getId().equals(id)) {
                entry.setSum(sum);

                entryDataSource.updateEntry(entry);
            }
        }
    }

    public void updateCategory(Integer id, Integer categoryId) {
        for (Entry entry : entries) {
            if (entry.getId().equals(id)) {
                entry.setCategoryId(categoryId);

                entryDataSource.updateEntry(entry);
            }
        }
    }

    public void deleteCategory(Integer categoryId) {
        for (Entry entry : entries) {
            if (categoryId.equals(entry.getCategoryId())) {
                entry.setCategoryId(null);

                entryDataSource.updateEntry(entry);
            }
        }
    }

    public Double calculateUnpaid() {
        return calculate(i -> !i.isPaid());
    }

    public Double calculatePaid() {
        return calculate(Entry::isPaid);
    }

    private Double calculate(Predicate<Entry> predicate) {
        Double paidAmount = 0.0;

        for (Entry entry : entries) {
            if (predicate.apply(entry)) {
                paidAmount = paidAmount + entry.getSum();
            }
        }

        return paidAmount;
    }
}
