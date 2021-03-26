package com.storm.mapping;

import java.util.HashSet;
import java.util.Set;

public class Database {
    private String databaseName;
    private Set<Schema> schemas;

    public Database(String databaseName) {
        this.databaseName = databaseName;
        schemas = new HashSet<>();
    }

    public Database(String databaseName, Set<Schema> schemas) {
        this.databaseName = databaseName;
        this.schemas = schemas;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Set<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Set<Schema> schemas) {
        this.schemas = schemas;
    }
}
