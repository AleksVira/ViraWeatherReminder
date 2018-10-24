package ru.virarnd.viraweatherreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimerPickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TimerPickedListener timerListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, 2, 0, DateFormat.is24HourFormat(getActivity()));
        return timePickerDialog;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            timerListener = (TimerPickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TimerPickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timerListener = null;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        timerListener.onTimerPicked(calendar);
    }


    public interface TimerPickedListener {
        void onTimerPicked(Calendar time);
    }

}
