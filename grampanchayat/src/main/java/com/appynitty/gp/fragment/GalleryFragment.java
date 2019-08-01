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
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;
import com.mithsoft.lib.adapter.TabViewPagerAdapter;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class GalleryFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Photo_Video_Gallery;

    private static final String TAG = "GalleryFragment";
    private View view;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static GalleryFragment newInstance() {

        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_layout_activity, container, false);
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.gallery));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPagerTab);

    }

    private void registerEvents() {

    }

    private void initData() {

        initTablayout();
    }

    private void initTablayout() {

        TabViewPagerAdapter viewPagerAdapter = new TabViewPagerAdapter(getFragmentManager(), context);
        viewPagerAdapter.addFrag(new ImagesGalleryFragment(), null, getString(R.string.photo), 0);
        viewPagerAdapter.addFrag(new VedioGalleryFragment(), null, getString(R.string.vedio), 1);

        viewPager.setAdapter(viewPagerAdapter);

        //This will tell the pager to keep all of them in memory and not destroy/create with every swipe (keep a close look on the memory management)
        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount() - 1);

//        viewPagerAdapter.notifyDataSetChanged();
//        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
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