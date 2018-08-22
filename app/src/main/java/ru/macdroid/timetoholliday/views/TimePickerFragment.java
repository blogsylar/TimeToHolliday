package ru.macdroid.timetoholliday.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    Calendar calendar = Calendar.getInstance();

    int hours = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hours, minutes, DateFormat.is24HourFormat(getActivity()));
    }
}
