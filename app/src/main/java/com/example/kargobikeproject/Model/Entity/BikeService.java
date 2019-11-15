package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class BikeService {

    //Create variables about BikeService
    private String idRider;
    private String idBike;
    private int statusReport;
    private String createdAt;

    //Constructor
    public BikeService(){

    }

    public BikeService( int statusReport, String createdAt)
    {

        this.statusReport = statusReport;
        this.createdAt = createdAt;
    }



    //Getters and setters
    @Exclude
    public String getIdRider() {
        return idRider;
    }

    public void setIdRider(String idRider) {
        this.idRider = idRider;
    }

    public String getIdBike() {
        return idBike;
    }

    public void setIdBike(String idBike) {
        this.idBike = idBike;
    }

    public int getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(int statusReport) {
        this.statusReport = statusReport;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("statusReport", statusReport);
        result.put("createdAt", createdAt);

        return result;
    }

}
