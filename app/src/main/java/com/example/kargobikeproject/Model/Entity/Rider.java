package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Rider {

    //Variables about Rider
    private String idRider;
    private String firstName;
    private String lastName;



    //Constructor
    public Rider()
    {

    }

    public Rider(String idRider, String firstName, String lastName)
    {
        this.idRider =idRider;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    //Getters and setters
    @Exclude

    public String getIdRider() {
        return idRider;
    }

    public void setIdRider(String idRider) {
        this.idRider = idRider;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);

        return result;
    }
}
