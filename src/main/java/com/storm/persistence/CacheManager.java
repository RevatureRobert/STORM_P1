package com.storm.persistence;

import com.storm.mapping.Database;
import com.storm.mapping.Schema;
import com.storm.mapping.Table;

public class CacheManager {
    Database context;
    Schema schema;
    Table table;

    public CacheManager(Database context){
        this.context = context;
    }

    public void addEntryToContext(Object o){

    }
}
