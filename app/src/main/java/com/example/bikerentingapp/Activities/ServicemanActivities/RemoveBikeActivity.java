package com.example.bikerentingapp.Activities.ServicemanActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.Bike;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.R;

import java.util.ArrayList;

public class RemoveBikeActivity extends AppCompatActivity {

    private EditText bikeID;
    private Bike bikeToRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_bike);

        bikeID = findViewById(R.id.bikeToRemove);
    }

    public void removeBikeClickButton(View View) {

        if (bikeID.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Nie podano ID roweru", Toast.LENGTH_SHORT).show();
        } else if ((bikeToRemove = DatabaseConnection.getBike(Integer.parseInt(bikeID.getText().toString()))) == null) {
            Toast.makeText(getApplicationContext(), "Nie ma takiego roweru", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.confirm_station_dialog, null);
            EditText station = view.findViewById(R.id.removeBike);
            Button submit = view.findViewById(R.id.remove);
            builder.setView(view);
            AlertDialog dialog = builder.create();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int stationID;
                    if (station.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Nie podano ID stacji", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else if ((stationID = Integer.parseInt(station.getText().toString())) != bikeToRemove.getStationID()) {
                        Toast.makeText(getApplicationContext(), "Nieprawidłowy numer stacji", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        removeConfirmation(stationID);
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }
    }

    private void removeConfirmation(int stationID){

        AlertDialog.Builder confirmationBuilder = new AlertDialog.Builder(this);
        confirmationBuilder.setMessage("Czy na pewno chcesz usunąć ten rower?");
        confirmationBuilder.setCancelable(true);

        confirmationBuilder.setPositiveButton(
                "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(DatabaseConnection.removeBike(bikeToRemove.getBikeID())){
                            DatabaseConnection.incrementFreeSpace(stationID);
                            Toast.makeText(getApplicationContext(),"Rower usunięty z systemu",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });

        confirmationBuilder.setNegativeButton(
                "Nie",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog confirm = confirmationBuilder.create();
        confirm.show();
    }
}