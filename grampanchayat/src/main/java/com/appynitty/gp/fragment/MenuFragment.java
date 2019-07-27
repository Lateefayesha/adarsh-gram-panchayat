package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


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
        registerEvents();
        initData();
        initToolbar();
        loadFragment();

    }

    private void loadFragment() {

        if (QuickUtils.prefs.getBoolean(AUtils.FCM_NOTI, false)) {

            mFragment = MyComplentStatusFragment.newInstance();
            mFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content_frame, mFragment).commit();

            QuickUtils.prefs.save(AUtils.FCM_NOTI, false);
        }
    }

    private void initToolbar() {

        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.GP_NAME, "")) &&
                !AUtils.isNullString(QuickUtils.prefs.getString(AUtils.GP_NAME_MAR, ""))) {

            if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
                ((HomeActivity) getActivity()).setTitleActionBar(QuickUtils.prefs.getString(AUtils.GP_NAME, ""));
            } else {
                ((HomeActivity) getActivity()).setTitleActionBar(QuickUtils.prefs.getString(AUtils.GP_NAME_MAR, ""));
            }
        } else {

            ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.app_name));
        }
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleIcon(R.mipmap.ic_launcher);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 1);
        mFragmentManager = getFragmentManager();
        menuGridView = view.findViewById(R.id.menuGV);
    }

    private void registerEvents() {

        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                menuOnClick(position);
            }
        });

    }

    private void menuOnClick(int position) {

        mFragment = null;

        switch (mainMenuAdaptor.getMenuId(position)) {
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
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, EPaymentActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Online_Booking:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, BookingActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Social_Network_Media:
                mFragment = SocialNetworkFragment.newInstance();
                break;
            case AUtils.MenuIdConstants.Online_Website:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, WebsiteActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Online_MAP:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, MapsMarkerActivity.class));

                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Ghanta_Gadi_Tracker:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, GhantaGadiTrackerActivity.class));

                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Utility:
                if (AUtils.isNetWorkAvailable(context)) {

                    startActivity(new Intent(context, UtilityActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case AUtils.MenuIdConstants.Weather:
                if (AUtils.isNetWorkAvailable(context)) {

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

        List<MenuPojo> menuPojoList = new ArrayList<MenuPojo>();

        Log.e(TAG,getResources().getString(R.string.our_gram_panchayat));

        menuPojoList.add(new MenuPojo(getString(R.string.our_gram_panchayat), AUtils.Colour.Green,
                AUtils.MenuIdConstants.Our_Gram_Panchayat, ContextCompat.getDrawable(context, R.drawable.ic_our_gp)));
        menuPojoList.add(new MenuPojo(getString(R.string.work_check_out), AUtils.Colour.Yellow,
                AUtils.MenuIdConstants.Work_Check_Out, ContextCompat.getDrawable(context, R.drawable.ic_works_of_grampanchayat)));

        menuPojoList.add(new MenuPojo(getString(R.string.young_business), AUtils.Colour.Blue,
                AUtils.MenuIdConstants.Young_Bussiness, ContextCompat.getDrawable(context, R.drawable.ic_self_employment)));
        menuPojoList.add(new MenuPojo(getString(R.string.young_jobs), AUtils.Colour.Red,
                AUtils.MenuIdConstants.Young_Jobs, ContextCompat.getDrawable(context, R.drawable.ic_rojgar)));

        menuPojoList.add(new MenuPojo(getString(R.string.cleaning_compleant), AUtils.Colour.Pink,
                AUtils.MenuIdConstants.Cleaning_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_1_cleaning_complaint)));
        menuPojoList.add(new MenuPojo(getString(R.string.water_compleant), AUtils.Colour.SkyBlue,
                AUtils.MenuIdConstants.Water_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_dirnking_water)));

        menuPojoList.add(new MenuPojo(getString(R.string.light_compleant), AUtils.Colour.Gray,
                AUtils.MenuIdConstants.Light_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_light_solar)));
        menuPojoList.add(new MenuPojo(getString(R.string.maintenance_compleant), AUtils.Colour.Khakhi,
                AUtils.MenuIdConstants.Maintenance_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_maintenance)));

        menuPojoList.add(new MenuPojo(getString(R.string.construction_compleant), AUtils.Colour.Orange,
                AUtils.MenuIdConstants.Construction_Complaints, ContextCompat.getDrawable(context, R.drawable.ic_construction)));
        menuPojoList.add(new MenuPojo(getString(R.string.complent_status_tab), AUtils.Colour.DarkGray,
                AUtils.MenuIdConstants.Complent_Status_Tab, ContextCompat.getDrawable(context, R.drawable.ic_my_grivvance)));

        menuPojoList.add(new MenuPojo(getString(R.string.samaj_bavan_booking), AUtils.Colour.Green,
                AUtils.MenuIdConstants.Samaj_Bhawan_Booking, ContextCompat.getDrawable(context, R.drawable.ic_samaj_bhavan)));
        menuPojoList.add(new MenuPojo(getString(R.string.tanker_booking), AUtils.Colour.SkyBlue,
                AUtils.MenuIdConstants.Tanker_Booking, ContextCompat.getDrawable(context, R.drawable.ic_water_tank)));

        menuPojoList.add(new MenuPojo(getString(R.string.property_tax), AUtils.Colour.DarkGray,
                AUtils.MenuIdConstants.Property_Tax, ContextCompat.getDrawable(context, R.drawable.ic_property_tax)));

        menuPojoList.add(new MenuPojo(getString(R.string.mandi), AUtils.Colour.Yellow,
                AUtils.MenuIdConstants.Mandi_Details, ContextCompat.getDrawable(context, R.drawable.ic_mandi)));
        menuPojoList.add(new MenuPojo(getString(R.string.upcoming_programs),AUtils.Colour.Pink,
                AUtils.MenuIdConstants.Upcoming_Events, ContextCompat.getDrawable(context, R.drawable.ic_upcoming_program)));

        menuPojoList.add(new MenuPojo(getString(R.string.classified), AUtils.Colour.Blue,
                AUtils.MenuIdConstants.Classification_Classified, ContextCompat.getDrawable(context, R.drawable.ic_classified)));
        menuPojoList.add(new MenuPojo(getString(R.string.schemes), AUtils.Colour.Red,
                AUtils.MenuIdConstants.Government_Schemes, ContextCompat.getDrawable(context, R.drawable.ic_goverment_scheme)));

        menuPojoList.add(new MenuPojo(getString(R.string.certificate), AUtils.Colour.Gray,
                AUtils.MenuIdConstants.Certificate, ContextCompat.getDrawable(context, R.drawable.ic_important_certificates)));
        menuPojoList.add(new MenuPojo(getString(R.string.suggestion_tab), AUtils.Colour.Khakhi,
                AUtils.MenuIdConstants.Smart_Suggestion, ContextCompat.getDrawable(context, R.drawable.ic_smart_suggestion)));

        menuPojoList.add(new MenuPojo(getString(R.string.gallery), AUtils.Colour.Orange,
                AUtils.MenuIdConstants.Photo_Video_Gallery, ContextCompat.getDrawable(context, R.drawable.ic_gallery)));
        menuPojoList.add(new MenuPojo(getString(R.string.e_payment), AUtils.Colour.DarkGray,
                AUtils.MenuIdConstants.Online_EPayment, ContextCompat.getDrawable(context, R.drawable.ic_epayment)));

        menuPojoList.add(new MenuPojo(getString(R.string.booking), AUtils.Colour.Green,
                AUtils.MenuIdConstants.Online_Booking, ContextCompat.getDrawable(context, R.drawable.ic_booking)));
        menuPojoList.add(new MenuPojo(getString(R.string.social_media), AUtils.Colour.Yellow,
                AUtils.MenuIdConstants.Social_Network_Media, ContextCompat.getDrawable(context, R.drawable.ic_social_media)));

        menuPojoList.add(new MenuPojo(getString(R.string.website), AUtils.Colour.Blue,
                AUtils.MenuIdConstants.Online_Website, ContextCompat.getDrawable(context, R.drawable.ic_website)));
        menuPojoList.add(new MenuPojo(getString(R.string.map), AUtils.Colour.Red,
                AUtils.MenuIdConstants.Online_MAP, ContextCompat.getDrawable(context, R.drawable.ic_map)));

//        menuPojoList.add(new MenuPojo(getString(R.string.title_activity_ghanta_gadi_tracker), AUtils.Colour.Khakhi, AUtils.MenuIdConstants.Ghanta_Gadi_Tracker));

        menuPojoList.add(new MenuPojo(getString(R.string.utility), AUtils.Colour.Pink,
                AUtils.MenuIdConstants.Utility, ContextCompat.getDrawable(context, R.drawable.ic_utility)));
        menuPojoList.add(new MenuPojo(getString(R.string.weather), AUtils.Colour.SkyBlue,
                AUtils.MenuIdConstants.Weather, ContextCompat.getDrawable(context, R.drawable.ic_weather)));

        menuPojoList.add(new MenuPojo(getString(R.string.contact_us), AUtils.Colour.Gray,
                AUtils.MenuIdConstants.Contact_Us, ContextCompat.getDrawable(context, R.drawable.ic_contact_us)));

        mainMenuAdaptor = new MainMenuAdapter(context, menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);
    }
}