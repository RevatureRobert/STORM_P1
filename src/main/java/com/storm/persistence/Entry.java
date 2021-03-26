package com.storm.persistence;

public class Entry {

    private boolean isDirty;
    private Object value;

    public Entry(boolean isDirty, Object value) {
        this.isDirty = isDirty;
        this.value = value;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
