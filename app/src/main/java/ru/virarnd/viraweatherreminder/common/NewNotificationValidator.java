package ru.virarnd.viraweatherreminder.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import ru.virarnd.viraweatherreminder.Model;

public class NewNotificationValidator implements TextWatcher {

    private TextView textView;
    private Notification notification;
    private String validationError;
    private final Model model = Model.getInstance();

    public boolean isValid(Notification notification) {
        if (notification.getEventName().equals("")) {
            validationError = "Имя события не должно быть пустым";
            return false;
        }
        if (notification.getCity() == null) {
            validationError = "Должен быть выбран какой-то город";
            return false;
        }
        if (notification.getEventDate() == null) {
            validationError = "Надо выбрать время события";
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
