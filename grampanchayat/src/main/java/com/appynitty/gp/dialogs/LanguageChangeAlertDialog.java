package com.appynitty.gp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.LanguageChangeListAdapter;
import com.appynitty.gp.customComponents.RecyclerViewDecorator;
import com.appynitty.gp.utils.AUtils;
import com.riaylibrary.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Ayan Dey on 27/7/19.
 */
public class LanguageChangeAlertDialog extends AlertDialog {

    private Context mContext;
    private LanguageSelectListener languageSelectListener;
    private ArrayList<LanguagePojo> pojoList;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private View dialogView;
    private RecyclerView languageRecyclerView;
    private LanguageChangeListAdapter languageChangeListAdapter;

    public LanguageChangeAlertDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void initComponent() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        builder = new AlertDialog.Builder(mContext);
        pojoList = new ArrayList<>();
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_language_list, null);

        languageRecyclerView = dialogView.findViewById(R.id.language_recycler);

        languageRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        languageRecyclerView.addItemDecoration(new RecyclerViewDecorator(mContext, R.dimen.cardview_radius));
        languageChangeListAdapter = new LanguageChangeListAdapter(mContext);
    }

    private void registerEvents() {
        languageChangeListAdapter.setLanguageAdapterListner(new LanguageChangeListAdapter.LanguageAdapterListner() {
            @Override
            public void onLanguageChange(String languageId) {
                languageSelectListener.onLanguageSelect(languageId);
                alertDialog.dismiss();
            }
        });
    }

    private void initData() {
        pojoList.add(new LanguagePojo("English", AUtils.LanguageConstants.ENGLISH));
        pojoList.add(new LanguagePojo("मराठी", AUtils.LanguageConstants.MARATHI));
        pojoList.add(new LanguagePojo("हिंदी", AUtils.LanguageConstants.HINDI));
        initView();
    }

    private void initView() {
        languageChangeListAdapter.setPojoList(pojoList);
        languageRecyclerView.setAdapter(languageChangeListAdapter);
    }

    public void setLanguageSelectListener(LanguageSelectListener languageSelectListener) {
        this.languageSelectListener = languageSelectListener;
    }

    public interface LanguageSelectListener{
        void onLanguageSelect(String selectedLanguage);
    }

    public class LanguagePojo{
        private String name;
        private String id;

        LanguagePojo(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
