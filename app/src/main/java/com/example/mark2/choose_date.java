package com.example.mark2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


public class choose_date extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


// Create a new instance of DatePickerDialog and return it.
        DatePickerDialog dates =  new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
        dates.getDatePicker().setMaxDate(c.getTimeInMillis());
        return dates;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ClickedActivity clickedActivity = (ClickedActivity)getActivity();
        assert clickedActivity != null;
        clickedActivity.processDate(year,month,dayOfMonth);
    }
}