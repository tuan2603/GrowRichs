package com.nhattuan.growrichs.model;

public class ObjectLogan {
    private int mID;
    private String mMorning;
    private String mEvening;

    public ObjectLogan(int mID, String mMorning, String mEvening) {
        this.mID = mID;
        this.mMorning = mMorning;
        this.mEvening = mEvening;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmMorning() {
        return mMorning;
    }

    public void setmMorning(String mMorning) {
        this.mMorning = mMorning;
    }

    public String getmEvening() {
        return mEvening;
    }

    public void setmEvening(String mEvening) {
        this.mEvening = mEvening;
    }
}
