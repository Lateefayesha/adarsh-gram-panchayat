package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.ContactUsListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ContactUs;
import com.appynitty.gp.pojo.ContactUsTeamMember;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.MyNoDataView;

import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class ContactUsFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private ContactUsListAdapter contactUsListAdapter;
    private ListView listView;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<ContactUsTeamMember> contactUsPojoList;
    private TextView officeAddressTextView;
    private CardView topCV;

    public static ContactUsFragment newInstance() {

        ContactUsFragment fragment = new ContactUsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contact_us_fragment, container, false);
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.contact_us));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.content_lv);
        officeAddressTextView = view.findViewById(R.id.office_address_tv);
        topCV = view.findViewById(R.id.topll);
//        swipeRefreshLayout = view.findViewById(R.id.content_refresh);
        noDataView = view.findViewById(R.id.no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
    }

    private void registerEvents() {

//        swipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
////                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
//
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
////                        myUpdateOperation();
//                        getDataFromServer(false);
//                    }
//                }
//        );

    }


    private void ininData() {

        initListData();
    }

    private void initListData() {

        ContactUs contactUs = new Gson().fromJson(QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_POJO + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), ContactUs.class);

        if (!AUtils.isNull(contactUs) && !AUtils.isNullString(contactUs.getAddress())) {

            noDataView.setVisibility(View.GONE);
            topCV.setVisibility(View.VISIBLE);
            officeAddressTextView.setText("" + contactUs.getAddress());

        } else {

            topCV.setVisibility(View.GONE);
        }

        Type type = new TypeToken<List<ContactUsTeamMember>>() {
        }.getType();

        contactUsPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        if (!AUtils.isNull(contactUsPojoList) && !contactUsPojoList.isEmpty()) {

            noDataView.setVisibility(View.GONE);
            contactUsListAdapter = new ContactUsListAdapter(context, contactUsPojoList);
            listView.setAdapter(contactUsListAdapter);

            getDataFromServer(false);

        } else {

            noDataView.setVisibility(View.VISIBLE);
            getDataFromServer(true);
        }
    }


    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullContactUsListFromServer();
            }

            @Override
            public void onFinished() {


                ContactUs contactUs = new Gson().fromJson(QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_POJO + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), ContactUs.class);

                if (!AUtils.isNull(contactUs) && !AUtils.isNullString(contactUs.getAddress())) {

                    noDataView.setVisibility(View.GONE);
                    topCV.setVisibility(View.VISIBLE);
                    officeAddressTextView.setText("" + contactUs.getAddress());

                } else {

                    topCV.setVisibility(View.GONE);
                }

                Type type = new TypeToken<List<ContactUsTeamMember>>() {
                }.getType();

                contactUsPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(contactUsPojoList) && !contactUsPojoList.isEmpty()) {

                    noDataView.setVisibility(View.GONE);
                    contactUsListAdapter = new ContactUsListAdapter(context, contactUsPojoList);
                    listView.setAdapter(contactUsListAdapter);
                }

//                if (swipeRefreshLayout.isRefreshing()) {
//
//                    Toasty.success(context, "Updated", Toast.LENGTH_SHORT).show();
//                    swipeRefreshLayout.setRefreshing(false);
//                }

            }
        }).execute();
    }
}