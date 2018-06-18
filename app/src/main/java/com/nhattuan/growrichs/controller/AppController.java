package com.nhattuan.growrichs.controller;

import android.app.Application;

import com.nhattuan.growrichs.helper.DBHelper;
import com.nhattuan.growrichs.helper.DatabaseManager;


public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();


    private static DBHelper dbHelper;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        dbHelper = new DBHelper(getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}