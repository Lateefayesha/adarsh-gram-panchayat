package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.UpcomingEventsPojo;
import com.appynitty.gp.utils.AUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import quickutils.core.QuickUtils;

/**
 * Created by Richali Pradhan Gupte on 05-10-2018.
 */
public class UpcomingEventsListAdapter extends ArrayAdapter<UpcomingEventsPojo> {

    private List<UpcomingEventsPojo> upcomingEventsPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public UpcomingEventsListAdapter(Context context, List<UpcomingEventsPojo> upcomingEventsPojoList) {

        super(context, android.R.layout.simple_list_item_1, upcomingEventsPojoList);
        this.context = context;
        this.upcomingEventsPojoList = upcomingEventsPojoList;
    }


    class ViewHolder {

        private ImageView eventImageView;
        private TextView eventTitleTextView;
        private TextView eventDateTextView;
        private TextView eventDescTextView;
        private TextView eventTimeTextView;
        private TextView eventAddressTextView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.upcoming_event_adapter_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.eventImageView = view.findViewById(R.id.upcoming_adp_Image);
            viewHolder.eventTitleTextView = view.findViewById(R.id.upcoming_adp_txt_event_title);
            viewHolder.eventDescTextView = view.findViewById(R.id.upcoming_adp_txt_event_details);
            viewHolder.eventDateTextView = view.findViewById(R.id.upcoming_adp_txt_event_date);
            viewHolder.eventTimeTextView = view.findViewById(R.id.upcoming_adp_txt_event_time);
            viewHolder.eventAddressTextView = view.findViewById(R.id.upcoming_adp_txt_event_address);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(upcomingEventsPojoList) && !upcomingEventsPojoList.isEmpty()) {
            UpcomingEventsPojo upcomingEventsPojo = upcomingEventsPojoList.get(position);

            if (!AUtils.isNullString(upcomingEventsPojo.getImageUrl())) {

                Glide.with(context)
                        .load(upcomingEventsPojo.getImageUrl())
                        .placeholder(null)
                        .error(null)
                        .into(holder.eventImageView);
            } else {
                holder.eventImageView.setBackground(null);
            }

            if (!AUtils.isNullString(upcomingEventsPojo.getSubject())) {
                holder.eventTitleTextView.setText(upcomingEventsPojo.getSubject());
            } else {
                holder.eventTitleTextView.setText("");
            }

            if (!AUtils.isNullString(upcomingEventsPojo.getDate())) {

                if(!AUtils.isNullString(upcomingEventsPojo.getEnddate()))
                {
                    holder.eventDateTextView.setText(AUtils.getEventDisplayDate(upcomingEventsPojo.getDate()) + "\nTo\n "
                            + AUtils.getEventDisplayDate(upcomingEventsPojo.getEnddate()));
                } else {
                    holder.eventDateTextView.setText(AUtils.getEventDisplayDate(upcomingEventsPojo.getDate()));
                }
            } else {
                holder.eventDateTextView.setText("");
            }

            if (!AUtils.isNullString(upcomingEventsPojo.getTime())) {

                holder.eventTimeTextView.setText(upcomingEventsPojo.getTime());
            } else {
                holder.eventTimeTextView.setText("");
            }

            if (!AUtils.isNullString(upcomingEventsPojo.getDescription())) {
                holder.eventDescTextView.setText(upcomingEventsPojo.getDescription());
            } else {
                holder.eventDescTextView.setText("");
            }

            if (!AUtils.isNullString(upcomingEventsPojo.getLocation())) {
                holder.eventAddressTextView.setText(upcomingEventsPojo.getLocation());
            } else {
                holder.eventAddressTextView.setText("");
            }
        }
        return view;
    }
}
