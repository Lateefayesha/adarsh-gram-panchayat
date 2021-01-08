package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.WorkCheckOutPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class WorkChechOutListAdapter extends ArrayAdapter<WorkCheckOutPojo> {

    private List<WorkCheckOutPojo> workCheckOutPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public WorkChechOutListAdapter(Context context, List<WorkCheckOutPojo> workCheckOutPojoList) {

        super(context, android.R.layout.simple_list_item_1, workCheckOutPojoList);
        this.context = context;
        this.workCheckOutPojoList = workCheckOutPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.work_check_out_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.work_check_out_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.work_check_out_adp_desc);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(workCheckOutPojoList) && !workCheckOutPojoList.isEmpty()) {
            WorkCheckOutPojo workCheckOutPojo = workCheckOutPojoList.get(position);

            if (!AUtils.isNullString(workCheckOutPojo.getTitle())) {
                holder.titleTextView.setText(workCheckOutPojo.getTitle());
            } else {
                holder.titleTextView.setText("");
            }
            if (!AUtils.isNullString(workCheckOutPojo.getDescription())) {
                holder.descTextView.setText(workCheckOutPojo.getDescription());
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