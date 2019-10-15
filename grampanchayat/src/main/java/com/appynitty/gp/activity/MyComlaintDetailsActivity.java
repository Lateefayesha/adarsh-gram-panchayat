package com.appynitty.gp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.appynitty.gp.R;
import com.appynitty.gp.controller.BaseActivity;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiTHUN on 17/8/18.
 */
public class MyComlaintDetailsActivity extends BaseActivity {

    private ComplentStatusPojo complentStatusPojo;
    private TextView refrenceIdTextView;
    private TextView dateTextView;
    private TextView statusTextView;
    private TextView wardNoTextView;
    private TextView loactionTextView;
    private TextView complaintTextView;
    private TextView tipsTextView;
    private TextView commentsTextView;
    private ImageView startImageView;
    private ImageView endImageView;

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void generateId() {

        setContentView(R.layout.my_complaint_details);

        complentStatusPojo = (ComplentStatusPojo) getIntent().getSerializableExtra(AUtils.COMPLAINT_STATUS_POJO);

        initToolbar();

        refrenceIdTextView = findViewById(R.id.complent_details_refrence_id_tv);
        dateTextView = findViewById(R.id.complent_details_date_id_tv);
        statusTextView = findViewById(R.id.complent_details_status_tv);
        wardNoTextView = findViewById(R.id.complent_details_ward_no_tv);
        loactionTextView = findViewById(R.id.complent_details_location_tv);
        complaintTextView = findViewById(R.id.complent_details_complaint_tv);
        tipsTextView = findViewById(R.id.complent_details_tips_tv);
        commentsTextView = findViewById(R.id.complent_details_comment_tv);
        startImageView = findViewById(R.id.start_iv);
        endImageView = findViewById(R.id.end_iv);
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (!AUtils.isNull(complentStatusPojo.getComplaintType())) {

            toolbar.setTitle(complentStatusPojo.getComplaintType() + " " + getString(R.string.details));
        } else {
            toolbar.setTitle(getString(R.string.details));
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    @Override
    protected void registerEvents() {

        startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startImageViewOnClick();
            }
        });

        endImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                endImageViewOnClick();
            }
        });
    }

    private void endImageViewOnClick() {

        if (!AUtils.isNull(complentStatusPojo.getEndImage())) {

            List<PhotoGalleryImages> imagesPojoList = new ArrayList<PhotoGalleryImages>();

            PhotoGalleryImages photoGalleryImages = new PhotoGalleryImages();
            photoGalleryImages.setImageUrl(complentStatusPojo.getEndImage());

            imagesPojoList.add(photoGalleryImages);

            Intent intent = new Intent(this, ViewImageActivity.class);
            intent.putExtra(AUtils.GALLERY_IMG_POSITION, 0);
            intent.putExtra(AUtils.GALLERY_IMG_POJO_LIST, (Serializable) imagesPojoList);
            startActivity(intent);
        }
    }

    private void startImageViewOnClick() {

        if (!AUtils.isNull(complentStatusPojo.getStartImage())) {

            List<PhotoGalleryImages> imagesPojoList = new ArrayList<PhotoGalleryImages>();

            PhotoGalleryImages photoGalleryImages = new PhotoGalleryImages();
            photoGalleryImages.setImageUrl(complentStatusPojo.getStartImage());

            imagesPojoList.add(photoGalleryImages);

            Intent intent = new Intent(this, ViewImageActivity.class);
            intent.putExtra(AUtils.GALLERY_IMG_POSITION, 0);
            intent.putExtra(AUtils.GALLERY_IMG_POJO_LIST, (Serializable) imagesPojoList);
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {

        if (!AUtils.isNullString(complentStatusPojo.getRefId())) {
            refrenceIdTextView.setText(getString(R.string.grievance_id) + " : " + complentStatusPojo.getRefId());
        } else {
            refrenceIdTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getCreatedDate())) {
            dateTextView.setText(getString(R.string.date) + " : " + complentStatusPojo.getCreatedDate());
        } else {
            dateTextView.setVisibility(View.GONE);
        }
//        dateTextView.setText("Date : " + complentStatusPojo.getCreatedDate());
        if (!AUtils.isNullString(complentStatusPojo.getStatus())) {

            switch (complentStatusPojo.getStatus()) {

                case "Pending":
                    statusTextView.setTextColor(getResources().getColor(R.color.pending));
                    statusTextView.setText(getString(R.string.pending));
                    break;

                case "Processing":
                    statusTextView.setTextColor(getResources().getColor(R.color.processing));
                    statusTextView.setText(getString(R.string.processing));
                    break;

                case "Resolved":
                    statusTextView.setTextColor(getResources().getColor(R.color.accepeted));
                    statusTextView.setText(getString(R.string.resolved));
                    break;

                case "Rejected":
                    statusTextView.setTextColor(getResources().getColor(R.color.rejeceted));
                    statusTextView.setText(getString(R.string.rejected));
                    break;
            }
        } else {
            statusTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getWardNo())) {
            wardNoTextView.setText(getString(R.string.ward_no) + " : " + complentStatusPojo.getWardNo());
        } else {
            wardNoTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getPlace())) {
            loactionTextView.setText(getString(R.string.place) + " : " + complentStatusPojo.getPlace());
        } else {
            loactionTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getDetails())) {
            complaintTextView.setText(getString(R.string.grievance) + " : " + complentStatusPojo.getDetails());
        } else {
            complaintTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getTips())) {
            tipsTextView.setText(getString(R.string.tip) + " : " + complentStatusPojo.getTips());
        } else {
            tipsTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getComment())) {
            commentsTextView.setText("" + complentStatusPojo.getComment());
        } else {
            commentsTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getStartImage())) {

            Glide.with(this).load(complentStatusPojo.getStartImage())
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.no_image)
                    .into(startImageView);
        } else {

            startImageView.setBackgroundResource(R.drawable.no_image);
        }

        if (!AUtils.isNullString(complentStatusPojo.getEndImage())) {

            Glide.with(this).load(complentStatusPojo.getEndImage())
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.image_load_error)
                    .into(endImageView);
        } else {

            endImageView.setBackgroundResource(R.drawable.no_image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        AUtils.changeLanguage(this, Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID));
        super.onDestroy();
    }
}
