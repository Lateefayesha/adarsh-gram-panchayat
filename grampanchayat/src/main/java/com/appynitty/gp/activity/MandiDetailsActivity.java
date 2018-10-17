package com.appynitty.gp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.MandiListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.componants.MyNoDataView;
import com.mithsoft.lib.componants.Toasty;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import quickutils.core.QuickUtils;

/**
 * Created by Richali Pradhan Gupte on 02-10-2018.
 */
public class MandiDetailsActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private String title;
    private TextView mMandiName;
    private TextView mDate;
    private ListView mMandiListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<MandiPojo> mandiPojoList;
    private MandiListAdapter mandiListAdapter;
    private CardView mTitleCardView;
    private DatePickerDialog datePickerDialog;
    private String selecetedDateformat;
    private String selecetedDate;
    private SimpleDateFormat format;
    private Calendar currentCalender;

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void generateId() {
        setContentView(R.layout.mandi_activity);

        mMandiName = findViewById(R.id.mandi_name_value);
        mDate = findViewById(R.id.mandi_date_value);
        mMandiListView = findViewById(R.id.mandi_content_lv);
        mTitleCardView = findViewById(R.id.mandi_title_view);

        swipeRefreshLayout = findViewById(R.id.mandi_content_refresh);
        noDataView = findViewById(R.id.mandi_no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
        selecetedDateformat = AUtils.getServerDateFormate();
        selecetedDate = AUtils.getSeverDate();

        currentCalender = Calendar.getInstance();

        initToolbar();
    }

    @Override
    protected void registerEvents() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer(false);
            }
        });
    }

    @Override
    protected void initData() {
        initListData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mandi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            MandiDetailsActivity.this.finish();
            return true;
        } else if (item.getItemId() == R.id.action_mandi_filter) {
            try {

                format = new SimpleDateFormat(selecetedDateformat, Locale.ENGLISH);

                Date currentDate = null;

                currentDate = format.parse(selecetedDate);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(currentDate);


                AUtils.changeLanguage(MandiDetailsActivity.this,1);

                datePickerDialog = new DatePickerDialog(MandiDetailsActivity.this,
                        MandiDetailsActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(currentCalender.getTimeInMillis());
                datePickerDialog.show();
            }
            catch (Exception e)
            {
                Log.e(MandiDetailsActivity.class.getName(),e.getMessage());
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.mandi));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    private void initListData() {

        Type type = new TypeToken<List<MandiPojo>>() {
        }.getType();

        mandiPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.MANDI_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

            noDataView.setVisibility(View.GONE);

            mTitleCardView.setVisibility(View.VISIBLE);
            mMandiListView.setVisibility(View.VISIBLE);

            mMandiName.setText(mandiPojoList.get(0).getMandi() + " (" + mandiPojoList.get(0).getState() + ")");
            mDate.setText(mandiPojoList.get(0).getDate());

            mandiListAdapter = new MandiListAdapter(this, mandiPojoList);
            mMandiListView.setAdapter(mandiListAdapter);

            getDataFromServer(false);

        } else {

            noDataView.setVisibility(View.VISIBLE);
            mTitleCardView.setVisibility(View.GONE);
            mMandiListView.setVisibility(View.GONE);
            getDataFromServer(true);
        }
    }

    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(MandiDetailsActivity.this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullMandiListFromServer(selecetedDate);
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();

                mandiPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.MANDI_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

                    noDataView.setVisibility(View.GONE);

                    mTitleCardView.setVisibility(View.VISIBLE);
                    mMandiListView.setVisibility(View.VISIBLE);

                    mMandiName.setText(mandiPojoList.get(0).getMandi() +" (" + mandiPojoList.get(0).getState() +")");
                    mDate.setText(mandiPojoList.get(0).getDate());

                    mandiListAdapter = new MandiListAdapter(MandiDetailsActivity.this, mandiPojoList);
                    mMandiListView.setAdapter(mandiListAdapter);
                }
                else {

                    mTitleCardView.setVisibility(View.GONE);
                    mMandiListView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    Toasty.success(MandiDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        try {
            int languageId = Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID,AUtils.DEFAULT_LANGUAGE_ID));
            AUtils.changeLanguage(MandiDetailsActivity.this,languageId);
            Calendar mCalendar = new GregorianCalendar(year,month,day);
            Date date = mCalendar.getTime();
            Date currentDate = currentCalender.getTime();

            long selectedDateLong = date.getTime();
            long currentDateLong = currentDate.getTime();
            if(selectedDateLong <= currentDateLong) {
                selecetedDate = format.format(date);

                getDataFromServer(false);
            }
            else
            {
                Toast.makeText(MandiDetailsActivity.this.getApplicationContext(),"Please Select Valid Date",Toast.LENGTH_SHORT);
            }
        }
        catch (Exception e)
        {
            Log.e(MandiDetailsActivity.class.getName(), e.getMessage());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
