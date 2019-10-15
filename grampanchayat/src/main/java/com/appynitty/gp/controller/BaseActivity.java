package com.appynitty.gp.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Ayan Dey on 7/8/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
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
