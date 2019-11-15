package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Client {

    //Create variables about Client
    private String idClient;
    private String firstName;
    private String lastName;


    //Constructor
    public Client()
    {

    }

    public Client(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Exclude

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
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
        result.put("latName", lastName);

        return result;
    }
}
