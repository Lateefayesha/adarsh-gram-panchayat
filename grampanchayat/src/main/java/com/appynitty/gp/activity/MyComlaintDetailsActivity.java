package com.appynitty.gp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;
import com.mithsoft.lib.activity.BaseActivity;

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

            toolbar.setTitle(complentStatusPojo.getComplaintType() + " Details");
        } else {
            toolbar.setTitle("Complaint Details");
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
            refrenceIdTextView.setText("Reference Id : " + complentStatusPojo.getRefId());
        } else {
            refrenceIdTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getCreatedDate())) {
            dateTextView.setText("Date : " + complentStatusPojo.getCreatedDate());
        } else {
            dateTextView.setVisibility(View.GONE);
        }
//        dateTextView.setText("Date : " + complentStatusPojo.getCreatedDate());
        if (!AUtils.isNullString(complentStatusPojo.getStatus())) {
            statusTextView.setText("" + complentStatusPojo.getStatus());
        } else {
            statusTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getWardNo())) {
            wardNoTextView.setText("Ward No : " + complentStatusPojo.getWardNo());
        } else {
            wardNoTextView.setVisibility(View.GONE);
        }
        if (!AUtils.isNullString(complentStatusPojo.getPlace())) {
            loactionTextView.setText("Location : " + complentStatusPojo.getPlace());
        } else {
            loactionTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getDetails())) {
            complaintTextView.setText("Complaint : " + complentStatusPojo.getDetails());
        } else {
            complaintTextView.setVisibility(View.GONE);
        }

        if (!AUtils.isNullString(complentStatusPojo.getTips())) {
            tipsTextView.setText("Tips : " + complentStatusPojo.getTips());
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
                    .error(R.drawable.image_load_error)
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
}
