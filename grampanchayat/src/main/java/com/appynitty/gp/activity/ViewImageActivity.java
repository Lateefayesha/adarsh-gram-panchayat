package com.appynitty.gp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.ViewImagePagerAdapter;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.viewpager.ViewPagerTransformer;

import java.util.List;

/**
 * Created by MiTHUN on 7/5/18.
 */
public class ViewImageActivity extends BaseActivity {

    private ImageView backImageView;
    private ViewPager viewImgViewPager;
    private List<PhotoGalleryImages> imagesGalleryPojoList;
    private int imagePosition;

    @Override
    protected void generateId() {

        setContentView(R.layout.view_photo_activity);
        viewImgViewPager = findViewById(R.id.view_img_vp);
        backImageView = findViewById(R.id.view_img_back_iv);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void registerEvents() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewImageActivity.this.finish();
            }
        });
    }

    @Override
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
