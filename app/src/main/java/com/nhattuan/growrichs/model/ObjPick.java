package com.nhattuan.growrichs.model;

import android.util.Log;

import java.io.Serializable;

public class ObjPick implements Serializable{

    private int timeHour;
    private int timeMinute;
    private String ringTone;
    private int mSpeakers;
    private boolean mVibration;
    private int mSnooze;
    private long mCreateTime;

    public ObjPick() {
    }

    public ObjPick(int timeHour, int timeMinute, String ringTone, int mSpeakers, boolean mVibration, int mSnooze) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.ringTone = ringTone;
        this.mSpeakers = mSpeakers;
        this.mVibration = mVibration;
        this.mSnooze = mSnooze;
    }

    public ObjPick(int timeHour, int timeMinute, String ringTone, int mSpeakers, boolean mVibration, int mSnooze, long mCreateTime) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.ringTone = ringTone;
        this.mSpeakers = mSpeakers;
        this.mVibration = mVibration;
        this.mSnooze = mSnooze;
        this.mCreateTime = mCreateTime;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public String getRingTone() {
        return ringTone;
    }

    public void setRingTone(String ringTone) {
        this.ringTone = ringTone;
    }

    public int getmSpeakers() {
        return mSpeakers;
    }

    public void setmSpeakers(int mSpeakers) {
        this.mSpeakers = mSpeakers;
    }

    public boolean ismVibration() {
        return mVibration;
    }

    public void setmVibration(boolean mVibration) {
        this.mVibration = mVibration;
    }

    public int getmSnooze() {
        return mSnooze;
    }

    public void setmSnooze(int mSnooze) {
        this.mSnooze = mSnooze;
    }

    public void viewAll(){
        Log.d("ObjPick", "viewAll: "+ this.getRingTone());
    }

    public long getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }
}
