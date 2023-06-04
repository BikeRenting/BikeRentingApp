package com.example.bikerentingapp.Classes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Complaint {

    private int customerID;

    private int hireID;

    private String dateOfFilling;

    private String description;

    private String complaintType;

    public Complaint(int customerID, int hireID, String description, String complaintType) {
        this.customerID = customerID;
        this.hireID = hireID;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        this.dateOfFilling = dtf.format(now);
        this.description = description;
        this.complaintType = complaintType;
    }

    public boolean makeComplaint(){
        return DatabaseConnection.makeComplaint(customerID, hireID, dateOfFilling, description, complaintType);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getHireID() {
        return hireID;
    }

    public void setHireID(int hireID) {
        this.hireID = hireID;
    }

    public String getDateOfFilling() {
        return dateOfFilling;
    }

    public void setDateOfFilling(String dateOfFilling) {
        this.dateOfFilling = dateOfFilling;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

}
