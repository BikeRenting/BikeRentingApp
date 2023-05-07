package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.R;

import java.sql.Connection;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoggingInActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_in_activity);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void openSignUpActivity(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void openClientMenuActivity(View view){

        Intent intent = new Intent(this, ClientMenuActivity.class);
        startActivity(intent);

    }

    public boolean passwordVerification()
    {
        // pobierz zahashowane haslo uzytkownika z bazy danych
        String bcryptHashString = "$2a$04$1XEFJqH8I8fKHHNwR11CrOpJFfD2rHvoc47gDHmp3Ck1rJSFyGp/O";

        // sprawdzenie czy sie zgadza z haslem podanym przy logowaniu
        BCrypt.Result result = BCrypt.verifyer().verify(password.toString().toCharArray(), bcryptHashString);

        return result.verified;
    }

}