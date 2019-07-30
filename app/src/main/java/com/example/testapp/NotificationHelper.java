package com.example.testapp;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "01";
    private static NotificationManager nm;

    public static void createNotification(Context context, Intent intent, int idNotification) {

        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, idNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText("Notification " + idNotification)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Chat heads active")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLights(Color.WHITE, 1000, 2000)
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setAutoCancel(true);
        nm.notify(idNotification, builder.build());
    }

    public static void destroyNotification(int position) {
            nm.cancel(position);
    }
}
