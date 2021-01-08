package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.YoungBusinessPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class YoungBusinessListAdapter extends ArrayAdapter<YoungBusinessPojo> {

    private List<YoungBusinessPojo> youngBusinessPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public YoungBusinessListAdapter(Context context, List<YoungBusinessPojo> youngBusinessPojoList) {

        super(context, android.R.layout.simple_list_item_1, youngBusinessPojoList);
        this.context = context;
        this.youngBusinessPojoList = youngBusinessPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.youn_business_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.youn_business_out_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.youn_business_out_adp_desc);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {
            YoungBusinessPojo youngBusinessPojo = youngBusinessPojoList.get(position);

            if (!AUtils.isNullString(youngBusinessPojo.getTitle())) {
                holder.titleTextView.setText(youngBusinessPojo.getTitle());
            } else {
                holder.titleTextView.setText("");
            }
            if (!AUtils.isNullString(youngBusinessPojo.getDetails())) {
                holder.descTextView.setText(youngBusinessPojo.getDetails());
            } else {
                holder.descTextView.setText("");
            }
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
    }
}