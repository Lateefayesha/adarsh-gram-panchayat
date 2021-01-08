package com.appynitty.gp.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by Ayan Dey on 27/7/19.
 */
public class LanguageChangeAdapter extends AlertDialog {

    public static final int languageEnglish = 1;
    public static final int languageMarathi = 2;
    public static final int languageHindi = 3;

    private Context mContext;
    private LanguageSelectListner languageSelectListner;
    private ArrayList<LanguagePojo> pojoList;

    protected LanguageChangeAdapter(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initComponent() {
        generateid();
        registerEvents();
        initData();
    }

    private void generateid() {
        pojoList = new ArrayList<>();
    }

    private void registerEvents() {
    }

    private void initData() {
        pojoList.add(new LanguagePojo("English", 1));
        pojoList.add(new LanguagePojo("मराठी", 2));
        pojoList.add(new LanguagePojo("हिंदी", 3));
    }

    public void setLanguageSelectListner(LanguageSelectListner languageSelectListner) {
        this.languageSelectListner = languageSelectListner;
    }

    public interface LanguageSelectListner{
        void onLanguageSelect(int selectedLanguage);
    }

    private class LanguagePojo{
        private String name;
        private int id;

        LanguagePojo(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
