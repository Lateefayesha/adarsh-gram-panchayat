package com.appynitty.gp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appynitty.gp.R;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.fragment.SocialNetworkItemFragment;
import com.appynitty.gp.pojo.SocialNetworkPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.adapter.TabViewPagerAdapter;

import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by mithun on 16/10/17.
 */

public class SocialNetworkActivity extends BaseActivity {

    //    private LinearLayout contentLinearLayout;
    private TabLayout customTabLayout;
    private ViewPager tabViewPager;
    private TabViewPagerAdapter tabViewPagerAdapter;
    private List<SocialNetworkPojo> socialNetworkPojoList;

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void generateId() {
        setContentView(R.layout.social_network_activity);
        customTabLayout = findViewById(R.id.custom_tab_layout);
        tabViewPager = findViewById(R.id.custom_tab_view_pager);
        customTabLayout.setupWithViewPager(tabViewPager);
//        customTabLayout.setBackgroundColor(getResources().getColor(R.color.White));
//        customTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        customTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getResources().getString(R.string.social_media));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void registerEvents() {


    }

    @Override
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

        new MyAsyncTask(this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
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

        tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), SocialNetworkActivity.this);
        tabViewPager.setAdapter(tabViewPagerAdapter);

        for (SocialNetworkPojo socialNetworkPojo : socialNetworkPojoList) {

            Bundle bundle = new Bundle();
            bundle.putSerializable(AUtils.SOCIAL_NETWORK_POJO, socialNetworkPojo);
            tabViewPagerAdapter.addFrag(new SocialNetworkItemFragment(), bundle, socialNetworkPojo.getSocialTitle());
            tabViewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
        super.onDestroy();
    }
}
