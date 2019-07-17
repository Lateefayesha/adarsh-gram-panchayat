package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import quickutils.core.QuickUtils;

public class SettingActivity extends AppCompatActivity {

    private Context mContext;
    private Spinner languageSpinner;
    private HashMap<String, String> languageStringHash;
    private ArrayList<String> languageList;

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
        initDate();
    }

    private void generateId(){
        setContentView(R.layout.activity_setting);
        mContext = SettingActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        languageStringHash = new HashMap<>();

        languageSpinner = findViewById(R.id.spinner_language);

    }
    private void registerEvents(){

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String lang = languageStringHash.get(languageList.get(position));
                try {
                    if(!lang.equals(QuickUtils.prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME))){
                        AUtils.changeLanguage((Activity) mContext, lang);
                        ((Activity)mContext).recreate();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDate(){
        initLanguageStringHash();
        initlanguageSpinner();
    }

    private void initlanguageSpinner(){
        languageList = new ArrayList<String>();
        languageList.add("English");
        languageList.add("मराठी");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1, languageList);
        languageSpinner.setAdapter(adapter);

        String language = QuickUtils.prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME);
        int position = languageList.indexOf(languageStringHash.get(language));
        if(position > 0){
            languageSpinner.setSelection(position);
        }

    }

    private void initLanguageStringHash(){
        languageStringHash.put("English","en");
        languageStringHash.put("en","English");
        languageStringHash.put("मराठी","mr");
        languageStringHash.put("mr","मराठी");
        languageStringHash.put("हिंदी","hi");
        languageStringHash.put("hi","हिंदी");
        languageStringHash.put("ગુજરાતી","gu");
        languageStringHash.put("gu","ગુજરાતી");
        languageStringHash.put("ਪੰਜਾਬੀ","pa");
        languageStringHash.put("pa","ਪੰਜਾਬੀ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recreate() {
        super.recreate();
        AUtils.isChangeLang = true;
    }

    @Override
    public void onBackPressed() {
        if(AUtils.isChangeLang){
            AUtils.isChangeLang = false;
            setResult(RESULT_OK, new Intent());
            finish();
        }
        else
            super.onBackPressed();
    }
}
