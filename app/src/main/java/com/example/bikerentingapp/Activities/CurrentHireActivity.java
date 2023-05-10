package com.example.bikerentingapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.gson.Gson;

public class CurrentHireActivity extends AppCompatActivity{

    Customer customer;
    private Timer timer;
    private TextView lengthLabel;
    private TextView timeLabel;
    private boolean updateLength;
    private String time;
    private String length;
    private EditText text;
    private int endStation;

    private Random rand; // temp
    private int currentTime; // temp
    private int currentLength; // temp


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_hire);

        text = findViewById(R.id.enterStation);
        lengthLabel = findViewById(R.id.lengthLabel);
        timeLabel = findViewById(R.id.timeLabel);

        updateLength = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateLength = !updateLength;
                updateHire();
            }
        }, 0, 1000);

        rand = new Random();

        customer = (Customer) UserHolder.getInstance().getUser();
    }

    public void endHire(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz zakończyć wypożyczenie?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String selectedStation = text.getText().toString();
                        if(!selectedStation.isEmpty()) {
                            ArrayList<Integer> availableStations = DatabaseConnection.getAvailableStations();
                            if(availableStations.contains(Integer.parseInt(selectedStation))) {
                                endStation = Integer.parseInt(selectedStation);
                                timer.cancel();
                                dialog.cancel();
                                openSummaryActivity(view);
                            }
                            else {
                                Toast.makeText(view.getContext(),"Podano nieistniejącą stację.",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }
                        else {
                            Toast.makeText(view.getContext(),"Podaj numer stacji.",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }


                    }
                });


        builder.setNegativeButton(
                "Nie",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void updateHire() {
        currentTime++;
        currentLength+=(rand.nextInt(6) + 3);

        if((currentTime/60.0) > 1) {
            time = Integer.toString(currentTime/60) + "min " + Integer.toString(currentTime%60) + "s";
        }
        else
            time = Integer.toString(currentTime) + "s";

        if((currentLength/1000.0) > 1) {
            length = Integer.toString(currentLength/1000) + "." + Integer.toString(currentLength%100) + "km";
        }
        else
            length = Integer.toString(currentLength) + "m";

        timeLabel.setText(time);
        if(updateLength)
            lengthLabel.setText(length);
    }

    public void openSummaryActivity(View view) {

        double cost = Math.round((currentTime))/100.0;
        customer.getHire().setTime(currentTime);
        customer.getHire().setLength(currentLength);
        customer.getHire().setPayment(cost);
        customer.returnABike(endStation, customer.payForHire());
        Intent intent = new Intent(view.getContext(), ClientMenuActivity.class);
        startActivity(intent);
        finish();
    }

}
