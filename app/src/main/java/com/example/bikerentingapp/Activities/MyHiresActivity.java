package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.MyHiresRecyclerAdapter;
import com.example.bikerentingapp.Classes.RecyclerViewClickListener;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MyHiresActivity extends AppCompatActivity {

    private ArrayList<Hire> myHires;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myhires_recycler_view);

        recyclerView = findViewById(R.id.myHiresRecyclerAdapter);
        setMyHires();
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
        MyHiresRecyclerAdapter adapter = new MyHiresRecyclerAdapter(myHires, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View V, int position) {
                Intent intent = new Intent(getApplicationContext(),SelectedHireActivity.class);
                intent.putExtra("hireNumber", position+1);
                intent.putExtra("selectedHire", (Serializable) myHires.get(position));
                startActivity(intent);
            }
        };
    }

    public void setMyHires(){
        myHires = DatabaseConnection.getUserHires(UserHolder.getInstance().getUser().getAccountID());

    }
}