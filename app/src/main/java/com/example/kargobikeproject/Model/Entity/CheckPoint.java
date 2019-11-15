package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CheckPoint {

    //Create variable about CheckPoint
    private String idCheckPoint;
    private String idRoute;
    private String checkPointName;


    //Constructor
    public CheckPoint(){

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

    public String getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("checkPointName", checkPointName);

        return result;
    }
}
