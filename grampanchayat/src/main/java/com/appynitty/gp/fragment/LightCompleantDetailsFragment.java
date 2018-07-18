package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class LightCompleantDetailsFragment extends MyFragemtV4 {

    private static final String TAG = "LightCompleantDetailsFragment";
    private View view;
    private Context context;
    private TextView complentDetailsTextView;
    private Button applyButton;

    public static LightCompleantDetailsFragment newInstance() {

        LightCompleantDetailsFragment fragment = new LightCompleantDetailsFragment();
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
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.light_info));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        complentDetailsTextView = view.findViewById(R.id.complent_details);
        complentDetailsTextView.setText(R.string.light_complent_details);
        applyButton = view.findViewById(R.id.complent_appy_btn);
        applyButton.setText(R.string.light_compleant_booking);
    }

    private void registerEvents() {

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyButtonOnClick();
            }
        });
    }

    private void applyButtonOnClick() {

        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, LightCompleantFragment.newInstance()).commit();
    }


}