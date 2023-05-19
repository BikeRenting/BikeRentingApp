package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.BikesInStationRecyclerAdapter;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.RecyclerViewClickListener;
import com.example.bikerentingapp.R;

import java.util.ArrayList;

public class BikesInStationActivity extends AppCompatActivity {

    private ArrayList<Bike> bikes;
    private int stationNumber;
    private TextView station;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bikes_in_station_recycler_view);
        station = findViewById(R.id.stationNumber);

        stationNumber = Integer.parseInt(getIntent().getStringExtra("stationNumber"));
        station.setText("Stacja nr " + stationNumber);
        recyclerView = findViewById(R.id.bikesInStationRecyclerAdapter);

        fillStations();
        setAdapter();
    }

    private void fillStations(){
        bikes = DatabaseConnection.getBikesInStation(stationNumber);
    }

    private void setAdapter(){
        setOnClickListener();
        BikesInStationRecyclerAdapter adapter = new BikesInStationRecyclerAdapter(bikes, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View V, int position) {
                Intent intent = new Intent(getApplicationContext(),ChangeBikeStatusActivity.class);
                intent.putExtra("selectedBike",bikes.get(position));
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillStations();
        setAdapter();
    }
}