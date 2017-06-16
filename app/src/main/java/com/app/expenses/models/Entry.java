package com.app.expenses.models;


public class Entry {
    public static final String TABLE_ENTRY_NAME = "ENTRY";
    public static final String COLUMN_ENTRY_ID = "_ID";
    public static final String COLUMN_ENTRY_SUBJECT = "SUBJECT";
    public static final String COLUMN_ENTRY_SUM = "SUM";
    public static final String COLUMN_ENTRY_MONTH = "MONTH";
    public static final String COLUMN_ENTRY_IS_PAID = "IS_PAID";
    public static final String COLUMN_ENTRY_CATEGORY_ID = "CATEGORY_ID";

    private Integer id;

    private String subject;

    private Double sum;

    private Integer month;

    private Boolean isPaid = false;

    private Integer categoryId;

    private boolean checked;

    private boolean isNew = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public Boolean isPaid() {
        return isPaid;
    }

    public void setPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
