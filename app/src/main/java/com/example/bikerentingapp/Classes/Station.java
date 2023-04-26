package com.example.bikerentingapp.Classes;

import android.util.Pair;

public class Station {

    private int stationID;

    private int freeSpace;

    private int capacity;

    Pair<Double,Double> coordinates;

    public Station(int stationID, int freeSpace, int capacity, Pair<Double, Double> coordinates) {
        this.stationID = stationID;
        this.freeSpace = freeSpace;
        this.capacity = capacity;
        this.coordinates = coordinates;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Pair<Double, Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Pair<Double, Double> coordinates) {
        this.coordinates = coordinates;
    }

    public int getAvailableBikes(){
        return DatabaseConnection.getAvailableBikes().get(stationID-1);
    }

    public void getDamagedBikes(){

    }

    public void addBike(int bikeID) {

    }

    public void removeBike(int bikeID) {

    }

}
