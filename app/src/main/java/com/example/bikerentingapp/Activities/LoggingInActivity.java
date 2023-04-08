package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bikerentingapp.R;

public class LoggingInActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_in_activity);

    }

    public void openSignUpActivity(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void openClientMenuActivity(View view){
        Intent intent = new Intent(this, ClientMenuActivity.class);
        startActivity(intent);
    }

}