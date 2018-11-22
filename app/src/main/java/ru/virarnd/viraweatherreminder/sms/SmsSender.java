package ru.virarnd.viraweatherreminder.sms;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;

import ru.virarnd.viraweatherreminder.common.MyApp;

public class SmsSender {

    private static final String TAG = SmsSender.class.getName();

    public final static String SMS_SENT = "SMS_SENT";

    // Метод для отправки СМС на определенный номер
    public void sendMySms(final String phoneNumber, final String message) {
        // Поскольку СМС могут отправляться н момментально, делаю в отдельном потоке. По факту отправки отправляю LocalBroadcast.
        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                PendingIntent sentPI = PendingIntent.getBroadcast(MyApp.getContext(), 0, new Intent(SMS_SENT), 0);
                SmsManager sms = SmsManager.getDefault();
                Log.i(TAG, "На номер " + phoneNumber + " отправляю -->" + message + "<--\n");
                sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
            }
        });
    }
}
