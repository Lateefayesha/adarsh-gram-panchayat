package com.appynitty.gp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appynitty.gp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Richali Pradhan Gupte on 13-10-2018.
 */
public class ClassificationAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    List<String> mUrl;

    public ClassificationAdapter(Context ctx, List<String> url) {
        this.context = ctx;
        this.mUrl = url;
        this.mLayoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.mUrl.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = this.mLayoutInflater.inflate(R.layout.classification_adapter_layout, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        Glide.with(context).load(this.mUrl.get(position))
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.image_load_error)
                .into(imageView);

        //imageView.setImageURI(Uri.parse(this.mUrl.get(position)));
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
