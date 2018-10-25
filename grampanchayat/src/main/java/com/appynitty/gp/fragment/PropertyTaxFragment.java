package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.BookingActivity;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.activity.MyComlaintDetailsActivity;
import com.appynitty.gp.activity.PropertTaxDetailsActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.PropertyTaxPojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.mithsoft.lib.componants.Toasty;

import java.util.List;

import quickutils.core.QuickUtils;

public class PropertyTaxFragment extends MyFragemtV4 {
    private View view;
    private Context context;
    private Button getPropertyTaxes;
    private EditText propertyNo;

    private String mPreviousLanguage = "";
    private static PropertyTaxFragment fragment = null;

    public static PropertyTaxFragment newInstance() {

        fragment = new PropertyTaxFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_property_tax, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    @Override
    public void initComponents() {

        if (!mPreviousLanguage.equals(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID))) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
            mPreviousLanguage = QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID);
        }

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId(){

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.property_tax));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        getPropertyTaxes = view.findViewById(R.id.getPropertyTaxes);
        propertyNo = view.findViewById(R.id.propertyNumber);
    }

    private void registerEvents(){
        getPropertyTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPropertyDetails();
            }
        });
    }

    private void ininData(){}

    private void fetchPropertyDetails(){
        if(checkValid()){
            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public Boolean resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.getDetailsUsingId(propertyNo.getText().toString());
                }

                @Override
                public void onFinished() {
                    if(resultPojo){

                        startActivity(new Intent(context, PropertTaxDetailsActivity.class));

                    }else{
                        Toasty.info(context, getString(R.string.noData), Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute();
        }
    }

    private Boolean checkValid(){
        if(AUtils.isNullString(propertyNo.getText().toString())){
            Toasty.warning(context, getString(R.string.plz_ent_property_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
