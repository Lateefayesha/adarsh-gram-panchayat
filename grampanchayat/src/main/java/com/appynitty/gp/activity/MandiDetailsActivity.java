package com.appynitty.gp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.appynitty.gp.R;
import com.appynitty.gp.adapter.MandiListAdapter;
import com.appynitty.gp.controller.BaseActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyNoDataView;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
    Boolean isfirstTime;

    @Override
    protected void attachBaseContext(Context base) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
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
        isfirstTime = true;

        initToolbar();
    }

    @Override
    protected void registerEvents() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer(false, false);
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


                AUtils.changeLanguage(MandiDetailsActivity.this,AUtils.LanguageConstants.ENGLISH);

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
                Prefs.getString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

            noDataView.setVisibility(View.GONE);

            mTitleCardView.setVisibility(View.VISIBLE);
            mMandiListView.setVisibility(View.VISIBLE);

            String mandiNameStr = mandiPojoList.get(0).getMandi() + " (" + mandiPojoList.get(0).getState() + ")";
            mMandiName.setText(mandiNameStr);
            mDate.setText(mandiPojoList.get(0).getDate());

            mandiListAdapter = new MandiListAdapter(this, mandiPojoList);
            mMandiListView.setAdapter(mandiListAdapter);

            getDataFromServer(false, false);

        } else {

            noDataView.setVisibility(View.VISIBLE);
            mTitleCardView.setVisibility(View.GONE);
            mMandiListView.setVisibility(View.GONE);
            getDataFromServer(true, false);
        }
    }

    private void getDataFromServer(boolean isShowPrgressDialog, final Boolean loadDefault) {

        new MyAsyncTask(MandiDetailsActivity.this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                if(loadDefault){
                    isDataPull = syncServer.pullDefaultMandiListFromServer();
                }else{
                    isDataPull = syncServer.pullMandiListFromServer(selecetedDate);
                }
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();

                mandiPojoList = new Gson().fromJson(
                        Prefs.getString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

                    noDataView.setVisibility(View.GONE);

                    mTitleCardView.setVisibility(View.VISIBLE);
                    mMandiListView.setVisibility(View.VISIBLE);

                    String mandiNameStr = mandiPojoList.get(0).getMandi() + " (" + mandiPojoList.get(0).getState() + ")";
                    mMandiName.setText(mandiNameStr);
                    mDate.setText(mandiPojoList.get(0).getDate());

                    mandiListAdapter = new MandiListAdapter(MandiDetailsActivity.this, mandiPojoList);
                    mMandiListView.setAdapter(mandiListAdapter);
                    isfirstTime = false;
                }
                else {

                    mTitleCardView.setVisibility(View.GONE);
                    mMandiListView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);

                    if(isfirstTime){
                        isfirstTime = false;
                        getDataFromServer(true, true);
                    }
                }

                if (swipeRefreshLayout.isRefreshing()) {

                    AUtils.success(MandiDetailsActivity.this, "Updated", Toast.LENGTH_SHORT);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }).execute();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        try {
            String languageId = Prefs.getString(AUtils.LANGUAGE_ID,AUtils.DEFAULT_LANGUAGE_ID);
            AUtils.changeLanguage(MandiDetailsActivity.this, languageId);
            Calendar mCalendar = new GregorianCalendar(year,month,day);
            Date date = mCalendar.getTime();
            Date currentDate = currentCalender.getTime();

            long selectedDateLong = date.getTime();
            long currentDateLong = currentDate.getTime();
            if(selectedDateLong <= currentDateLong) {
                selecetedDate = format.format(date);

                getDataFromServer(false, false);
            }
            else
            {
                Toast.makeText(MandiDetailsActivity.this.getApplicationContext(),"Please Select Valid Date",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        if(!AUtils.isRecreate)
            AUtils.removeMenuNavigationValue(AUtils.MenuIdConstants.Main_Menu_Dashboard,
                    AUtils.MenuIdConstants.Mandi_Details);
    }
}
