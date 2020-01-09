package com.group3.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    //Variables about User
    private String idUser;
    private String firstName;
    private String lastName;
    private String mail;
    private String regionWorking;
    private String phoneNumber;
    private int access;
    private int role;

    //Constructor
    public User()
    {

    }
    public User(String idUser, String firstName, String lastName, String mail, String regionWorking, String phoneNumber, int access,int role)
    {
        this.idUser =idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.regionWorking = regionWorking;
        this.phoneNumber = phoneNumber;
        this.access = access;
        this.role=role;
    }
    //Getters and setters
    @Exclude
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
