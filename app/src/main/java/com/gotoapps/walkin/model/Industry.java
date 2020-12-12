package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Industry {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public Industry() {
    }

    public Industry(int id, String name) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Industry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
