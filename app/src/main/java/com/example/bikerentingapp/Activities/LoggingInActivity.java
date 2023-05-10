package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Activities.ServicemanActivities.ServicemanMenuActivity;
import com.example.bikerentingapp.Classes.AccountModel.Account;
import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.AccountModel.Serviceman;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoggingInActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String USER_KEY = "user_key";

    private EditText username, password;
    private SharedPreferences sharedpreferences;
    private String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_in_activity);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        usr = sharedpreferences.getString(USER_KEY, null);
    }

    public void openSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void openClientMenuActivity(View view) {
        String user = username.getText().toString();
        String pswd = password.getText().toString();
        ResultSet rs;
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (user.equals("") || pswd.equals("")) {
            Toast.makeText(this, "Please enter all information!", Toast.LENGTH_SHORT).show();
        } else {
            rs = DatabaseConnection.getCustomer(user);
            try {
                if (rs.next() == true) {
                    if (passwordVerification(rs.getString(6))) {
                        int id = rs.getInt(1);
                        String email = rs.getString(2);
                        String number = rs.getString(3);
                        double wallet = rs.getFloat(4);

                        Customer customer = new Customer(id, email, number, wallet);
                        UserHolder.getInstance().setUsername(user);

                        Gson gson = new Gson();
                        String json = gson.toJson(customer);

                        editor.putString(USER_KEY, json);
                        editor.apply();

                        Intent intent = new Intent(LoggingInActivity.this, ClientMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    rs = DatabaseConnection.getServiceman(user);
                    if (rs.next() == true) {
                        if (passwordVerification(rs.getString(5))) {
                            int id = rs.getInt(1);
                            String email = rs.getString(2);
                            String number = rs.getString(3);

                            Serviceman serviceman = new Serviceman(id, email, number);
                            UserHolder.getInstance().setUser(serviceman);

                            Gson gson = new Gson();
                            String json = gson.toJson(serviceman);

                            editor.putString(USER_KEY, json);
                            editor.apply();

                            Intent intent = new Intent(LoggingInActivity.this, ServicemanMenuActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "This account does not exist!", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean passwordVerification(String bcryptHashString) {
        // sprawdzenie czy sie zgadza z haslem podanym przy logowaniu
        BCrypt.Result result = BCrypt.verifyer().verify(password.getText().toString().toCharArray(), bcryptHashString);

        return result.verified;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (usr != null) {
            Intent intent = new Intent(LoggingInActivity.this, ClientMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}