package com.example.bikerentingapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.Reservation;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

public class SelectedReservationActivity extends AppCompatActivity {


    private TextView reservationNumber;
    private TextView reservationID;
    private TextView clientID;
    private TextView bikeID;
    private TextView startDate;
    private TextView endDate;
    private TextView status;
    private Button cancelReservation;

    private Reservation selectedReservation;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_reservation);

        reservationNumber = this.findViewById(R.id.reservationNumber);
        reservationID = this.findViewById(R.id.reservationIdText);
        clientID = this.findViewById(R.id.clientIDText);
        bikeID = this.findViewById(R.id.bikeIDText);
        startDate = this.findViewById(R.id.startDateText);
        endDate = this.findViewById(R.id.endDateText);
        status = this.findViewById(R.id.reservationStatusText);
        cancelReservation = this.findViewById(R.id.cancelReservationButton);


        Intent i = getIntent();
        selectedReservation = (Reservation) i.getSerializableExtra("selectedReservation");
        number = i.getIntExtra("reservationNumber", 0);

        reservationNumber.setText(String.valueOf(reservationNumber.getText()) + String.valueOf(number));
        reservationID.setText(String.valueOf(selectedReservation.getReservationID()));
        clientID.setText(String.valueOf(selectedReservation.getCustomerID()));
        bikeID.setText(String.valueOf(selectedReservation.getBikeID()));
        startDate.setText(selectedReservation.getStartDate());
        endDate.setText(selectedReservation.getEndDate());

        if (selectedReservation.isExecuted()) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_check_24, null);
            status.setText("zakończona");
            status.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            cancelReservation.setVisibility(View.GONE);
        } else {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_close_24, null);
            status.setText("w trakcie");
            status.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    public void cancelReservation(View view) {

        AlertDialog.Builder confirmationBuilder = new AlertDialog.Builder(this);
        confirmationBuilder.setMessage("Czy na pewno chcesz zakończyć aktualną rezerwację?");
        confirmationBuilder.setCancelable(true);

        confirmationBuilder.setPositiveButton(
                "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseConnection.updateReservation(selectedReservation.getReservationID(), 1);
                        DatabaseConnection.updateBikeStatus(selectedReservation.getBikeID(), 1);
                        selectedReservation.setExecuted(true);
                        dialog.cancel();
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(getApplicationContext(), "Rezerwacja została zakończona", Toast.LENGTH_SHORT).show();
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