package com.group3.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Role {
    private String id;
    private String name;

    public String getIdRole() {
        return id;
    }

    public void setIdRole(String idRole) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }

}
