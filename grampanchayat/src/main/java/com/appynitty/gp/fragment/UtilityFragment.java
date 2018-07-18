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
import com.appynitty.gp.adapter.UtilityListAdapter;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class UtilityFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private ListView listView;
    private FragmentManager mFragmentManager;

    public static UtilityFragment newInstance() {

        UtilityFragment fragment = new UtilityFragment();
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.utility));
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
//                bundle.putString(AUtils.UTILITY_URL, "http://www.nvsp.in/");
                bundle.putString(AUtils.UTILITY_URL, "http://www.nvsp.in/Forms/Forms/form6");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.voter_id);
                break;
            case 1:
                bundle.putString(AUtils.UTILITY_URL, "https://www.irctc.co.in/eticketing/loginHome.jsf");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.railway);
                break;
            case 2:
                mFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame, PoliceFragment.newInstance()).commit();
                break;
            case 3:
                bundle.putString(AUtils.UTILITY_URL, "https://portal2.passportindia.gov.in/AppOnlineProject/welcomeLink");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.passport);
                break;
            case 4:
                mFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame, JobsFragment.newInstance()).commit();
                break;
//            case 5:
//                bundle.putString(AUtils.UTILITY_URL, "https://www.bloodbankindia.net/");
//                bundle.putInt(AUtils.UTILITY_TITLE, R.string.bloodbank);
//                break;
            case 5:
                mFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame, NewsPaperFragment.newInstance()).commit();
                break;
            case 6:
                bundle.putString(AUtils.UTILITY_URL, "http://zpamravati.gov.in");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.apali_zilha_parisad);
                break;
            case 7:
                bundle.putString(AUtils.UTILITY_URL, "https://amravati.gov.in/collector-office");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.aple_zilaadhikhari_kayalay);
                break;
        }

        if (position != 5 && position != 2 && position != 4) {

            UtilityItemFragment utilityItemFragment = new UtilityItemFragment();
            utilityItemFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content_frame, utilityItemFragment).commit();
        }
    }

    private void ininData() {

        setDataToAdapter();
    }

    private void setDataToAdapter() {


        List<String> settingItemList = new ArrayList<String>();

        settingItemList.add(getString(R.string.voter_id));
        settingItemList.add(getString(R.string.railway));
        settingItemList.add(getString(R.string.police));
        settingItemList.add(getString(R.string.passport));
        settingItemList.add(getString(R.string.jobs));
        settingItemList.add(getString(R.string.newspaper));
        settingItemList.add(getString(R.string.apali_zilha_parisad));
        settingItemList.add(getString(R.string.aple_zilaadhikhari_kayalay));

        UtilityListAdapter utilityListAdapter = new UtilityListAdapter(context, settingItemList);
        listView.setAdapter(utilityListAdapter);
    }

}