package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.BookingActivity;
import com.appynitty.gp.activity.ClassificationActivity;
import com.appynitty.gp.activity.EPaymentActivity;
import com.appynitty.gp.activity.GhantaGadiTrackerActivity;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.activity.MandiDetailsActivity;
import com.appynitty.gp.activity.MapsMarkerActivity;
import com.appynitty.gp.activity.UtilityActivity;
import com.appynitty.gp.activity.WeatherActivity;
import com.appynitty.gp.activity.WebsiteActivity;
import com.appynitty.gp.adapter.MainMenuAdapter;
import com.appynitty.gp.pojo.MenuPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class MenuFragment extends MyFragemtV4 {

    private static final String TAG = "MenuFragment";
    private View view;
    private Context context;
    private GridView menuGridView;
    private Fragment mFragment = null;
    private FragmentManager mFragmentManager;
    private MainMenuAdapter mainMenuAdaptor;

    public static Fragment newInstance() {

        MenuFragment menuFragment = new MenuFragment();
        return menuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_fragmet, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {
        genrateId();
        initData();
        registerEvents();
        initToolbar();
        loadFragment();

    }

    private void loadFragment() {

        if (Prefs.getBoolean(AUtils.FCM_NOTI, false)) {

//            mFragment = MyComplentStatusFragment.newInstance();
//            mFragmentManager.beginTransaction().addToBackStack(null)
//                    .replace(R.id.content_frame, mFragment).commit();
            menuOnClick(AUtils.MenuIdConstants.Complent_Status_Tab);

            Prefs.putBoolean(AUtils.FCM_NOTI, false);
        }
    }

    private void initToolbar() {

        if (!AUtils.isNullString(Prefs.getString(AUtils.GP_NAME, "")) &&
                !AUtils.isNullString(Prefs.getString(AUtils.GP_NAME_MAR, ""))) {

//            if (Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            if (Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageIDConstants.ENGLISH)) {
                ((HomeActivity) getActivity()).setTitleActionBar(Prefs.getString(AUtils.GP_NAME, ""));
            } else {
                ((HomeActivity) getActivity()).setTitleActionBar(Prefs.getString(AUtils.GP_NAME_MAR, ""));
            }
        } else {

            ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.app_name));
        }
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleIcon(R.mipmap.ic_launcher);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 1);
        mFragmentManager = getFragmentManager();
        menuGridView = view.findViewById(R.id.menuGV);
    }

    private void registerEvents() {

//        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                menuOnClick(position);
//            }
//        });

        mainMenuAdaptor.setMenuItemClickListner(new MainMenuAdapter.MenuItemClickListner() {
            @Override
            public void onItemClick(String menuId, int position) {
                menuOnClick(menuId);
            }
        });

    }

//    private void menuOnClick(int position) {
    private void menuOnClick(String menuId) {

        mFragment = null;

        AUtils.setMenuNavigation(AUtils.MenuIdConstants.Main_Menu_Dashboard, menuId);

//        switch (mainMenuAdaptor.getMenuId(position)) {
        switch (menuId) {
            case AUtils.MenuIdConstants.Our_Gram_Panchayat:
                mFragment = OurGramPanchayatFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Work_Check_Out:
                mFragment = WorkCheckOutFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Young_Bussiness:
                mFragment = YoungBusinessFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Young_Jobs:
                mFragment = YoungJobApplyFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Cleaning_Complaints:
                mFragment = CleaningCompleantDetailsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Water_Complaints:
                mFragment = WaterCompleantDetailsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Light_Complaints:
                mFragment = LightCompleantDetailsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Maintenance_Complaints:
                mFragment = MentananceCompleantDetailsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Construction_Complaints:
                mFragment = ConstructionCompleantDetailsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Complent_Status_Tab:
                mFragment = MyComplentStatusFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Samaj_Bhawan_Booking:
                mFragment = SamajBhawanBookingFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Tanker_Booking:
                mFragment = TankerBookingFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Property_Tax:
                mFragment = PropertyTaxFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Mandi_Details:
                context.startActivity(new Intent(context, MandiDetailsActivity.class));
                break;
            case AUtils.MenuIdConstants.Upcoming_Events:
                mFragment = UpcomingEventsFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Classification_Classified:
                context.startActivity(new Intent(context, ClassificationActivity.class));
                break;
            case AUtils.MenuIdConstants.Government_Schemes:
                mFragment = SchemesFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Certificate:
                mFragment = CertificateFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Smart_Suggestion:
                mFragment = SuggestionFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Photo_Video_Gallery:
                mFragment = GalleryFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Online_EPayment:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, EPaymentActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Online_Booking:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, BookingActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Social_Network_Media:
                mFragment = SocialNetworkFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Online_Website:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, WebsiteActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Online_MAP:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, MapsMarkerActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Ghanta_Gadi_Tracker:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, GhantaGadiTrackerActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Utility:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    startActivity(new Intent(context, UtilityActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Weather:
                if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
                    context.startActivity(new Intent(context, WeatherActivity.class));
                } else {
                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Contact_Us:
                mFragment = ContactUsFragment.newInstance();
                break;
        }

        if (!AUtils.isNull(mFragment)) {
//            mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content_frame, mFragment).commit();
        }

    }

    private void initData() {

        Log.e(TAG,getResources().getString(R.string.our_gram_panchayat));
        List<MenuPojo> menuPojoList = AUtils.getMenuPojoList(context);

        mainMenuAdaptor = new MainMenuAdapter(context, menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);
        checkOpenMenu();
    }

    private void checkOpenMenu() {
        if(!AUtils.isNull(AUtils.getMenuNavigation()) && AUtils.getMenuNavigation().size() > 0 && AUtils.isRecreate){
            String key = AUtils.getMenuNavigation(AUtils.MenuIdConstants.Main_Menu_Dashboard);
            menuOnClick(key);
        }
    }
}