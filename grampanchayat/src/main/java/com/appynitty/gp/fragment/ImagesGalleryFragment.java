package com.appynitty.gp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.ViewImageActivity;
import com.appynitty.gp.adapter.PhotoGalleryAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class ImagesGalleryFragment extends Fragment {

    private View view;
    private Context context;
    private PhotoGalleryAdapter photoGalleryAdapter;
    private GridView imagesGridView;
    private List<PhotoGalleryImages> imagesPojoList = null;

    public static ImagesGalleryFragment newInstance() {

        ImagesGalleryFragment fragment = new ImagesGalleryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.photo_gallery_fragment, container, false);
        context = container.getContext();
        initComponants();
        return view;
    }

    public void initComponants() {

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId() {

        imagesGridView = view.findViewById(R.id.content_gv);
    }

    private void registerEvents() {


        imagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                imagesGridViewOnItemClick(position);
            }
        });

    }

    private void imagesGridViewOnItemClick(int position) {

        Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra(AUtils.GALLERY_IMG_POSITION, position);
        intent.putExtra(AUtils.GALLERY_IMG_POJO_LIST, (Serializable) imagesPojoList);
        startActivity(intent);
    }


    private void ininData() {


        Type type = new TypeToken<List<PhotoGalleryImages>>() {
        }.getType();

        imagesPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.IMAGES_GALLERY_POJO_LIST, null), type);


        if (!AUtils.isNull(imagesPojoList) && !imagesPojoList.isEmpty()) {

            getDataFromLocal();
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


                isDataPull = syncServer.pullGalleryListFromServer();
            }

            @Override
            public void onFinished() {

                if (isDataPull) {
                    Type type = new TypeToken<List<PhotoGalleryImages>>() {
                    }.getType();

                    imagesPojoList = new Gson().fromJson(
                            QuickUtils.prefs.getString(AUtils.PREFS.IMAGES_GALLERY_POJO_LIST, null), type);

                    if (!AUtils.isNull(imagesPojoList) && !imagesPojoList.isEmpty()) {

//                    noDataView.setVisibility(View.GONE);
                        photoGalleryAdapter = new PhotoGalleryAdapter(context, imagesPojoList);
                        imagesGridView.setAdapter(photoGalleryAdapter);
                    }
                }
            }
        }).execute();
    }

    private void getDataFromLocal() {

        photoGalleryAdapter = new PhotoGalleryAdapter(context, imagesPojoList);
        imagesGridView.setAdapter(photoGalleryAdapter);
    }

}