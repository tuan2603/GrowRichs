package com.nhattuan.growrichs.ultil;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.nhattuan.growrichs.Activity.NotifyActivity;
import com.nhattuan.growrichs.R;


/**
 * Created by TRANTUAN on 15-Mar-18.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mNotificationManager;
    public static final String MESSAGE_CHANNEL_ID = "com.nhattuan.growrichs.NOTIFYCATION";
    public static final String MESSAGE_CHANNEL_NAME = "NOTIFICATION";


    public NotificationUtils(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
            // create MESSAGE channel
            NotificationChannel messageChannel  = new NotificationChannel(MESSAGE_CHANNEL_ID,
                    MESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            // Sets whether notifications posted to this channel should display notification lights
            messageChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            messageChannel.enableVibration(true);
            //messageChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 500, 200, 500});
            // Sets the notification light color for notifications posted to this channel
            messageChannel.setLightColor(Color.RED);

            //messageChannel.canShowBadge();
            messageChannel.setShowBadge(true);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            messageChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
         getNotificationManager().createNotificationChannel(messageChannel);


    }




    @TargetApi(Build.VERSION_CODES.O)
    private Notification.Builder getMessageChannelNotification(String title, String body) {
        Notification.Builder notifi = new Notification.Builder(getApplicationContext(), MESSAGE_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setColorized(true)
                .setShowWhen(true)
                .setSmallIcon(R.drawable.logo_trans)
                .setContentIntent(getPendingIntentMessage2())
                .setAutoCancel(true);
        return notifi;
    }


    private NotificationCompat.Builder getMessageChannelNotificationCompat(String title, String body) {
        long[] v = {500, 1000};
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         NotificationCompat.Builder notifi = new NotificationCompat.Builder(getApplicationContext(),MESSAGE_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setColorized(true)
                .setShowWhen(true)
                .setVibrate(v)
                .setSound(uri)
                 .setSmallIcon(R.drawable.logo_trans)
                 .setContentIntent(getPendingIntentMessage2())
                .setAutoCancel(true);

        return notifi;
    }






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private PendingIntent getPendingIntentMessage2() {
        Intent openMainIntent = new Intent(this, NotifyActivity.class);

        openMainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotifyActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(openMainIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
    }

    private PendingIntent getPendingIntentMessage() {
        String click_action = MESSAGE_CHANNEL_ID;
        Intent resultIntent = new Intent(click_action);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT

        );
        return resultPendingIntent;

    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }


    public void setNotifyAppoint(String title, String body) {
        int id=102;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder notificationBuilder = null;
            notificationBuilder = getMessageChannelNotification(
                    title
                    , body);

            if (notificationBuilder != null) {
                getNotificationManager().notify(id, notificationBuilder.build());
            }
        }else{
            NotificationCompat.Builder mBuilder = null;
            mBuilder = getMessageChannelNotificationCompat(
                    title
                    , body
                    );
            if (mBuilder != null) {
                getNotificationManager().notify(id, mBuilder.build());
            }
        }
    }

}