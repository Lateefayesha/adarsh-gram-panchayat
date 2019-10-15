package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.activity.PropertTaxDetailsActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.pixplicity.easyprefs.library.Prefs;

public class PropertyTaxFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Property_Tax;

    private View view;
    private Context context;
    private Button getPropertyTaxes;
    private EditText propertyNo;

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

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId(){

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.property_tax));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 0);

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
                        AUtils.info(context, getString(R.string.noData), Toast.LENGTH_SHORT);
                    }
                }
            }).execute();
        }
    }

    private Boolean checkValid(){
        if(AUtils.isNullString(propertyNo.getText().toString())){
            AUtils.warning(context, getString(R.string.plz_ent_property_no), Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!AUtils.menuNavigationListHasItem(fragmentMenuId)){
            AUtils.setMenuNavigationList(fragmentMenuId);
        }else
        if(getFragmentManager() != null)
            getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(!AUtils.isRecreate){
            AUtils.removeMenuNavigationValue(AUtils.MenuIdConstants.Main_Menu_Dashboard,
                    fragmentMenuId);
        }
        AUtils.removeMenuNavigationListValue(fragmentMenuId);
    }
}
