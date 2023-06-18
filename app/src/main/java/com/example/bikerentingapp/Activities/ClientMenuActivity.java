package com.example.bikerentingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class ClientMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String USER_KEY = "user_key";

    private boolean doubleTap;

    private String username;
    private SharedPreferences sharedpreferences;
    private Customer customer;
    private Gson gson;
    private String json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu_activity);

        gson = new Gson();
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        json = sharedpreferences.getString(USER_KEY, null);

        if (json != null) {
            customer = gson.fromJson(json, Customer.class);
        }
        customer.getWallet().setFunds(DatabaseConnection.getFunds(customer.getAccountID()));
        UserHolder.getInstance().setUser(customer);
        username = UserHolder.getInstance().getUser().getUsername();

        //Set funds label
        resetFunds();

        //Operating on navigation view
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.m_recharge) {
            accountRecharge();
        } else if (id == R.id.m_rides) {
            Intent intent = new Intent(this, MyHiresActivity.class);
            startActivity(intent);
        } else if (id == R.id.m_bookings) {
            Intent intent = new Intent(this, MyReservationsActivity.class);
            startActivity(intent);
        } else if (id == R.id.m_logout) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, LoggingInActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void accountRecharge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_funds_dialog, null);

        EditText recharge;
        recharge = view.findViewById(R.id.accountRecharge);

        Button submit = view.findViewById(R.id.doładuj);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = recharge.getText().toString();
                try {
                    double income = Double.parseDouble(value);
                    if (income > 50) {
                        Toast.makeText(getApplicationContext(), "Jednorazowa kwota doładowania nie może przekraczać 50zł.", Toast.LENGTH_LONG).show();
                    } else {
                        if (customer.updateFunds(getApplicationContext(), income)) {
                            Toast.makeText(getApplicationContext(), "Dodano środki do konta.", Toast.LENGTH_SHORT).show();
                            resetFunds();
                            dialog.dismiss();
                        } else
                            Toast.makeText(getApplicationContext(), "Ups coś poszło nie tak.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Podano nieprawidłową kwotę", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void resetFunds() {
        TextView wallet = (TextView) findViewById(R.id.account);
        DecimalFormat dform = new DecimalFormat("#.##");
        wallet.setText(String.valueOf(dform.format(customer.getWallet().getFunds())));
    }

    public void openNavigationMenu(View view) {
        final DrawerLayout navigation = findViewById(R.id.drawerLayout);
        navigation.openDrawer(GravityCompat.START);

        /* Setting username in slided menu from.*/
        TextView usernameView = (TextView) navigation.findViewById(R.id.navheader_username);
        usernameView.setText(username);
    }

    public void openMapActivity(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openCameraActivity(View view) {
        Intent intent = new Intent(view.getContext(), CameraActivity.class);
        //intent.putExtra("userObject", user);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleTap) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Naciśnij jeszcze raz by wyjść z  aplikacji!", Toast.LENGTH_SHORT).show();
            doubleTap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTap = false;
                }
            }, 500);
        }
    }

    public void makeComplaint(View view) {
        Intent intent = new Intent(view.getContext(), MakeComplaintActivity.class);
        startActivity(intent);
    }

    public void openDamageReportActivity(View view) {
        Intent intent = new Intent(view.getContext(), BikeFailureReportActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFunds();
    }
}