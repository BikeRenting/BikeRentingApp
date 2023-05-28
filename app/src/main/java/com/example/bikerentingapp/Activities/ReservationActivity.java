package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bikerentingapp.Activities.ServicemanActivities.ChangeBikeStatusActivity;
import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.BikesInStationRecyclerAdapter;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.RecyclerViewClickListener;
import com.example.bikerentingapp.R;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity implements ReservationDialog.ReservationDialogListener {

    private ArrayList<Bike> bikes;
    private int stationNumber;
    private TextView station;
    private RecyclerView recyclerView;
    private Button reservationButton;

    private String reservationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_recycler_view);

        station = findViewById(R.id.stationNumber);

        stationNumber = Integer.parseInt(getIntent().getStringExtra("stationNumber"));
        station.setText("Stacja nr " + stationNumber);
        recyclerView = findViewById(R.id.bikesInStationRecyclerAdapter);
        reservationButton = this.findViewById(R.id.reservationButton);

        fillStations();
        setAdapter();
    }

    private void fillStations(){
        bikes = DatabaseConnection.getBikesAvailableInStation(stationNumber);
    }

    private void setAdapter(){
        BikesInStationRecyclerAdapter adapter = new BikesInStationRecyclerAdapter(bikes, null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void openReservationDialog(View view) {
        ReservationDialog reservationDialog = new ReservationDialog();
        reservationDialog.show(getSupportFragmentManager(), "Rezerwacja");
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillStations();
        setAdapter();
    }

    @Override
    public void applyReservationTime(String selectedTime) {
        reservationTime = selectedTime;
        makeReservation(reservationTime);
    }

    private void makeReservation(String time){

    }
}