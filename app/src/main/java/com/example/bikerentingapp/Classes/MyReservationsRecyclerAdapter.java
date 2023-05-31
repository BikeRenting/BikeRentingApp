package com.example.bikerentingapp.Classes;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerentingapp.R;

import java.util.ArrayList;

public class MyReservationsRecyclerAdapter extends RecyclerView.Adapter<MyReservationsRecyclerAdapter.MyViewHolder> {

    private ArrayList<Reservation> myReservations;
    private RecyclerViewClickListener listener;

    public MyReservationsRecyclerAdapter(ArrayList<Reservation> myReservations, RecyclerViewClickListener listener) {
        this.myReservations = myReservations;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView reservationNumber;
        private TextView startTime;
        private TextView endTime;
        private TextView isRealized;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationNumber = itemView.findViewById(R.id.reservationNumber);
            startTime = itemView.findViewById(R.id.reservation_start_date);
            endTime = itemView.findViewById(R.id.reservation_end_date);
            isRealized = itemView.findViewById(R.id.isRealizedLabel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyReservationsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myreservations_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReservationsRecyclerAdapter.MyViewHolder holder, int position) {

        String start_date = myReservations.get(position).getStartDate();
        String end_date = myReservations.get(position).getEndDate();
        boolean isRealized = myReservations.get(position).isExecuted();

        holder.reservationNumber.setText(String.valueOf(position+1));
        holder.startTime.setText(start_date);
        holder.endTime.setText(end_date);

        if(isRealized){
            Drawable drawable = ResourcesCompat.getDrawable(holder.isRealized.getResources(), R.drawable.ic_baseline_assignment_turned_in_24,null);
            holder.isRealized.setText("zakończona");
            holder.isRealized.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }else {
            Drawable drawable = ResourcesCompat.getDrawable(holder.isRealized.getResources(), R.drawable.ic_baseline_schedule_24,null);
            holder.isRealized.setText("w trakcie");
            holder.isRealized.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }
    }

    @Override
    public int getItemCount() {
        return myReservations.size();
    }


}
