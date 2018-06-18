package com.nhattuan.growrichs.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.nhattuan.growrichs.model.ObjGoals;
import com.nhattuan.growrichs.model.ObjPick;
import com.nhattuan.growrichs.model.ObjectMessage;
import com.google.gson.Gson;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_PICK = "pick";
    private static final String OBJ_MORNING = "morning";
    private static final String OBJ_SLEEP = "sleep";
    private static final String TOTAL = "totalduration";
    private static final String CURRENT = "currentduration";
    private static final String OPENAPP = "open";
    private static final String MESSAGE = "message";
    private static final String GOAL = "goal";
    private static final String PERMISSION  = "permission";


    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_PICK, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setMorning(ObjPick objPick) {
        Gson gson = new Gson();
        String json = gson.toJson(objPick);
        editor.putString(OBJ_MORNING, json);

        // commit changes
        editor.commit();
    }

    public ObjPick getMorning() {
        Gson gson = new Gson();
        String json = pref.getString(OBJ_MORNING, "");
        ObjPick obj = gson.fromJson(json, ObjPick.class);
        return obj;
    }

    public void setTimeUse(long timeUse) {
        editor.putLong("USE", timeUse);
        // commit changes
        editor.commit();
    }

    public long getTimeUse() {
        return pref.getLong("USE", 0);
    }

    public void setPermission(boolean open) {
        editor.putBoolean(PERMISSION, open);
        // commit changes
        editor.commit();
    }

    public boolean getPermission() {
        return pref.getBoolean(PERMISSION, false);
    }

    public void setOpenApp(boolean open) {
        editor.putBoolean(OPENAPP, open);
        // commit changes
        editor.commit();
    }

    public boolean getOpenApp() {
        return pref.getBoolean(OPENAPP, false);
    }

    public void setTotal(int totalDuratuion) {
        editor.putInt(TOTAL, totalDuratuion);

        // commit changes
        editor.commit();
    }

    public int getTotal() {
        return pref.getInt(TOTAL, 0);
    }

    public void setCurrent(int currentDuration) {
        editor.putInt(CURRENT, currentDuration);

        // commit changes
        editor.commit();
    }

    public int getCurrent() {
        return pref.getInt(CURRENT, 0);
    }

    public void setSleep(ObjPick objPick) {
        Gson gson = new Gson();
        String json = gson.toJson(objPick);
        editor.putString(OBJ_SLEEP, json);
        // commit changes
        editor.commit();
    }


    public ObjPick getSleep() {
        Gson gson = new Gson();
        String json = pref.getString(OBJ_SLEEP, "");
        return gson.fromJson(json, ObjPick.class);
    }

    public void setMESSAGE(ObjectMessage message) {
        editor.putString(MESSAGE, new Gson().toJson(message));
        // commit changes
        editor.commit();
    }


    public ObjectMessage getMESSAGE() {
        String json = pref.getString(MESSAGE, "");
        return new Gson().fromJson(json, ObjectMessage.class);
    }

    public void setGOAL(ObjGoals message) {
        editor.putString(GOAL, new Gson().toJson(message));
        // commit changes
        editor.commit();
    }


    public ObjGoals getGOAL() {
        String json = pref.getString(GOAL, "");
        return new Gson().fromJson(json, ObjGoals.class);
    }
}
