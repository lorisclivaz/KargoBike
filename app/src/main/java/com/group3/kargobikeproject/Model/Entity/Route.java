package com.group3.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Route {

    //Variables about Route
    private String idRoute;
    private String startDestination;
    private String endDestination;


    //Constructor

    public Route()
    {

    }

    public Route(String startDestination, String endDestination)
    {
        this.startDestination = startDestination;
        this.endDestination = endDestination;
    }


    //Getters and setters
    @Exclude

    public String getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("startDestination", startDestination);
        result.put("endDestination", endDestination);

        return result;
    }
}
