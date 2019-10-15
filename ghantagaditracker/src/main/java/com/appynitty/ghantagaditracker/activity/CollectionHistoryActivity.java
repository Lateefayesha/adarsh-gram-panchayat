package com.appynitty.ghantagaditracker.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.InflateCollectionHistoryUI;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.CollectionHistoryPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
import com.riaylibrary.custom_component.MyNoDataView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CollectionHistoryActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView recyclerView;
    private Spinner yearSpinner, monthSpinner;
    private InflateCollectionHistoryUI adpter;
    private MyNoDataView noDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    private void initComponents() {
        generateId();
        initData();
        registerEvents();
    }

    private void generateId() {
        setContentView(R.layout.activity_collection_history);
        mContext = CollectionHistoryActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_collection_history));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        monthSpinner = findViewById(R.id.spinner_month);
        yearSpinner = findViewById(R.id.spinner_year);

        recyclerView = findViewById(R.id.history_detail_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        noDataView = findViewById(R.id.no_data_view);

    }

    private void initData() {
        initSpinner();
        adpter = new InflateCollectionHistoryUI(mContext);
        setHistoryFetchFilter((AUtils.getCurrentMonth()+1), AUtils.getCurrentYear());
    }

    private void registerEvents() {

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position > 0 && yearSpinner.getSelectedItemPosition() > 0){
                    setHistoryFetchFilter(position, Integer.valueOf((String)yearSpinner.getSelectedItem()));
                }else{
                    AUtils.info(mContext, getResources().getString(R.string.select_month_year_warn));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position > 0 && monthSpinner.getSelectedItemPosition() > 0){

                    setHistoryFetchFilter(monthSpinner.getSelectedItemPosition(), Integer.valueOf((String)yearSpinner.getSelectedItem()));

                }else{
                    AUtils.info(mContext, getResources().getString(R.string.select_month_year_warn));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initSpinner() {
        setMonthSpinner(monthSpinner);
        setYearSpinner(yearSpinner);
    }

    public void setMonthSpinner(Spinner monthSpinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext,
                R.layout.layout_simple_white_textview, AUtils.getMonthSpinnerList());
        monthSpinner.setAdapter(spinnerAdapter);
        monthSpinner.setSelection((AUtils.getCurrentMonth()+1), true);
    }

    public void setYearSpinner(Spinner yearSpinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext,
                R.layout.layout_simple_white_textview, AUtils.getYearSpinnerList());
        yearSpinner.setAdapter(spinnerAdapter);
        yearSpinner.setSelection(1, true);
    }

    private void inflateHistoryAdapter(List<CollectionHistoryPojo> list){
        if(list.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            adpter.setHistoryPojos(list);
            recyclerView.setAdapter(adpter);
        }else {
            recyclerView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            AUtils.warning(mContext, getResources().getString(R.string.noData));
        }
    }

    private void setHistoryFetchFilter(int month, int year){
        HashMap<String, String> dates = AUtils.getMonthStartEndDate(month, year);
        if(!AUtils.isNull(dates)){
            String startDate = dates.get(AUtils.MONTH_START_DATE) + " 12:00:00 AM";
            String endDate = dates.get(AUtils.MONTH_END_DATE) + " 11:59:59 PM";
            fetchHistory(startDate, endDate);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchHistory(final String startDate, final String endDate){
        new MyAsyncTask(mContext, true, new MyAsyncTask.AsynTaskListener() {
            List<CollectionHistoryPojo> pojoList;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                pojoList = syncServer.fetchCollectionHistory(startDate, endDate);
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(pojoList)){
                    inflateHistoryAdapter(pojoList);
                }else
                    AUtils.error(mContext, getResources().getString(R.string.something_error));
            }
        }).execute();
    }

}
