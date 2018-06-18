package com.nhattuan.growrichs.model;

import java.io.Serializable;

/**
 * Created by TRANTUAN on 09-Apr-18.
 */

public class Song implements Serializable {
    private int mID;
    private String mName ;
    private String mTitle ;

    public Song(int mID, String mName) {
        this.mID = mID;
        this.mName = mName;
    }

    public Song(int mID, String mName, String mTitle) {
        this.mID = mID;
        this.mName = mName;
        this.mTitle = mTitle;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }



    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        Song itemCompare = (Song) obj;
        if(itemCompare.getmName().equals(this.getmName()))
            return true;

        return false;
    }
}
