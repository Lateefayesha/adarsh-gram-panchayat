package com.appynitty.gp.controller;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.riaylibrary.utils.LocaleHelper;

/**
 * Created by Ayan Dey on 7/8/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
    }

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initComponents();
    }

    private void initComponents() {
        this.generateId();
        this.registerEvents();
        this.initData();
    }

    protected abstract void generateId();

    protected abstract void registerEvents();

    protected abstract void initData();
}
