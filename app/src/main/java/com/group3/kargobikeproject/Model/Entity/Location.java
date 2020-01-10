package com.group3.kargobikeproject.Model.Entity;

public class Location {

    String id;
    String name;

    public Location(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
