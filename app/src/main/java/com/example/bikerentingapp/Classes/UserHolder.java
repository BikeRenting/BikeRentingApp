package com.example.bikerentingapp.Classes;

import android.app.Service;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;

public class UserHolder {
    private String username;
    private Customer customer;
    private Serviceman serviceman;

    public Customer getCustomer(){
        return customer;
    }

    public Serviceman getServiceman(){
        return serviceman;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setServiceman(Serviceman serviceman){
        this.serviceman = serviceman;
    }

    private static UserHolder holder = new UserHolder();

    public static UserHolder getInstance(){
        return holder;
    }
}