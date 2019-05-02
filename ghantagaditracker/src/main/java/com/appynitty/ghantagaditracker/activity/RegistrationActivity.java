package com.appynitty.ghantagaditracker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.utils.AUtils;

public class RegistrationActivity extends AppCompatActivity {

    private Context mContext;
    private ViewStub viewStub;
    private View viewLoaded;
    private String viewToLoadName;


    EditText txtHouseid, txtOtp, txtContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();

    }

    private void initComponents(){

        generateId();
        initData();
    }

    private void generateId(){
        setContentView(R.layout.activity_registration);
        mContext = RegistrationActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewStub = findViewById(R.id.view_stub);

    }

    private void initData(){
        loadViewStub(AUtils.ViewToLoad.HouseIdView);
    }

    private void loadViewStub(String viewName){

        if(!AUtils.isNull(viewLoaded)){
            ((ViewGroup)viewLoaded).removeAllViews();
        }

        viewToLoadName = viewName;
        LayoutInflater inflater = viewStub.getLayoutInflater();

        switch (viewName){
            case AUtils.ViewToLoad.HouseIdView:
                if(AUtils.isNull(viewLoaded)){
                    viewStub.setLayoutResource(R.layout.content_registration);
                    viewLoaded = viewStub.inflate();
                }else {
                    viewLoaded = inflater.inflate(R.layout.content_registration, (ViewGroup)viewLoaded);
                }
                loadRegistrationView();
                break;
            case AUtils.ViewToLoad.OtpView:
                viewLoaded = inflater.inflate(R.layout.content_registration_otp,(ViewGroup)viewLoaded);
                loadOtpView();
                break;
            case AUtils.ViewToLoad.VerifyContactView:
                viewLoaded = inflater.inflate(R.layout.content_verify_contact, (ViewGroup)viewLoaded);
                loadVerifyContactView();
                break;
        }
    }

    private void loadOtpView(){
        txtOtp = viewLoaded.findViewById(R.id.ip_otp);
        txtOtp.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_otp);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadViewStub(AUtils.ViewToLoad.HouseIdView);
            }
        });

        Button button = viewLoaded.findViewById(R.id.btn_resend_otp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loadVerifyContactView(){
        txtContact = viewLoaded.findViewById(R.id.ip_verify_contact);
        txtContact.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_contact);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadViewStub(AUtils.ViewToLoad.OtpView);
            }
        });

        Button button = viewLoaded.findViewById(R.id.btn_edit_contact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loadRegistrationView(){
        txtHouseid = viewLoaded.findViewById(R.id.ip_house_id);
        txtHouseid.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_hp_id);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadViewStub(AUtils.ViewToLoad.VerifyContactView);
            }
        });
    }


}
