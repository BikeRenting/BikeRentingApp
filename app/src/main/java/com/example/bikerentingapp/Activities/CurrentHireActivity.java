package com.example.bikerentingapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bikerentingapp.R;

public class CurrentHireActivity extends AppCompatActivity{
    private Timer timer;
    private TextView lengthLabel;
    private TextView timeLabel;
    private boolean updateLength;
    private String time;
    private String length;
    private EditText text;
    private int endStation;

    private Random rand; // temp
    private int currentTime; // temp
    private int currentLength; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_hire);
        text = findViewById(R.id.enterStation);
        lengthLabel = findViewById(R.id.lengthLabel);
        timeLabel = findViewById(R.id.timeLabel);

        updateLength = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateLength = !updateLength;
                updateHire();
            }
        }, 0, 1000);

        rand = new Random();
    }

    public void endHire(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz zakończyć wypożyczenie?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String selectedStation = text.getText().toString();
                        timer.cancel();
                        dialog.cancel();
                        Intent intent = new Intent(view.getContext(), ClientMenuActivity.class);
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton(
                "Nie",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void updateHire() {
        currentTime++;
        currentLength+=(rand.nextInt(6) + 3);

        if((currentTime/60.0) > 1) {
            time = Integer.toString(currentTime/60) + "min " + Integer.toString(currentTime%60) + "s";
        }
        else
            time = Integer.toString(currentTime) + "s";

        if((currentLength/1000.0) > 1) {
            length = Integer.toString(currentLength/1000) + "." + Integer.toString(currentLength%100) + "km";
        }
        else
            length = Integer.toString(currentLength) + "m";

        timeLabel.setText(time);
        if(updateLength)
            lengthLabel.setText(length);
    }

}
