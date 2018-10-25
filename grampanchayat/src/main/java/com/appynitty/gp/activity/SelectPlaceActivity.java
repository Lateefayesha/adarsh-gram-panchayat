package com.appynitty.gp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.DistrictPojo;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.pojo.StatePojo;
import com.appynitty.gp.pojo.TahsilPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.componants.Toasty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


public class SelectPlaceActivity extends BaseActivity {

    private TextView stateNameSpinnerHintTitleTextView;
    private Spinner stateNameSpinner;
    private TextView districtSpinnerHintTitleTextView;
    private Spinner districtContactSpinner;
    private TextView tahsilSpinnerHintTitleTextView;
    private Spinner tahsilContactSpinner;

    private List<StatePojo> statePojoList = null;

    private Button submitButton;
    private List<DistrictPojo> districtPojoList;
    private String stateId = "0";
    private String tahsilId = "0";
    private String districtId = "0";
    private List<TahsilPojo> tahsilPojoArrayList;

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

        setContentView(R.layout.select_place_activity);

        stateNameSpinnerHintTitleTextView = findViewById(R.id.state_name_sp_title_hint);
        stateNameSpinner = findViewById(R.id.state_name_sp);
        districtSpinnerHintTitleTextView = findViewById(R.id.district_sp_title_hint);
        districtContactSpinner = findViewById(R.id.district_name_sp);
        tahsilSpinnerHintTitleTextView = findViewById(R.id.tahsil_sp_title_hint);
        tahsilContactSpinner = findViewById(R.id.tahsil_name_sp);
        submitButton = findViewById(R.id.sp_save_btn);

        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();

        stateNameSpinnerHintTitleTextView.setText(getString(R.string.state));
        districtSpinnerHintTitleTextView.setText(getString(R.string.district));
        tahsilSpinnerHintTitleTextView.setText(getString(R.string.tahsil));

