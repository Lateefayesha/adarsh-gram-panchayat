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
import com.appynitty.gp.adapter.JobListAdapter;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class JobsFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private ListView listView;
    private FragmentManager mFragmentManager;

    public static JobsFragment newInstance() {

        JobsFragment fragment = new JobsFragment();
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.jobs));
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
                bundle.putString(AUtils.UTILITY_URL, "http://employmentnews.gov.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.employment_news);
                break;

            case 1:
                bundle.putString(AUtils.UTILITY_URL, "https://www.sarkarinaukriblog.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.sarkari_naukri_blog);
                break;

            case 2:
                bundle.putString(AUtils.UTILITY_URL, "https://www.sarkari-naukri.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.sarkari_naukri);
                break;

            case 3:
                bundle.putString(AUtils.UTILITY_URL, "https://www.egovtjob.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.e_govt_job_in);
                break;

            case 4:
                bundle.putString(AUtils.UTILITY_URL, "https://www.indgovtjobs.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.ind_govt_jobs);
                break;

            case 5:
                bundle.putString(AUtils.UTILITY_URL, "https://www.sarkariexam.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.sarkari_exam_com);
                break;

            case 6:
                bundle.putString(AUtils.UTILITY_URL, "https://www.jagranjosh.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.jagran_josh);
                break;

            case 7:
                bundle.putString(AUtils.UTILITY_URL, "https://www.careesma.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.careesma);
                break;

            case 8:
                bundle.putString(AUtils.UTILITY_URL, "https://www.jobsarkari.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.job_sarkari);
                break;
            case 9:
                bundle.putString(AUtils.UTILITY_URL, "https://www.naukri.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.naukri_com);
                break;
            case 10:
                bundle.putString(AUtils.UTILITY_URL, "https://www.shine.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.shine_com);
                break;
            case 11:
                bundle.putString(AUtils.UTILITY_URL, "https://www.timesjobs.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.times_jobs);
                break;
            case 12:
                bundle.putString(AUtils.UTILITY_URL, "http://www.monsterindia.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.monster);
                break;
            case 13:
                bundle.putString(AUtils.UTILITY_URL, "https://www.glassdoor.co.in/index.htm");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.glassdoor);
                break;
            case 14:
                bundle.putString(AUtils.UTILITY_URL, "https://www.freshersworld.com/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.freshersworld_com);
                break;
            case 15:
                bundle.putString(AUtils.UTILITY_URL, "https://www.indeed.co.in/");
                bundle.putInt(AUtils.UTILITY_TITLE, R.string.indeed);
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

        settingItemList.add(getString(R.string.employment_news));
        settingItemList.add(getString(R.string.sarkari_naukri_blog));
        settingItemList.add(getString(R.string.sarkari_naukri));
        settingItemList.add(getString(R.string.e_govt_job_in));
        settingItemList.add(getString(R.string.ind_govt_jobs));
        settingItemList.add(getString(R.string.sarkari_exam_com));
        settingItemList.add(getString(R.string.jagran_josh));
        settingItemList.add(getString(R.string.careesma));
        settingItemList.add(getString(R.string.job_sarkari));
        settingItemList.add(getString(R.string.naukri_com));
        settingItemList.add(getString(R.string.shine_com));
        settingItemList.add(getString(R.string.times_jobs));
        settingItemList.add(getString(R.string.monster));
        settingItemList.add(getString(R.string.glassdoor));
        settingItemList.add(getString(R.string.freshersworld_com));
        settingItemList.add(getString(R.string.indeed));

        JobListAdapter utilityListAdapter = new JobListAdapter(context, settingItemList);
        listView.setAdapter(utilityListAdapter);
    }

}