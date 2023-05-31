package com.example.bikerentingapp.Classes.AccountModel;

public class Account {

    private String username;

    private int accountID;

    private String email;

    private String phoneNumber;

    public Account(String username, int accountID, String email, String phoneNumber) {
        this.username = username;
        this.accountID = accountID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
