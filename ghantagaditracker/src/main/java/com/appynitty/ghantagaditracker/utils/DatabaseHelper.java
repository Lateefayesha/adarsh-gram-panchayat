package com.appynitty.ghantagaditracker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.appynitty.ghantagaditracker.controller.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 28/6/19.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_NOTIFICATIONS";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Notification.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Notification.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

//    public void insertNotification(String message, String dateTime, int status, String type){
    public void insertNotification(String message, String dateTime, int status){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notification.COLUMN_TEXT, message);
        values.put(Notification.COLUMN_DATE_TIME, dateTime);
//        values.put(Notification.COLUMN_TYPE, type);
        values.put(Notification.COLUMN_STATUS, status);

        database.insert(Notification.TABLE_NAME, null, values);
        database.close();
    }

    public List<Notification> getAllNotifications(){
        List<Notification> list = new ArrayList<>();

        String sql = "SELECT * FROM " + Notification.TABLE_NAME + " ORDER BY " +
                Notification.COLUMN_ID + " DESC";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Notification notification = new Notification();
                notification.setId(cursor.getInt(cursor.getColumnIndex(Notification.COLUMN_ID)));
                notification.setText(cursor.getString(cursor.getColumnIndex(Notification.COLUMN_TEXT)));
                notification.setDateTime(cursor.getString(cursor.getColumnIndex(Notification.COLUMN_DATE_TIME)));
                notification.setStatus(cursor.getInt(cursor.getColumnIndex(Notification.COLUMN_STATUS)));

                list.add(notification);
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public void deleteNotification(int id){

        SQLiteDatabase database = this.getWritableDatabase();

        String whereClause = Notification.COLUMN_ID + "= ?";
        String[] args = new String[]{String.valueOf(id)};

        database.delete(Notification.TABLE_NAME, whereClause, args);
        database.close();
    }

    public void deleteAllNotification(){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Notification.TABLE_NAME, null , null);
        database.close();
    }

    public void markNotificationsAsRead(){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notification.COLUMN_STATUS, Notification.STATUS_READ);

        database.update(Notification.TABLE_NAME, values, null, null);
        database.close();
    }
}
