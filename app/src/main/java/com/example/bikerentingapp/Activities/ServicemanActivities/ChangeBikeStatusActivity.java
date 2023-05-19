package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.R;

import java.util.Locale;

public class ChangeBikeStatusActivity extends AppCompatActivity {

    private Bike selectedBike;
    private TextView bikeNumber;
    private EditText condition;
    private RadioGroup group;
    private RadioButton availableButton;
    private RadioButton unavailableButton;

    private String currentCondition;
    private int currentAvailability;
    private String newCondition;
    private int newAvailability;


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

        currentCondition = selectedBike.getCondition();
        condition.setText(selectedBike.getCondition());

        currentAvailability = selectedBike.isAvailable() ? 1 : 0;

        setCurrentAvailability();
    }

    private void setCurrentAvailability() {
        if (selectedBike.isAvailable()) {
            availableButton.setChecked(true);
        } else {
            unavailableButton.setChecked(true);
        }
    }

    public void changeStatus(View view) {
        newCondition = condition.getText().toString().toLowerCase(Locale.ROOT);
        if (availableButton.isChecked()) {
            newAvailability = 1;
        }
        if (unavailableButton.isChecked()) {
            newAvailability = 0;
        }

        if (!newCondition.equals(currentCondition) || !(newAvailability == currentAvailability)) {
            if (DatabaseConnection.updateBike(selectedBike.getBikeID(), newCondition, selectedBike.getStationID(), newAvailability)) {
                Toast.makeText(getApplicationContext(), "Status roweru zmieniony", Toast.LENGTH_SHORT).show();
                finish();
            }else
                Toast.makeText(getApplicationContext(), "Wystąpił błąd", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Nie wprowadzono żadnych zmian", Toast.LENGTH_SHORT).show();

    }

    public void checkButton(View view) {

    }
}