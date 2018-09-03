package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.PhotoGalleryVideo;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class VedioGalleryAdapter extends ArrayAdapter<PhotoGalleryVideo> {

    private List<PhotoGalleryVideo> photoGalleryImagesList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public VedioGalleryAdapter(Context context, List<PhotoGalleryVideo> photoGalleryImagesList) {

        super(context, android.R.layout.simple_list_item_1, photoGalleryImagesList);
        this.context = context;
        this.photoGalleryImagesList = photoGalleryImagesList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photo_gallery_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.gallery_img);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(photoGalleryImagesList) && !photoGalleryImagesList.isEmpty()) {
            PhotoGalleryVideo photoGalleryVideo = photoGalleryImagesList.get(position);

            if (!AUtils.isNull(photoGalleryVideo.getVideoUrl())) {

                Glide.with(context).load("http://img.youtube.com/vi/" + AUtils.getYoutubeVedioId(photoGalleryVideo.getVideoUrl()) + "/0.jpg")
                        .placeholder(R.drawable.loading_image)
                        .error(R.drawable.no_image)
                        .into(holder.imageView);
            }
        }
        return view;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}