package com.nhattuan.growrichs.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.nhattuan.growrichs.model.ObjGoals;

import java.util.ArrayList;
import java.util.List;

public class GoalsHelper {
    private static final String TAG = "GoalsHelper";
    private ObjGoals objGoal;

    public GoalsHelper() {
        this.objGoal = new ObjGoals();
    }

    private static final String KEY_ID = "mID";

    private static final String CREATE_CART_TABLE = "CREATE TABLE `goals` (\n" +
            "`mID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "\t`mTitle`\tTEXT NOT NULL,\n" +
            "\t`mImages`\tTEXT,\n" +
            "\t`mTimer`\tTEXT,\n" +
            "\t`created_at`\tDATETIME DEFAULT CURRENT_TIMESTAMP);";

    public static String createTable() {
        return CREATE_CART_TABLE;
    }


    // Adding new addObjGoals
    public int addObjGoals(ObjGoals objGoal) {
        int courseId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();


        values.put("mTitle", objGoal.getmTilte());
        if (objGoal.getmImages() != null) {
            String mImages = convertArrayToString(objGoal.getmImages());
            values.put("mImages", mImages);
        }
        values.put("mTimer", objGoal.getmTimer());

        // Inserting Row
        courseId = (int) db.insert(ObjGoals.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();// Closing database connection
        return courseId;
    }

    // Getting single objGoal
    public ObjGoals getGoalID(String mId) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "select * from " + ObjGoals.TABLE + " where mID ='" + mId + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        ObjGoals objGoal = new ObjGoals();
        if (cursor.getCount() > 0) {
            int i = 0;
            objGoal.setmID(cursor.getInt(i++));
            objGoal.setmTilte(cursor.getString(i++));
            String image = cursor.getString(i++);
            if (!TextUtils.isEmpty(image)){
                String[] mImage = convertStringToArray(image);
                objGoal.setmImages(mImage);
            }
            objGoal.setmTimer(cursor.getLong(i++));
        }
        return objGoal;
    }
    // Getting single objGoal
    public ObjGoals getGoalTitle(String title) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "select * from " + ObjGoals.TABLE + " where mTitle ='" + title + "' ORDER BY created_at ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        ObjGoals objGoal = new ObjGoals();
        if (cursor.getCount() > 0) {
            int i = 0;
            objGoal.setmID(cursor.getInt(i++));
            objGoal.setmTilte(cursor.getString(i++));
            String image = cursor.getString(i++);
            if (!TextUtils.isEmpty(image)){
                String[] mImage = convertStringToArray(image);
                objGoal.setmImages(mImage);
            }
            objGoal.setmTimer(cursor.getLong(i++));
        }
        return objGoal;
    }

    // Getting All ObjGoals
    public List<ObjGoals> getAllObjGoalss() {
        List<ObjGoals> gList = new ArrayList<ObjGoals>();

        // Select All Query
        String selectQuery = "select * from " + ObjGoals.TABLE + " ORDER BY created_at ASC";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ObjGoals objGoal = new ObjGoals();
                int i = 0;
                objGoal.setmID(cursor.getInt(i++));
                objGoal.setmTilte(cursor.getString(i++));
                String image = cursor.getString(i++) + "";
                if(image.length()>0)

                {
                    String[] mImage = convertStringToArray(image);
                    Log.d(TAG, "getAllObjGoalss: " + image);

                    objGoal.setmImages(mImage);
                }

                objGoal.setmTimer(cursor.getLong(i++));
                // Adding contact to list
                gList.add(objGoal);
            } while (cursor.moveToNext());
        }
        // return contact list
        return gList;
    }

    // Getting ObjGoals Count
    public int getObjGoalssCount() {
        String countQuery = "select * from " + ObjGoals.TABLE;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }


    // Updating single ObjGoals
    public int updateObjGoals(ObjGoals objGoal) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("mID", objGoal.getmID());
        values.put("mTitle", objGoal.getmTilte());
        if (objGoal.getmImages() != null) {
            String mImages = convertArrayToString(objGoal.getmImages());
            values.put("mImages", mImages);
        }
        else values.put("mImages","");
        values.put("mTimer", objGoal.getmTimer());
        // updating row
        return db.update(ObjGoals.TABLE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(objGoal.getmID())});
    }

    // Deleting single ObjGoals
    public int deleteGoal(ObjGoals objGoal) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int count = db.delete(ObjGoals.TABLE, KEY_ID + " = ?",
                new String[]{String.valueOf(objGoal.getmID())});
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }


    // Deleting single ObjGoals
    public int deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int count = db.delete(ObjGoals.TABLE, null,
                null);
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }


    public static String strSeparator = ",";

    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);

        return arr;
    }
}
