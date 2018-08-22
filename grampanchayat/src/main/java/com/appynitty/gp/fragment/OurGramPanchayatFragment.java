package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.OurGramPanchayatListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ContactUsTeamMember;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.MyNoDataView;

import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class OurGramPanchayatFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private OurGramPanchayatListAdapter ourGramPanchayatListAdapter;
    //    private ListView listView;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private MyNoDataView noDataView;
    private List<OurGramPanchayatPojo> ourGramPanchayatPojoList;
    private LinearLayout listLinearLayout;
    private ScrollView scrollView;
//    private List<ContactUsTeamMember> contactUsTeamMembers;

    public static OurGramPanchayatFragment newInstance() {

        OurGramPanchayatFragment fragment = new OurGramPanchayatFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.our_grampanchyat_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.our_gram_panchayat));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

//        listView = view.findViewById(R.id.content_lv);
        scrollView = view.findViewById(R.id.our_gram_panchayat_sv);
        listLinearLayout = view.findViewById(R.id.our_gram_panchayat_ll);
//        swipeRefreshLayout = view.findViewById(R.id.content_refresh);
        noDataView = view.findViewById(R.id.no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
    }

    private void registerEvents() {

//        swipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
////                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
//
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
////                        myUpdateOperation();
//                        getDataFromServer(false);
//                    }
//                }
//        );

    }


    private void ininData() {

        initListData();
    }

    private void initListData() {

        listLinearLayout.removeAllViews();

        Type type = new TypeToken<List<OurGramPanchayatPojo>>() {
        }.getType();

        ourGramPanchayatPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        Type type2 = new TypeToken<List<ContactUsTeamMember>>() {
        }.getType();

//        contactUsTeamMembers = new Gson().fromJson(
//                QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type2);

        listLinearLayout.addView(getListViewItem2());

        if ((!AUtils.isNull(ourGramPanchayatPojoList) && !ourGramPanchayatPojoList.isEmpty())
//                || (!AUtils.isNull(contactUsTeamMembers) && !contactUsTeamMembers.isEmpty())
                ) {

            if (!AUtils.isNull(ourGramPanchayatPojoList) && !ourGramPanchayatPojoList.isEmpty()) {
                for (OurGramPanchayatPojo ourGramPanchayatPojo : ourGramPanchayatPojoList) {

                    listLinearLayout.addView(getListViewItem(ourGramPanchayatPojo));
                }
            }
//            if (!AUtils.isNull(contactUsTeamMembers) && !contactUsTeamMembers.isEmpty()) {
//                for (ContactUsTeamMember contactUsTeamMember : contactUsTeamMembers) {
//
//                    if (!contactUsTeamMember.getMemberId().equals("2023") &&
//                            !contactUsTeamMember.getMemberId().equals("2022")) {
//
//                        listLinearLayout.addView(getListViewItem2(contactUsTeamMember));
//                    }
//                }
//            }

            getDataFromServer(false);
        } else {

            noDataView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            getDataFromServer(true);
        }
    }

    private View getListViewItem(OurGramPanchayatPojo contentPojo) {

        final View view = getLayoutInflater().inflate(R.layout.content_list_adapter, null);

        TextView title = view.findViewById(R.id.note_adp_title);
        TextView description = view.findViewById(R.id.note_adp_desc);

        title.setText(contentPojo.getTitle());
        description.setText(contentPojo.getDescription());

        return view;
    }


    private View getListViewItem2() {

        final View view = getLayoutInflater().inflate(R.layout.staff_list_adapter, null);

        TextView title = view.findViewById(R.id.note_adp_title);
        TextView description = view.findViewById(R.id.note_adp_desc);
        ImageView image = view.findViewById(R.id.staff_img);

        title.setText(R.string.title);
        description.setText(R.string.dscription);
        image.setImageResource(R.drawable.profile_image_bawankule);


//        if (!AUtils.isNullString(staffPojo.getJobTitle())) {
//
//            title.setText(staffPojo.getMemberName() + "\n(" + staffPojo.getJobTitle() + ")");
//        } else {
//            title.setText(staffPojo.getMemberName());
//        }
//
//        if (!AUtils.isNullString(staffPojo.getProfileImageUrl())) {
//
//            Glide.with(context).load(staffPojo.getProfileImageUrl())
//                    .placeholder(R.drawable.loading_image)
//                    .error(R.drawable.loading_image)
//                    .into(image);
//        } else {
//
//            image.setImageResource(R.drawable.loading_image);
//        }
//
//        if (!AUtils.isNullString(staffPojo.getMemberDescription())) {
//
//            description.setText(staffPojo.getMemberDescription());
//        } else {
//            description.setText("");
//        }

        return view;
    }


    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullOurGramPanchayatListFromServer();
//                isDataPull = syncServer.pullContactUsListFromServer();
            }

            @Override
            public void onFinished() {

                if (AUtils.isNull(ourGramPanchayatPojoList)
//                        && AUtils.isNull(contactUsTeamMembers)
                        ) {

                    listLinearLayout.removeAllViews();

                    Type type = new TypeToken<List<OurGramPanchayatPojo>>() {
                    }.getType();

                    ourGramPanchayatPojoList = new Gson().fromJson(
                            QuickUtils.prefs.getString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                    Type type2 = new TypeToken<List<ContactUsTeamMember>>() {
                    }.getType();

                    listLinearLayout.addView(getListViewItem2());

//                    contactUsTeamMembers = new Gson().fromJson(
//                            QuickUtils.prefs.getString(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type2);

                    if ((!AUtils.isNull(ourGramPanchayatPojoList) && !ourGramPanchayatPojoList.isEmpty())
//                            || (!AUtils.isNull(contactUsTeamMembers) && !contactUsTeamMembers.isEmpty())
                            ) {

                        noDataView.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                        if (!AUtils.isNull(ourGramPanchayatPojoList) && !ourGramPanchayatPojoList.isEmpty()) {
                            for (OurGramPanchayatPojo ourGramPanchayatPojo : ourGramPanchayatPojoList) {

                                listLinearLayout.addView(getListViewItem(ourGramPanchayatPojo));
                            }
                        }
//                        if (!AUtils.isNull(contactUsTeamMembers) && !contactUsTeamMembers.isEmpty()) {
//                            for (ContactUsTeamMember contactUsTeamMember : contactUsTeamMembers) {
//
//                                if (!contactUsTeamMember.getMemberId().equals("2023") &&
//                                        !contactUsTeamMember.getMemberId().equals("2022")) {
//
//                                    listLinearLayout.addView(getListViewItem2(contactUsTeamMember));
//                                }
//                            }
//                        }
                    } else {

                        noDataView.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                    }
                }
            }
        }).execute();
    }
}