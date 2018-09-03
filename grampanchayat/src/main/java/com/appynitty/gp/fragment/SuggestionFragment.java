package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SuggestionPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.mithsoft.lib.componants.Toasty;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class SuggestionFragment extends MyFragemtV4 {

    private static final String TAG = "TankerBookingFragment";
    private View view;
    private Context context;
    private EditText nameTextView;
    private EditText mobileNoTextView;
    private EditText emailTextView;
    private EditText suggestionTextView;
    private EditText addressTextView;
    private Button saveButton;
    private SuggestionPojo suggestionPojo;

    public static SuggestionFragment newInstance() {

        SuggestionFragment fragment = new SuggestionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.feedback_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.suggestion_tab));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        nameTextView = view.findViewById(R.id.fb_name_et);
        mobileNoTextView = view.findViewById(R.id.fb_number_et);
        emailTextView = view.findViewById(R.id.fb_email_et);
        suggestionTextView = view.findViewById(R.id.fb_suggesstion_et);
        addressTextView = view.findViewById(R.id.fb_address_et);
        saveButton = view.findViewById(R.id.fb_save_btn);
    }

    private void registerEvents() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButtonOnClick();
            }
        });
    }


    private void saveButtonOnClick() {

//        if (validateForm()) {
        getFormData();

        new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
            public ResultPojo resultPojo = null;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                resultPojo = syncServer.saveSuggestion(suggestionPojo);
            }

            @Override
            public void onFinished() {

                if (!AUtils.isNull(resultPojo)) {

                    if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        Toasty.success(context, "" + getString(R.string.submit_done), Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    } else {
                        Toasty.error(context, "" + getString(R.string.submit_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(context, "" + getString(R.string.serverError), Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();
//        }

    }

    private boolean validateForm() {

        if (AUtils.isNullString(nameTextView.getText().toString())
                && AUtils.isNullString(mobileNoTextView.getText().toString())
                && AUtils.isNullString(emailTextView.getText().toString())
                && AUtils.isNullString(addressTextView.getText().toString())
                && AUtils.isNullString(suggestionTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_something), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (AUtils.isNullString(nameTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_name), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (AUtils.isNullString(mobileNoTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (mobileNoTextView.getText().toString().length() < 10) {
//            Toasty.warning(context, getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (!AUtils.isNullString(emailTextView.getText().toString()) && !AUtils.isEmailValid(emailTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_valid_email), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (AUtils.isNullString(addressTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_address), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (AUtils.isNullString(suggestionTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_suggestion), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    private void getFormData() {

        suggestionPojo = new SuggestionPojo();
        suggestionPojo.setName(nameTextView.getText().toString());
        suggestionPojo.setMobileNumber(mobileNoTextView.getText().toString());
        suggestionPojo.setEmailAddress(emailTextView.getText().toString());
        suggestionPojo.setAddress(addressTextView.getText().toString());
        suggestionPojo.setSuggesstion(suggestionTextView.getText().toString());
    }
}