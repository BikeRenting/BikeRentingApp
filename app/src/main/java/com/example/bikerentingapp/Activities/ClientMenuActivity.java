package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bikerentingapp.R;
import com.google.android.material.navigation.NavigationView;

public class ClientMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu_activity);

        //Operating on navigation view
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
    }

    public void openMapActivity(View view){

        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void openNavigationMenu(View view){
        final DrawerLayout navigation = findViewById(R.id.drawerLayout);
        navigation.openDrawer(GravityCompat.START);
    }

    public void openCameraActivity(View view){

        Intent intent = new Intent(view.getContext(), CameraActivity.class);
        //intent.putExtra("userObject", user);
        view.getContext().startActivity(intent);
    }
}