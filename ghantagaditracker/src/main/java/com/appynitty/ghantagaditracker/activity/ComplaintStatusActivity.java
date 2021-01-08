package com.appynitty.ghantagaditracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.MyComplentStatusListAdapter;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.ComplentStatusPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyNoDataView;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ComplaintStatusActivity extends AppCompatActivity {

    private Context context;
    private MyComplentStatusListAdapter sechemesListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<ComplentStatusPojo> complentStatusPojoList;

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    private void initComponents(){
        generateId();
        registerEvents();
        initData();
    }

    private void generateId(){
        setContentView(R.layout.activity_complaint_status);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_complaint_status);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = ComplaintStatusActivity.this;
        listView = findViewById(R.id.content_lv);
        swipeRefreshLayout = findViewById(R.id.content_refresh);
        noDataView = findViewById(R.id.no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
    }

    private void registerEvents(){
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
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

        Intent intent = new Intent(context, CleaningStatusDetailsActivity.class);
        intent.putExtra(AUtils.COMPLAINT_STATUS_POJO, complentStatusPojo);
        startActivity(intent);
    }

    private void initData(){
        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<ComplentStatusPojo>>() {
        }.getType();

        complentStatusPojoList = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME), null), type);


        if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

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
                        Prefs.getString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME), null), type);

                if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
