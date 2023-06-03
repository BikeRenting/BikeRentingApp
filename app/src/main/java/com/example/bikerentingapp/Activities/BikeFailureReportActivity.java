package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.DamageReport;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

public class BikeFailureReportActivity extends AppCompatActivity {

    private EditText bikeID;
    private EditText descriptionEdittext;
    private int ID;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_failure_report);

        bikeID = findViewById(R.id.bikeID);
        descriptionEdittext = findViewById(R.id.reportDescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("id_roweru");
            bikeID.setText(String.valueOf(ID));
        }
    }

    public void confirmFailureReport(View view){
        if(bikeID.getText().toString().equals("")||descriptionEdittext.getText().toString().equals("")){
            Toast.makeText(this,"Uzupełnij wszystkie informacje",Toast.LENGTH_SHORT).show();
        }
        else {
            ID = Integer.parseInt(bikeID.getText().toString());
            description = descriptionEdittext.getText().toString();
            if(!DatabaseConnection.getBikesID().contains(ID)){
                Toast.makeText(this,"Nie ma takiego roweru",Toast.LENGTH_SHORT).show();
            }else {
                DamageReport report = new DamageReport(UserHolder.getInstance().getUser().getAccountID(),ID,description);if(report.reportDamage()){
                    Toast.makeText(this, "Awaria zgłoszona pomyślnie", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                }
            }
    }

}