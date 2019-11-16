package com.example.kargobikeproject.Model.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product {

    //Variables about Product
    private String idProduct;
    private String name;
    private int inStock;


    //Constructor
    public Product(){

    }

    public Product(String name, int inStock)
    {
        this.name = name;
        this.inStock = inStock;
    }


    //Getters and setters
    @Exclude

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }



    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("inStock", inStock);

        return result;
    }
}
