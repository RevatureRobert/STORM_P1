package com.storm.mapping;

import com.storm.persistence.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table {
    private String tableName;
    private Set<Column> columns;
    // TODO when a query is executed, store the entry in a cache of entries
    private Set<Object> entries;
    // TODO store the queries that have been created for the table
    private List<Query> queries;

    public Table(String tableName) {
        this.tableName = tableName;
        this.columns = new HashSet<>();
    }

    public Table(String tableName, Set<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

}
