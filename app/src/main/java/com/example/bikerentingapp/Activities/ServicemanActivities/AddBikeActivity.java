package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Station;
import com.example.bikerentingapp.R;

public class AddBikeActivity extends AppCompatActivity {

    private EditText stationID;
    private EditText bikeAvailability;
    private EditText bikeCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        stationID = this.findViewById(R.id.id_stacji);
        bikeAvailability = this.findViewById(R.id.dostepnosc);
        bikeCondition = this.findViewById(R.id.stan_tech);
    }

    public void addBikeAndReturn(View View) {
        if (validate(stationID, bikeAvailability, bikeCondition)) {
            int id = Integer.parseInt(stationID.getText().toString());
            int isAvailable = Integer.parseInt(bikeAvailability.getText().toString());
            String condition = bikeCondition.getText().toString();

            Station station = DatabaseConnection.getStation(id);
            if (station == null) {
                Toast.makeText(getApplicationContext(), "Nie ma takiej stacji.", Toast.LENGTH_SHORT).show();
            } else {

                if (station.getFreeSpace() <= 0) {
                    Toast.makeText(getApplicationContext(), "Brak wolnych miejsc na tej stacji.", Toast.LENGTH_SHORT).show();
                } else {

                    if (DatabaseConnection.addBikeAndUpdate(condition, id, isAvailable, station.getFreeSpace() - 1)) {
                        Toast.makeText(getApplicationContext(), "Dodano rower do stacji.", Toast.LENGTH_SHORT).show();
                        stationID.setText("");
                        bikeAvailability.setText("");
                        bikeCondition.setText("");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean validate(EditText stationID, EditText bikeAvailability, EditText bikeCondition) {
        String id = stationID.getText().toString();
        String isAvailable = bikeAvailability.getText().toString();
        String condition = bikeCondition.getText().toString();

        if (id.equals("") || isAvailable.equals("") || condition.equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie dane", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isAvailable.equals("1") && !isAvailable.equals("0")) {
            Toast.makeText(getApplicationContext(), "Określ dostępność poprzez '1' lub '0' ", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}