package com.app.expenses.models;



public class Category {

    public static final String TABLE_CATEGORY_NAME = "CATEGORY";
    public static final String COLUMN_CATEGORY_ID = "_ID";
    public static final String COLUMN_CATEGORY_NAME = "NAME";

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
