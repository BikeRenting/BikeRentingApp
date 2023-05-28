package com.example.bikerentingapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.UserHolder;
import com.example.bikerentingapp.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String USER_KEY = "user_key";

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    PreviewView previewView;
    Button scanButton;
    EditText text;

    private SharedPreferences sharedpreferences;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        scanButton = findViewById(R.id.scanBTN);
        text = findViewById(R.id.enterID);
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e){
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }, getExecutor());


        customer = (Customer) UserHolder.getInstance().getUser();

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }

    public void Scan(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz wypożyczyć ten rower?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(text.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(),"Podaj ID roweru",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else {

                            if(customer.hasAnyUnpaidHire()) {
                                Toast.makeText(view.getContext(),"Posiadasz nieopłacony przejazd. Ureguluj płatność przed kolejnym wypożyczeniem",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                            else if(customer.getWallet().getFunds() < 2.00) {
                                Toast.makeText(view.getContext(),"Nie posiadasz wystarczających środków. Aby wypożyczyć rower potrzebujesz co najmniej 2zł w portfelu.",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                            else {
                                int bikeID = Integer.parseInt(text.getText().toString());
                                if(customer.rentBike(bikeID)) {
                                    Toast.makeText(view.getContext(),"Wypożyczono rower",Toast.LENGTH_SHORT).show();

                                    dialog.cancel();
                                    Intent intent = new Intent(view.getContext(), CurrentHireActivity.class);
                                    view.getContext().startActivity(intent);
                                    finish();

                                }
                                else {
                                    Toast.makeText(view.getContext(),"Ten rower jest niedostępny",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            }

                        }

                    }
                });

        builder.setNegativeButton(
                "Nie",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
