package com.example.bikerentingapp.Classes;

import java.time.LocalDateTime;
import java.util.Random;

public class Hire {

    private int hireID;

    private int customerID;

    private Bike bike;

    private int time;

    private int length;

    private double payment;

    private boolean paymentRealized;

    private LocalDateTime startDate;

    private Random rand;

    public Hire(int hireID, int customerID, Bike bike, int time, int length, double payment, boolean paymentRealized) {
        this.hireID = hireID;
        this.customerID = customerID;
        this.bike = bike;
        this.time = time;
        this.length = length;
        this.payment = payment;
        this.paymentRealized = paymentRealized;
        this.startDate = LocalDateTime.now();
        rand = new Random();
    }

    public Hire(int bikeID) {
        this.hireID = hireID;
        this.customerID = customerID;
        this.bike = bike;
        this.time = time;
        this.length = length;
        this.payment = payment;
        this.paymentRealized = paymentRealized;
        this.startDate = LocalDateTime.now();
        rand = new Random();

        time = 0;
        length = 0;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void endHire(){

    }

    public void update() {
        time++;
        length+=(rand.nextInt(6) + 3);
    }

}
