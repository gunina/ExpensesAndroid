package com.app.expenses.models;


import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Entry> entries = new ArrayList<>();

    private Category category;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
