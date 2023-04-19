package com.example.bikerentingapp.Classes;

public class Wallet {

    private double funds;

    public Wallet(double funds) {
        this.funds = funds;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void addFunds(double f){
        this.funds += f;
    }


    public void takeFunds(double f) {
        this.funds -= f;

    }

    public void showFunds() {

    }
}
