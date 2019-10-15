package com.appynitty.gp.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.VedioGalleryAdapter;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.PhotoGalleryVideo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by MiTHUN on 8/2/18.
 */

public class VedioGalleryFragment extends Fragment {

    private View view;
    private Context context;
    private VedioGalleryAdapter vedioGalleryAdapter;
    private GridView imagesGridView;
    private List<PhotoGalleryVideo> photoGalleryVideoList = null;

    public static VedioGalleryFragment newInstance() {

        VedioGalleryFragment fragment = new VedioGalleryFragment();
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

        PhotoGalleryVideo photoGalleryVideo = photoGalleryVideoList.get(position);

//        Intent intent = new Intent(context, YouTubePlayerActivity.class);
//        intent.putExtra("VIDEO_ID", AUtils.getYoutubeVideoId(photoGalleryVideo.getVideoUrl()));
//        startActivity(intent);

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + AUtils.getYoutubeVideoId(photoGalleryVideo.getVideoUrl())));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {

            AUtils.warning(context, "Please download youtube player", Toast.LENGTH_SHORT);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.youtube")));

//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.youtube.com/watch?v=8mKTiD02v3M"));
//            context.startActivity(intent);
        }
    }


    private void ininData() {

        Type type = new TypeToken<List<PhotoGalleryVideo>>() {
        }.getType();

        photoGalleryVideoList = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.VEDIO_GALLERY_POJO_LIST, null), type);


        if (!AUtils.isNull(photoGalleryVideoList) && !photoGalleryVideoList.isEmpty()) {

            getDataFromLocal();
//            getDataFromServer(false);
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
                    Type type = new TypeToken<List<PhotoGalleryVideo>>() {
                    }.getType();

                    photoGalleryVideoList = new Gson().fromJson(
                            Prefs.getString(AUtils.PREFS.VEDIO_GALLERY_POJO_LIST, null), type);

                    if (!AUtils.isNull(photoGalleryVideoList) && !photoGalleryVideoList.isEmpty()) {

//                    noDataView.setVisibility(View.GONE);
                        vedioGalleryAdapter = new VedioGalleryAdapter(context, photoGalleryVideoList);
                        imagesGridView.setAdapter(vedioGalleryAdapter);
                    }
                }
            }
        }).execute();
    }

    private void getDataFromLocal() {

        vedioGalleryAdapter = new VedioGalleryAdapter(context, photoGalleryVideoList);
        imagesGridView.setAdapter(vedioGalleryAdapter);
    }

}