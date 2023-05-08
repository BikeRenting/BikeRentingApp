package com.example.bikerentingapp.Classes;

import android.provider.ContactsContract;

public class Bike {

    private boolean isAvailable = true;

    private int bikeID;

    private int stationID;

    private String condition;

    public Bike(int bikeID, String condition, int stationID, boolean isAvailable) {
        this.isAvailable = isAvailable;
        this.bikeID = bikeID;
        this.stationID = stationID;
        this.condition = condition;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void changeStationID(int ID){

    }
}
