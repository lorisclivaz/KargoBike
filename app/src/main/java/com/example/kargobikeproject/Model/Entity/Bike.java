package com.example.kargobikeproject.Model.Entity;



import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class Bike {


    //Create variables about Bike
    private String idBike;
    private String modelName;
    private int bikeStatus;


    //Constructor
    public Bike(){

    }

    public Bike(String modelName, int bikeStatus)
    {
        this.modelName = modelName;
        this.bikeStatus = bikeStatus;
    }


    //Getters and setters
    @Exclude
    public String getIdBike(){return idBike;}

    public void setIdBike(String idBike){this.idBike = idBike;}

    public String getModelName(){return modelName;}

    public void setModelName(String modelName){this.modelName = modelName;}

    public int getBikeStatus(){return bikeStatus;}

    public void setBikeStatus(int bikeStatus){this.bikeStatus = bikeStatus;}


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("modelName", modelName);
        result.put("bikeStatus", bikeStatus);

        return result;
    }





}
