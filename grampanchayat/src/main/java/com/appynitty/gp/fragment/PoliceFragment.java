package com.appynitty.gp.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.PoliceListAdapter;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class PoliceFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private ListView listView;
    private FragmentManager mFragmentManager;

    public static PoliceFragment newInstance() {

        PoliceFragment fragment = new PoliceFragment();
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.police));
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
                bundle.putString(AUtils.UTILITY_URL, "http://amravatiruralpolice.gov.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.police);
                break;
            case 1:

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "100"));
                    context.startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call has failed", Toast.LENGTH_LONG).show();
                }

                break;
        }

        if (position != 1) {
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

        settingItemList.add(getString(R.string.visit_website));
        settingItemList.add(getString(R.string.call));

        PoliceListAdapter utilityListAdapter = new PoliceListAdapter(context, settingItemList);
        listView.setAdapter(utilityListAdapter);
    }

}