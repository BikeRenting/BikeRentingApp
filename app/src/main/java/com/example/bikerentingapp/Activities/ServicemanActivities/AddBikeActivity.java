package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.bikerentingapp.R;

public class AddBikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);
    }

    public void addBikeAndReturn(View View){
        finish();
    }
}