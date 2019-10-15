package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.RegistrationAdapterClass;
import com.appynitty.ghantagaditracker.pojo.RegistrationDetailsPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

public class RegistrationActivity extends AppCompatActivity {

    EditText txtHouseid, txtOtp, txtContact;
    private Context mContext;
    private ViewStub viewStub;
    private View viewLoaded;
    private String viewToLoadName;
    private String refId, mobNo, otp;

    private MyProgressDialog progressDialog;

    private RegistrationAdapterClass registrationAdapterClass;

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

    private void initComponents() {

        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        setContentView(R.layout.activity_registration);
        mContext = RegistrationActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_registration));
        setSupportActionBar(toolbar);
        progressDialog = new MyProgressDialog(mContext, R.drawable.progress_bar, false);

        viewStub = findViewById(R.id.view_stub);

        registrationAdapterClass = new RegistrationAdapterClass();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.registration_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_skip_registration)
            startDashboard();
        return true;
    }

    private void registerEvents() {
        registrationAdapterClass.setRegistrationListner(new RegistrationAdapterClass.RegistrationListner() {
            @Override
            public void onSuccessCallback(RegistrationDetailsPojo detailsPojo, int CallType) {
                progressDialog.dismiss();
                if (!AUtils.isNull(detailsPojo)) {
                    if (detailsPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        switch (CallType) {
                            case RegistrationAdapterClass.RESPONSE_REGISTRATION_DETAILS:
                                mobNo = detailsPojo.getMobileNo();
                                loadViewStub(AUtils.ViewToLoad.VerifyContactView);
                                break;
                            case RegistrationAdapterClass.RESPONSE_REGISTRATION_OTP:
                                otp = detailsPojo.getOTP();
                                loadViewStub(AUtils.ViewToLoad.OtpView);
                                break;
                            case RegistrationAdapterClass.RESPONSE_REGISTRATION_VERIFIED:
                                Prefs.putBoolean(AUtils.PREFS.IS_USER_LOGIN, true);
                                Prefs.putString(AUtils.PREFS.REFERENCE_ID, refId);
                                startDashboard();
                                break;
                        }
                    } else {
                        if (Prefs.getString(AUtils.LANGUAGE_NAME, "en").equals("en"))
                            AUtils.info(mContext, detailsPojo.getMessage());
                        else
                            AUtils.info(mContext, detailsPojo.getMessageMar());
                    }
                } else
                    AUtils.error(mContext, getResources().getString(R.string.something_error));
            }

            @Override
            public void onFailureCallback() {
                progressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.something_error));
            }

            @Override
            public void onErrorCallback() {
                progressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.serverError));
            }
        });
    }

    private void initData() {
        loadViewStub(AUtils.ViewToLoad.HouseIdView);
    }

    private void loadViewStub(String viewName) {

        if (!AUtils.isNull(viewLoaded)) {
            ((ViewGroup) viewLoaded).removeAllViews();
        }

        viewToLoadName = viewName;
        LayoutInflater inflater = viewStub.getLayoutInflater();

        switch (viewName) {
            case AUtils.ViewToLoad.HouseIdView:
                if (AUtils.isNull(viewLoaded)) {
                    viewStub.setLayoutResource(R.layout.content_registration);
                    viewLoaded = viewStub.inflate();
                } else {
                    viewLoaded = inflater.inflate(R.layout.content_registration, (ViewGroup) viewLoaded);
                }
                loadRegistrationView();
                break;
            case AUtils.ViewToLoad.OtpView:
                viewLoaded = inflater.inflate(R.layout.content_registration_otp, (ViewGroup) viewLoaded);
                loadOtpView();
                break;
            case AUtils.ViewToLoad.VerifyContactView:
                viewLoaded = inflater.inflate(R.layout.content_verify_contact, (ViewGroup) viewLoaded);
                loadVerifyContactView();
                break;
        }
    }

    private void loadOtpView() {
        txtOtp = viewLoaded.findViewById(R.id.ip_otp);
        txtOtp.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_otp);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userOtp = txtOtp.getText().toString();
                if (userOtp.isEmpty())
                    txtOtp.setError(getResources().getString(R.string.otp_hint));
                else if (userOtp.equals(otp)) {
                    progressDialog.show();
                    registrationAdapterClass.callSaveDeviceDetails(refId);
                } else
                    AUtils.error(mContext, getResources().getString(R.string.otp_hint_valid));
            }
        });

        Button button = viewLoaded.findViewById(R.id.btn_resend_otp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOtp();
            }
        });
    }

    private void loadVerifyContactView() {
        Button button = viewLoaded.findViewById(R.id.btn_edit_contact);
        txtContact = viewLoaded.findViewById(R.id.ip_verify_contact);

        if (mobNo != null && !mobNo.isEmpty()) {
            txtContact.setText(mobNo);
            txtContact.setEnabled(false);
            button.setVisibility(View.VISIBLE);
        }

        txtContact.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_contact);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = txtContact.getText().toString();
                if (contact.isEmpty())
                    txtContact.setError(getResources().getString(R.string.plz_ent_mobile_no));
                else if (contact.length() == 10 && contact.matches("[0-9]+$")) {
                    mobNo = contact;
                    requestOtp();
                } else {
                    txtContact.setError(getResources().getString(R.string.plz_ent_valid_mobile_no));
                    AUtils.error(mContext, getResources().getString(R.string.plz_ent_valid_mobile_no));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtContact.setEnabled(true);
                txtContact.setSelection(txtContact.getText().toString().length());
                txtContact.requestFocus();
                view.setVisibility(View.GONE);
            }
        });
    }

    private void loadRegistrationView() {
        txtHouseid = viewLoaded.findViewById(R.id.ip_house_id);
        if (refId != null && !refId.isEmpty()) {
            txtHouseid.setText(refId);
            txtHouseid.setSelection(refId.length());
        }
        txtHouseid.requestFocus();

        ImageButton imageButton = viewLoaded.findViewById(R.id.btn_submit_hp_id);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String referenceId = txtHouseid.getText().toString();
                if (referenceId.isEmpty())
                    txtHouseid.setError(getResources().getString(R.string.house_number_hint));
                if (referenceId.toLowerCase().matches("hpsba[0-9]+$")) {
                    refId = referenceId;
                    submitReferenceId();
                } else {
                    txtHouseid.setError(getResources().getString(R.string.house_number_hint_valid));
                    AUtils.warning(mContext, getResources().getString(R.string.house_number_hint_valid));
                }
            }
        });
    }

    private void submitReferenceId() {
        progressDialog.show();
        registrationAdapterClass.callRegistrationDetails(refId);
    }

    private void requestOtp() {
        progressDialog.show();
        registrationAdapterClass.callRegistrationOtp(refId, mobNo);
    }

    private void startDashboard() {
        startActivity(new Intent(mContext, DashboardActivity.class));
        ((Activity) mContext).finish();
    }

    @Override
    public void onBackPressed() {
        switch (viewToLoadName) {
            case AUtils.ViewToLoad.OtpView:
                loadViewStub(AUtils.ViewToLoad.VerifyContactView);
                break;
            case AUtils.ViewToLoad.VerifyContactView:
                loadViewStub(AUtils.ViewToLoad.HouseIdView);
                break;
            default:
                super.onBackPressed();
        }
    }
}
