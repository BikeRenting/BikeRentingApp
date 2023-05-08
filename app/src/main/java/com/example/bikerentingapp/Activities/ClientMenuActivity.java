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
import com.example.bikerentingapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class ClientMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String USER_KEY = "user_key";

    private boolean doubleTap;
    private TextView username;
    private SharedPreferences sharedpreferences;
    private Customer customer;
    private Gson gson;
    private String json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu_activity);

        //Operating on navigation view
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        username = findViewById(R.id.usernickname);

        gson = new Gson();
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        json = sharedpreferences.getString(USER_KEY, null);

        if(json != null) {
            customer = gson.fromJson(json, Customer.class);
            username.setText(customer.getEmail());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.m_recharge) {
            accountRecharge();
        }
        return true;
    }

    private void accountRecharge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_funds_dialog,null);

        EditText recharge;
        recharge = view.findViewById(R.id.accountRecharge);

        Button submit = view.findViewById(R.id.doładuj);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String value = recharge.getText().toString();
                double income = Double.valueOf(value);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openNavigationMenu(View view){
        final DrawerLayout navigation = findViewById(R.id.drawerLayout);
        navigation.openDrawer(GravityCompat.START);
    }

    public void openMapActivity(View view){

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openCameraActivity(View view){

        Intent intent = new Intent(view.getContext(), CameraActivity.class);
        //intent.putExtra("userObject", user);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        if(doubleTap){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            super.onBackPressed();
        }
        else{
            Toast.makeText(this, "Naciśnij jeszcze raz by wyjść z  aplikacji!", Toast.LENGTH_SHORT).show();
            doubleTap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {doubleTap = false;}
            },500);
        }
    }
}