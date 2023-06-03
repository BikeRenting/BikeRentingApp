package com.example.bikerentingapp.Classes.AccountModel;

import android.content.Context;
import android.util.Pair;

import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.Reservation;
import com.example.bikerentingapp.Classes.Wallet;

import java.util.ArrayList;

public class Customer extends Account {

    private static final String TYPE = "Customer";

    private Wallet wallet;

    private Hire hire;

    public Customer(String username, int accountID, String email, String phoneNumber, double value) {
        super(username, accountID, email, phoneNumber);
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

        ArrayList<Reservation> reservations = DatabaseConnection.getUserReservations(getAccountID());
        boolean hasReservation = false;
        int reservationID = 0;

        for(Reservation r : reservations) {
            if(!r.isExecuted() && r.getBikeID() == bikeID){
                hasReservation = true;
                reservationID = r.getReservationID();
                break;
            }
        }

        if(DatabaseConnection.isBikeAvailable(bikeID) || hasReservation)
        {
            Bike bike = DatabaseConnection.getBike(bikeID);
            hire = new Hire(bike, getAccountID());
            DatabaseConnection.incrementFreeSpace(bike.getStationID());

            if(hasReservation)
                DatabaseConnection.updateReservation(reservationID, 1);
            return true;
        }
        else
            return false;

    }


    public void reportAccident() {

    }


    public void makeComplaint() {

    }


    public void returnABike(int stationID, double remainingPayment) {

        int isPaid = remainingPayment > 0 ? 0 : 1;
        hire.endHire(stationID, isPaid, remainingPayment);
        hire = null;
        DatabaseConnection.decrementFreeSpace(stationID);
    }

    public boolean updateFunds(Context context, double income){
        if(DatabaseConnection.rechargeWallet(this.getAccountID(), getWallet().getFunds()+income)){
            wallet.addFunds(income);
            return true;
        }
        else
            return false;
    }

    public double payForHire(){
        double remainingPayment = 0;
        double payment = hire.getPayment();
        double funds = wallet.getFunds();
        if(funds >= payment)
        {
            wallet.takeFunds(payment);
            DatabaseConnection.rechargeWallet(getAccountID(), wallet.getFunds());
        }
        else {
            remainingPayment = Math.round((payment-funds) * 100.0) / 100.0;
            wallet.takeFunds(funds);
            DatabaseConnection.rechargeWallet(getAccountID(), 0);
        }
        return remainingPayment;
    }

    public double payForRemainingPayment(double remainingPayment){
        double payment = hire.getPayment();
        double funds = wallet.getFunds();
        if(funds >= payment)
        {
            wallet.takeFunds(payment);
            DatabaseConnection.rechargeWallet(getAccountID(), wallet.getFunds());
        }
        else {
            remainingPayment = payment-funds;
            wallet.takeFunds(funds);
            DatabaseConnection.rechargeWallet(getAccountID(), 0);
        }
        return remainingPayment;
    }

    public Hire getHire() {
        return hire;
    }

    public boolean hasAnyUnpaidHire(){
        ArrayList<Hire> hires = DatabaseConnection.getUserHires(this.getAccountID());

        for(Hire h : hires) {
            if(!h.isPaymentRealized())
                return true;
        }
        return false;
    }

    public boolean hasAnyReservation(){
        ArrayList<Reservation> reservations = DatabaseConnection.getUserReservations(this.getAccountID());

        for(Reservation r : reservations) {
            if(!r.isExecuted())
                return true;
        }
        return false;
    }
}
