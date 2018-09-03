package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;
import com.mithsoft.lib.zoomimageview.MyZoomageView;

import java.util.List;


/**
 * Created by MiTHUN on 11/5/18.
 */
public class ViewImagePagerAdapter extends android.support.v4.view.PagerAdapter {

    private List<PhotoGalleryImages> imagesGalleryPojoList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public ViewImagePagerAdapter(Context context, List<PhotoGalleryImages> imagesGalleryPojoList) {

        this.context = context;
        this.imagesGalleryPojoList = imagesGalleryPojoList;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        if (!AUtils.isNull(imagesGalleryPojoList)) {

            return imagesGalleryPojoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mLayoutInflater.inflate(R.layout.view_image_adapter, container, false);

        MyZoomageView photoImageView = view.findViewById(R.id.view_photo_adp_iv);

        Glide.with(context).load(imagesGalleryPojoList.get(position).getImageUrl())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.no_image)
                .into(photoImageView);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
