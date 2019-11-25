package com.example.kargobikeproject;

public class Order {

    //Variables about Order
    private int idClient;
    private int idOrder;
    private int idRider;
    private int idRoute;
    private String address;
    private String deliverStart;
    private String deliverEnd;
    private int orderStatus;


    //COnstructor
    public Order(int idOrder,int idClient, int idRider, int idRoute, String address, String deliverStart,
                 String deliverEnd, int orderStatus) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idRider = idRider;
        this.idRoute = idRoute;
        this.address = address;
        this.deliverStart = deliverStart;
        this.deliverEnd = deliverEnd;
        this.orderStatus = orderStatus;
    }


    //Getters and Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdRider() {
        return idRider;
    }

    public void setIdRider(int idRider) {
        this.idRider = idRider;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
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
}
