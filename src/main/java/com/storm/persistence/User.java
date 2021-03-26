package com.storm.persistence;

import com.storm.annotations.Entity;
import com.storm.annotations.Id;


@Entity
public class User implements com.storm.Entity {
    @Id
    private int id;

    private String name;


    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }
}
