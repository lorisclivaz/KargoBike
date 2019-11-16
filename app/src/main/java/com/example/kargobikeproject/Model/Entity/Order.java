package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Order {

    //Variables about order
    private String idOrder;
    private String idClient;
    private String idRider;
    private String idRoute;
    private String address;
    private String deliverStart;
    private String deliverEnd;
    private int orderStatus;


    //Constructor
    public Order(){

    }

    public Order(String address, String deliverStart, String deliverEnd, int orderStatus)
    {
        this.address = address;
        this.deliverStart = deliverStart;
        this.deliverEnd = deliverEnd;
        this.orderStatus = orderStatus;
    }


    //Getters and setters
    @Exclude
    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdRider() {
        return idRider;
    }

    public void setIdRider(String idRider) {
        this.idRider = idRider;
    }

    public String getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(String idRoute) {
        this.idRoute = idRoute;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverStart() {
        return deliverStart;
    }

    public void setDeliverStart(String deliverStart) {
        this.deliverStart = deliverStart;
    }

    public String getDeliverEnd() {
        return deliverEnd;
    }

    public void setDeliverEnd(String deliverEnd) {
        this.deliverEnd = deliverEnd;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("deliverStart", deliverStart);
        result.put("deliverEnd", deliverEnd);
        result.put("orderStatus", orderStatus);


        return result;
    }


}
