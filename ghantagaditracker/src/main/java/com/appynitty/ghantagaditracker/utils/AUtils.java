package com.appynitty.ghantagaditracker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.controller.Notification;
import com.google.gson.Gson;
import com.mithsoft.lib.utils.MsUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import quickutils.core.QuickUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by MiTHUN on 5/2/18.
// */

public class AUtils extends MsUtils {

    //    Local URL
    public static final String SERVER_URL = "http://192.168.200.3:8077/";

     //    Staging URL
//    public static final String SERVER_URL = "http://sbaappynitty.co.in:6088/";

    //    Relese URL
//    public static final String SERVER_URL = "http://sbaappynitty.co.in:7055/";

    //    Relese BACKUP URL
//    public static final String SERVER_URL = "http://202.65.157.253:7055/";

    //   SBA Local URL
    public static final String SERVER_URL_SBA = "http://192.168.200.4:6077/";

    //   SBA Staging URL
//    public static final String SERVER_URL_SBA = "http://sbaappynitty.co.in:4088/";

    //   SBA Relese URL
//    public static final String SERVER_URL_SBA = "https://ghantagadi.in:444/";

    //   SBA Relese BACKUP URL
//    public static final String SERVER_URL_SBA = "http://202.65.157.253:4044/";

    //   SBA Relese BACKUP URL
//    public static final String SERVER_URL_SBA = "http://115.115.153.117:4044/";

    //    Genral Constant
    public static final String STATUS_SUCCESS = "Success";

    public static final String STATUS_ERROR = "error";
    public static final String STATUS_EXPIRED = "expired";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 666;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 444;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 555;

    //    location costant
    public static final String APP_ID = "AppId";
    public static final String APP_ID_GG = "AppIdGg";
    public static final String VERSION_CODE = "AppVersion";
    public static final String LOCATION = "LocationLatLog";
    public static final String COMPLAINT_STATUS_POJO = "ComplaintStatusPojo";
    public static final String GALLERY_IMG_POSITION = "GalleryImagePosition";
    public static final String GALLERY_IMG_POJO_LIST = "GalleryImagePojo";
    public static final String USER_ID = "UserId";
    public static final String FCM_NOTI = "IsFcmNotification";

    //    date formate
    private static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    private static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    private static final String SERVER_TIME_FORMATE = "HH:mm:ss";
    private static final String NOTIFY_DATE_TIME_FORMATE = "dd MMM  hh:mm a";
    private static final String MOBILE_DATE_TIME_FORMATE = "dd/MM/yyyy hh:mm a";
    private static final String MOBILE_DATE_FORMATE = "dd/MM/yyyy";
    private static final String MOBILE_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_EVENT_DATE_FORMATE = "dd-MM-yyyy";
    private static final String MOBILE_EVENT_DATE_FORMATE = "dd MMM yy";

    private static final String TAG = "AUtils";
    public static final String TYPE = "TypeOfApplication";
    public static final String CONTENT_TYPE = "application/json";

    public static final String LANGUAGE_NAME = "LanguageName";
    public static final String DEFAULT_LANGUAGE_NAME = "en";

    public static final String CURRENT_FRAGMENT_TAG = "CurrentFregmentTag";

    public static final String TAG_SERVER_ERROR = "OkHttp : RESPONSE ERROR";

    public static boolean isChangeLang = false;

    private AUtils() {
    }

    public static <S> S createService(Class<S> serviceClass, Gson gson, String url) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .readTimeout(3600, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .readTimeout(3600, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static String getCurrentDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(new Date());
    }

    public static String formatLocationTime(String time) {

        DateFormat serverTime = new SimpleDateFormat(SERVER_TIME_FORMATE, Locale.ENGLISH);
        DateFormat mobileTime = new SimpleDateFormat(MOBILE_TIME_FORMATE, Locale.ENGLISH);

        try{
            Date server = serverTime.parse(time);
            return mobileTime.format(server);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    public static String getSeverDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(new Date());
    }

    public static String getNotificationDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.NOTIFY_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(new Date());
    }

    public static String getServerDateFormate() {

        return AUtils.SERVER_DATE_FORMATE;
    }


    //app setting for permissions dialog
    public static void showPermissionDialog(Context context, String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context)
                .setTitle("Need Permission")
                .setMessage("App needs a permission to access " + message)
                .setPositiveButton("Grant", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }


    // Language Change of an application
    public static void changeLanguage(Activity context, String languageId) {

        QuickUtils.prefs.save(AUtils.LANGUAGE_NAME, languageId);

        LocaleHelper.setLocale(context, languageId);
    }

    public static void saveFcmId(String fcmId) {

        QuickUtils.prefs.save(MsUtils.FCM_ID, fcmId);
    }

    public static void deleteAllImagesInTheFolder() {

        File dir = new File(Environment.getExternalStorageDirectory()
                .toString() + "/Gram Panchayat");

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public static void showConfirmationDialog(Context context, String type, DialogInterface.OnClickListener
            positiveListener, @Nullable DialogInterface.OnClickListener negativeLisner){

        String message = "";
        String title = "";
        String positiveText = context.getResources().getString(R.string.yes_txt);
        String negativeText = context.getResources().getString(R.string.no_txt);

        if(type.equals(VERSION_CODE)){
            title = context.getResources().getString(R.string.update_title);
            message = context.getResources().getString(R.string.update_message);
            positiveText = context.getResources().getString(R.string.update_txt);
            negativeText = context.getResources().getString(R.string.no_thanks_txt);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positiveListener);

        if(negativeLisner != null){
            builder.setNegativeButton(negativeText, negativeLisner);
        }else{
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

        builder.create()
                .show();
    }

    // SharedPreferences Constant
    public interface PREFS {

        //    Save Data Constant
        String IS_USER_LOGIN = "UserLoginStatus";
        String USER_MASTER = "UserMasterPojo";
        String USER_ID = "UserIdORCreatedBy";
        //    pull list from server dates
        String WORK_CHECK_OUT_PULL_DATE = "WorkCheckOutPullDate";

        String SBA_AREA_LIST = "SbaAreaList";
        String SBA_USER_LIST = "SbaUserList";
        String SBA_ALL_USER_LIST = "SbaAllUserList";

        String COMPLENT_TYPE_POJO_LIST = "ComplaientTypeList";
        String MY_COMPLENT_STATUS_POJO_LIST = "MyComplentStatusPojoList";
        String REFERENCE_ID = "SBA_REFERENCE_ID";
    }

    // Color Constant
    public interface Colour {

        //    Color Constant
        String Green = "#8BC34A";
        String Yellow = "#FFC107";
        String Blue = "#3949AB";
        String Red = "#E53935";
        String Pink = "#E91E63";
        String SkyBlue = "#03A9F4";
        String Gray = "#607D8B";
        String Khakhi = "#BA9B47";
        String Orange ="#FF9800";
        String DarkGray = "#384259";
    }

    // View to Load Constant
    public interface ViewToLoad {

        String HouseIdView = "HouseIdView";
        String OtpView = "OtpView";
        String VerifyContactView = "VerifyContactView";
    }

    public static void insertNotification(DatabaseHelper helper, String message, String dateTime){
        helper.insertNotification(message, dateTime, Notification.STATUS_UNREAD);
    }

}
