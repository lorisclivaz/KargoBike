package com.example.kargobikeproject;

public class Order {

    private int idClient;
    private int idRider;
    private String nameOrder;


    public Order(int idClient, int idRieder, String nameOrder) {
        this.idClient = idClient;
        this.idRider = idRieder;
        this.nameOrder = nameOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRider() {
        return idRider;
    }

    public void setIdRieder(int idRieder) {
        this.idRider = idRieder;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }
}
