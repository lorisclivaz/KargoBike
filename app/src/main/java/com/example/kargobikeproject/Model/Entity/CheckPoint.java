package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CheckPoint {

    //Create variable about CheckPoint
    private String idCheckPoint;
    private String checkPointName;
    private double latitude;
    private double longtitude;


    //Constructor
    public CheckPoint(){

    }
    public CheckPoint(String checkPointName, double latitude, double longtitude)
    {
        this.checkPointName = checkPointName;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    public CheckPoint(String checkPointName)
    {
        this.checkPointName = checkPointName;
    }

    @Exclude

    public String getIdCheckPoint() {
        return idCheckPoint;
    }

    public void setIdCheckPoint(String idCheckPoint) {
        this.idCheckPoint = idCheckPoint;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
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
        result.put("checkPointName", checkPointName);
        result.put("latitude", latitude);
        result.put("longitude", longtitude);
        return result;
    }
}