        initToolbar();
        initStateNameSpinner();
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.smart_gram_panchayat);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    protected void registerEvents() {

        stateNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StatePojo statePojo = (StatePojo) parent.getItemAtPosition(position);
                stateId = statePojo.getId();
                stateNameSpinnerOnItemSelected(statePojo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtContactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DistrictPojo districtPojo = (DistrictPojo) parent.getItemAtPosition(position);
                districtId = districtPojo.getId();
                districtSpinnerOnItemSelected(districtPojo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tahsilContactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TahsilPojo tahsilPojo = (TahsilPojo) parent.getItemAtPosition(position);
                tahsilId = tahsilPojo.getId();
                tahsilSpinnerOnItemSelected(tahsilPojo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormValid()) {
                    submitButtonOnClick();
                }
            }
        });
    }

    private boolean isFormValid() {

        if (stateNameSpinner.getSelectedItemPosition() == 0) {
            Toasty.warning(this, "" + getString(R.string.plz_select_state), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void submitButtonOnClick() {

        getGeamPanchyatList(true);
    }

    private void stateNameSpinnerOnItemSelected(StatePojo statePojo) {

        if (statePojo.getId().equals("0")) {

            stateNameSpinnerHintTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            stateNameSpinnerHintTitleTextView.setVisibility(View.VISIBLE);
        }
        initDistrictSpinner();
    }

    private void initStateNameSpinner() {

        List<StatePojo> statePojoListTemp = new ArrayList<StatePojo>();

        if (!AUtils.isNull(statePojoList) && !statePojoList.isEmpty()) {

            for (StatePojo statePojo : statePojoList) {

                statePojoListTemp.add(statePojo);
            }
        }

        StatePojo statePojo = new StatePojo();
        statePojo.setId("0");
        statePojo.setStateName(getString(R.string.select_state));
        statePojo.setStateNameMar(getString(R.string.select_state));

        statePojoListTemp.add(0, statePojo);

        ArrayAdapter<StatePojo> statePojoArrayAdapter = new ArrayAdapter(
                this, R.layout.spinner_text_view, statePojoListTemp);
        stateNameSpinner.setAdapter(statePojoArrayAdapter);
    }

    private void initDistrictSpinner() {

        if (stateId.equals("0")) {

            List<DistrictPojo> districtPojoList = new ArrayList<>();

            DistrictPojo districtPojo = new DistrictPojo();
            districtPojo.setId("0");
            districtPojo.setDistrictNameMar(getString(R.string.select_district));
            districtPojo.setDistrictName(getString(R.string.select_district));
            districtPojoList.add(0, districtPojo);

            ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                    this, R.layout.spinner_text_view, districtPojoList);
            districtContactSpinner.setAdapter(contactArrayAdapter);

        } else {

            getDistrictList(true);
        }
        initTahsilSpinner();
    }

    private void initTahsilSpinner() {

        if (districtId.equals("0")) {

            List<TahsilPojo> tahsilPojoArrayList = new ArrayList<>();

            TahsilPojo tahsilPojo = new TahsilPojo();
            tahsilPojo.setId("0");
            tahsilPojo.setName("Select Tahsil");
            tahsilPojo.setNameMar("तहसील निवडा");
//            tahsilPojo.setName(getString(R.string.select_tahsil));
//            tahsilPojo.setNameMar(getString(R.string.select_tahsil));
            tahsilPojoArrayList.add(0, tahsilPojo);

            ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                    this, R.layout.spinner_text_view, tahsilPojoArrayList);
            tahsilContactSpinner.setAdapter(contactArrayAdapter);

        } else {

            getTahsilList(true);
        }
    }

    private void tahsilSpinnerOnItemSelected(TahsilPojo tahsilPojo) {

        if (tahsilPojo.getId().equals("0")) {

            tahsilSpinnerHintTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            tahsilSpinnerHintTitleTextView.setVisibility(View.VISIBLE);
        }
    }


    private void getDistrictList(boolean isShowPrgressDialog) {

        new MyAsyncTask(this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullDistrictListFromServer(stateId);
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<DistrictPojo>>() {
                }.getType();

                districtPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.DISTRICT_POJO_LIST, null), type);

                if (!AUtils.isNull(districtPojoList) && !districtPojoList.isEmpty()) {

                    DistrictPojo districtPojo = new DistrictPojo();
                    districtPojo.setId("0");
                    districtPojo.setDistrictName(getString(R.string.select_district));
                    districtPojo.setDistrictNameMar(getString(R.string.select_district));
                    districtPojoList.add(0, districtPojo);

                    ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                            SelectPlaceActivity.this, R.layout.spinner_text_view, districtPojoList);
                    districtContactSpinner.setAdapter(contactArrayAdapter);
                } else {

                    List<DistrictPojo> districtPojoList = new ArrayList<>();

                    DistrictPojo districtPojo = new DistrictPojo();
                    districtPojo.setId("0");
                    districtPojo.setDistrictName(getString(R.string.select_district));
                    districtPojo.setDistrictNameMar(getString(R.string.select_district));
                    districtPojoList.add(0, districtPojo);

                    ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                            SelectPlaceActivity.this, R.layout.spinner_text_view, districtPojoList);
                    districtContactSpinner.setAdapter(contactArrayAdapter);
                }
            }
        }).execute();
    }

    private void getTahsilList(boolean isShowPrgressDialog) {

        new MyAsyncTask(this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullTahsilListFromServer(districtId);
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<TahsilPojo>>() {
                }.getType();

                tahsilPojoArrayList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.TAHSIL_POJO_LIST, null), type);

                if (!AUtils.isNull(tahsilPojoArrayList) && !tahsilPojoArrayList.isEmpty()) {

                    TahsilPojo tahsilPojo = new TahsilPojo();
                    tahsilPojo.setId("0");
                    tahsilPojo.setName(getString(R.string.select_tahsil));
                    tahsilPojo.setNameMar(getString(R.string.select_tahsil));
                    tahsilPojoArrayList.add(0, tahsilPojo);

                    ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                            SelectPlaceActivity.this, R.layout.spinner_text_view, tahsilPojoArrayList);
                    tahsilContactSpinner.setAdapter(contactArrayAdapter);
                } else {


                    List<TahsilPojo> tahsilPojoArrayList = new ArrayList<>();

                    TahsilPojo tahsilPojo = new TahsilPojo();
                    tahsilPojo.setId("0");
                    tahsilPojo.setName(getString(R.string.select_tahsil));
                    tahsilPojo.setNameMar(getString(R.string.select_tahsil));
                    tahsilPojoArrayList.add(0, tahsilPojo);

                    ArrayAdapter<DistrictPojo> contactArrayAdapter = new ArrayAdapter(
                            SelectPlaceActivity.this, R.layout.spinner_text_view, tahsilPojoArrayList);
                    tahsilContactSpinner.setAdapter(contactArrayAdapter);
                }
            }
        }).execute();
    }


    private void districtSpinnerOnItemSelected(DistrictPojo districtPojo) {

        if (districtPojo.getId().equals("0")) {

            districtSpinnerHintTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            districtSpinnerHintTitleTextView.setVisibility(View.VISIBLE);
        }
        initTahsilSpinner();
    }


    @Override
    protected void initData() {

        initStateNameSpinner();
        getStateList(true);
    }

    private void getStateList(boolean isShowPrgressDialog) {

        new MyAsyncTask(this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullStateListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<StatePojo>>() {
                }.getType();

                statePojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.STATE_POJO_LIST, null), type);

                initStateNameSpinner();

            }
        }).execute();
    }

    private void getGeamPanchyatList(boolean isShowPrgressDialog) {

        new MyAsyncTask(this, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullGramPanchayatListFromServer(stateId, districtId, tahsilId);
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<GramPanchayatPojo>>() {
                }.getType();

                List<GramPanchayatPojo> gramPanchayatPojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.GRAM_PANCHAYAT_LIST, null), type);

                if (!AUtils.isNull(gramPanchayatPojoList) && !gramPanchayatPojoList.isEmpty()) {

                    startActivity(new Intent(SelectPlaceActivity.this, SelectGramPanchayatActivity.class));

                } else {

//                    Toasty.warning(SelectPlaceActivity.this, "No gram panchayat found", Toast.LENGTH_SHORT).show();
                    Toasty.warning(SelectPlaceActivity.this, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (android.R.id.home == item.getItemId()) {
            if (QuickUtils.prefs.getInt(AUtils.FRAGMENT_COUNT, 1) == 0) {
                onBackPressed();
            }
            return true;
        } else if (R.id.action_language == item.getItemId()) {

            changeLanguageOnClick();
            return true;

        } else if (R.id.action_rate == item.getItemId()) {
            AUtils.rateApp(SelectPlaceActivity.this);
            return true;

        } else if (R.id.action_share_app == item.getItemId()) {
            AUtils.shareThisApp(SelectPlaceActivity.this, "");
            return true;

        } else if (R.id.action_about_appynitty == item.getItemId()) {
            startActivity(new Intent(SelectPlaceActivity.this, AboutAppynittyActivity.class));
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void changeLanguageOnClick() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose Language / भाषा निवडा");

        alert.setPositiveButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                QuickUtils.prefs.save(AUtils.LANGUAGE_ID, "1");
                changeLanguage(1);
            }
        });
        alert.setNegativeButton("मराठी", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                QuickUtils.prefs.save(AUtils.LANGUAGE_ID, "2");
                changeLanguage(2);
            }
        });
        alert.show();
    }

    public void changeLanguage(int type) {

        AUtils.changeLanguage(this, type);
        onResume();
    }

}
