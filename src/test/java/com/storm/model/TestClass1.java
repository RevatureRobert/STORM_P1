package com.storm.model;

import com.storm.annotations.Entity;
import com.storm.annotations.Id;
import com.storm.annotations.NotNullable;

@Entity
public class TestClass1 {
    int id;
    String notNullable;

    public TestClass1(int id, String notNullable) {
        this.id = id;
        this.notNullable = notNullable;
    }
}
