package com.example.bikerentingapp.Classes;

import android.provider.ContactsContract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private int reservationID;

    private int customerID;

    private int bikeID;

    private boolean executed;

    private String startDate;

    private String endDate;

    public Reservation(int reservationID, int customerID, int bikeID, boolean executed, String startDate, String endDate) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.bikeID = bikeID;
        this.executed = executed;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation(int customerID, int bikeID, long duration) {
        this.reservationID = DatabaseConnection.getLastReservationID();
        this.customerID = customerID;
        this.bikeID = bikeID;
        this.executed = false;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.startDate = dtf.format(now);
        LocalDateTime end = now.plusMinutes(duration);
        this.endDate = dtf.format(end);

        DatabaseConnection.addNewReservation(reservationID, customerID, bikeID, 0, startDate, endDate);
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private void endReservation() {
        DatabaseConnection.updateReservation(reservationID, 1);
        DatabaseConnection.updateBikeStatus(bikeID,  1);
    }
}
