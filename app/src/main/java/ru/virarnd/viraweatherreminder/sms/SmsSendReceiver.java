package ru.virarnd.viraweatherreminder.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import ru.virarnd.viraweatherreminder.common.MyApp;

public class SmsSendReceiver extends BroadcastReceiver {

    private static final String TAG = SmsSendReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(MyApp.getContext(), "SMS sent!", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(MyApp.getContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(MyApp.getContext(), "No service", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(MyApp.getContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(MyApp.getContext(), "Radio off", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
