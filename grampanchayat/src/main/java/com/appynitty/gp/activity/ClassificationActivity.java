package com.appynitty.gp.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.ClassificationAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ClassificationPojo;
import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.componants.MyNoDataView;
import com.mithsoft.lib.viewpager.AutoScrollViewPager;
import com.mithsoft.lib.viewpager.ViewPagerTransformer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import quickutils.core.QuickUtils;

/**
 * Created by Richali Pradhan Gupte on 13-10-2018.
 */
public class ClassificationActivity extends BaseActivity {

    private static final String TAG = "ClassificationActivity";

    private AutoScrollViewPager autoScrollViewPagerLarge = null,
            autoScrollViewPagerMedium1 = null,
            autoScrollViewPagerMedium2 = null,
            autoScrollViewPagerLarge2 = null,
            autoScrollViewPagerSmall = null;

    private List<ClassificationPojo> classificationPojoList = null;

    private MyNoDataView noDataView = null;

    private ScrollView recordDataView = null;

    private HashMap<String,List<String>> mRecordHashMap = null;


    @Override
    protected void generateId() {
        setContentView(R.layout.classification_activity);

        autoScrollViewPagerLarge = findViewById(R.id.viewPagerLarge);

        autoScrollViewPagerMedium1 = findViewById(R.id.viewPagerMedium_1);

        autoScrollViewPagerMedium2 = findViewById(R.id.viewPagerMedium_2);

        autoScrollViewPagerLarge2 = findViewById(R.id.viewPagerLarge_2);

        autoScrollViewPagerSmall = findViewById(R.id.viewPagerSmall);

        noDataView = findViewById(R.id.classification_no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));

        recordDataView = findViewById(R.id.classification_data_view);

