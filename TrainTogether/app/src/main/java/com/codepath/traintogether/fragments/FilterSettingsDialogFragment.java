package com.codepath.traintogether.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.SpinnerAdapter;
import com.codepath.traintogether.models.FilterSettings;

import java.util.Arrays;

/**
 * Created by ameyapandilwar on 8/18/16 at 2:30 AM.
 */
public class FilterSettingsDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText etLatLng, etRadius, etCity, etState, etZip, etCountry, etStartDate;
    private Spinner spSortOrder;
    private Button btnSave;

    public FilterSettingsDialogFragment() {

    }

    @Override
    public void onClick(View view) {
        FilterSettingsDialogListener listener = (FilterSettingsDialogListener) getActivity();
        FilterSettings settings = new FilterSettings(
                etLatLng.getText().toString(),
                etRadius.getText().toString(),
                etCity.getText().toString(),
                etState.getText().toString(),
                etZip.getText().toString(),
                etCountry.getText().toString(),
                etStartDate.getText().toString()
        );

        listener.onUpdateFilterSettings(settings);
        dismiss();
    }

    public interface FilterSettingsDialogListener {
        void onUpdateFilterSettings(FilterSettings settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLatLng = (EditText) view.findViewById(R.id.etLatLng);
        etRadius = (EditText) view.findViewById(R.id.etRadius);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etState = (EditText) view.findViewById(R.id.etState);
        etZip = (EditText) view.findViewById(R.id.etZip);
        etCountry = (EditText) view.findViewById(R.id.etCountry);
        etStartDate = (EditText) view.findViewById(R.id.etStartDate);
        spSortOrder = (Spinner) view.findViewById(R.id.spSortOrder);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        SharedPreferences preferences = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        etLatLng.setText(preferences.getString("lat_lon", ""));
        etRadius.setText(preferences.getString("radius", ""));
        etCity.setText(preferences.getString("city", ""));
        etState.setText(preferences.getString("state", ""));
        etZip.setText(preferences.getString("zip", ""));
        etCountry.setText(preferences.getString("country", ""));
        etStartDate.setText(preferences.getString("start_date", ""));

        SpinnerAdapter<String> adapterSortOrder = new SpinnerAdapter(
                getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(getResources().getStringArray(R.array.sort_order_values))
        );
        spSortOrder.setAdapter(adapterSortOrder);

        etStartDate.setOnClickListener(v -> showDatePickerDialog());
        btnSave.setOnClickListener(this);
    }

    public static FilterSettingsDialogFragment newInstance(String title) {
        FilterSettingsDialogFragment fragment = new FilterSettingsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setCallback(listener);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            etStartDate.setText(String.format("%d-%s-%s", year, String.format("%02d", month + 1), String.format("%02d", day)));
        }
    };

}