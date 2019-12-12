package com.group3.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

public class Type {
    private String idType;
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }
    @Exclude
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
