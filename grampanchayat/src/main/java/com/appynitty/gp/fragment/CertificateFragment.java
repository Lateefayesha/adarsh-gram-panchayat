package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.CertificateItemActivity;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.adapter.CertificateListAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.CertificatePojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class CertificateFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Certificate;

    private View view;
    private Context context;
    private CertificateListAdapter certificateListAdapter;
    private ListView listView;
    private List<CertificatePojo> certificatePojoList;

    public static CertificateFragment newInstance() {

        CertificateFragment fragment = new CertificateFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.certificate_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.certificate));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        Prefs.putInt(AUtils.FRAGMENT_COUNT, 0);

        listView = view.findViewById(R.id.content_lv);
    }

    private void registerEvents() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CertificatePojo certificatePojo = (CertificatePojo) parent.getItemAtPosition(position);

                if (!AUtils.isNull(certificatePojo) && !AUtils.isNullString(certificatePojo.getPageLink())
                        && !AUtils.isNullString(certificatePojo.getDocumentName())) {

                    if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {

                        Intent intent = new Intent(context, CertificateItemActivity.class);
                        intent.putExtra(AUtils.UTILITY_URL, certificatePojo.getPageLink());
                        intent.putExtra(AUtils.UTILITY_TITLE, certificatePojo.getDocumentName());
                        startActivity(intent);
                    } else {

                        AUtils.warning(context, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT);
                    }

                } else {

                    Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initData() {

        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<CertificatePojo>>() {
        }.getType();

        certificatePojoList = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.CERTIFICATE_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

        if (!AUtils.isNull(certificatePojoList) && !certificatePojoList.isEmpty()) {

            certificateListAdapter = new CertificateListAdapter(context, certificatePojoList);
            listView.setAdapter(certificateListAdapter);

            getDataFromServer(false);

        } else {

            getDataFromServer(true);
        }


    }

    private void getDataFromServer(boolean isShowPrgressDialog) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullCertificateListFromServer();
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<CertificatePojo>>() {
                }.getType();

                certificatePojoList = new Gson().fromJson(
                        Prefs.getString(AUtils.PREFS.CERTIFICATE_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null), type);

                if (!AUtils.isNull(certificatePojoList) && !certificatePojoList.isEmpty()) {

                    certificateListAdapter = new CertificateListAdapter(context, certificatePojoList);
                    listView.setAdapter(certificateListAdapter);
                }
            }
        }).execute();
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