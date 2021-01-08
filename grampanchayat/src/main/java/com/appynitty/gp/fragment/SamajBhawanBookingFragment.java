package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SamajBavanBookingPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class SamajBhawanBookingFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Complent_Status_Tab;

    private static final String TAG = "SamajBhawanBookingF";
    private View view;
    private Context context;
    private EditText nameTextView;
    private EditText mobileNoTextView;
    private EditText wardNoTextView;
    private EditText addressTextView;
    private Button saveButton;
    private SamajBavanBookingPojo samajBavanBookingPojo;

    private static SamajBhawanBookingFragment fragment = null;

    public static SamajBhawanBookingFragment newInstance() {

        fragment = new SamajBhawanBookingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.samaj_bhawan_booking_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.samaj_bavan_booking));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 0);

        nameTextView = view.findViewById(R.id.tb_name_et);
        mobileNoTextView = view.findViewById(R.id.tb_number_et);
        wardNoTextView = view.findViewById(R.id.tb_prabhag_kramak_et);
        addressTextView = view.findViewById(R.id.tb_address_et);
        saveButton = view.findViewById(R.id.tb_save_btn);

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

        if (validateForm()) {
            getFormData();

            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public ResultPojo resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.saveSamajBavanBooking(samajBavanBookingPojo);
                }

                @Override
                public void onFinished() {

                    if (!AUtils.isNull(resultPojo)) {

                        if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                            AUtils.success(context, "" + getString(R.string.submit_done), Toast.LENGTH_SHORT);
                            getFragmentManager().popBackStack();
                        } else {
                            AUtils.error(context, "" + getString(R.string.submit_error), Toast.LENGTH_SHORT);
                        }
                    } else {
                        AUtils.error(context, "" + getString(R.string.serverError), Toast.LENGTH_SHORT);
                    }

                }
            }).execute();
        }

    }

    private boolean validateForm() {

        if (AUtils.isNullString(nameTextView.getText().toString())) {
            AUtils.warning(context, getString(R.string.plz_ent_name), Toast.LENGTH_SHORT);
            return false;
        }

        if (AUtils.isNullString(mobileNoTextView.getText().toString())) {
            AUtils.warning(context, getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT);
            return false;
        }

        if (mobileNoTextView.getText().toString().length() < 10) {
            AUtils.warning(context, getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT);
            return false;
        }

        if (AUtils.isNullString(wardNoTextView.getText().toString())) {
            AUtils.warning(context, getString(R.string.plz_ent_ward_no), Toast.LENGTH_SHORT);
            return false;
        }

        if (AUtils.isNullString(addressTextView.getText().toString())) {
            AUtils.warning(context, getString(R.string.plz_ent_address), Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    private void getFormData() {

        samajBavanBookingPojo = new SamajBavanBookingPojo();
        samajBavanBookingPojo.setName(nameTextView.getText().toString());
        samajBavanBookingPojo.setNumber(mobileNoTextView.getText().toString());
        samajBavanBookingPojo.setDoorNo(wardNoTextView.getText().toString());
        samajBavanBookingPojo.setWardNo(wardNoTextView.getText().toString());
        samajBavanBookingPojo.setAddress(addressTextView.getText().toString());
        samajBavanBookingPojo.setCreatedDate(AUtils.getCurrentDateTime());
        samajBavanBookingPojo.setLanguageId("1");
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