package com.nhattuan.growrichs.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nhattuan.growrichs.Activity.ExperimentActivity;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjPick;

import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiverMorning extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiverMorning";

    @Override
    public void onReceive(Context context, Intent intent) {
        ObjPick objPick = new SessionManager(context).getMorning();
        if (objPick != null) {
            Calendar calendar = Calendar.getInstance();
            int timeHour = calendar.get(Calendar.HOUR_OF_DAY);
            int timeMinute = calendar.get(Calendar.MINUTE);
            int date = calendar.get(Calendar.DATE);
            Calendar creatTime = Calendar.getInstance();
            creatTime.setTimeInMillis(objPick.getmCreateTime());
            if ((timeHour == objPick.getTimeHour())
                    && (timeMinute == objPick.getTimeMinute())
                    && (date == creatTime.get(Calendar.DATE))){
                Intent launchIntent = new Intent(context, ExperimentActivity.class);
                launchIntent.putExtra("MORNING", true);
                launchIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(launchIntent);
            }
            Log.d(TAG, "onReceive: " + timeHour + " " + objPick.getTimeHour()+ " " +timeMinute + " " + objPick.getTimeMinute());
        }
    }
}
