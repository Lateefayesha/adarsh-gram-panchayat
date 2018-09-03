package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class MyComplentStatusListAdapter extends ArrayAdapter<ComplentStatusPojo> {

    private List<ComplentStatusPojo> complentStatusPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public MyComplentStatusListAdapter(Context context, List<ComplentStatusPojo> complentStatusPojoList) {

        super(context, android.R.layout.simple_list_item_1, complentStatusPojoList);
        this.context = context;
        this.complentStatusPojoList = complentStatusPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_complent_status_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.typeTextView = view.findViewById(R.id.my_complent_adp_type_tv);
            viewHolder.refrenceIdTextView = view.findViewById(R.id.my_complent_adp_refrence_id_tv);
            viewHolder.statusTextView = view.findViewById(R.id.my_complent_adp_status_tv);
            viewHolder.dateTextView = view.findViewById(R.id.my_complent_adp_date_tv);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {
            ComplentStatusPojo complentStatusPojo = complentStatusPojoList.get(position);

            if (!AUtils.isNullString(complentStatusPojo.getComplaintType())) {
                holder.typeTextView.setText(complentStatusPojo.getComplaintType());
            } else {
                holder.typeTextView.setText("");
            }
            if (!AUtils.isNullString(complentStatusPojo.getRefId())) {
                holder.refrenceIdTextView.setText("Grievance Id : " + complentStatusPojo.getRefId());
            } else {
                holder.refrenceIdTextView.setText("");
            }
            if (!AUtils.isNullString(complentStatusPojo.getStatus())) {

                switch (complentStatusPojo.getStatus()) {

                    case "Pending":
                        holder.statusTextView.setTextColor(context.getResources().getColor(R.color.pending));
                        break;

                    case "Processing":
                        holder.statusTextView.setTextColor(context.getResources().getColor(R.color.processing));
                        break;

                    case "Resolved":
                        holder.statusTextView.setTextColor(context.getResources().getColor(R.color.accepeted));
                        break;

                    case "Rejected":
                        holder.statusTextView.setTextColor(context.getResources().getColor(R.color.rejeceted));
                        break;
                }
                holder.statusTextView.setText(complentStatusPojo.getStatus());
            } else {
                holder.statusTextView.setText("");
            }
            if (!AUtils.isNullString(complentStatusPojo.getStatus())) {
                holder.dateTextView.setText("Date : " + complentStatusPojo.getCreatedDate());
            } else {
                holder.dateTextView.setText("");
            }
        }
        return view;
    }

    class ViewHolder {
        private TextView typeTextView;
        private TextView refrenceIdTextView;
        private TextView statusTextView;
        private TextView dateTextView;
    }
}