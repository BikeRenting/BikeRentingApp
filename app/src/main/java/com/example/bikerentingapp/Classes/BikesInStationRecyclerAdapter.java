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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BikesInStationRecyclerAdapter extends RecyclerView.Adapter<BikesInStationRecyclerAdapter.MyViewHolder> {

    private ArrayList<Bike> bikes;
    private RecyclerViewClickListener listener;

    public BikesInStationRecyclerAdapter(ArrayList<Bike> bikes,RecyclerViewClickListener listener) {
        this.bikes = bikes;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView number;
        private TextView bikeID;
        private TextView bikeCondition;
        private TextView bikeStatus;

        public MyViewHolder(final View view) {
            super(view);
            number = view.findViewById(R.id.number);
            bikeID = view.findViewById(R.id.bikeID);
            bikeCondition = view.findViewById(R.id.condition);
            bikeStatus = view.findViewById(R.id.isAvailable);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_in_station_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int number = bikes.get(position).getBikeID();
        boolean status = bikes.get(position).isAvailable();
        String condition = bikes.get(position).getCondition();

        holder.number.setText(String.valueOf(position + 1));
        holder.bikeID.setText(String.valueOf("ID roweru: #" + number));

        if(condition.equals("sprawny")){
            Drawable drawable = ResourcesCompat.getDrawable(holder.bikeCondition.getResources(), R.drawable.ic_baseline_check_circle_24,null);
            holder.bikeCondition.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }else {
            Drawable drawable = ResourcesCompat.getDrawable(holder.bikeCondition.getResources(), R.drawable.ic_baseline_warning_amber_24,null);
            holder.bikeCondition.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }

        if (status) {
            holder.bikeStatus.setText("dostepny");
        } else
            holder.bikeStatus.setText("niedostepny");
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }


}
