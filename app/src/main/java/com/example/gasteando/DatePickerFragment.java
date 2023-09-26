package com.example.gasteando;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerListener listener;
    private Calendar selectedDate;

    public interface DatePickerListener {
        void onDateSet(Calendar date);
    }

    public static DatePickerFragment newInstance(Calendar selectedDate, DatePickerListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.selectedDate = selectedDate;
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int anio = selectedDate.get(Calendar.YEAR);
        int mes = selectedDate.get(Calendar.MONTH);
        int dia = selectedDate.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireActivity(), this, anio, mes, dia);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDate.set(year, month, dayOfMonth);
        listener.onDateSet(selectedDate);
    }
}
