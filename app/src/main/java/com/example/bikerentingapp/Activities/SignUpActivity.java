package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.R;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, e_mail, phone_number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        username = findViewById(R.id.username);
        e_mail = findViewById(R.id.email);
        phone_number = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
    }

    public void registerAccount(View view){
        String mail = e_mail.getText().toString();
        String phone = phone_number.getText().toString();
        String passwd = password.getText().toString();
        String user = username.getText().toString();

        if (mail.equals("") || phone.equals("") || passwd.equals("") || user.equals("")){
            Toast.makeText(this,"Please enter all information!",Toast.LENGTH_SHORT).show();
        } else if (DatabaseConnection.ifExist(user, mail, phone)) {
            Toast.makeText(this,"This account already exists!",Toast.LENGTH_SHORT).show();
        } else if(DatabaseConnection.createAccount(user, mail, phone, BCrypt.withDefaults().hashToString(4, passwd.toCharArray()))) {
            Toast.makeText(this,"Your account created succesfully. Log in Now!",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
    }
}