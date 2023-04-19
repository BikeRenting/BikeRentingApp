package com.example.bikerentingapp.Classes;

import java.time.LocalDateTime;

public class Complaint {

    private int complaintID;

    private int customerID;

    private int hireID;

    private LocalDateTime dateOfFilling;

    private String description;

    private ComplaintType complaintType;

    public Complaint(int complaintID, int customerID, int hireID, LocalDateTime dateOfFilling, String description, ComplaintType complaintType) {
        this.complaintID = complaintID;
        this.customerID = customerID;
        this.hireID = hireID;
        this.dateOfFilling = dateOfFilling;
        this.description = description;
        this.complaintType = complaintType;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
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

    public LocalDateTime getDateOfFilling() {
        return dateOfFilling;
    }

    public void setDateOfFilling(LocalDateTime dateOfFilling) {
        this.dateOfFilling = dateOfFilling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }

    public enum ComplaintType{
        COSTS,
        BIKE_PROBLEM,
        STATION_PROBLEM,
    }
}
