package com.app.expenses.presenters;



import com.app.expenses.PageActivity;
import com.app.expenses.models.Entry;
import com.app.expenses.models.EntryModel;

import java.util.List;

public class Presenter {
    private PageActivity activity;
    private EntryModel entryModel;

    public Presenter(PageActivity activity, int month) {
        this.activity = activity;

        entryModel = new EntryModel(activity, month);
    }

    public void delete() {
        entryModel.delete();
    }

    public void setChecked(Integer id, Boolean isChecked) {
        entryModel.setChecked(id, isChecked);
    }

    public Entry addNewEntry() {
        return entryModel.addNewEntry();
    }

    public Entry create(String subject, Double sum, Integer categoryId) {
        return entryModel.create(subject, sum, categoryId);
    }

    public void done() {
        entryModel.done();
    }

    public List<Entry> getEntries() {
        List<Entry> entries = entryModel.getEntries();
        activity.showUnpaidAmount(entryModel.calculateUnpaid());
        activity.showPaidAmount(entryModel.calculatePaid());
        return entries;
    }

    public void update(Integer id, String subject) {
        entryModel.update(id, subject);
    }

    public void updateSum(Integer id, Double sum) {
        entryModel.updateSum(id, sum);
        activity.showUnpaidAmount(entryModel.calculateUnpaid());
        activity.showPaidAmount(entryModel.calculatePaid());
    }

    public void updateCategory(Integer id, Integer categoryId) {
        entryModel.updateCategory(id, categoryId);
    }
}
