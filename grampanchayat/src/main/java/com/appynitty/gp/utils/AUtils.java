package com.appynitty.gp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.LanguagePojo;
import com.appynitty.gp.pojo.MenuPojo;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.CommonUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MiTHUN on 5/2/18.
// */

public class AUtils extends CommonUtils {

    //    Local URL
//    public static final String SERVER_URL = "http://192.168.200.3:8077/";

    //    Staging URL
//    public static final String SERVER_URL = "http://sbaappynitty.co.in:6088/";

    //    Relese URL
    public static final String SERVER_URL = "http://sbaappynitty.co.in:7055/";

    //    Relese BACKUP URL
//    public static final String SERVER_URL = "http://202.65.157.253:7055/";

    //   SBA Local URL
//    public static final String SERVER_URL_SBA = "http://192.168.200.4:6077/";

    //   SBA Staging URL
//    public static final String SERVER_URL_SBA = "http://sbaappynitty.co.in:4088/";

    //   SBA Relese URL
    public static final String SERVER_URL_SBA = "https://ghantagadi.in:444/";

    //   SBA Relese BACKUP URL
//    public static final String SERVER_URL_SBA = "http://202.65.157.253:4044/";

    public static Application mApplicationConstant;

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
    public static final String APP_ID_GG = "AppIdGg";
    public static final String VERSION_CODE = "AppVersion";

    public static final String DEFAULT_LANGUAGE_ID = LanguageConstants.MARATHI;
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
    public static final String GP_NAME_HINDI = "GramPanchayatNameInHindi";
    public static final String  GP_DETAILS = "GramPanchayatDetailDistrictTaluka";
    public static final String USER_ID = "UserId";
    public static final String FCM_NOTI = "IsFcmNotification";
    public static final String COMPLAINT_STATUS_POJO = "ComplaintStatusPojo";
    public static final String GP_WEATHER_NAME = "GPWeatherScreenName";
    public static final String WEBSITE_ENG = "Website";
    public static final String WEBSITE_MAR = "WebsiteMarathi";
    public static final String WEBSITE_HI = "WebsiteHindi";
    public static final String DEFAULT_LANGUAGE_NAME = LanguageConstants.MARATHI;
    //    date formate
    private static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    private static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    //    public static final String SERVER_DATE_TIME_FORMATE = "dd-MM-yyyy HH:mm:ss";
    //    public static final String SERVER_DATE_FORMATE = "dd-MM-yyyy";
    private static final String SERVER_TIME_FORMATE = "HH:mm:ss";
    private static final String MOBILE_DATE_TIME_FORMATE = "dd/MM/yyyy hh:mm a";
    private static final String MOBILE_DATE_FORMATE = "dd/MM/yyyy";
    private static final String MOBILE_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_EVENT_DATE_FORMATE = "dd-MM-yyyy";
    private static final String MOBILE_EVENT_DATE_FORMATE = "dd MMM yy";

    private static final String TAG = "AUtils";
    public static final String TYPE = "TypeOfApplication";

    public static final String LANGUAGE_NAME = "LanguageName";

    public static final String FCM_ID = "FirebaseCloudMessageId";

    public static final String CURRENT_FRAGMENT_TAG = "CurrentFregmentTag";
    public static boolean isRecreate = false;

    private static List<MenuPojo> menuPojoList;
    private static ArrayList<LanguagePojo> languagePojoList;

    private static HashMap<String, String> menuNavigation = null;
    private static ArrayList<String> menuNavigationList = null;

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

