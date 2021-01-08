package com.appynitty.gp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.ViewImageActivity;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richali Pradhan Gupte on 13-10-2018.
 */
public class ClassificationAdapter extends PagerAdapter {

    Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> mUrl;
    private String imgSize;

    public ClassificationAdapter(Context ctx, List<String> url) {
        this.context = ctx;
        this.mUrl = url;
        this.mLayoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgSize = "large";
    }

    public ClassificationAdapter(Context ctx, List<String> url, String size) {
        this.context = ctx;
        this.mUrl = url;
        this.mLayoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imgSize = size;
    }

    public int getCount() {
        return this.mUrl.size();
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = this.mLayoutInflater.inflate(R.layout.classification_adapter_layout, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        int errorImg;
        int loadingImg;
        String urlPath = this.mUrl.get(position);

        switch (imgSize){
            case "large":
                errorImg = R.drawable.img_error_loading;
                loadingImg = R.drawable.img_loading;
                break;
            case "medium":
                errorImg = R.drawable.error_image;
                loadingImg = R.drawable.loading_image;
                break;
            case "small":
                errorImg = R.drawable.img_error_loading_small;
                loadingImg = R.drawable.img_loading_small;
                break;
            default:
                errorImg = R.drawable.img_error_loading;
                loadingImg = R.drawable.img_loading;
                break;
        }
        Glide.with(context).load(urlPath)
                .placeholder(loadingImg)
                .error(errorImg)
                .into(imageView);

        viewImageOnClick(imageView, urlPath);

        //imageView.setImageURI(Uri.parse(this.mUrl.get(position)));
        container.addView(itemView);
        return itemView;
    }

    private void viewImageOnClick(ImageView imageView, final String imgUrl){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<PhotoGalleryImages> imagesPojoList = new ArrayList<PhotoGalleryImages>();

                PhotoGalleryImages photoGalleryImages = new PhotoGalleryImages();
                photoGalleryImages.setImageUrl(imgUrl);

                imagesPojoList.add(photoGalleryImages);

                Intent intent = new Intent(context, ViewImageActivity.class);
                intent.putExtra(AUtils.GALLERY_IMG_POSITION, 0);
                intent.putExtra(AUtils.GALLERY_IMG_POJO_LIST, (Serializable) imagesPojoList);
                context.startActivity(intent);
            }
        });
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