        initToolbar();
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    protected void initData() {

        mRecordHashMap = new HashMap<>();

        Type type = new TypeToken<List<ClassificationPojo>>() {
        }.getType();

        classificationPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.CLASSIFICATION_POJO_LIST +
                        QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null),
                type);

        if (!AUtils.isNull(classificationPojoList) && !classificationPojoList.isEmpty()) {

            makeViewVisible(true);

            mRecordHashMap =  prepareHashMap(classificationPojoList);

            initLargeScroll(mRecordHashMap);
            initMedium1Scroll(mRecordHashMap);
            initMedium2Scroll(mRecordHashMap);
            initLarge2Scroll(mRecordHashMap);
            initSmallScroll(mRecordHashMap);
        } else {

            makeViewVisible(false);
            getDataFromServer(true);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                ClassificationActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
        super.onDestroy();
    }

    private void initLargeScroll(HashMap<String,List<String>> Data)
    {
        List<String> LargeData = Data.get("Large1");

        Collections.shuffle(LargeData, new Random());

        ClassificationAdapter pagerAdapter = new ClassificationAdapter(ClassificationActivity.this, LargeData);
        autoScrollViewPagerLarge.setAdapter(pagerAdapter);

//        autoScrollViewPager.setPageTransformer(false, new FadePageTransformer());
        autoScrollViewPagerLarge.setPageTransformer(true, new ViewPagerTransformer(ViewPagerTransformer.TransformType.DEPTH));
        autoScrollViewPagerLarge.setOffscreenPageLimit(pagerAdapter.getCount());
        autoScrollViewPagerLarge.startAutoScroll(4000);
        autoScrollViewPagerLarge.setBorderAnimation(false);
    }

    private void initMedium1Scroll(HashMap<String,List<String>> Data)
    {
        List<String> MediumData = Data.get("Medium1");

        Collections.shuffle(MediumData, new Random());

        ClassificationAdapter pagerAdapter = new ClassificationAdapter(ClassificationActivity.this, MediumData);
        autoScrollViewPagerMedium1.setAdapter(pagerAdapter);

//        autoScrollViewPager.setPageTransformer(false, new FadePageTransformer());
        autoScrollViewPagerMedium1.setPageTransformer(true, new ViewPagerTransformer(ViewPagerTransformer.TransformType.DEPTH));
        autoScrollViewPagerMedium1.setOffscreenPageLimit(pagerAdapter.getCount());
        autoScrollViewPagerMedium1.startAutoScroll(3000);
        autoScrollViewPagerMedium1.setBorderAnimation(false);
    }

    private void initMedium2Scroll(HashMap<String,List<String>> Data)
    {

        List<String> MediumData = Data.get("Medium2");

        Collections.shuffle(MediumData, new Random());

        ClassificationAdapter pagerAdapter = new ClassificationAdapter(ClassificationActivity.this, MediumData);
        autoScrollViewPagerMedium2.setAdapter(pagerAdapter);

//        autoScrollViewPager.setPageTransformer(false, new FadePageTransformer());
        autoScrollViewPagerMedium2.setPageTransformer(true, new ViewPagerTransformer(ViewPagerTransformer.TransformType.DEPTH));
        autoScrollViewPagerMedium2.setOffscreenPageLimit(pagerAdapter.getCount());
        autoScrollViewPagerMedium2.startAutoScroll(4000);
        autoScrollViewPagerMedium2.setBorderAnimation(false);
    }

    private void initLarge2Scroll(HashMap<String,List<String>> Data)
    {

        List<String> LargeData = Data.get("Large2");

        Collections.shuffle(LargeData, new Random());

        ClassificationAdapter pagerAdapter = new ClassificationAdapter(ClassificationActivity.this, LargeData);
        autoScrollViewPagerLarge2.setAdapter(pagerAdapter);

//        autoScrollViewPager.setPageTransformer(false, new FadePageTransformer());
        autoScrollViewPagerLarge2.setPageTransformer(true, new ViewPagerTransformer(ViewPagerTransformer.TransformType.DEPTH));
        autoScrollViewPagerLarge2.setOffscreenPageLimit(pagerAdapter.getCount());
        autoScrollViewPagerLarge2.startAutoScroll(4000);
        autoScrollViewPagerLarge2.setBorderAnimation(false);
    }

    private void initSmallScroll(HashMap<String,List<String>> Data)
    {

        List<String> SmallData = Data.get("Small");

        Collections.shuffle(SmallData, new Random());

        ClassificationAdapter pagerAdapter = new ClassificationAdapter(ClassificationActivity.this, SmallData);
        autoScrollViewPagerSmall.setAdapter(pagerAdapter);

//        autoScrollViewPager.setPageTransformer(false, new FadePageTransformer());
        autoScrollViewPagerSmall.setPageTransformer(true, new ViewPagerTransformer(ViewPagerTransformer.TransformType.DEPTH));
        autoScrollViewPagerSmall.setOffscreenPageLimit(pagerAdapter.getCount());
        autoScrollViewPagerSmall.startAutoScroll(4000);
        autoScrollViewPagerSmall.setBorderAnimation(false);
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.classified));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    private void makeViewVisible(boolean isRecord)
    {
        if (isRecord)
        {
            noDataView.setVisibility(View.GONE);

            recordDataView.setVisibility(View.VISIBLE);
        }
        else
        {
            recordDataView.setVisibility(View.GONE);

            noDataView.setVisibility(View.VISIBLE);
        }
    }

    private HashMap<String,List<String>> prepareHashMap(List<ClassificationPojo> mList)
    {
        HashMap<String,List<String>> mRecord = new HashMap<>();
        List<String> mLarge1Url = new ArrayList<>();
        List<String> mMedium1Url = new ArrayList<>();
        List<String> mLarge2Url = new ArrayList<>();
        List<String> mMedium2Url = new ArrayList<>();
        List<String> mSmallUrl = new ArrayList<>();
        for(int i = 0; i < mList.size(); i++)
        {
            switch (mList.get(i).getType())
            {
                case "Large1":
                    mLarge1Url.add(mList.get(i).getImage());
                    break;

                case "Large2":
                    mLarge2Url.add(mList.get(i).getImage());
                    break;

                case "Medium1":
                    mMedium1Url.add(mList.get(i).getImage());
                    break;

                case "Medium2":
                    mMedium2Url.add(mList.get(i).getImage());
                    break;

                case "Small":
                    mSmallUrl.add(mList.get(i).getImage());
                    break;
            }
        }

        mRecord.put("Large1",mLarge1Url);
        mRecord.put("Large2",mLarge2Url);
        mRecord.put("Medium1",mMedium1Url);
        mRecord.put("Medium2",mMedium2Url);
        mRecord.put("Small",mSmallUrl);

        return mRecord;
    }

    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(ClassificationActivity.this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullClassificationListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();

                classificationPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.MANDI_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(classificationPojoList) && !classificationPojoList.isEmpty()) {

                    makeViewVisible(true);

                    prepareHashMap(classificationPojoList);

                    initLargeScroll(mRecordHashMap);
                    initMedium1Scroll(mRecordHashMap);
                    initMedium2Scroll(mRecordHashMap);
                    initLarge2Scroll(mRecordHashMap);
                    initSmallScroll(mRecordHashMap);
                }
                else {

                    makeViewVisible(false);
                }

            }
        }).execute();
    }
}