    public static String getEventDisplayDate(String dateTime) {

        DateFormat currentFormat = new SimpleDateFormat(SERVER_EVENT_DATE_FORMATE, Locale.ENGLISH);
        DateFormat displayFormat = new SimpleDateFormat(MOBILE_EVENT_DATE_FORMATE, Locale.ENGLISH);

        Date givenDate = null;
        try {
            givenDate = currentFormat.parse(dateTime);
            return displayFormat.format(givenDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSeverDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(new Date());
    }

    public static String getServerDateFormate() {

        return AUtils.SERVER_DATE_FORMATE;
    }

    public static void changeLanguage(Activity context, String languageId) {
        Prefs.putString(AUtils.LANGUAGE_NAME, languageId);
        Prefs.putString(AUtils.LANGUAGE_ID, AUtils.getLanguageId(languageId));
        LocaleHelper.setLocale(context, languageId);
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

    public static String getYoutubeVideoId(String youtubeVideoUrl) {

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
//    public static void changeLanguage(Activity context, int languageId) {
//
//        if(languageId > 0){
//            Prefs.putString(AUtils.LANGUAGE_ID, String.valueOf(languageId));
//        }
//
//        String languageStr = "";
//        switch (languageId) {
//            case 1:
//                languageStr = "en";
//                Prefs.putString(AUtils.LANGUAGE_NAME,languageStr);
//                break;
//            case 2:
////                languageStr = "mr";
//                languageStr = "hi";
//                Prefs.putString(AUtils.LANGUAGE_NAME,languageStr);
//                break;
//            case 3:
//                languageStr = "hi";
//                Prefs.putString(AUtils.LANGUAGE_NAME,languageStr);
//                break;
//            case 4:
//                languageStr = "gu";
//                Prefs.putString(AUtils.LANGUAGE_NAME,languageStr);
//                break;
//            case 5:
//                languageStr = "pa";
//                Prefs.putString(AUtils.LANGUAGE_NAME,languageStr);
//                break;
//        }
//
//        LocaleHelper.setLocale(context, languageStr);
//
////        Locale locale = new Locale(languageStr);
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            Locale.setDefault(Locale.Category.DISPLAY, locale);
////        } else {
////            Locale.setDefault(locale);
////        }
////        Configuration config = new Configuration();
////        config.locale = locale;
////        context.getApplicationContext().getResources().updateConfiguration(config, null);
////        context.onConfigurationChanged(config);
//
//    }

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

        Prefs.putString(FCM_ID, fcmId);
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

    public static void setMenuNavigationList(String value) {
        if(AUtils.isNull(menuNavigationList))
            menuNavigationList = new ArrayList<>();

        if(menuNavigationList.indexOf(value) < 0)
            menuNavigationList.add(value);
    }

    public static Boolean menuNavigationListHasItem(String key) {
        if(!AUtils.isNull(menuNavigationList) && menuNavigationList.size() > 0)
            return menuNavigationList.indexOf(key) >= 0;

        return false;
    }

    public static void removeMenuNavigationListValue(String value){
        if(!AUtils.isNull(menuNavigationList))
            menuNavigationList.remove(value);
    }

    public static void setMenuNavigation(String key, String value){
        if(menuNavigation == null)
            menuNavigation = new HashMap<>();
        menuNavigation.put(key, value);
    }

    @Nullable
    public static HashMap<String, String> getMenuNavigation(){
        return menuNavigation;
    }

    public static String getMenuNavigation(String key){
        return (menuNavigation.containsKey(key)) ? menuNavigation.get(key): "";
    }

    public static void removeMenuNavigationValue(String parent, String value){
        if(menuNavigation.containsValue(value))
            menuNavigation.remove(parent);
    }

    public static void setMenuPojoList(List<MenuPojo> list) {
        AUtils.menuPojoList = list;
    }

    public static List<MenuPojo> getMenuPojoList(Context context) {

        if(AUtils.isNull(menuPojoList)){

            menuPojoList = new ArrayList<MenuPojo>();

            menuPojoList.add(new MenuPojo(R.string.our_gram_panchayat, AUtils.Colour.Green,
                    AUtils.MenuIdConstants.Our_Gram_Panchayat, ContextCompat.getDrawable(context, R.drawable.ic_our_gp)));
            menuPojoList.add(new MenuPojo(R.string.work_check_out, AUtils.Colour.Yellow,
                    AUtils.MenuIdConstants.Work_Check_Out, ContextCompat.getDrawable(context, R.drawable.ic_works_of_grampanchayat)));

            menuPojoList.add(new MenuPojo(R.string.young_business, AUtils.Colour.Blue,
                    AUtils.MenuIdConstants.Young_Bussiness, ContextCompat.getDrawable(context, R.drawable.ic_self_employment)));
            menuPojoList.add(new MenuPojo(R.string.young_jobs, AUtils.Colour.Red,
                    AUtils.MenuIdConstants.Young_Jobs, ContextCompat.getDrawable(context, R.drawable.ic_rojgar)));

            menuPojoList.add(new MenuPojo(R.string.cleaning_compleant, AUtils.Colour.Pink,
                    AUtils.MenuIdConstants.Cleaning_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_1_cleaning_complaint)));
            menuPojoList.add(new MenuPojo(R.string.water_compleant, AUtils.Colour.SkyBlue,
                    AUtils.MenuIdConstants.Water_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_dirnking_water)));

            menuPojoList.add(new MenuPojo(R.string.light_compleant, AUtils.Colour.Gray,
                    AUtils.MenuIdConstants.Light_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_light_solar)));
            menuPojoList.add(new MenuPojo(R.string.maintenance_compleant, AUtils.Colour.Khakhi,
                    AUtils.MenuIdConstants.Maintenance_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_maintenance)));

            menuPojoList.add(new MenuPojo(R.string.construction_compleant, AUtils.Colour.Orange,
                    AUtils.MenuIdConstants.Construction_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_construction)));
            menuPojoList.add(new MenuPojo(R.string.complent_status_tab, AUtils.Colour.DarkGray,
                    AUtils.MenuIdConstants.Complent_Status_Tab, ContextCompat.getDrawable(context, R.drawable.ic_my_grivvance)));

            menuPojoList.add(new MenuPojo(R.string.samaj_bavan_booking, AUtils.Colour.Green,
                    AUtils.MenuIdConstants.Samaj_Bhawan_Booking, ContextCompat.getDrawable(context, R.drawable.ic_samaj_bhavan)));
            menuPojoList.add(new MenuPojo(R.string.tanker_booking, AUtils.Colour.SkyBlue,
                    AUtils.MenuIdConstants.Tanker_Booking, ContextCompat.getDrawable(context, R.drawable.ic_water_tank)));

            menuPojoList.add(new MenuPojo(R.string.property_tax, AUtils.Colour.DarkGray,
                    AUtils.MenuIdConstants.Property_Tax, ContextCompat.getDrawable(context, R.drawable.ic_property_tax)));

        menuPojoList.add(new MenuPojo(R.string.mandi, AUtils.Colour.Yellow,
                AUtils.MenuIdConstants.Mandi_Details, ContextCompat.getDrawable(context, R.drawable.ic_mandi)));
            menuPojoList.add(new MenuPojo(R.string.upcoming_programs,AUtils.Colour.Pink,
                    AUtils.MenuIdConstants.Upcoming_Events, ContextCompat.getDrawable(context, R.drawable.ic_upcoming_program)));

            menuPojoList.add(new MenuPojo(R.string.classified, AUtils.Colour.Blue,
                    AUtils.MenuIdConstants.Classification_Classified, ContextCompat.getDrawable(context, R.drawable.ic_classified)));
            menuPojoList.add(new MenuPojo(R.string.schemes, AUtils.Colour.Red,
                    AUtils.MenuIdConstants.Government_Schemes, ContextCompat.getDrawable(context, R.drawable.ic_goverment_scheme)));

            menuPojoList.add(new MenuPojo(R.string.certificate, AUtils.Colour.Gray,
                    AUtils.MenuIdConstants.Certificate, ContextCompat.getDrawable(context, R.drawable.ic_important_certificates)));
            menuPojoList.add(new MenuPojo(R.string.suggestion_tab, AUtils.Colour.Khakhi,
                    AUtils.MenuIdConstants.Smart_Suggestion, ContextCompat.getDrawable(context, R.drawable.ic_smart_suggestion)));

            menuPojoList.add(new MenuPojo(R.string.gallery, AUtils.Colour.Orange,
                    AUtils.MenuIdConstants.Photo_Video_Gallery, ContextCompat.getDrawable(context, R.drawable.ic_gallery)));
            menuPojoList.add(new MenuPojo(R.string.e_payment, AUtils.Colour.DarkGray,
                    AUtils.MenuIdConstants.Online_EPayment, ContextCompat.getDrawable(context, R.drawable.ic_epayment)));

            menuPojoList.add(new MenuPojo(R.string.booking, AUtils.Colour.Green,
                    AUtils.MenuIdConstants.Online_Booking, ContextCompat.getDrawable(context, R.drawable.ic_booking)));
            menuPojoList.add(new MenuPojo(R.string.social_media, AUtils.Colour.Yellow,
                    AUtils.MenuIdConstants.Social_Network_Media, ContextCompat.getDrawable(context, R.drawable.ic_social_media)));

            menuPojoList.add(new MenuPojo(R.string.website, AUtils.Colour.Blue,
                    AUtils.MenuIdConstants.Online_Website, ContextCompat.getDrawable(context, R.drawable.ic_website)));
            menuPojoList.add(new MenuPojo(R.string.map, AUtils.Colour.Red,
                    AUtils.MenuIdConstants.Online_MAP, ContextCompat.getDrawable(context, R.drawable.ic_map)));

            menuPojoList.add(new MenuPojo(R.string.title_activity_ghanta_gadi_tracker, AUtils.Colour.Khakhi,
                    AUtils.MenuIdConstants.Ghanta_Gadi_Tracker, ContextCompat.getDrawable(context, R.drawable.ic_truck_location)));

            menuPojoList.add(new MenuPojo(R.string.utility, AUtils.Colour.Pink,
                    AUtils.MenuIdConstants.Utility, ContextCompat.getDrawable(context, R.drawable.ic_utility)));
            menuPojoList.add(new MenuPojo(R.string.weather, AUtils.Colour.SkyBlue,
                    AUtils.MenuIdConstants.Weather, ContextCompat.getDrawable(context, R.drawable.ic_weather)));

            menuPojoList.add(new MenuPojo(R.string.contact_us, AUtils.Colour.Gray,
                    AUtils.MenuIdConstants.Contact_Us, ContextCompat.getDrawable(context, R.drawable.ic_contact_us)));
        }

