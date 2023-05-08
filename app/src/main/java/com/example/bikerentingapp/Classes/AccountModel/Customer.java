package com.example.bikerentingapp.Classes.AccountModel;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.Wallet;

public class Customer extends Account {

    private static final String TYPE = "Customer";

    private Wallet wallet;

    private Hire hire;

    public Customer(int accountID, String email, String phoneNumber, double value) {
        super(accountID, email, phoneNumber);
        wallet = new Wallet(value);
        hire = null;
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


    public boolean rentBike(int bikeID) {

        Bike bike = DatabaseConnection.getBike(bikeID);
        if(bike.isAvailable())
        {
            hire = new Hire(bike, getAccountID());
            return true;
        }
        else
            return false;
    }


    public void reportAccident() {

    }


    public void makeComplaint() {

    }


    public void returnABike(int stationID) {

        hire.endHire(stationID);
    }


    public void updateFoundsInDB(){

    }

    public Hire getHire() {
        return hire;
    }
}
