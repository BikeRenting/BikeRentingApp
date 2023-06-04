package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

public class TripSummaryActivity extends AppCompatActivity {

    TextView tripDataLabel;
    TextView tripTimeLabel;
    TextView tripDistanceLabel;
    TextView tripCostLabel;

    LinearLayout toPayLayout;

    TextView toPayLabel;
    String date;
    String time;
    String distance;
    double cost;
    Customer user;
    int hire_id;
    int bike_id;
    double remainingPayment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tripDataLabel = (TextView)findViewById(R.id.tripDataLabel);
        tripTimeLabel = (TextView)findViewById(R.id.tripTimeLabel);
        tripDistanceLabel = (TextView)findViewById(R.id.tripDistanceLabel);
        tripCostLabel = (TextView)findViewById(R.id.tripCostLabel);
        toPayLayout = (LinearLayout) findViewById(R.id.toPayLayout);
        toPayLabel = (TextView) findViewById(R.id.toPayLabel);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            user = (Customer) UserHolder.getInstance().getUser();

            date = extras.getString("date");
            time = extras.getString("time");
            distance = extras.getString("distance");
            cost = extras.getDouble("cost");
            hire_id = extras.getInt("id_wypozyczenia");
            bike_id = extras.getInt("id_roweru");
            remainingPayment = DatabaseConnection.getUserHireByID(hire_id).getRemainingPayment();

            tripDataLabel.setText(date);
            tripTimeLabel.setText(time);
            tripDistanceLabel.setText(distance);
            tripCostLabel.setText(cost + " zł");

            if(remainingPayment > 0.0){
                toPayLabel.setText(remainingPayment + " zł");
            }else {
                toPayLayout.setVisibility(View.GONE);
            }

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
        intent.putExtra("id_wypozyczenia",hire_id);
        startActivity(intent);
    }

    public void reportFailure(View view){
        Intent intent = new Intent(view.getContext(), BikeFailureReportActivity.class);
        intent.putExtra("id_roweru",bike_id);
        startActivity(intent);
    }
}