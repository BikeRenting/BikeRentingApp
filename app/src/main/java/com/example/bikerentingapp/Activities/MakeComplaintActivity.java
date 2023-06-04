package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.Complaint;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;

public class MakeComplaintActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText hireID;
    private EditText descriptionEditText;
    private int ID;
    private String selectedType;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complaint);

        spinner = findViewById(R.id.spinner);
        hireID = findViewById(R.id.hireID);
        descriptionEditText = findViewById(R.id.complaintDescription);

        String[] complaintTypes = {"Niewłaściwy stan roweru", "Niewłaściwe naliczanie opłat", "Problem z zamkiem rowerowym", "Błędne informacje o stacji", "Inne"};
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spiner_item, complaintTypes);
        spinner.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("id_wypozyczenia");
            hireID.setText(String.valueOf(ID));
        }

    }

    public void confirmComplaint(View view) {

        if (hireID.getText().toString().equals("") || descriptionEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Uzupełnij wszystkie informacje", Toast.LENGTH_SHORT).show();
        } else {
            ID = Integer.parseInt(hireID.getText().toString());
            description = descriptionEditText.getText().toString();

            if (!DatabaseConnection.getUserHiresID(UserHolder.getInstance().getUser().getAccountID()).contains(ID)) {
                Toast.makeText(this, "Nie posiadasz wypożyczenia o ID: " + ID, Toast.LENGTH_SHORT).show();
            }
            else{
                Complaint complaint = new Complaint(UserHolder.getInstance().getUser().getAccountID(), ID, description, selectedType);
                if(complaint.makeComplaint()){
                    Toast.makeText(this, "Reklamacja złożona pomyślnie", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            selectedType = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}