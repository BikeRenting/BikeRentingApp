package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikerentingapp.Activities.LoggingInActivity;
import com.example.bikerentingapp.Activities.MapActivity;
import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.gson.Gson;

public class ServicemanMenuActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_KEY = "user_key";

    private boolean doubleTap;
    private Serviceman serviceman;
    private SharedPreferences sharedpreferences;
    private Gson gson;
    private String json;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_menu);

        gson = new Gson();
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        json = sharedpreferences.getString(USER_KEY, null);

        if (json != null) {
            serviceman = gson.fromJson(json, Serviceman.class);
            //username.setText(customer.getEmail());
        }

        UserHolder.getInstance().setUser(serviceman);
        serviceman = (Serviceman) UserHolder.getInstance().getUser();

        username = findViewById(R.id.technicianID);
        username.setText(serviceman.getUsername());

    }

    public void openMapActivity(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openAddBikeActivity(View view){
        Intent intent = new Intent(this, AddBikeActivity.class);
        startActivity(intent);
    }

    public void openRemoveBikeActivity(View view){
        Intent intent = new Intent(this, RemoveBikeActivity.class);
        startActivity(intent);
    }

    public void logOut(View view){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, LoggingInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (doubleTap) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Naciśnij jeszcze raz by wyjść z  aplikacji!", Toast.LENGTH_SHORT).show();
            doubleTap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTap = false;
                }
            }, 500);
        }
    }


}