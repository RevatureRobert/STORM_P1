package com.storm.mapping;

public class Column {
    private String columnName;
    private Class<?> dataType;
    private boolean isPrimary;
    private boolean isNullable;

    public Column(String columnName, Class<?> dataType, boolean isPrimary, boolean isNullable) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.isPrimary = isPrimary;
        this.isNullable = isNullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", dataType=" + dataType +
                ", isPrimary=" + isPrimary +
                ", isNullable=" + isNullable +
                '}';
    }
}
