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
import android.widget.Toast;

import com.example.bikerentingapp.Activities.ServicemanActivities.ChangeBikeStatusActivity;
import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.BikesInStationRecyclerAdapter;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.RecyclerViewClickListener;
import com.example.bikerentingapp.Classes.Reservation;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

import java.util.ArrayList;
import java.util.Random;

public class ReservationActivity extends AppCompatActivity implements ReservationDialog.ReservationDialogListener {

    private ArrayList<Bike> bikes;
    private int stationNumber;
    private TextView station;
    private RecyclerView recyclerView;
    private Button reservationButton;

    private long reservationTime;

    private Customer customer;

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

        customer = (Customer) UserHolder.getInstance().getUser();
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
    public void applyReservationTime(long duration) {
        reservationTime = duration;
        if(!customer.hasAnyReservation())
            makeReservation(reservationTime);
        else{
            Toast.makeText(this,"Posiadasz już aktywną rezerwację", Toast.LENGTH_LONG).show();
        }
    }

    private void makeReservation(long duration){
        Random rand = new Random();
        Bike bike = bikes.get(rand.nextInt(bikes.size()));
        Reservation reservation = new Reservation(customer.getAccountID(), bike.getBikeID(), duration);
        DatabaseConnection.updateBike(bike.getBikeID(), bike.getCondition(), bike.getStationID(), 0);
        finish();
        startActivity(getIntent());
        Toast.makeText(this,"Udało ci się zarezerwować rower. ID twojego roweru to: " + String.valueOf(bike.getBikeID()), Toast.LENGTH_LONG).show();
    }
}