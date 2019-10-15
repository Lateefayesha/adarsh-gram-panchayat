package com.appynitty.ghantagaditracker.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.ViewImagePagerAdapter;
import com.appynitty.ghantagaditracker.pojo.PhotoGalleryImages;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.riaylibrary.custom_component.ViewPagerTransformer;
import com.riaylibrary.utils.LocaleHelper;

import java.util.List;

/**
 * Created by Ayan Dey on 14/4/19.
 */
public class ViewImageActivity extends AppCompatActivity {

    private ImageView backImageView;
    private ViewPager viewImgViewPager;
    private List<PhotoGalleryImages> imagesGalleryPojoList;
    private int imagePosition;

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    private void initComponents(){
        generateId();
        registerEvents();
        initData();
    }

    protected void generateId() {

        setContentView(R.layout.view_photo_activity);
        viewImgViewPager = findViewById(R.id.view_img_vp);
        backImageView = findViewById(R.id.view_img_back_iv);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    protected void registerEvents() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewImageActivity.this.finish();
            }
        });
    }

    protected void initData() {

        Bundle bundle = getIntent().getExtras();
        imagePosition = bundle.getInt(AUtils.GALLERY_IMG_POSITION);
        imagesGalleryPojoList = (List<PhotoGalleryImages>) bundle.getSerializable(AUtils.GALLERY_IMG_POJO_LIST);

        ViewImagePagerAdapter viewImagePagerAdapter = new ViewImagePagerAdapter(this, imagesGalleryPojoList);
        viewImgViewPager.setAdapter(viewImagePagerAdapter);
        viewImgViewPager.setCurrentItem(imagePosition, true);
        viewImgViewPager.setPageTransformer(false, new ViewPagerTransformer(ViewPagerTransformer.TransformType.ZOOM));
    }

}
