package com.example.bikerentingapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.bikerentingapp.R;

public class ReservationDialog extends AppCompatDialogFragment {

    private String[] items = {"15 minut", "30 minut", "1 godzina", "1 godzina 30 minut", "2 godziny"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private String selectedItem = "none";
    private ReservationDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reservation_dialog, null);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, items);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
            }
        });

        builder.setView(view)
                .setTitle("Rezerwacja")
                .setNegativeButton("anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selectedItem.equals("none")){
                            Toast.makeText(view.getContext(),"Nie wybrano czasu rezerwacji", Toast.LENGTH_LONG).show();
                        }
                        else {
                            long duration = 0;
                            switch (selectedItem) {
                                case "15 minut":
                                    duration = 15;
                                    break;
                                case "30 minut":
                                    duration = 30;
                                    break;
                                case "1 godzina":
                                    duration = 60;
                                    break;
                                case "1 godzina 30 minut":
                                    duration = 90;
                                    break;
                                case "2 godziny":
                                    duration = 120;
                                    break;
                                default:
                                    break;
                            }
                            listener.applyReservationTime(duration);
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ReservationDialogListener) context;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ReservationDialogListener{
        void applyReservationTime(long selectedTime);
    }
}
