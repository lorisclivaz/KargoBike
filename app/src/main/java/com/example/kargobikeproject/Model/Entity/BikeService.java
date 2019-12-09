package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class BikeService {


    //Create variables about BikeService
    private String idService;
    private String idRider;
    private String createdAt;

    //Constructor
    public BikeService(){

    }

    public BikeService(String createdAt, String idRider)
    {
        this.createdAt = createdAt;
        this.idRider = idRider;
    }



    //Getters and setters

    @Exclude
    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIdRider() {
        return idRider;
    }

    public void setIdRider(String idRider) {
        this.idRider = idRider;
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
        result.put("createdAt", createdAt);
        result.put("idRider", idRider);
        return result;
    }

}
