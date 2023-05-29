package com.example.bikerentingapp.Classes;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Hire implements Serializable {

    private int hireID;

    private int customerID;

    private Bike bike;

    private int time;

    private int length;

    private double payment;

    private boolean paymentRealized = false;

    private double remainingPayment = 0.0;

    private String startDate;

    private Random rand;




    public Hire(int hireID, int customerID, Bike bike, int time, int length, double payment) {
        this.hireID = hireID;
        this.customerID = customerID;
        this.bike = bike;
        this.time = time;
        this.length = length;
        this.payment = payment;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.startDate = dtf.format(now);
        rand = new Random();
    }

    public Hire(int hireID, int customerID, Bike bike, int time, int length, double payment, boolean paymentRealized,String startDate, double remainingpayment) {
        this(hireID,customerID,bike,time,length,payment);
        this.paymentRealized = paymentRealized;
        this.startDate = startDate;
        this.remainingPayment = remainingpayment;
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

        DatabaseConnection.addNewHire(hireID, time, length, payment, bike.getBikeID(), startDate, paymentRealized ? 1 : 0, customerID, 0.0);
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

    public double getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(double remainingpayment) {
        this.remainingPayment = remainingpayment;
    }

    public String getStartDate() {
        String str = startDate.substring(0, startDate.length()-2);
        return str;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void endHire(int stationID, int isPaid, double remainingPayment){

        DatabaseConnection.updateHire(hireID, time, length, payment, isPaid, remainingPayment);
        DatabaseConnection.updateBike(bike.getBikeID(), bike.getCondition(), stationID, 1);
    }

    public void update() {
        time++;
        length+=(rand.nextInt(6) + 3);
    }

    public static int timeToInt(Time time){

        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();

        return hours * 3600 + minutes * 60 + seconds;
    }

    public static LocalTime intToTime(int timeInSeconds){
        Duration duration = Duration.ofSeconds(timeInSeconds);
        return LocalTime.MIDNIGHT.plus(duration);
    }

}
