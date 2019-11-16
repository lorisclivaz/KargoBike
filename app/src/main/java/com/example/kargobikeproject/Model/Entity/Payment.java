package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Payment {

    //Variables about payment
    private String idPayment;
    private String idOrder;
    private double amount;
    private String paymentType;
    private String dateReceived;
    private int paymentStatus;


    //Constructor
    public Payment(){

    }


    public Payment(double amount, String paymentType, String dateReceived, int paymentStatus)
    {
        this.amount = amount;
        this.paymentType = paymentType;
        this.dateReceived = dateReceived;
        this.paymentStatus = paymentStatus;
    }


    //Getters and setters

    @Exclude

    public String getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("paymentType", paymentType);
        result.put("dateReceived", dateReceived);
        result.put("paymentStatus", paymentStatus);


        return result;
    }
}
