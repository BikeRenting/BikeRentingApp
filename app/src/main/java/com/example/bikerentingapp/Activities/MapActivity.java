package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Station;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import android.util.Pair;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;
import com.example.bikerentingapp.R;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //String x = DatabaseConnection.getUserById(1);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.2541165, 19.0232932), 15));

        ArrayList<Station> stations = DatabaseConnection.getStations();

        Drawable vectorDrawable = ContextCompat.getDrawable(MapActivity.this, R.drawable.bike_parking);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

        ArrayList<Integer> availableBikes = DatabaseConnection.getAvailableBikes();

        for (Station station: stations) {

            Pair<Double, Double> coordinates = station.getCoordinates();
            LatLng pos = new LatLng(coordinates.first, coordinates.second);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(pos)
                            .title("Stacja nr. " + Integer.toString(station.getStationID())).snippet("Dostępne rowery: " + Integer.toString(availableBikes.get(station.getStationID()-1)))
                            .icon(icon));

        }


    }
}