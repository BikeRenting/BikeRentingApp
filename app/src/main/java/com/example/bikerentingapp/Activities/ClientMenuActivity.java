package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bikerentingapp.R;

public class ClientMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu_activity);
    }

    public void openMapActivity(View view){

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}