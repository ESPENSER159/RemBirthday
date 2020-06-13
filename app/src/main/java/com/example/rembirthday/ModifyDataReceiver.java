package com.example.rembirthday;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class ModifyDataReceiver extends BroadcastReceiver {

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.intent.action.TIME_SET")) {
                Calendar instance = Calendar.getInstance();
                instance.set(11, 6);
                instance.set(12, 0);
                instance.set(13, 0);
                ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(context, 1, new Intent(context, AlarmReceiver.class), 0));

            }
        } catch (Exception unused) {
            Toast.makeText(context, "-_-", 0).show();
        }
    }

}
