package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class MenuFragmentLess extends MyFragemtV4 {

    private static final String TAG = "MenuFragment";
    private View view;
    private Context context;
    private GridView menuGridView;
    private Fragment mFragment = null;
    private FragmentManager mFragmentManager;

    public static Fragment newInstance() {

        MenuFragmentLess menuFragment = new MenuFragmentLess();
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

        switch (position) {
            case 0:
                mFragment = OurGramPanchayatFragment.newInstance();
                break;
            case 1:
                mFragment = WorkCheckOutFragment.newInstance();
                break;
            case 2:
                mFragment = YoungBusinessFragment.newInstance();
                break;
            case 3:
                mFragment = YoungJobApplyFragment.newInstance();
                break;
            case 4:
                mFragment = CleaningCompleantDetailsFragment.newInstance();
                break;
            case 5:
                mFragment = WaterCompleantDetailsFragment.newInstance();
                break;
            case 6:
                mFragment = LightCompleantDetailsFragment.newInstance();
                break;
            case 7:
                mFragment = MentananceCompleantDetailsFragment.newInstance();
                break;
            case 8:
                mFragment = ConstructionCompleantDetailsFragment.newInstance();
                break;
            case 9:
                mFragment = MyComplentStatusFragment.newInstance();
                break;
            case 10:
                mFragment = SamajBhawanBookingFragment.newInstance();
                break;
            case 11:
                mFragment = TankerBookingFragment.newInstance();
                break;
//            case 12: // Mandi
//                context.startActivity(new Intent(context, MandiDetailsActivity.class));
//                break;
            case 12: // Upcoming Events 13
                mFragment = UpcomingEventsFragment.newInstance();
                break;
//            case 14: // Classifief 14
//                context.startActivity(new Intent(context, ClassificationActivity.class));
//                break;
            case 13:
                mFragment = SchemesFragment.newInstance();
                break;
            case 14:
                mFragment = CertificateFragment.newInstance();
                break;
            case 15:
                mFragment = SuggestionFragment.newInstance();
                break;
            case 16:
                mFragment = GalleryFragment.newInstance();
                break;
            case 17:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, EPaymentActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 18:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, BookingActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 19:
                mFragment = SocialNetworkFragment.newInstance();
                break;
            case 20:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, WebsiteActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 21:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, MapsMarkerActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 22:
                if (AUtils.isNetWorkAvailable(context)) {

                    startActivity(new Intent(context, UtilityActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 23:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, WeatherActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 24:
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

        menuPojoList.add(new MenuPojo(getString(R.string.our_gram_panchayat), AUtils.Colour.Green));
        menuPojoList.add(new MenuPojo(getString(R.string.work_check_out), AUtils.Colour.Yellow));

        menuPojoList.add(new MenuPojo(getString(R.string.young_business), AUtils.Colour.Blue));
        menuPojoList.add(new MenuPojo(getString(R.string.young_jobs), AUtils.Colour.Red));

        menuPojoList.add(new MenuPojo(getString(R.string.cleaning_compleant), AUtils.Colour.Pink));
        menuPojoList.add(new MenuPojo(getString(R.string.water_compleant), AUtils.Colour.SkyBlue));

        menuPojoList.add(new MenuPojo(getString(R.string.light_compleant), AUtils.Colour.Gray));
        menuPojoList.add(new MenuPojo(getString(R.string.maintenance_compleant), AUtils.Colour.Khakhi));

        menuPojoList.add(new MenuPojo(getString(R.string.construction_compleant), AUtils.Colour.Orange));
        menuPojoList.add(new MenuPojo(getString(R.string.complent_status_tab), AUtils.Colour.DarkGray));

        menuPojoList.add(new MenuPojo(getString(R.string.samaj_bavan_booking), AUtils.Colour.Green));
        menuPojoList.add(new MenuPojo(getString(R.string.tanker_booking), AUtils.Colour.SkyBlue));

//        menuPojoList.add(new MenuPojo(getString(R.string.mandi), AUtils.Colour.Yellow));
        menuPojoList.add(new MenuPojo(getString(R.string.upcoming_programs),AUtils.Colour.Pink));

//        menuPojoList.add(new MenuPojo(getString(R.string.classified), AUtils.Colour.Blue));
        menuPojoList.add(new MenuPojo(getString(R.string.schemes), AUtils.Colour.Red));

        menuPojoList.add(new MenuPojo(getString(R.string.certificate), AUtils.Colour.Gray));
        menuPojoList.add(new MenuPojo(getString(R.string.suggestion_tab), AUtils.Colour.Khakhi));

        menuPojoList.add(new MenuPojo(getString(R.string.gallery), AUtils.Colour.Orange));
        menuPojoList.add(new MenuPojo(getString(R.string.e_payment), AUtils.Colour.DarkGray));

        menuPojoList.add(new MenuPojo(getString(R.string.booking), AUtils.Colour.Green));
        menuPojoList.add(new MenuPojo(getString(R.string.social_media), AUtils.Colour.Yellow));

        menuPojoList.add(new MenuPojo(getString(R.string.website), AUtils.Colour.Blue));
        menuPojoList.add(new MenuPojo(getString(R.string.map), AUtils.Colour.Red));

        menuPojoList.add(new MenuPojo(getString(R.string.utility), AUtils.Colour.Pink));
        menuPojoList.add(new MenuPojo(getString(R.string.weather), AUtils.Colour.SkyBlue));

        menuPojoList.add(new MenuPojo(getString(R.string.contact_us), AUtils.Colour.Gray));

        MainMenuAdapter mainMenuAdaptor = new MainMenuAdapter(context, menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);
    }
}