package com.example.bikerentingapp.Classes;

import android.provider.ContactsContract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Hire {

    private int hireID;

    private int customerID;

    private Bike bike;

    private int time;

    private int length;

    private double payment;

    private boolean paymentRealized = false;

    private String startDate;

    private Random rand;

    public Hire(int hireID, int customerID, Bike bike, int time, int length, double payment, boolean paymentRealized) {
        this.hireID = hireID;
        this.customerID = customerID;
        this.bike = bike;
        this.time = time;
        this.length = length;
        this.payment = payment;
        this.paymentRealized = paymentRealized;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.startDate = dtf.format(now);
        rand = new Random();
    }

    public Hire(Bike b, int customerID) {
        setHireID(DatabaseConnection.getLastHireID());
        this.customerID = customerID;
        setBike(b);
        payment = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.startDate = dtf.format(now);
        rand = new Random();

        time = 0;
        length = 0;

        DatabaseConnection.addNewHire(hireID, time, length, payment, bike.getBikeID(), startDate, paymentRealized ? 1 : 0, customerID);
        DatabaseConnection.updateBike(bike.getBikeID(), bike.getCondition(), bike.getStationID(), 0);
    }

    public int getHireID() {
        return hireID;
    }

    public void setHireID(int hireID) {
        this.hireID = hireID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public boolean isPaymentRealized() {
        return paymentRealized;
    }

    public void setPaymentRealized(boolean paymentRealized) {
        this.paymentRealized = paymentRealized;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void endHire(int stationID, boolean isPaid){

        DatabaseConnection.updateHire(hireID, time, length, payment, isPaid);
        DatabaseConnection.updateBike(bike.getBikeID(), bike.getCondition(), stationID, 1);
    }

    public void update() {
        time++;
        length+=(rand.nextInt(6) + 3);
    }

}
