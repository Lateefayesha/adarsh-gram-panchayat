package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.CustomTabViewPagerAdapter;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.SocialNetworkPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class SocialNetworkFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Social_Network_Media;

    private static final String TAG = "SocialNetworkFragment";
    private View view;
    private Context context;
    private TabLayout customTabLayout;
    private ViewPager tabViewPager;
    private CustomTabViewPagerAdapter customTabViewPagerAdapter;
    private List<SocialNetworkPojo> socialNetworkPojoList;

    public static SocialNetworkFragment newInstance() {

        SocialNetworkFragment fragment = new SocialNetworkFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.social_network_activity, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        initData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.social_media));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        customTabLayout = view.findViewById(R.id.custom_tab_layout);
        tabViewPager = view.findViewById(R.id.custom_tab_view_pager);
        customTabLayout.setupWithViewPager(tabViewPager);

    }

    protected void registerEvents() {


    }

    protected void initData() {

        Type type = new TypeToken<List<SocialNetworkPojo>>() {
        }.getType();

        socialNetworkPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.SOCIAL_NETWORK_POJO_LIST, null), type);

        if (!AUtils.isNull(socialNetworkPojoList) && !socialNetworkPojoList.isEmpty()) {

            setDataFromLocal();
            getDataFromServer(false);
        } else {

            getDataFromServer(true);
        }
    }

    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullSocialNetworkListFromServer();
            }

            @Override
            public void onFinished() {

                if (isDataPull) {
                    Type type = new TypeToken<List<SocialNetworkPojo>>() {
                    }.getType();

                    socialNetworkPojoList = new Gson().fromJson(
                            QuickUtils.prefs.getString(AUtils.PREFS.SOCIAL_NETWORK_POJO_LIST, null), type);

                    if (!AUtils.isNull(socialNetworkPojoList) && !socialNetworkPojoList.isEmpty()) {

                        setDataFromLocal();
                    }
                }
            }
        }).execute();
    }

    private void setDataFromLocal() {

        customTabViewPagerAdapter = new CustomTabViewPagerAdapter(getFragmentManager(), context);
        tabViewPager.setAdapter(customTabViewPagerAdapter);

        for (SocialNetworkPojo socialNetworkPojo : socialNetworkPojoList) {

            Bundle bundle = new Bundle();
            bundle.putSerializable(AUtils.SOCIAL_NETWORK_POJO, socialNetworkPojo);
            customTabViewPagerAdapter.addFrag(new SocialNetworkItemFragment(), bundle, socialNetworkPojo.getSocialTitle());
            customTabViewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        AUtils.changeLanguage(getActivity(), Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!AUtils.menuNavigationListHasItem(fragmentMenuId)){
            AUtils.setMenuNavigationList(fragmentMenuId);
        }else
        if(getFragmentManager() != null)
            getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(!AUtils.isRecreate){
            AUtils.removeMenuNavigationValue(AUtils.MenuIdConstants.Main_Menu_Dashboard,
                    fragmentMenuId);
        }
        AUtils.removeMenuNavigationListValue(fragmentMenuId);
    }
}