package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bikerentingapp.R;

public class MakeComplaintActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText hireID;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complaint);

        spinner = findViewById(R.id.spinner);
        hireID = findViewById(R.id.hireID);

        String[] complaintTypes = {"Wypożyczenie", "Rower", "Stacja", "Inne"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spiner_item, complaintTypes);
        spinner.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("id_wypozyczenia");
            hireID.setText(String.valueOf(ID));
        }
        
    }

    public void confirmComplaint(View view) {
        finish();
    }
}