package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.NewsPaperListAdapter;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class NewsPaperFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private ListView listView;
    private FragmentManager mFragmentManager;

    public static NewsPaperFragment newInstance() {

        NewsPaperFragment fragment = new NewsPaperFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.utility_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.newspaper));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.utility_lv);

        mFragmentManager = getFragmentManager();
    }

    private void registerEvents() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listViewOnClick(position);
            }
        });
    }

    private void listViewOnClick(int position) {

        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString(AUtils.UTILITY_URL, "http://www.lokmat.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.lokmat);
                break;
            case 1:
                bundle.putString(AUtils.UTILITY_URL, "http://www.tarunbharat.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.tarun_bharat);
                break;
            case 2:
                bundle.putString(AUtils.UTILITY_URL, "http://www.navabharat.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.navbharat);
                break;
            case 3:
                bundle.putString(AUtils.UTILITY_URL, "https://www.bhaskar.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.dainik_bhaskar);
                break;
            case 4:
                bundle.putString(AUtils.UTILITY_URL, "https://www.loksatta.com/maharashtra/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.locsatta);
                break;
            case 5:
                bundle.putString(AUtils.UTILITY_URL, "http://sakal.epapr.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.sakal);
                break;
            case 6:
                bundle.putString(AUtils.UTILITY_URL, "http://prahaar.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.prahar);
                break;
            case 7:
                bundle.putString(AUtils.UTILITY_URL, "http://epaper.dainikekmat.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.ekmat);
                break;
            case 8:
                bundle.putString(AUtils.UTILITY_URL, "http://www.pudhari.news/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.pudhari);
                break;
            case 9:
                bundle.putString(AUtils.UTILITY_URL, "https://maharashtratimes.indiatimes.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.maharashtra_times);
                break;
            case 10:
                bundle.putString(AUtils.UTILITY_URL, "http://www.dainikhindusthan.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.hindustan);
                break;
            case 11:
                bundle.putString(AUtils.UTILITY_URL, "https://divyamarathi.bhaskar.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.divya_marathi);
                break;
            case 12:
                bundle.putString(AUtils.UTILITY_URL, "http://www.esanchar.co.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.sanchar);
                break;
            case 13:
                bundle.putString(AUtils.UTILITY_URL, "https://navbharattimes.indiatimes.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.navbharat_times);
                break;
            case 14:
                bundle.putString(AUtils.UTILITY_URL, "https://www.livehindustan.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.live_hundustan);
                break;
            case 15:
                bundle.putString(AUtils.UTILITY_URL, "https://timesofindia.indiatimes.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.times_of_india);
                break;
            case 16:
                bundle.putString(AUtils.UTILITY_URL, "https://economictimes.indiatimes.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.the_echonomic_times);
                break;
            case 17:
                bundle.putString(AUtils.UTILITY_URL, "http://www.ehitavada.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.the_hitvada);
                break;
        }

        UtilityItemFragment utilityItemFragment = new UtilityItemFragment();
        utilityItemFragment.setArguments(bundle);
        mFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.content_frame, utilityItemFragment).commit();
    }

    private void ininData() {

        setDataToAdapter();
    }

    private void setDataToAdapter() {


        List<String> settingItemList = new ArrayList<String>();

        settingItemList.add(getString(R.string.lokmat));
        settingItemList.add(getString(R.string.tarun_bharat));
        settingItemList.add(getString(R.string.navbharat));
        settingItemList.add(getString(R.string.dainik_bhaskar));
        settingItemList.add(getString(R.string.locsatta));
        settingItemList.add(getString(R.string.sakal));
        settingItemList.add(getString(R.string.prahar));
        settingItemList.add(getString(R.string.ekmat));
        settingItemList.add(getString(R.string.pudhari));
        settingItemList.add(getString(R.string.maharashtra_times));
        settingItemList.add(getString(R.string.hindustan));
        settingItemList.add(getString(R.string.divya_marathi));
        settingItemList.add(getString(R.string.sanchar));
        settingItemList.add(getString(R.string.navbharat_times));
        settingItemList.add(getString(R.string.live_hundustan));
        settingItemList.add(getString(R.string.times_of_india));
        settingItemList.add(getString(R.string.the_echonomic_times));
        settingItemList.add(getString(R.string.the_hitvada));

        NewsPaperListAdapter utilityListAdapter = new NewsPaperListAdapter(context, settingItemList);
        listView.setAdapter(utilityListAdapter);
    }

}