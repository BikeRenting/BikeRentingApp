package com.example.bikerentingapp.Classes;

import android.app.Service;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;

public class UserHolder {

    private Account user;

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    private static UserHolder holder = new UserHolder();

    public static UserHolder getInstance(){
        return holder;
    }
}