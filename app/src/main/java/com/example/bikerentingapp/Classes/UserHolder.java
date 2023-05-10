package com.example.bikerentingapp.Classes;

import android.app.Service;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;

public class UserHolder {

    private String username;
    private Account user;

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    private static UserHolder holder = new UserHolder();

    public static UserHolder getInstance(){
        return holder;
    }
}