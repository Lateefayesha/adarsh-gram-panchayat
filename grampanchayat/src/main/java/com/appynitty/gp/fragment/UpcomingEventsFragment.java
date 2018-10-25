package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.UpcomingEventsListAdapter;
import com.appynitty.gp.adapter.WorkChechOutListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.UpcomingEventsPojo;
import com.appynitty.gp.pojo.WorkCheckOutPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.MyNoDataView;
import com.mithsoft.lib.componants.Toasty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;

/**
 * Created by Richali Pradhan Gupte on 05-10-2018.
 */
public class UpcomingEventsFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private UpcomingEventsListAdapter upcomingEventsListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<UpcomingEventsPojo> upcomingEventsPojoList;
    private List<UpcomingEventsPojo> updatedUpcomingEventsPojoList;

    public static UpcomingEventsFragment newInstance() {

        UpcomingEventsFragment fragment = new UpcomingEventsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.upcoming_event_layout, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    @Override
    public void initComponents() {

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.upcoming_programs));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.upcoming_list_view);
        swipeRefreshLayout = view.findViewById(R.id.upcoming_content_refresh);
        noDataView = view.findViewById(R.id.upcoming_no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
    }

    private void registerEvents() {

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
//                        myUpdateOperation();
                        getDataFromServer(false);
                    }
                }
        );


    }

    private void ininData() {

        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<UpcomingEventsPojo>>() {
        }.getType();

        upcomingEventsPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.UPCOMING_EVENT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        updatedUpcomingEventsPojoList = new ArrayList<>();

        if (!AUtils.isNull(upcomingEventsPojoList) && !upcomingEventsPojoList.isEmpty()) {

//            for(int i = 0; i < upcomingEventsPojoList.size(); i++) {
//                if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals(upcomingEventsPojoList.get(i).getLanguageId()))
//                {
//                    updatedUpcomingEventsPojoList.add(upcomingEventsPojoList.get(i));
//                }
//            }
//            if(!AUtils.isNull(updatedUpcomingEventsPojoList) && !updatedUpcomingEventsPojoList.isEmpty()) {
                noDataView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                upcomingEventsListAdapter = new UpcomingEventsListAdapter(context, upcomingEventsPojoList);
                listView.setAdapter(upcomingEventsListAdapter);
//            }
//            else
//            {
//                noDataView.setVisibility(View.VISIBLE);
//                listView.setVisibility(View.GONE);
//            }

            getDataFromServer(false);

        } else {

            listView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            getDataFromServer(true);
        }
    }


    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullUpcomingEventListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<UpcomingEventsPojo>>() {
                }.getType();

                upcomingEventsPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.UPCOMING_EVENT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                updatedUpcomingEventsPojoList = new ArrayList<>();

                if (!AUtils.isNull(upcomingEventsPojoList) && !upcomingEventsPojoList.isEmpty()) {

//                    for(int i = 0; i < upcomingEventsPojoList.size(); i++) {
//                        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals(upcomingEventsPojoList.get(i).getLanguageId()))
//                        {
//                            updatedUpcomingEventsPojoList.add(upcomingEventsPojoList.get(i));
//                        }
//                    }

//                    if(!AUtils.isNull(updatedUpcomingEventsPojoList) && !updatedUpcomingEventsPojoList.isEmpty()) {
                        noDataView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        upcomingEventsListAdapter = new UpcomingEventsListAdapter(context, upcomingEventsPojoList);
                        listView.setAdapter(upcomingEventsListAdapter);
//                    }

//                    else
//                    {
//                        noDataView.setVisibility(View.VISIBLE);
//                        listView.setVisibility(View.GONE);
//                    }
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    Toasty.success(context, "Updated", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
    }
}
