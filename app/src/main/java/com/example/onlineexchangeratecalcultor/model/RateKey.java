package com.example.onlineexchangeratecalcultor.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "rateKey")
public class RateKey {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public RateKey(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
