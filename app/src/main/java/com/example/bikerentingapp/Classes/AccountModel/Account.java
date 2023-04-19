package com.example.bikerentingapp.Classes.AccountModel;

public class Account {

    private int accountID;

    private String email;

    private String phoneNumber;

    public Account(int accountID, String email, String phoneNumber) {
        this.accountID = accountID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void authorizeUser() {

    }


    public void signIn() {

    }


    public void signUp() {

    }


}
