package com.appynitty.gp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Environment;

import com.google.gson.Gson;
import com.mithsoft.lib.utils.MsUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import quickutils.core.QuickUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by MiTHUN on 5/2/18.
 */

public class AUtils extends MsUtils {

    //    Local URL
//    public static final String SERVER_URL = "http://192.168.200.3:8077/";

    //    Staging URL
    public static final String SERVER_URL = "http://115.115.153.117:6088/";

    //    Relese URL
//    public static final String SERVER_URL = "http://115.115.153.117:7055/";


    //    Genral Constant
    public static final String STATUS_SUCCESS = "Success";

    public static final String STATUS_ERROR = "error";
    public static final String STATUS_EXPIRED = "expired";
    public static final int CAMERA_CAPTURE = 111;
    public static final int PIC_CROP = 222;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 333;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 444;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 555;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 666;
    //    location costant
    public static final String START_PATH_IMAGE = "/storage/";
    public static final String GALLERY_POJO = "GalleryPhotoPojo";
    public static final String GALLERY_IMG_PATH = "GalleryImagePath";
    public static final String APP_ID = "AppId";
    public static final String DEFAULT_LANGUAGE_ID = "2";
    public static final String LANGUAGE_ID = "LanguageId";
    public static final String GALLERY_IMG_POSITION = "GalleryImagePosition";
    public static final String GALLERY_IMG_POJO_LIST = "GalleryImagePojo";
    public static final String SOCIAL_NETWORK_POJO = "SocialneworkPojo";
    public static final String UTILITY_URL = "UtilityWebUrl";
    public static final String UTILITY_TITLE = "UtilityTitle";
    public static final String FRAGMENT_COUNT = "FragmentCountForBackOpration";
    public static final String LOCATION = "LocationLatLog";
    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;
    public static final String APP_LOCATION = "LocationOfTheVillage";
    public static final String YOCC_NO = "YoccNoForOrg";
    public static final String GP_NAME = "GramPanchayatName";
    public static final String GP_NAME_MAR = "GramPanchayatNameInMarathi";
    public static final String GP_DETAILS = "GramPanchayatDetailDistrictTaluka";
    public static final String USER_ID = "UserId";
    public static final String FCM_NOTI = "IsFcmNotification";
    public static final String COMPLAINT_STATUS_POJO = "ComplaintStatusPojo";
    public static final String GP_WEATHER_NAME = "GPWeatherScreenName";
    public static final String WEBSITE_ENG = "Website";
    public static final String WEBSITE_MAR = "WebsiteMarathi";
    //    date formate
    private static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    private static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    //    public static final String SERVER_DATE_TIME_FORMATE = "dd-MM-yyyy HH:mm:ss";
    //    public static final String SERVER_DATE_FORMATE = "dd-MM-yyyy";
    private static final String SERVER_TIME_FORMATE = "HH:mm:ss";
    private static final String MOBILE_DATE_TIME_FORMATE = "dd/MM/yyyy hh:mm a";
    private static final String MOBILE_DATE_FORMATE = "dd/MM/yyyy";
    private static final String MOBILE_TIME_FORMATE = "hh:mm a";
    private static final String TAG = "AUtils";


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

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE);
        return format.format(new Date());
    }


    //app setting for permissions dialog
    public static void showPermissionDialog(Context context, String message, DialogInterface.OnClickListener okListener) {

        new android.support.v7.app.AlertDialog.Builder(context)
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

    public static String getYoutubeVedioId(String youtubeVideoUrl) {

        String videoId = "";
        if (youtubeVideoUrl != null && youtubeVideoUrl.trim().length() > 0 && youtubeVideoUrl.startsWith("http")) {

            String expression = "^.*((youtu.be" + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeVideoUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    videoId = groupIndex1;
            }
        }
        return (videoId);
    }

    // Language Change of an application
    public static void changeLanguage(Activity context, int languageId) {

        String languageStr = "";
        switch (languageId) {
            case 1:
                languageStr = "en";
                break;
            case 2:
                languageStr = "mi";
                break;
            case 3:
                languageStr = "hi";
                break;
            case 4:
                languageStr = "gu";
                break;
            case 5:
                languageStr = "pa";
                break;
        }

        Locale locale = new Locale(languageStr);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
        context.onConfigurationChanged(config);

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

    public static void saveFcmId(String fcmId) {

        QuickUtils.prefs.save(MsUtils.FCM_ID, fcmId);
    }

    // SharedPreferences Constant
    public interface PREFS {

        //    Save Data Constant
        String IS_USER_LOGIN = "UserLoginStatus";
        String USER_MASTER = "UserMasterPojo";
        String USER_ID = "UserIdORCreatedBy";
        String ORG_ID = "OrganizationId";
        String CLIENT_MASTER_POJO = "ClientMasterPojo";


        //    pull list from server dates
        String WORK_CHECK_OUT_PULL_DATE = "WorkCheckOutPullDate";


        //    pull list from server object
        String WORK_CHECK_OUT_POJO_LIST = "WorkCheckOutPullList";
        String YOUNG_BUSINESS_POJO_LIST = "YoungBusinessPojoList";
        String SCHEMES_POJO_LIST = "SchemesPojoList";
        String MY_COMPLENT_STATUS_POJO_LIST = "MyComplentStatusPojoList";
        String OUR_GRAM_PANCHAYT_POJO_LIST = "OutGramPanchayatPojoList";
        String IMAGES_GALLERY_POJO_LIST = "GalleryImagesList";
        String VEDIO_GALLERY_POJO_LIST = "GalleryVedioList";
        String SOCIAL_NETWORK_POJO_LIST = "SocialNetworkPojoList";
        String CONTACT_US_POJO = "ConatctUsPojo";
        String CONTACT_US_MEMBER_POJO_LIST = "ContactUsMemberList";
        String CERTIFICATE_POJO_LIST = "CertificatePojoList";

        String STATE_POJO_LIST = "StatePojoList";
        String DISTRICT_POJO_LIST = "DistrictPojoList";
        String TAHSIL_POJO_LIST = "TahsilPojoList";
        String GRAM_PANCHAYAT_LIST = "GramPanchayatList";
    }
}
