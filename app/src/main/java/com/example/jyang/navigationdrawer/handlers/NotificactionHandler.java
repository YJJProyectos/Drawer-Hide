package com.example.jyang.navigationdrawer.handlers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.activities.NotificacionReciboActivity;

public class NotificactionHandler extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";

    public NotificactionHandler(Context base) {
        super(base);
        createChannel();
    }

    public NotificationManager getManager() {
        if ( manager == null ) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    // para oreo
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= 26 ) {
            // crear channel
            NotificationChannel highChannel = new NotificationChannel(
                    CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);

            // EXTRA CONFIG
            highChannel.enableLights(true);
            highChannel.setLightColor(Color.YELLOW);
            highChannel.setShowBadge(true);
            highChannel.enableVibration(true);
            //highChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            //Uri defaultSoundUri = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION));
            //highChannel.setSound(defaultSoundUri, null);



            NotificationChannel lowChannel = new NotificationChannel(
                    CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);
            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public Notification.Builder createNotification(String title, String message, boolean isHighImportance) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if ( isHighImportance ) {
                return this.createNotificationWithChannel(title, message, CHANNEL_HIGH_ID);
            }
            return this.createNotificationWithChannel(title, message, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(title, message, isHighImportance);
    }

    private Notification.Builder createNotificationWithChannel(String title, String message, String channelId) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, NotificacionReciboActivity.class);
            intent.putExtra("titulo", title);
            intent.putExtra("mensaje", message);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            Notification.Action action = new Notification.Action.Builder(
                    Icon.createWithResource(this, android.R.drawable.ic_menu_send),
                    getString(R.string.notificacion_mensaje_pop),
                    pIntent).build();

            return new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    //.setContentIntent(pIntent)
                    .addAction(action)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setShowWhen(true)
                    .setLargeIcon(Icon.createWithResource(this, R.drawable.ic_notification))
                    .setAutoCancel(true);
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String title, String message, boolean isHighImportance) {
        Intent intent = new Intent(this, NotificacionReciboActivity.class);
        intent.putExtra("titulo", title);
        intent.putExtra("mensaje", message);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder nb = new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pIntent)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
        if ( isHighImportance ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                nb.setPriority(Notification.PRIORITY_HIGH);
            }
            nb.setDefaults(Notification.DEFAULT_ALL);
        }
        Notification.Action action = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            action = new Notification.Action.Builder(
                    Icon.createWithResource(this, android.R.drawable.ic_menu_send),
                    getString(R.string.notificacion_mensaje_pop),
                    pIntent).build();
            nb.addAction(action);
            nb.setShowWhen(true);
            nb.setLargeIcon(Icon.createWithResource(this, R.drawable.ic_notification));
        }
        return nb;
    }
}
