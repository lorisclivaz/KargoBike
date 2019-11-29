package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckPoint {

    //Create variable about CheckPoint
    private String idCheckPoint;
    private String checkPointName;
    private double latitude;
    private double longtitude;
    private String typeName;
    private String idOrder;
    private String nameRider;
    private String timeStamp;


    //Constructor
    public CheckPoint(){

    }
    public CheckPoint(String checkPointName, double latitude, double longtitude, String typeName, String nameRider, String timeStamp)
    {
        this.checkPointName = checkPointName;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.typeName = typeName;
        this.nameRider = nameRider;
        this.timeStamp =timeStamp;
    }
    public CheckPoint(String idOrder,String checkPointName, String nameRider, String timeStamp, String typeName)
    {
        this.idOrder = idOrder;
        this.checkPointName = checkPointName;
        this.nameRider = nameRider;
        this.timeStamp = timeStamp;
        this.typeName = typeName;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNameRider() {
        return nameRider;
    }

    public void setNameRider(String nameRider) {
        this.nameRider = nameRider;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("checkPointName", checkPointName);
        result.put("latitude", latitude);
        result.put("longitude", longtitude);
        result.put("nameRider", nameRider);
        result.put("timeStamp", timeStamp);
        return result;
    }
}
