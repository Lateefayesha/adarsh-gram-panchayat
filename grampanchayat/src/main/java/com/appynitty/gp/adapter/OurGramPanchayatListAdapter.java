package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by MiTHUN on 5/3/18.
 */

public class OurGramPanchayatListAdapter extends ArrayAdapter<OurGramPanchayatPojo> {

    private List<OurGramPanchayatPojo> ourGramPanchayatPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public OurGramPanchayatListAdapter(Context context, List<OurGramPanchayatPojo> ourGramPanchayatPojoList) {

        super(context, android.R.layout.simple_list_item_1, ourGramPanchayatPojoList);
        this.context = context;
        this.ourGramPanchayatPojoList = ourGramPanchayatPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.our_grampanchyat_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.our_gp_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.our_gp_adp_desc);
            viewHolder.personImageView = view.findViewById(R.id.our_gp_adp_image);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(ourGramPanchayatPojoList) && !ourGramPanchayatPojoList.isEmpty()) {
            OurGramPanchayatPojo ourGramPanchayatPojo = ourGramPanchayatPojoList.get(position);

            if (!AUtils.isNullString(ourGramPanchayatPojo.getImageUrl())) {

                holder.personImageView.setVisibility(View.VISIBLE);
                holder.titleTextView.setGravity(Gravity.CENTER);
                Glide.with(context).load(ourGramPanchayatPojo.getImageUrl())
                        .placeholder(R.drawable.loading_image)
                        .error(R.drawable.no_image)
                        .into(holder.personImageView);
            } else {
                holder.titleTextView.setGravity(Gravity.LEFT);
                holder.personImageView.setVisibility(View.GONE);
            }
            if (!AUtils.isNullString(ourGramPanchayatPojo.getTitle())) {
                holder.titleTextView.setText(ourGramPanchayatPojo.getTitle());
            } else {
                holder.titleTextView.setText("");
            }
            if (!AUtils.isNullString(ourGramPanchayatPojo.getDescription())) {
                holder.descTextView.setText(ourGramPanchayatPojo.getDescription());
            } else {
                holder.descTextView.setText("");
            }


//            } else {
//
//                holder.personImageView.setVisibility(View.GONE);
//
//                if (!AUtils.isNullString(ourGramPanchayatPojo.getTitle())) {
//                    holder.titleTextView.setText(ourGramPanchayatPojo.getTitle());
//                } else {
//                    holder.titleTextView.setText("");
//                }
//                if (!AUtils.isNullString(ourGramPanchayatPojo.getDescription())) {
//                    holder.descTextView.setText(ourGramPanchayatPojo.getDescription());
//                } else {
//                    holder.descTextView.setText("");
//                }
//            }
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
        private ImageView personImageView;
    }
}