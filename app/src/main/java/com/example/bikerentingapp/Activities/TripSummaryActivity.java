package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

public class TripSummaryActivity extends AppCompatActivity {

    TextView tripDataLabel;
    TextView tripTimeLabel;
    TextView tripDistanceLabel;
    TextView tripCostLabel;
    String date;
    String time;
    String distance;
    double cost;
    Customer user;
    Integer hire_id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tripDataLabel = (TextView)findViewById(R.id.tripDataLabel);
        tripTimeLabel = (TextView)findViewById(R.id.tripTimeLabel);
        tripDistanceLabel = (TextView)findViewById(R.id.tripDistanceLabel);
        tripCostLabel = (TextView)findViewById(R.id.tripCostLabel);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            user = (Customer) UserHolder.getInstance().getUser();

            date = extras.getString("date");
            time = extras.getString("time");
            distance = extras.getString("distance");
            cost = extras.getDouble("cost");
            hire_id = extras.getInt("id_wypozyczenia");

            tripDataLabel.setText(date);
            tripTimeLabel.setText(time);
            tripDistanceLabel.setText(distance);
            tripCostLabel.setText(cost + " zł");

        }
    }

    public void returnToMainMenu(View view) {
        Intent intent = new Intent(view.getContext(), ClientMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void makeAComplaint(View view) {
        Intent intent = new Intent(view.getContext(), MakeComplaintActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("id_wypozyczenia",hire_id);
        startActivity(intent);
    }

    public void reportFailure(View view){
        Intent intent = new Intent(view.getContext(), BikeFailureReportActivity.class);
        startActivity(intent);
    }
}