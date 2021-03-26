package com.storm.mapping;

import java.util.HashSet;
import java.util.Set;

public class Schema {
    private String schemaName;
    private Set<Table> tables;


    public Schema(String schemaName){
        this.schemaName = schemaName;
        this.tables = new HashSet<>();
    }

    public Schema(String schemaName, Set<Table> tables) {
        this.schemaName = schemaName;
        this.tables = tables;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }
}
