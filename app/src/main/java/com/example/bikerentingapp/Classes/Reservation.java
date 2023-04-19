package com.example.bikerentingapp.Classes;

import java.time.LocalDateTime;

public class Reservation {

    private int reservationID;

    private int customerID;

    private int bikeID;

    private boolean executed;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Reservation(int reservationID, int customerID, int bikeID, boolean executed, LocalDateTime startDate, LocalDateTime endDate) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.bikeID = bikeID;
        this.executed = executed;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
