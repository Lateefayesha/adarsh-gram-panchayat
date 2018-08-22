package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.BookingActivity;
import com.appynitty.gp.activity.EPaymentActivity;
import com.appynitty.gp.activity.HomeActivity;
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

        if (getArguments().getBoolean(AUtils.FCM_NOTI)) {

            mFragment = MyComplentStatusFragment.newInstance();
            mFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content_frame, mFragment).commit();
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

            ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.app_name_tab));
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
                mFragment = TankerBookingFragment.newInstance();
                break;
            case 9:
                mFragment = SchemesFragment.newInstance();
                break;
            case 10:
                mFragment = SuggestionFragment.newInstance();
                break;
            case 11:
                mFragment = MyComplentStatusFragment.newInstance();
                break;
            case 12:
                mFragment = CertificateFragment.newInstance();
                break;
            case 13:
                mFragment = GalleryFragment.newInstance();
                break;
            case 14:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, EPaymentActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 15:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, BookingActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 16:
                mFragment = SocialNetworkFragment.newInstance();
                break;
            case 17:
                context.startActivity(new Intent(context, MapsMarkerActivity.class));
                break;
            case 18:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, WebsiteActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 19:
                mFragment = ContactUsFragment.newInstance();
                break;
            case 20:
                if (AUtils.isNetWorkAvailable(context)) {

                    context.startActivity(new Intent(context, WeatherActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
                break;
            case 21:
//                mFragment = UtilityItemFragment.newInstance();
                if (AUtils.isNetWorkAvailable(context)) {

                    startActivity(new Intent(context, UtilityActivity.class));
                } else {

                    Toast.makeText(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                }
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

        menuPojoList.add(new MenuPojo(getString(R.string.our_gram_panchayat), "#8BC34A"));
        menuPojoList.add(new MenuPojo(getString(R.string.work_check_out), "#FFC107"));

        menuPojoList.add(new MenuPojo(getString(R.string.young_business), "#3949AB"));
        menuPojoList.add(new MenuPojo(getString(R.string.young_jobs), "#E53935"));

        menuPojoList.add(new MenuPojo(getString(R.string.cleaning_compleant), "#E91E63"));
        menuPojoList.add(new MenuPojo(getString(R.string.water_compleant), "#03A9F4"));

        menuPojoList.add(new MenuPojo(getString(R.string.light_compleant), "#607D8B"));
        menuPojoList.add(new MenuPojo(getString(R.string.maintenance_compleant), "#BA9B47"));

        menuPojoList.add(new MenuPojo(getString(R.string.tanker_booking), "#03A9F4"));
        menuPojoList.add(new MenuPojo(getString(R.string.schemes), "#FF9800"));

        menuPojoList.add(new MenuPojo(getString(R.string.suggestion_tab), "#FF9800"));
        menuPojoList.add(new MenuPojo(getString(R.string.complent_status_tab), "#4CAF50"));

        menuPojoList.add(new MenuPojo(getString(R.string.certificate), "#8C7676"));
        menuPojoList.add(new MenuPojo(getString(R.string.gallery), "#8BC34A"));

        menuPojoList.add(new MenuPojo(getString(R.string.e_payment), "#03A9F4"));
        menuPojoList.add(new MenuPojo(getString(R.string.booking), "#3949AB"));

        menuPojoList.add(new MenuPojo(getString(R.string.social_media), "#F44336"));
        menuPojoList.add(new MenuPojo(getString(R.string.map), "#FFC107"));

        menuPojoList.add(new MenuPojo(getString(R.string.website), "#4CAF50"));
        menuPojoList.add(new MenuPojo(getString(R.string.contact_us), "#03A9F4"));

        menuPojoList.add(new MenuPojo(getString(R.string.weather), "#FFC107"));
        menuPojoList.add(new MenuPojo(getString(R.string.utility), "#384259"));

        MainMenuAdapter mainMenuAdaptor = new MainMenuAdapter(context, menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);
    }
}