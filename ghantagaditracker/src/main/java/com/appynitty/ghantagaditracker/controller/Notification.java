package com.appynitty.ghantagaditracker.controller;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ayan Dey on 28/6/19.
 */
public class Notification {

    public static final String TABLE_NAME = "notification";
    public static final int STATUS_READ = 0;
    public static final int STATUS_UNREAD = 1;
    public static final String TYPE_GG = "GG";
    public static final String TYPE_GP = "GP";
    public static final String TYPE_GGB = "GGB";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "message";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_STATUS = "read_status";
    public static final String COLUMN_TYPE = "notification_type";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEXT + " TEXT, " + COLUMN_DATE_TIME + " TEXT, " +
            COLUMN_STATUS + " INTEGER )";

//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + " (" +
//                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    COLUMN_TEXT + " TEXT, " + COLUMN_DATE_TIME + " TEXT, " +
//                    COLUMN_STATUS + " INTEGER, " + COLUMN_STATUS + " INTEGER )";

    private int id;
    private String text, dateTime, type;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
