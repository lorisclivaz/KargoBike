package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    //Variables about User
    private String idRider;
    private String firstName;
    private String lastName;
    private String mail;
    private String regionWorking;
    private String phoneNumber;
    private Boolean access;


    //Constructor
    public User()
    {

    }

    public User(String idRider, String firstName, String lastName, String mail, String regionWorking, String phoneNumber)
    {
        this.idRider =idRider;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.regionWorking = regionWorking;
        this.phoneNumber = phoneNumber;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRegionWorking() {
        return regionWorking;
    }

    public void setRegionWorking(String regionWorking) {
        this.regionWorking = regionWorking;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("mail", mail);
        result.put("regionWorking", regionWorking);
        result.put("phoneNumber", phoneNumber);
        result.put("access", access);

        return result;
    }
}