        return menuPojoList;
    }

    public static void setLanguagePojoList(ArrayList<LanguagePojo> pojo){
        languagePojoList = pojo;
    }

    public static ArrayList<LanguagePojo> getLanguagePojoList() {
        return languagePojoList;
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
        String MANDI_POJO_LIST = "MandiPullList";
        String UPCOMING_EVENT_POJO_LIST = "UpcomingEventPullList";
        String CLASSIFICATION_POJO_LIST = "ClassificationPullList";

        String STATE_POJO_LIST = "StatePojoList";
        String DISTRICT_POJO_LIST = "DistrictPojoList";
        String TAHSIL_POJO_LIST = "TahsilPojoList";
        String GRAM_PANCHAYAT_LIST = "GramPanchayatList";
        String COMPLENT_TYPE_POJO_LIST = "ComplaientTypeList";
        String PROPERTY_TAX_DETAILS_POJO_LIST = "PropertyTaxDetailsList";

        String SBA_AREA_LIST = "SbaAreaList";
        String SBA_USER_LIST = "SbaUserList";
        String SBA_ALL_USER_LIST = "SbaAllUserList";
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

    public interface MenuIdConstants{
        String Main_Menu_Dashboard = "Main_Menu_Dashboard";
        String Our_Gram_Panchayat = "Our_Gram_Panchayat";
        String Work_Check_Out = "Work_Check_Out";

        String Young_Bussiness = "Young_Bussiness";
        String Young_Bussiness_Apply_Form = "Young_Bussiness_Apply_Form";

        String Young_Jobs = "Young_Jobs";

        String Cleaning_Complaints = "Cleaning_Complaints";
        String Cleaning_Complaints_Register_Form = "Cleaning_Complaints_Register_Form";

        String Water_Complaints = "Water_Complaints";
        String Water_Complaints_Register_Form = "Water_Complaints_Register_Form";

        String Light_Complaints = "Light_Complaints";
        String Light_Complaints_Register_Form = "Light_Complaints_Register_Form";

        String Maintenance_Complaints = "Maintenance_Complaints";
        String Maintenance_Complaints_Register_Form = "Maintenance_Complaints_Register_Form";

        String Construction_Complaints = "Construction_Complaints";
        String Construction_Complaints_Register_Form = "Construction_Complaints_Register_Form";

        String Complent_Status_Tab = "Complent_Status_Tab";
        String Samaj_Bhawan_Booking = "Samaj_Bhawan_Booking";
        String Tanker_Booking = "Tanker_Booking";
        String Property_Tax = "Property_Tax";
        String Mandi_Details = "Mandi_Details";
        String Upcoming_Events = "Upcoming_Events";
        String Classification_Classified = "Classification_Classified";
        String Government_Schemes = "Government_Schemes";
        String Certificate = "Certificate";
        String Smart_Suggestion = "Smart_Suggestion";
        String Photo_Video_Gallery = "Photo_Video_Gallery";
        String Online_EPayment = "Online_EPayment";
        String Online_Booking = "Online_Booking";
        String Social_Network_Media = "Social_Network_Media";
        String Online_Website = "Online_Website";
        String Online_MAP = "Online_MAP";
        String Ghanta_Gadi_Tracker = "Ghanta_Gadi_Tracker";
        String Utility = "Utility";
        String Weather = "Weather";
        String Contact_Us = "Contact_Us";
    }
}
