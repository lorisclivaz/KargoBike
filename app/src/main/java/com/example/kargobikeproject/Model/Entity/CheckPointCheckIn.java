package com.example.kargobikeproject.Model.Entity;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckPointCheckIn {
    private String idCheckIn;
    private String idType;
    private String idUser;
    private String name;
    private Date timeStamp;
    private double latitude;
    private double longtitude;
    //Constructor
    public CheckPointCheckIn(String idType, String idUser, String name, Date timeStamp, double latitude, double longtitude) {
        this.idType = idType;
        this.idUser = idUser;
        this.name = name;
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    // Getter and Setters
    @Exclude
    public String getIdCheckIn() {
        return idCheckIn;
    }

    public void setIdCheckIn(String idCheckIn) {
        this.idCheckIn = idCheckIn;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("timeStamp", timeStamp);
        result.put("latitude", latitude);
        result.put("longitude", longtitude);
        return result;
    }
}