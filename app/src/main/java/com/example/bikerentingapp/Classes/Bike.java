package com.example.bikerentingapp.Classes;

public class Bike {

    private boolean isAvailable;

    private int bikeID;

    private int stationID;

    private String condition;

    public Bike(boolean isAvailable, int bikeID, int stationID, String condition) {
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
