package com.nhattuan.growrichs.services;

import android.content.Intent;

import com.nhattuan.growrichs.Activity.NotifyActivity;
import com.nhattuan.growrichs.helper.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    private static final String TAG = "MyFirebaseMsgService";
    private SessionManager sessionManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Log.d(TAG, "From: " + remoteMessage.getFrom());
        sessionManager = new SessionManager(getApplicationContext());
            if (remoteMessage.getData().size() > 0) {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
               // ObjectMessage message = new Gson().fromJson(jsonObject.toString(), ObjectMessage.class);
                Intent intent = new Intent(this, NotifyActivity.class);
                try {
                    intent.putExtra("FullMessage", jsonObject.getString("FullMessage"));
                    intent.putExtra("linkURL", jsonObject.getString("linkURL"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //sendNotification("Goal Card", remoteMessage.getNotification().getBody(), "NOTIFICATION");
            }
    }

}
