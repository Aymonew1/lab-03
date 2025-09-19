package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private City city;
    private EditCityDialogListener listener;

    // Interface for notifying MainActivity
    public interface EditCityDialogListener {
        void onCityEdited();
    }

    public static EditCityFragment newInstance(City city) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Retrieve City from arguments
        city = (City) requireArguments().getSerializable("city");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill fields with existing city info
        editCityName.setText(city.getName());
        editProvinceName.setText(city.getProvince());

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Update the existing city object directly
                    city.setName(editCityName.getText().toString());
                    city.setProvince(editProvinceName.getText().toString());
                    if (listener != null) listener.onCityEdited();
                })
                .create();
    }
}
