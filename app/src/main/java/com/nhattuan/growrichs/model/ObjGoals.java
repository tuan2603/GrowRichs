package com.nhattuan.growrichs.model;

import java.io.Serializable;

public class ObjGoals implements Serializable{
    public static final String TABLE = "goals";
    private int mID;
    private String mTitle;
    private String[] mImages;
    private long mTimer;


    public ObjGoals() {
    }

    public ObjGoals(int mID, String mTitle, long mTimer) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mTimer = mTimer;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmTilte() {
        return mTitle;
    }

    public void setmTilte(String mTitle) {
        this.mTitle = mTitle;
    }

    public String[] getmImages() {
        return mImages;
    }

    public void setmImages(String[] mImages) {
        this.mImages = mImages;
    }

    public long getmTimer() {
        return mTimer;
    }

    public void setmTimer(long mTimer) {
        this.mTimer = mTimer;
    }
}
