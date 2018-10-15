package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.YoungBusinessListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.YoungBusinessPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.MyNoDataView;
import com.mithsoft.lib.componants.Toasty;

import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class YoungBusinessFragment extends MyFragemtV4 {

    private static final String TAG = "YoungBusinessFragment";
    private View view;
    private Context context;
    private YoungBusinessListAdapter youngBusinessListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<YoungBusinessPojo> youngBusinessPojoList;
    private Button applyBusinessButton;

    public static YoungBusinessFragment newInstance() {

        YoungBusinessFragment fragment = new YoungBusinessFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.young_business_fragment, container, false);
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.young_business));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.content_lv);
        swipeRefreshLayout = view.findViewById(R.id.content_refresh);
        applyBusinessButton = view.findViewById(R.id.apply_btn);
        applyBusinessButton.setText(R.string.young_business_apply);
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


        applyBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyBusinessButtonOnClick();
//                FirebaseInstanceId.getInstance().getToken();
//                Log.e(TAG, "Fcm Id1 = " + FirebaseInstanceId.getInstance().getToken());
//                Log.e(TAG, "Fcm Id = " + QuickUtils.prefs.getString(MsUtils.FCM_ID, null));
            }
        });

    }

    private void applyBusinessButtonOnClick() {

        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, YoungBusinessApplyFragment.newInstance())
                .commit();
    }

    private void ininData() {

        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<YoungBusinessPojo>>() {
        }.getType();

        youngBusinessPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.YOUNG_BUSINESS_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

            noDataView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            youngBusinessListAdapter = new YoungBusinessListAdapter(context, youngBusinessPojoList);
            listView.setAdapter(youngBusinessListAdapter);

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


                isDataPull = syncServer.pullYoungBusinessListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();

                youngBusinessPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.YOUNG_BUSINESS_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                    noDataView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    youngBusinessListAdapter = new YoungBusinessListAdapter(context, youngBusinessPojoList);
                    listView.setAdapter(youngBusinessListAdapter);
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    Toasty.success(context, "Updated", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
    }


}