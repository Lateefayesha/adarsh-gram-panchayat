package com.appynitty.gp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appynitty.gp.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mithun on 9/14/2017.
 */
public class CustomTabViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Long> mTitleCatId = new ArrayList<>();
    Context context;

    public CustomTabViewPagerAdapter(FragmentManager manager, Context ctx) {
        super(manager);
        this.context = ctx;
    }

    public List<Long> getmTitleCatId() {
        return mTitleCatId;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addFrag(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add("");
    }

    public void addFrag(Fragment fragment, Bundle bundle, String title) {
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addFrag(Fragment fragment, Bundle bundle, String title, long catId) {
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mTitleCatId.add(catId);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);

    }
}
