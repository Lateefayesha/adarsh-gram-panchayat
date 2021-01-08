package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.activity.MyComlaintDetailsActivity;
import com.appynitty.gp.adapter.MyComplentStatusListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyNoDataView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class MyComplentStatusFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Complent_Status_Tab;

    private View view;
    private Context context;
    private MyComplentStatusListAdapter sechemesListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<ComplentStatusPojo> complentStatusPojoList;

    public static MyComplentStatusFragment newInstance() {

        MyComplentStatusFragment fragment = new MyComplentStatusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.my_complent_status_fragment, container, false);
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

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.complent_status_tab));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 0);

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ComplentStatusPojo complentStatusPojo = (ComplentStatusPojo) adapterView.getItemAtPosition(position);

                listViewOnItemClick(complentStatusPojo);
            }
        });

    }

    private void listViewOnItemClick(ComplentStatusPojo complentStatusPojo) {

        Intent intent = new Intent(context, MyComlaintDetailsActivity.class);
        intent.putExtra(AUtils.COMPLAINT_STATUS_POJO, complentStatusPojo);
        startActivity(intent);
    }

    private void ininData() {

        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<ComplentStatusPojo>>() {
        }.getType();

        complentStatusPojoList = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);


        if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

//            Collections.sort(complentStatusPojoList, new Comparator<SchemesPojo>() {
//                @Override
//                public int compare(SchemesPojo lhs, SchemesPojo rhs) {
//                    if (lhs.getSchemeId() == 0)
//                        return 0;
//                    return Long.compare(rhs.getSchemeId(), lhs.getSchemeId());
//                }
//            });

            noDataView.setVisibility(View.GONE);
            sechemesListAdapter = new MyComplentStatusListAdapter(context, complentStatusPojoList);
            listView.setAdapter(sechemesListAdapter);

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


                isDataPull = syncServer.pullMyComplentStatusListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<ComplentStatusPojo>>() {
                }.getType();

                complentStatusPojoList = new Gson().fromJson(
                        Prefs.getString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

//                    Collections.sort(complentStatusPojoList, new Comparator<SchemesPojo>() {
//                        @Override
//                        public int compare(SchemesPojo lhs, SchemesPojo rhs) {
//                            if (lhs.getSchemeId() == 0)
//                                return 0;
//                            return Long.compare(rhs.getSchemeId(), lhs.getSchemeId());
//                        }
//                    });

                    noDataView.setVisibility(View.GONE);
                    sechemesListAdapter = new MyComplentStatusListAdapter(context, complentStatusPojoList);
                    listView.setAdapter(sechemesListAdapter);
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    AUtils.success(context, "Updated", Toast.LENGTH_SHORT);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
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