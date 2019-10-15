package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class MentananceCompleantDetailsFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Maintenance_Complaints;
    private final static String nextFragmentMenuId = AUtils.MenuIdConstants.Maintenance_Complaints_Register_Form;

    private static final String TAG = "MentananceCompleantDetailsFragment";
    private View view;
    private Context context;
    private TextView complentDetailsTextView;
    private Button applyButton;

    public static MentananceCompleantDetailsFragment newInstance() {

        MentananceCompleantDetailsFragment fragment = new MentananceCompleantDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cleaning_compleant_details_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        initData();
    }

    private void genrateId() {

        if(AUtils.getMenuNavigation(AUtils.MenuIdConstants.Main_Menu_Dashboard).isEmpty())
            if(getFragmentManager() != null)
                getFragmentManager().popBackStack();

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.mantanance_info));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 0);

        complentDetailsTextView = view.findViewById(R.id.complent_details);
        complentDetailsTextView.setText(R.string.maintenance_complent_details);
        applyButton = view.findViewById(R.id.complent_appy_btn);
        applyButton.setText(R.string.mentainence_compleant_booking);
    }

    private void registerEvents() {

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyButtonOnClick();
            }
        });
    }

    private void initData(){
        if(AUtils.getMenuNavigation() != null && AUtils.getMenuNavigation().containsKey(fragmentMenuId))
            applyButtonOnClick();
    }

    private void applyButtonOnClick() {

        AUtils.setMenuNavigation(fragmentMenuId, nextFragmentMenuId);
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, MaintenanceCompleantFragment.newInstance()).commit();
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