package com.storm.model;

import com.storm.annotations.Entity;
import com.storm.annotations.Id;
import com.storm.annotations.NotNullable;

@Entity(schema = "tc2")
public class TestClass2{
    @Id
    int id;
    @NotNullable
    String notNullable;

    public TestClass2(Integer id, String notNullable) {
        this.id = id;
        this.notNullable = notNullable;
    }

    @Override
    public String toString() {
        return "TestClass2{" +
                "id=" + id +
                ", notNullable=" + notNullable +
                '}';
    }
}