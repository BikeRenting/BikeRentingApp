package com.example.bikerentingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.MyReservationsRecyclerAdapter;
import com.example.bikerentingapp.Classes.RecyclerViewClickListener;
import com.example.bikerentingapp.Classes.Reservation;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MyReservationsActivity extends AppCompatActivity {

    private ArrayList<Reservation> myReservations;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreservations_recycler_view);

        recyclerView = findViewById(R.id.myReservationsRecyclerAdapter);
        setMyReservations();
        setAdapter();
    }
    @Override
    public void onRestart() {
        super.onRestart();

        finish();
        startActivity(getIntent());
    }
    private void setAdapter(){
        setOnClickListener();
        MyReservationsRecyclerAdapter adapter = new MyReservationsRecyclerAdapter(myReservations, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View V, int position) {
                Intent intent = new Intent(getApplicationContext(),SelectedReservationActivity.class);
                intent.putExtra("reservationNumber", position+1);
                intent.putExtra("selectedReservation", (Serializable) myReservations.get(position));
                startActivity(intent);
            }
        };
    }

    public void setMyReservations(){
        myReservations = DatabaseConnection.getUserReservations(UserHolder.getInstance().getUser().getAccountID());
    }
}