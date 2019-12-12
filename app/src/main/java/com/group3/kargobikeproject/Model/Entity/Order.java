package com.group3.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Order {

    //Variables about order
    private String idOrder;
    private String nameClient;
    private String nameRider;
    private String nameRoute;
    private String address;
    private String deliverStart;
    private String deliverEnd;
    private String orderStatus;


    public Order()
    {

    }

    public Order(String idOrder, String nameClient, String nameRider, String nameRoute, String address, String deliverStart, String deliverEnd, String orderStatus)
    {
        this.idOrder = idOrder;
        this.nameClient = nameClient;
        this.nameRider = nameRider;
        this.nameRoute = nameRoute;
        this.address = address;
        this.deliverStart = deliverStart;
        this.deliverEnd = deliverEnd;
        this.orderStatus = orderStatus;
    }

    public Order(String nameClient, String nameRider, String nameRoute, String address, String deliverStart, String deliverEnd, String orderStatus)
    {
        this.nameClient = nameClient;
        this.nameRider = nameRider;
        this.nameRoute = nameRoute;
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

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameRider() {
        return nameRider;
    }

    public void setNameRider(String nameRider) {
        this.nameRider = nameRider;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nameClient", nameClient);
        result.put("nameRider", nameRider);
        result.put("nameRoute", nameRoute);
        result.put("address", address);
        result.put("deliverStart", deliverStart);
        result.put("deliverEnd", deliverEnd);
        result.put("orderStatus", orderStatus);


        return result;
    }


}
