package com.example.rembirthday;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 6);
            instance.set(12, 0);
            instance.set(13, 0);
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(context, 1, new Intent(context, AlarmReceiver.class), 0));

        }
    }

}
