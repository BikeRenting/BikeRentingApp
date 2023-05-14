package com.example.bikerentingapp.Classes;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerentingapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyHiresRecyclerAdapter extends RecyclerView.Adapter<MyHiresRecyclerAdapter.MyViewHolder> {

    private ArrayList<Hire> myHires;
    private RecyclerViewClickListener listener;

    public MyHiresRecyclerAdapter(ArrayList<Hire> myHires, RecyclerViewClickListener listener) {
        this.myHires = myHires;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView hireNumber;
        private TextView startTime;
        private TextView cost;
        private TextView isPaid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hireNumber = itemView.findViewById(R.id.hireNumber);
            startTime = itemView.findViewById(R.id.date);
            cost = itemView.findViewById(R.id.cost);
            isPaid = itemView.findViewById(R.id.isPaidLabel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyHiresRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myhires_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHiresRecyclerAdapter.MyViewHolder holder, int position) {

        String date = myHires.get(position).getStartDate();
        double cost = myHires.get(position).getPayment();
        boolean isPaid = myHires.get(position).isPaymentRealized();

        holder.hireNumber.setText(String.valueOf(position+1));
        holder.startTime.setText(date);
        holder.cost.setText(String.valueOf(cost + " zł"));

        if(isPaid){
            Drawable drawable = ResourcesCompat.getDrawable(holder.isPaid.getResources(), R.drawable.ic_baseline_check_24,null);
            holder.isPaid.setText("opłacone");
            holder.isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }else {
            Drawable drawable = ResourcesCompat.getDrawable(holder.isPaid.getResources(), R.drawable.ic_baseline_close_24,null);
            holder.isPaid.setText("nieopłacone");
            holder.isPaid.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }
    }

    @Override
    public int getItemCount() {
        return myHires.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View V, int position);
    }
}
