package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.R;

public class SelectedHireActivity extends AppCompatActivity {


    private TextView hireNumber;
    private TextView hireID;
    private TextView clientID;
    private TextView bikeID;
    private TextView startDate;
    private TextView hireTime;
    private TextView distance;
    private TextView cost;
    private TextView isPaid;
    private Button regulatePayment;

    private Hire selectedHire;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_hire);

        hireNumber = this.findViewById(R.id.hireNumber);
        hireID = this.findViewById(R.id.hireIdText);
        clientID = this.findViewById(R.id.clientIDText);
        bikeID = this.findViewById(R.id.bikeIDText);
        startDate = this.findViewById(R.id.startDateText);
        hireTime = this.findViewById(R.id.timeText);
        distance = this.findViewById(R.id.distanceText);
        cost = this.findViewById(R.id.costText);
        isPaid = this.findViewById(R.id.isPaidText);
        regulatePayment = this.findViewById(R.id.regulatePaymentButton);


        Intent i = getIntent();
        selectedHire = (Hire) i.getSerializableExtra("selectedHire");
        number = i.getIntExtra("hireNumber",0);

        hireNumber.setText(String.valueOf(hireNumber.getText()) + String.valueOf(number));
        hireID.setText(String.valueOf(selectedHire.getHireID()));
        clientID.setText(String.valueOf(selectedHire.getCustomerID()));
        bikeID.setText(String.valueOf(selectedHire.getBike().getBikeID()));
        startDate.setText(selectedHire.getStartDate());
        hireTime.setText(String.valueOf(Hire.intToTime(selectedHire.getTime())));
        distance.setText(String.valueOf(selectedHire.getLength() + " m"));
        cost.setText(selectedHire.getPayment() + "zł");

        if(selectedHire.isPaymentRealized()){
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_check_24,null);
            isPaid.setText("opłacone");
            isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            regulatePayment.setVisibility(View.GONE);
        }else {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_close_24,null);
            isPaid.setText("nieopłacone");
            isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }

    }
}