package com.appynitty.gp.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.TankerBookingPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.mithsoft.lib.componants.Toasty;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class TankerBookingFragment extends MyFragemtV4 {

    private static final String TAG = "TankerBookingFragment";
    private View view;
    private Context context;
    private EditText nameTextView;
    private EditText mobileNoTextView;
    private EditText wardNoTextView;
    private EditText addressTextView;
    private LinearLayout yoccLinearLayout;
    private Button saveButton;
    private TankerBookingPojo tankerBookingPojo;

    public static TankerBookingFragment newInstance() {

        TankerBookingFragment fragment = new TankerBookingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tanker_booking_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.tanker_booking));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        nameTextView = view.findViewById(R.id.tb_name_et);
        mobileNoTextView = view.findViewById(R.id.tb_number_et);
        wardNoTextView = view.findViewById(R.id.tb_prabhag_kramak_et);
        addressTextView = view.findViewById(R.id.tb_address_et);
        saveButton = view.findViewById(R.id.tb_save_btn);
        yoccLinearLayout = view.findViewById(R.id.tb_yocc_no_ll);
    }

    private void registerEvents() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButtonOnClick();
            }
        });

        yoccLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "8806750750"));
                    context.startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call has failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void saveButtonOnClick() {

        if (validateForm()) {
            getFormData();

            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public ResultPojo resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.saveTankerBooking(tankerBookingPojo);
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
        }

    }

    private boolean validateForm() {

        if (AUtils.isNullString(nameTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(mobileNoTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mobileNoTextView.getText().toString().length() < 10) {
            Toasty.warning(context, getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(wardNoTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(addressTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_address), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getFormData() {

        tankerBookingPojo = new TankerBookingPojo();
        tankerBookingPojo.setName(nameTextView.getText().toString());
        tankerBookingPojo.setNumber(mobileNoTextView.getText().toString());
        tankerBookingPojo.setDoorNo(wardNoTextView.getText().toString());
        tankerBookingPojo.setWardNo(wardNoTextView.getText().toString());
        tankerBookingPojo.setAddress(addressTextView.getText().toString());
        tankerBookingPojo.setCreatedDate(AUtils.getCurrentDateTime());
        tankerBookingPojo.setLanguageId("1");
    }
}