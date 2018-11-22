package ru.virarnd.viraweatherreminder.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = SmsBroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            String format = intent.getExtras().getString("format");

            final SmsMessage[] messages = new SmsMessage[pdus.length];
            String senderPhoneNo = null;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                builder.append(messages[i].getMessageBody());
                senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                Log.i(TAG, "Message " + messages[i].getMessageBody() + ", from " + senderPhoneNo);
//                Toast.makeText(context, "Message " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_SHORT).show();

                // Для уведомления другого компонента программы о пришедшем СМС:
                if (senderPhoneNo.equals("+79281866472") ) {
                    Toast.makeText(context, builder + "\nFrom --> " + senderPhoneNo, Toast.LENGTH_SHORT).show();
                }
                Intent localIntent = new Intent("mySmsCaught");
                localIntent.putExtra("messageCaught", builder.toString());
                localIntent.putExtra("numberCaught", senderPhoneNo);
                context.sendBroadcast(localIntent);
            }
        }
    }
}