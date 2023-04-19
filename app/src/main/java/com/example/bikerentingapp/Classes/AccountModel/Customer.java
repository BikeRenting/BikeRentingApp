package com.example.bikerentingapp.Classes.AccountModel;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.Wallet;

public class Customer extends Account {

    private static final String TYPE = "Customer";

    private Wallet wallet;

    public Customer(int accountID, String email, String phoneNumber, double value) {
        super(accountID, email, phoneNumber);
        this.wallet = new Wallet(value);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    public void cancelReservation() {

    }


    public void bookBike() {

    }


    public  boolean rentBike() {
        return true;
    }


    public void reportAccident() {

    }


    public void makeComplaint() {

    }


    public void returnABike(int stationID) {

    }


    public void updateFoundsInDB(){

    }
}
