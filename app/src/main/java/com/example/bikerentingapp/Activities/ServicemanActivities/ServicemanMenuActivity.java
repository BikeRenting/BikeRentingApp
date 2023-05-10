package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.gson.Gson;

public class ServicemanMenuActivity extends AppCompatActivity {

    private Serviceman serviceman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_menu);

        serviceman = (Serviceman) UserHolder.getInstance().getUser();

    }

    public void openMapActivity(View view){

    }

    public void openAddBikeActivity(View view){
        Intent intent = new Intent(this, AddBikeActivity.class);
        startActivity(intent);
    }
}