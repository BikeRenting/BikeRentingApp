package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.R;

public class ChangeBikeStatusActivity extends AppCompatActivity {

    private Bike selectedBike;
    private TextView bikeNumber;
    private EditText condition;
    private RadioGroup group;
    private RadioButton availableButton;
    private RadioButton unavailableButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bike_status);

        bikeNumber = findViewById(R.id.statusBikeNumber);
        condition = findViewById(R.id.statusChange);
        group = findViewById(R.id.radioGroup);
        availableButton = findViewById(R.id.availableButton);
        unavailableButton = findViewById(R.id.unavailableButton);

        selectedBike = (Bike) getIntent().getSerializableExtra("selectedBike");
        bikeNumber.setText("Rower nr " + selectedBike.getBikeID());
        condition.setText(selectedBike.getCondition());
        setCurrentAvailability();

    }

    private void setCurrentAvailability(){
        if(selectedBike.isAvailable()){
            availableButton.setChecked(true);
        }
        else
            unavailableButton.setChecked(true);

    }

    public void checkButton(View view){

    }
}