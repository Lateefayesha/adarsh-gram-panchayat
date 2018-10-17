package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.OurGramPanchayatListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;
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
 * Created by MiTHUN on 8/2/18.
 */

public class OurGramPanchayatFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private OurGramPanchayatListAdapter ourGramPanchayatListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private ArrayList<OurGramPanchayatPojo> ourGramPanchayatList;

    public static OurGramPanchayatFragment newInstance() {

        OurGramPanchayatFragment fragment = new OurGramPanchayatFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.our_grampanchyat_fragment, container, false);
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.our_gram_panchayat));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.content_lv);
        swipeRefreshLayout = view.findViewById(R.id.content_refresh);
        noDataView = view.findViewById(R.id.no_data_view);
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

        Type type = new TypeToken<List<OurGramPanchayatPojo>>() {
        }.getType();

        ourGramPanchayatList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);


        if (!AUtils.isNull(ourGramPanchayatList) && !ourGramPanchayatList.isEmpty()) {

            noDataView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            ourGramPanchayatListAdapter = new OurGramPanchayatListAdapter(context, ourGramPanchayatList);
            listView.setAdapter(ourGramPanchayatListAdapter);

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

                isDataPull = syncServer.pullOurGramPanchayatListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<OurGramPanchayatPojo>>() {
                }.getType();

                ourGramPanchayatList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(ourGramPanchayatList) && !ourGramPanchayatList.isEmpty()) {

                    listView.setVisibility(View.VISIBLE);
                    noDataView.setVisibility(View.GONE);
                    ourGramPanchayatListAdapter = new OurGramPanchayatListAdapter(context, ourGramPanchayatList);
                    listView.setAdapter(ourGramPanchayatListAdapter);
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    Toasty.success(context, "Updated", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
    }
}