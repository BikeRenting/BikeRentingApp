package com.example.bikerentingapp.Classes.AccountModel;

import android.content.Context;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.Wallet;

import java.util.ArrayList;

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

        ArrayList<Integer> availableBikes = DatabaseConnection.getAvailableBikes();
        if(bikeID <= availableBikes.size() && availableBikes.get(bikeID-1) == 1)
        {
            Bike bike = DatabaseConnection.getBike(bikeID);
            if(bike.isAvailable())
            {
                hire = new Hire(bike, getAccountID());
                return true;
            }
            else
                return false;
        }
        else
            return false;

    }


    public void reportAccident() {

    }


    public void makeComplaint() {

    }


    public void returnABike(int stationID, boolean isPaid) {

        hire.endHire(stationID, isPaid);
        hire = null;
    }

    public boolean updateFunds(Context context, double income){
        if(DatabaseConnection.rechargeWallet(this.getAccountID(), getWallet().getFunds()+income)){
            wallet.addFunds(income);
            return true;
        }
        else
            return false;
    }

    public boolean payForHire(){
        double payment = hire.getPayment();
        double funds = wallet.getFunds();
        if(funds >= payment)
        {
            wallet.takeFunds(payment);
            DatabaseConnection.rechargeWallet(getAccountID(), wallet.getFunds());
            return true;
        }
        else {
            double remainingPayment = payment-funds;
            wallet.takeFunds(funds);
            DatabaseConnection.rechargeWallet(getAccountID(), 0);
            return false;
            //TODO send remainingPayment to database
        }
    }

    public Hire getHire() {
        return hire;
    }
}
