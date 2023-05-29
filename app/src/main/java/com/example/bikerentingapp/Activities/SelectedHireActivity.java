package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.AccountModel.Customer;
import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.Classes.Hire;
import com.example.bikerentingapp.Classes.UserHolder;
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
    private TextView remainingPayment;
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
        remainingPayment = this.findViewById(R.id.remainingPaymentText);
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
        cost.setText(selectedHire.getPayment() + " zł");

        if(selectedHire.isPaymentRealized()){
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_check_24,null);
            isPaid.setText("opłacone");
            isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            remainingPayment.setText("0.00 zł");
            regulatePayment.setVisibility(View.GONE);
        }else {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_close_24,null);
            isPaid.setText("nieopłacone");
            isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            remainingPayment.setText(String.valueOf(selectedHire.getRemainingPayment()) + " zł");
        }
    }

    public void regulatePayment(View view){

        double costToRegulate = selectedHire.getRemainingPayment();
        Customer customer =(Customer)UserHolder.getInstance().getUser();

        if(costToRegulate > customer.getWallet().getFunds()){
            Toast.makeText(getApplicationContext(),"Brak wystarczających środków by uregulować płatność", Toast.LENGTH_LONG).show();
        }else {
            customer.getWallet().takeFunds(costToRegulate);
            DatabaseConnection.rechargeWallet(customer.getAccountID(), customer.getWallet().getFunds());
            DatabaseConnection.updateHire(selectedHire.getHireID(), selectedHire.getTime(), selectedHire.getLength(), selectedHire.getPayment(), 1, 0.0);
            selectedHire.setPaymentRealized(true);
            finish();
            startActivity(getIntent());
            Toast.makeText(getApplicationContext(),"Płatność została zrealizowana", Toast.LENGTH_LONG).show();
        }

    }
}